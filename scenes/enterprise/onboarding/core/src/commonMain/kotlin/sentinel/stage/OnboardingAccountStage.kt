@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel.stage

import kotlinx.JsExport
import sentinel.fields.OnboardingAccountTypeFields


data class OnboardingAccountStage(
    override val heading: String,
    override val details: String,
    override val fields: OnboardingAccountTypeFields
) : OnBoardingStage()