package sentinel

import koncurrent.Later

interface EmailAuthenticationApi : AuthenticationApi, EmailAuthenticationScheme {
    fun sendPasswordResetLink(email: String): Later<String>
}