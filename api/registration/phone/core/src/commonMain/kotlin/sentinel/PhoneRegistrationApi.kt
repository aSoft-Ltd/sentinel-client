@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import koncurrent.Later
import kotlinx.JsExport

interface PhoneRegistrationApi : RegistrationApi, PhoneRegistrationScheme {
    fun sendVerificationLink(email: String): Later<String>
}