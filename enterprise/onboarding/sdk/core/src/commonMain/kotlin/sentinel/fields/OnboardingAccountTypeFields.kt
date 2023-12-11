@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel.fields

import sentinel.OnBoardingAccountType
import symphony.Fields
import symphony.Option
import symphony.selectSingle
import kotlinx.JsExport

class OnboardingAccountTypeFields(result: OnboardingAccountTypeOutput) : Fields<OnboardingAccountTypeOutput>(result) {
    val type = selectSingle(
        name = output::type,
        label = "Account Type",
        items = OnBoardingAccountType.values().toList(),
        mapper = { Option(it.name, it.name) }
    )
}