package sentinel

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kase.decodeResponseFromString
import koncurrent.Later
import koncurrent.later
import sentinel.params.SignUpParams
import sentinel.params.UserAccountParams
import sentinel.params.VerificationParams

class RegistrationApiFlix(
    private val config: RegistrationFlixApiConfig
) : RegistrationApi {

    private val client = config.client
    private val endpoint = config.endpoint
    private val codec = config.codec
    override fun signUp(params: SignUpParams): Later<SignUpParams> = config.scope.later {
        val res = client.post(endpoint.signUp()) {
            contentType(ContentType.Application.Json)
            setBody(codec.encodeToString(SignUpParams.serializer(), params))
        }.bodyAsText()
        codec.decodeResponseFromString<SignUpParams>(res).getOrThrow()
    }

    override fun sendVerificationLink(email: String): Later<String> {
        TODO("Not yet implemented")
    }

    override fun verify(params: VerificationParams): Later<VerificationParams> {
        TODO("Not yet implemented")
    }

    override fun createUserAccount(params: UserAccountParams): Later<UserAccountParams> {
        TODO("Not yet implemented")
    }
}