@file:JsExport
@file:Suppress("OPT_IN_USAGE", "NON_EXPORTABLE_TYPE")

package sentinel.fields

import geo.Country
import neat.required
import symphony.Fields
import symphony.Option
import geo.matches
import kollections.toList
import symphony.selectSingle
import kotlinx.JsExport

class OnboardingCurrencyFields(output: OnboardingCurrencyOutput) : Fields<OnboardingCurrencyOutput>(output) {
    val currency = selectSingle(
        name = output::country,
        items = Country.entries.sortedBy { it.currency.name }.toList(),
        mapper = { Option(it.currency.name) },
        filter = { country, key -> country.matches(key) }
    ) { required() }
}