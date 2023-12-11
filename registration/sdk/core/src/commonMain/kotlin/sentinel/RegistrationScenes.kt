@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import kotlinx.JsExport

interface RegistrationScenes {
    val signUp: SignUpScene
    val verification: VerificationScene
    val password: SetPasswordScene
}