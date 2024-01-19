@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import kotlinx.JsExport

class RegistrationScenes(options: RegistrationSceneOptions<EmailRegistrationApi>) {
    val signUp by lazy { SignUpScene(options) }
    val verification by lazy { VerificationScene(options) }
    val password by lazy { SetPasswordScene(options) }
}