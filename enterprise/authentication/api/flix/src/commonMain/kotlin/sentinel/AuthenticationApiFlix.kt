package sentinel

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kase.response.getOrThrow
import koncurrent.Later
import koncurrent.later
import koncurrent.later.await
import sentinel.params.PasswordResetParams
import sentinel.params.SendPasswordResetParams
import sentinel.params.SessionParams
import sentinel.params.SignInParams

class AuthenticationApiFlix(
    private val options: AuthenticationApiFlixOptions
) : AuthenticationApi {

    private val client = options.http
    private val endpoint = options.endpoint
    private val codec = options.codec
    private val logger by options.logger
    private val cache = options.cache
    private val actions by lazy { AuthenticationActionMessage() }

    companion object {
        const val KEY_SESSION = "authentication.session"
    }

    override fun signIn(params: SignInParams): Later<UserSession> = options.scope.later {
        val action = actions.signIn(params.email)
        logger.info(action.begin)
        val res = client.post(endpoint.signIn()) {
            setBody(codec.encodeToString(SignInParams.serializer(), params))
        }

        logger.info("Got response back")

        res.getOrThrow<UserSession>(codec, logger, action.begin).also {
            cache.save(KEY_SESSION, it, UserSession.serializer()).await()
        }
    }

    override fun session(): Later<UserSession> = options.scope.later {
        val action = actions.session()
        logger.info(action.begin)
        val session = cache.load(KEY_SESSION, UserSession.serializer()).await()
        client.post(endpoint.session()) {
            setBody(codec.encodeToString(SessionParams.serializer(), SessionParams(session.secret)))
        }.getOrThrow(codec, logger, action.begin)
    }

    override fun signOut(): Later<Unit> = cache.remove(KEY_SESSION).then { Unit }

    override fun sendPasswordResetLink(email: String): Later<String> = options.scope.later {
        val action = actions.sendPasswordResetLink(email)
        logger.info(action.begin)
        client.post(endpoint.sendPasswordResetLink()) {
            setBody(codec.encodeToString(SendPasswordResetParams.serializer(), SendPasswordResetParams(email, options.link)))
        }.getOrThrow(codec, logger, action.begin)
    }

    override fun resetPassword(params: PasswordResetParams): Later<PasswordResetParams> = options.scope.later {
        val action = actions.resetPassword(params.passwordResetToken)
        logger.info(action.begin)
        client.post(endpoint.resetPassword()) {
            setBody(codec.encodeToString(PasswordResetParams.serializer(), params))
        }.getOrThrow(codec, logger, action.begin)
    }
}