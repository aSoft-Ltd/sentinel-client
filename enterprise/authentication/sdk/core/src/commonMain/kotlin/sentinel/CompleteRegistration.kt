@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import cinematic.BaseScene
import cinematic.LazyScene
import cinematic.mutableLiveOf
import kase.LazyState
import kase.Loading
import kase.Pending
import kase.Result
import kase.toLazyState
import koncurrent.Later
import koncurrent.later.andThen
import koncurrent.later.finally
import kotlinx.JsExport
import sentinel.params.SignInParams
import sentinel.tools.loadUserAccountParams
import sentinel.tools.removeUserAccountParams

class CompleteRegistration(config: AuthenticationScenesConfig<AuthenticationApi>) : LazyScene<UserSession>() {
    private val api = config.api
    private val cache = config.cache

    fun initialize(callback: (Result<UserSession>) -> Unit): Later<UserSession> {
        ui.value = Loading("Please wait as we set your account up . . .")
        return cache.loadUserAccountParams().andThen {
            api.signIn(SignInParams(it.loginId, it.password))
        }.finally {
            ui.value = it.toLazyState()
            cache.removeUserAccountParams()
            callback(it)
        }
    }
}