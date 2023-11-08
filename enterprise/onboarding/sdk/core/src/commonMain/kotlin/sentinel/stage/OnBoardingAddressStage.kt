@file:JsExport
package sentinel.stage

import kotlin.js.JsExport
import sentinel.fields.OnboardingAddressFields

data class OnBoardingAddressStage(
    override val heading: String,
    override val details: String,
    override val fields: OnboardingAddressFields
) : OnBoardingStage()