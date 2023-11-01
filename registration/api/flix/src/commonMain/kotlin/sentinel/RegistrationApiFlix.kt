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
    private val config: RegistrationApiFlixOptions
) : RegistrationApi {

    private val client = config.http
    private val endpoint = config.endpoint
    private val codec = config.codec
    private val logger by config.logger
    private val actions by lazy { RegistrationActionMessage() }

    override fun signUp(params: SignUpParams): Later<SignUpParams> = config.scope.later {
        val tracer = logger.trace(actions.signUp(params.email))
        client.post(endpoint.signUp()) {
            setBody(codec.encodeToString(SignUpParams.serializer(), params))
        }.getOrThrow<SignUpParams>(codec, tracer)
    }

    override fun sendVerificationLink(email: String): Later<String> = config.scope.later {
        val tracer = logger.trace(actions.sendVerificationLink(email))
        client.post(endpoint.sendEmailVerificationLink()) {
            val params = SendVerificationLinkParams(email, config.link)
            setBody(codec.encodeToString(SendVerificationLinkParams.serializer(), params))
        }.getOrThrow<String>(codec, tracer)
    }

    override fun verify(params: VerificationParams): Later<VerificationParams> = config.scope.later {
        val tracer = logger.trace(actions.verify(params.email))
        client.post(endpoint.verifyEmail()) {
            setBody(codec.encodeToString(VerificationParams.serializer(), params))
        }.getOrThrow(codec, tracer)
    }

    override fun createUserAccount(params: UserAccountParams): Later<UserAccountParams> = config.scope.later {
        val tracer = logger.trace(actions.createAccount(params.loginId))
        client.post(endpoint.createAccount()) {
            setBody(codec.encodeToString(UserAccountParams.serializer(), params))
        }.getOrThrow(codec, tracer)
    }
}