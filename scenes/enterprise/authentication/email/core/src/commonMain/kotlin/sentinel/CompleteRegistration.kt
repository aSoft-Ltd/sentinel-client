@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import cinematic.LazyScene
import kase.Loading
import kase.Result
import kase.toLazyState
import koncurrent.Later
import koncurrent.later.andThen
import koncurrent.later.finally
import kotlinx.JsExport
import sentinel.params.EmailSignInParams
import sentinel.tools.loadUserAccountParams
import sentinel.tools.removeUserAccountParams

class CompleteRegistration(config: AuthenticationSceneOptions) : LazyScene<UserSession>() {
    private val api = config.api
    private val cache = config.cache

    fun initialize(callback: (Result<UserSession>) -> Unit): Later<UserSession> {
        ui.value = Loading("Please wait as we set your account up . . .")
        return cache.loadUserAccountParams().andThen {
            api.signIn(EmailSignInParams(it.loginId, it.password))
        }.finally {
            ui.value = it.toLazyState()
            cache.removeUserAccountParams()
            callback(it)
        }
    }
}