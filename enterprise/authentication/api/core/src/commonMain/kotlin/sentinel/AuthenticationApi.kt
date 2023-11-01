@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import koncurrent.Later
import kotlin.js.JsExport

interface AuthenticationApi : AuthenticationScheme {
    fun session(): Later<UserSession>
    fun signOut(): Later<Unit>
    fun sendPasswordResetLink(email: String): Later<String>
}