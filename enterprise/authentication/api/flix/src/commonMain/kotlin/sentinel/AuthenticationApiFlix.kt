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

    override fun signIn(params: SignInParams): Later<UserSession> = options.scope.later {
        val tracer = logger.trace(actions.signIn(params.email))
        val res = client.post(endpoint.signIn()) {
            setBody(codec.encodeToString(SignInParams.serializer(), params))
        }
        res.getOrThrow<UserSession>(codec, tracer).also {
            cache.save(options.sessionCacheKey, it, UserSession.serializer()).await()
        }
    }

    override fun session(): Later<UserSession> = options.scope.later {
        val tracer = logger.trace(actions.session())
        val session = cache.load(options.sessionCacheKey, UserSession.serializer()).await()
        client.post(endpoint.session()) {
            setBody(codec.encodeToString(SessionParams.serializer(), SessionParams(session.secret)))
        }.getOrThrow(codec, tracer)
    }

    override fun signOut(): Later<Unit> = cache.remove(options.sessionCacheKey).then { Unit }

    override fun sendPasswordResetLink(email: String): Later<String> = options.scope.later {
        val tracer = logger.trace(actions.sendPasswordResetLink(email))
        client.post(endpoint.sendPasswordResetLink()) {
            setBody(codec.encodeToString(SendPasswordResetParams.serializer(), SendPasswordResetParams(email, options.link)))
        }.getOrThrow(codec, tracer)
    }

    override fun resetPassword(params: PasswordResetParams): Later<PasswordResetParams> = options.scope.later {
        val tracer = logger.trace(actions.resetPassword(params.passwordResetToken))
        client.post(endpoint.resetPassword()) {
            setBody(codec.encodeToString(PasswordResetParams.serializer(), params))
        }.getOrThrow(codec, tracer)
    }
}