@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import sentinel.fields.SetPasswordFields
import sentinel.tools.save
import sentinel.transformers.toParams
import symphony.toForm
import koncurrent.later.then
import koncurrent.later.andThen
import kotlinx.JsExport
import sentinel.tools.loadEmailVerificationParams
import sentinel.tools.removeEmailVerificationParams

class SetPasswordScene(private val options: RegistrationSceneOptions<RegistrationApi>) {
    private var successFunction: (() -> Unit)? = null

    private val cache = options.cache

    fun initialize(onSuccess: () -> Unit) {
        successFunction = onSuccess
    }

    fun deInitialize() {
        successFunction = null
        form.fields.finish()
    }

    val form = SetPasswordFields().toForm(
        heading = "Make your account secure",
        details = "Set up your password",
        logger = options.logger
    ) {
        onSubmit { output ->
            cache.loadEmailVerificationParams().then {
                output.toParams(it).getOrThrow()
            }.andThen {
                cache.save(it)
            }.andThen {
                options.api.createUserAccount(it)
            }
        }

        onSuccess {
            cache.removeEmailVerificationParams()
            successFunction?.invoke()
        }
    }
}