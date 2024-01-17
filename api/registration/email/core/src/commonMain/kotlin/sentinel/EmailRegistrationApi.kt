@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import koncurrent.Later
import kotlinx.JsExport

interface EmailRegistrationApi : RegistrationApi, EmailRegistrationScheme {
    fun sendVerificationLink(email: String): Later<String>
}