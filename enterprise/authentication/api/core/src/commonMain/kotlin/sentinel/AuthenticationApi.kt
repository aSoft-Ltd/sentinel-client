@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import kotlinx.JsExport

interface AuthenticationApi : AuthenticationScheme {
    fun session(): Later<UserSession>
    fun signOut(): Later<Unit>
    fun sendPasswordResetLink(email: String): Later<String>
}