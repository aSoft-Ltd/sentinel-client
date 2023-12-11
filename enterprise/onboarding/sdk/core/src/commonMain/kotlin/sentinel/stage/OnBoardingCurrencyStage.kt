@file:JsExport

package sentinel.stage

import kotlinx.JsExport
import sentinel.fields.OnboardingCurrencyFields

data class OnBoardingCurrencyStage(
    override val heading: String,
    override val details: String,
    override val fields: OnboardingCurrencyFields,
    override val onNext: () -> Unit
) : OnBoardingStage()