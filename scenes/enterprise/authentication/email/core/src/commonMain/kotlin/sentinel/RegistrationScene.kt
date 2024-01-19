@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import cinematic.LazyScene
import kase.Loading
import kase.Pending
import kase.Success
import kase.toLazyState
import koncurrent.Later
import koncurrent.later.andThen
import koncurrent.later.finally
import kotlinx.JsExport
import sentinel.tools.loadUserAccountParams
import sentinel.tools.removeUserAccountParams
import sentinel.transformers.toEmailSignInParams

class RegistrationScene(config: AuthenticationSceneOptions<EmailAuthenticationApi>) : LazyScene<UserSession>(Pending) {
    private val api = config.api
    private val cache = config.cache

    fun initialize(onSuccess: () -> Unit): Later<UserSession> {
        ui.value = Loading("Attempting to signing you in automatically, please wait . . . ")
        return api.signOut().andThen {
            cache.loadUserAccountParams()
        }.andThen {
            api.signIn(it.toEmailSignInParams())
        }.finally {
            ui.value = it.toLazyState()
            if (it is Success) {
                cache.removeUserAccountParams()
                onSuccess()
            }
        }
    }
}