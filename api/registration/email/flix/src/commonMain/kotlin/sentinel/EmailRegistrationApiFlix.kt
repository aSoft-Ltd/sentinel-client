package sentinel

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kase.response.getOrThrow
import koncurrent.Later
import koncurrent.later
import sentinel.params.EmailSignUpParams
import sentinel.params.EmailVerificationParams
import sentinel.params.SendVerificationLinkParams
import sentinel.params.UserAccountParams

class EmailRegistrationApiFlix(
    private val config: EmailRegistrationApiFlixOptions
) : EmailRegistrationApi {

    private val client = config.http
    private val endpoint = config.endpoint
    private val codec = config.codec
    private val logger by config.logger
    private val actions by lazy { EmailRegistrationActionMessage() }

    override fun signUp(params: EmailSignUpParams): Later<EmailSignUpParams> = config.scope.later {
        val tracer = logger.trace(actions.signUp(params.email))
        client.post(endpoint.signUp()) {
            setBody(codec.encodeToString(EmailSignUpParams.serializer(), params))
        }.getOrThrow<EmailSignUpParams>(codec, tracer)
    }

    override fun sendVerificationLink(email: String): Later<String> = config.scope.later {
        val tracer = logger.trace(actions.sendVerificationLink(email))
        client.post(endpoint.sendEmailVerificationLink()) {
            val params = SendVerificationLinkParams(email, config.link)
            setBody(codec.encodeToString(SendVerificationLinkParams.serializer(), params))
        }.getOrThrow<String>(codec, tracer)
    }

    override fun verify(params: EmailVerificationParams): Later<EmailVerificationParams> = config.scope.later {
        val tracer = logger.trace(actions.verify(params.email))
        client.post(endpoint.verifyEmail()) {
            setBody(codec.encodeToString(EmailVerificationParams.serializer(), params))
        }.getOrThrow(codec, tracer)
    }

    override fun createUserAccount(params: UserAccountParams): Later<UserAccountParams> = config.scope.later {
        val tracer = logger.trace(actions.createAccount(params.loginId))
        client.post(endpoint.createAccount()) {
            setBody(codec.encodeToString(UserAccountParams.serializer(), params))
        }.getOrThrow(codec, tracer)
    }
}