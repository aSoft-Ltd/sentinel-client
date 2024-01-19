@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import kotlinx.JsExport

class PasswordScenes(options: AuthenticationSceneOptions) {
    val forgot by lazy { PasswordForgotScene(options) }
    val reset by lazy { PasswordResetScene(options) }

    internal companion object {
        val KEY_RESET_EMAIL = "sentinel.reset.email"
    }
}