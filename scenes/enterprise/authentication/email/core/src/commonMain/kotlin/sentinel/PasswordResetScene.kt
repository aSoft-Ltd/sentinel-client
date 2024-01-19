@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import cinematic.LazyScene
import kase.Failure
import kase.Pending
import kase.Result
import kase.Success
import keep.load
import koncurrent.later.finally
import koncurrent.later.zip
import koncurrent.toLater
import koncurrent.later.then
import koncurrent.later.andThen
import sentinel.fields.PasswordResetFields
import sentinel.fields.PasswordResetOutput
import sentinel.transformers.toParams
import symphony.Form
import symphony.toForm
import kotlinx.JsExport
import sentinel.params.EmailSignInParams

class PasswordResetScene(
    private val options: AuthenticationSceneOptions<AuthenticationApi>
) : LazyScene<Form<Any, PasswordResetOutput, PasswordResetFields>>() {

    protected val api = options.api

    private var callback: ((Result<Unit>) -> Unit)? = null

    fun initialize(link: String, onResult: (Result<Unit>) -> Unit) {
        callback = onResult
        ui.value = when (val res = parseToken(link)) {
            is Success -> Success(form(token = res.data))
            is Failure -> Failure(res.cause)
        }
    }

    private fun form(token: String) = PasswordResetFields().toForm(
        heading = "Reset your password",
        details = "enter your new password",
        logger = options.logger
    ) {
        onCancel { ui.value = Pending }
        onSubmit { output ->
            output.toLater().then {
                it.toParams(token).getOrThrow()
            }.andThen {
                api.resetPassword(it)
            }.zip(options.cache.load<String>(PasswordScenes.KEY_RESET_EMAIL)) { (params, email) ->
                EmailSignInParams(email = email, params.password)
            }.andThen {
                api.signIn(it)
            }.finally {
                options.cache.remove(PasswordScenes.KEY_RESET_EMAIL)
            }
        }

        onSuccess { callback?.invoke(Success(Unit)) }
        onFailure { callback?.invoke(Failure(it)) }
    }

    override fun deInitialize() {
        callback = null
        super.deInitialize()
    }

    @Deprecated("""TODO: Use captain.url.query["token"]""")
    private fun parseToken(link: String): Result<String> {
        val query = link.split("?").getOrNull(1) ?: return Failure(IllegalArgumentException("No query params found"))
        val token = query.split("=").getOrNull(1) ?: return Failure(IllegalArgumentException("Couldn't get reset token from query params"))
        return Success(token)
    }
}