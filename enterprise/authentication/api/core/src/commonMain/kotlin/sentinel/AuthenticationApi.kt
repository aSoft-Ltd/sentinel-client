@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import koncurrent.Later
import kotlinx.JsExport

interface AuthenticationApi : AuthenticationScheme {
    fun session(): Later<UserSession>
    fun signOut(): Later<Unit>
    fun sendPasswordResetLink(email: String): Later<String>
}