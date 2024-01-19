@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import cinematic.Scene
import kase.LazyState
import kase.Pending
import kotlinx.JsExport
import sentinel.fields.SignInFields
import symphony.toForm

class SignInScene(options: AuthenticationSceneOptions<EmailAuthenticationApi>) : Scene<LazyState<UserSession>>(Pending) {

    private val api = options.api
    private var successHandler: ((UserSession) -> Unit)? = null

    val form = SignInFields().toForm(
        heading = "Sign In",
        details = "Log in to your space",
        logger = options.logger
    ) {
        onSubmit { api.signIn(it.toParams()) }
        onSuccess { successHandler?.invoke(it) }
    }

    fun initialize(onSuccess: (UserSession) -> Unit) {
        successHandler = onSuccess
    }

    fun deInitialize() {
        form.exit()
        successHandler = null
        ui.value = Pending
    }
}