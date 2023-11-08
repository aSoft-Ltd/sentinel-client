@file:JsExport
@file:Suppress("OPT_IN_USAGE")

package sentinel.fields

import neat.min
import neat.required
import sentinel.OnBoardingAccountType
import symphony.Fields
import symphony.text
import kotlin.js.JsExport

class OnboardingBusinessNameFields internal constructor(
    type: OnBoardingAccountType?, params: OnboardingBusinessNameOutput
) : Fields<OnboardingBusinessNameOutput>(params) {
    val name = text(
        name = output::businessName,
        label = "Business name",
        hint = "Peperoni Inc"
    ) {
        if (type == OnBoardingAccountType.Business) {
            min(5)
            required()
        } else {
            optional()
        }
    }
}