package sentinel

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kase.response.getOrThrow
import koncurrent.Later
import koncurrent.later
import sentinel.params.EmailSignUpParams
import sentinel.params.EmailVerificationParams
import sentinel.params.SendVerificationLinkParams
import sentinel.params.UserAccountParams

class EmailRegistrationScopedApiFlix(
    private val config: EmailRegistrationScopedApiFlixOptions
) : EmailRegistrationApi {

    private val client = config.http
    private val endpoint = config.endpoint
    private val codec = config.codec
    private val logger by config.logger
    private val actions by lazy { EmailRegistrationActionMessage() }
    private val resolver = config.resolver

    fun HttpRequestBuilder.header(resolver: Resolver?) {
        if (resolver == null) return
        header(resolver.key, resolver.value)
    }

    override fun signUp(params: EmailSignUpParams): Later<EmailSignUpParams> = config.scope.later {
        val tracer = logger.trace(actions.signUp(params.email))
        client.post(endpoint.signUp()) {
            header(resolver)
            setBody(codec.encodeToString(EmailSignUpParams.serializer(), params))
        }.getOrThrow<EmailSignUpParams>(codec, tracer)
    }

    override fun sendVerificationLink(email: String): Later<String> = config.scope.later {
        val tracer = logger.trace(actions.sendVerificationLink(email))
        val params = SendVerificationLinkParams(email, config.link, config.meta)
        client.post(endpoint.sendEmailVerificationLink()) {
            header(resolver)
            setBody(codec.encodeToString(SendVerificationLinkParams.serializer(), params))
        }.getOrThrow<String>(codec, tracer)
    }

    override fun verify(params: EmailVerificationParams): Later<EmailVerificationParams> = config.scope.later {
        val tracer = logger.trace(actions.verify(params.email))
        client.post(endpoint.verifyEmail()) {
            header(resolver)
            setBody(codec.encodeToString(EmailVerificationParams.serializer(), params))
        }.getOrThrow(codec, tracer)
    }

    override fun abort(email: String): Later<String> = config.scope.later {
        val tracer = logger.trace(actions.abort(email))
        client.get(endpoint.abort(email)) {
            header(resolver)
        }.getOrThrow(codec, tracer)
    }

    override fun createUserAccount(params: UserAccountParams): Later<UserAccountParams> = config.scope.later {
        val tracer = logger.trace(actions.createAccount(params.loginId))
        client.post(endpoint.createAccount()) {
            header(resolver)
            setBody(codec.encodeToString(UserAccountParams.serializer(), params))
        }.getOrThrow(codec, tracer)
    }
}