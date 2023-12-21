@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import cinematic.BaseScene
import identifier.CorporatePresenter
import identifier.transformers.toPresenter
import kase.Result
import kase.bagOf
import koncurrent.later.finally
import koncurrent.toLater
import sentinel.fields.OnboardingAccountTypeFields
import sentinel.fields.OnboardingAddressFields
import sentinel.fields.OnboardingBusinessNameFields
import sentinel.fields.OnboardingCurrencyFields
import symphony.toForm
import symphony.toSubmitConfig
import kollections.listOf
import kotlinx.JsExport
import sentinel.stage.OnboardingAccountStage
import sentinel.stage.OnBoardingBusinessNameStage
import sentinel.stage.OnBoardingCurrencyStage
import sentinel.stage.OnBoardingAddressStage
import symphony.Visibilities

class OnBoardingScene(config: OnboardingScenesConfig<ProfileApi>) : BaseScene() {

    private val output = OnBoardingOutput()

    private val af = OnboardingAddressFields(output)

    val form = listOf(
        OnboardingAccountStage(
            heading = "Choose your account type",
            details = "Are you an individual or business?",
            fields = OnboardingAccountTypeFields(output)
        ),
        OnBoardingBusinessNameStage(
            heading = "Enter the name of your business",
            details = "Whats the name of your business?",
            fields = OnboardingBusinessNameFields(output.type, output)
        ),
        OnBoardingCurrencyStage(
            heading = "Choose your default currency",
            details = "Which currency are you operating in?",
            fields = OnboardingCurrencyFields(output),
            onNext = {
                if (output.address != null) return@OnBoardingCurrencyStage
                af.address.country.set(output.country)
            }
        ),
        OnBoardingAddressStage(
            heading = "Enter your operating address",
            details = "Where are you operating from?",
            fields = af
        ),
    ).toForm(
        output = output,
        config = config.toSubmitConfig(),
        visibility = Visibilities.Visible
    ) {
        onSubmit { completeOnBoarding() }
    }

    private val api = config.api

    private val completionHandler = bagOf<(Result<CorporatePresenter>) -> Unit>()
    fun initialize(onComplete: (Result<CorporatePresenter>) -> Unit) {
        completionHandler.value = onComplete
    }

    fun deInitialize() {
        completionHandler.clean()
    }

    private fun completeOnBoarding() = output.toLater().then {
        it.toCurrency().getOrThrow()
    }.andThen {
        api.organisation.updateCurrency(it)
    }.then {
        output.toParams().getOrThrow()
    }.andThen {
        api.organisation.update(it)
    }.then {
        it.toPresenter()
    }.finally {
        completionHandler.value?.invoke(it)
    }
}