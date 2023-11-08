@file:JsExport

package sentinel.stage

import kotlin.js.JsExport
import sentinel.fields.OnboardingCurrencyFields

data class OnBoardingCurrencyStage(
    override val heading: String,
    override val details: String,
    override val fields: OnboardingCurrencyFields,
    override val onNext: () -> Unit
) : OnBoardingStage()