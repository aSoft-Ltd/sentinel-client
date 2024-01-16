@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import koncurrent.Later
import kotlinx.JsExport

interface RegistrationApi : RegistrationScheme {
    fun sendVerificationLink(email: String): Later<String>
}