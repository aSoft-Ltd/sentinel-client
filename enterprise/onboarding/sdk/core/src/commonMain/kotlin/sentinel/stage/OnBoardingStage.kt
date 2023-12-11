@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel.stage

import symphony.FormStage
import kotlinx.JsExport

sealed class OnBoardingStage : FormStage {

    val asAccount get() = this as? OnboardingAccountStage
    val asBusinessName get() = this as? OnBoardingBusinessNameStage
    val asCurrency get() = this as? OnBoardingCurrencyStage
    val asAddress get() = this as? OnBoardingAddressStage
}