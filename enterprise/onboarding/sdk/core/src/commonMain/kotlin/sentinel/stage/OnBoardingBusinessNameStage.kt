@file:JsExport
package sentinel.stage

import kotlin.js.JsExport
import sentinel.fields.OnboardingBusinessNameFields

data class OnBoardingBusinessNameStage(
    override val heading: String,
    override val details: String,
    override val fields: OnboardingBusinessNameFields
) : OnBoardingStage()