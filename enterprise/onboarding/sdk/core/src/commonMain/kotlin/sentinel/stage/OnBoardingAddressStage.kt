@file:JsExport
package sentinel.stage

import kotlinx.JsExport
import sentinel.fields.OnboardingAddressFields

data class OnBoardingAddressStage(
    override val heading: String,
    override val details: String,
    override val fields: OnboardingAddressFields
) : OnBoardingStage()