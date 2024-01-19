@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import kotlinx.JsExport

class AuthenticationScenes(options: AuthenticationSceneOptions) {
    val signIn by lazy { SignInScene(options) }
    val barrier by lazy { ProtectedScene(options) }
    val registration by lazy { RegistrationScene(options) }
    val password by lazy { PasswordScenes(options) }
}