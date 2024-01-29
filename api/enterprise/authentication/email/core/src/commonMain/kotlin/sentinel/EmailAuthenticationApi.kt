@file:JsExport
package sentinel

import koncurrent.Later
import kotlinx.JsExport

interface EmailAuthenticationApi : AuthenticationApi, EmailAuthenticationScheme {
    fun sendPasswordResetLink(email: String): Later<String>
}