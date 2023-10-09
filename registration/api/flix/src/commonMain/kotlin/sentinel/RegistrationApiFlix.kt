package sentinel

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kase.response.getOrThrow
import koncurrent.Later
import koncurrent.later
import sentinel.params.SendVerificationLinkParams
import sentinel.params.SignUpParams
import sentinel.params.UserAccountParams
import sentinel.params.VerificationParams

class RegistrationApiFlix(
    private val config: RegistrationFlixApiConfig
) : RegistrationApi {

    private val client = config.client
    private val endpoint = config.endpoint
    private val codec = config.codec
    private val logger by config.logger
    private val actions by lazy { RegistrationActionMessage() }

    override fun signUp(params: SignUpParams): Later<SignUpParams> = config.scope.later {
        val action = actions.signUp(params.email)
        logger.info(action.begin)
        client.post(endpoint.signUp()) {
            setBody(codec.encodeToString(SignUpParams.serializer(), params))
        }.getOrThrow<SignUpParams>(codec, logger, action.begin)
    }

    override fun sendVerificationLink(email: String): Later<String> = config.scope.later {
        val action = "Sending email verification to $email"
        logger.info(action)
        client.post(endpoint.sendEmailVerificationLink()) {
            val params = SendVerificationLinkParams(email, config.link)
            setBody(codec.encodeToString(SendVerificationLinkParams.serializer(), params))
        }.getOrThrow<String>(codec, logger, action)
    }

    override fun verify(params: VerificationParams): Later<VerificationParams> = config.scope.later {
        val action = "Verifying ${params.email}"
        logger.info(action)
        client.post(endpoint.verifyEmail()) {
            setBody(codec.encodeToString(VerificationParams.serializer(), params))
        }.getOrThrow(codec, logger, action)
    }

    override fun createUserAccount(params: UserAccountParams): Later<UserAccountParams> = config.scope.later {
        val action = "Creating user account for ${params.loginId}"
        logger.info(action)
        client.post(endpoint.createAccount()) {
            setBody(codec.encodeToString(UserAccountParams.serializer(), params))
        }.getOrThrow(codec, logger, action)
    }
}