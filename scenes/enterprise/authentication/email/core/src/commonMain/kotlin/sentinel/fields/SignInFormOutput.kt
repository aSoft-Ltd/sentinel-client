package sentinel.fields

import sentinel.params.EmailSignInParams

data class SignInFormOutput(
    var email: String? = "",
    var password: String? = "",
) {
    fun toParams() = EmailSignInParams(
        email = email ?: throw IllegalArgumentException("Email must not be null"),
        password = password ?: throw IllegalArgumentException("password must not be null"),
    )
}