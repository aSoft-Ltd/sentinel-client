@file:JsExport
package sentinel.stage

import kotlinx.JsExport
import sentinel.fields.OnboardingBusinessNameFields

data class OnBoardingBusinessNameStage(
    override val heading: String,
    override val details: String,
    override val fields: OnboardingBusinessNameFields
) : OnBoardingStage()