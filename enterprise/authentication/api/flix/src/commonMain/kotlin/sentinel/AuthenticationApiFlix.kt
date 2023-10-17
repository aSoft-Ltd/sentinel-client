package sentinel

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kase.response.getOrThrow
import koncurrent.Later
import koncurrent.TODOLater
import koncurrent.later
import sentinel.params.PasswordResetParams
import sentinel.params.SignInParams

class AuthenticationApiFlix(
    private val config: AuthenticationApiFlixOptions
) : AuthenticationApi {

    private val client = config.client
    private val endpoint = config.endpoint
    private val codec = config.codec
    private val logger by config.logger
    private val actions by lazy { AuthenticationActionMessage() }
    override fun signIn(params: SignInParams): Later<UserSession> = config.scope.later {
        val action = actions.signIn(params.email)
        logger.info(action.begin)
        client.post(endpoint.signIn()) {
            setBody(codec.encodeToString(SignInParams.serializer(), params))
        }.getOrThrow<UserSession>(codec, logger, action.begin)
    }

    override fun session(): Later<UserSession> = TODOLater()

    override fun signOut(): Later<Unit> = TODOLater()

    override fun sendPasswordResetLink(email: String): Later<String> = TODOLater()

    override fun resetPassword(params: PasswordResetParams): Later<PasswordResetParams> = TODOLater()
}