@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import koncurrent.Later
import kotlinx.JsExport

interface AuthenticationApi : AuthenticationScheme {

    fun signOut(): Later<UserSession>
    fun session(): Later<UserSession>
}