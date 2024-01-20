@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import cinematic.BaseScene
import koncurrent.FailedLater
import koncurrent.Later
import koncurrent.later.andThen
import koncurrent.later.then
import koncurrent.toLater
import kotlinx.JsExport
import sentinel.fields.SignUpFields
import sentinel.tools.loadEmailSignUpParams
import sentinel.tools.save
import symphony.toForm

class SignUpScene(private val options: RegistrationSceneOptions) : BaseScene() {

    private val api get() = options.api

    private val cache get() = options.cache

    fun initialize() = restorePreviousSession()

    val form = SignUpFields().toForm(
        heading = "Create an account",
        details = "Signup in less than two minutes",
        logger = options.logger
    ) {
        onSubmit { output ->
            output.toLater().then {
                it.toParams().getOrThrow()
            }.andThen {
                cache.save(it)
            }.andThen {
                api.signUp(it)
            }.andThen {
                api.sendVerificationLink(it.email)
            }
        }
    }

    private fun restorePreviousSession() = cache.loadEmailSignUpParams().then {
        form.fields.apply {
            email.set(it.email)
            name.set(it.name)
        }
    }

    fun resendVerificationLink(): Later<String> {
        val email = form.fields.email.output ?: return FailedLater(IllegalArgumentException("Email is not entered"))
        return api.sendVerificationLink(email)
    }
}