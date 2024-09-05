@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import sentinel.fields.SetPasswordFields
import sentinel.tools.loadVerificationParams
import sentinel.tools.removeVerificationParams
import sentinel.tools.save
import sentinel.transformers.toParams
import symphony.toForm
import koncurrent.later.then
import koncurrent.later.andThen
import symphony.toSubmitConfig
import kotlinx.JsExport

class SetPasswordScene(private val config: RegistrationSceneConfig<RegistrationApi>) {
    private var successFunction: (() -> Unit)? = null

    private val cache = config.cache

    fun initialize(onSuccess: () -> Unit) {
        successFunction = onSuccess
    }

    fun deInitialize() {
        successFunction = null
//        form.fields.finish()
    }

    fun form(link:String) = SetPasswordFields(link).toForm(
        heading = "Make your account secure",
        details = "Set up your password",
        config = config.toSubmitConfig()
    ) {
        onSubmit { output ->
            val params = VerificationScene.parseUrlToEmailVerificationParams(link)
            config.api.createUserAccount(output.toParams(params).getOrThrow())
        }

        onSuccess {
//            cache.removeVerificationParams()
            successFunction?.invoke()
        }
    }
}