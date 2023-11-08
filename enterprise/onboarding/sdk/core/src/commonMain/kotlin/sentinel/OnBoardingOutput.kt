package sentinel

import geo.AddressOutput
import geo.Country
import geo.GeoLocation
import identifier.params.CorporateParams
import kase.catching
import neat.required
import sentinel.fields.OnboardingAccountTypeOutput
import sentinel.fields.OnboardingAddressOutput
import sentinel.fields.OnboardingLocationOutput
import sentinel.fields.OnboardingBusinessNameOutput
import sentinel.fields.OnboardingCurrencyOutput

data class OnBoardingOutput(
    override var type: OnBoardingAccountType? = null,
    override var businessName: String? = null,
    override var country: Country? = null,
    override var location: GeoLocation? = null,
    override var address: AddressOutput? = null,
    var tax: Int? = null
) : OnboardingAccountTypeOutput, OnboardingBusinessNameOutput, OnboardingAddressOutput, OnboardingLocationOutput, OnboardingCurrencyOutput {
    fun toParams() = catching {
        CorporateParams(
            name = this::businessName.required,
            hqLocation = location,
            address = address?.toDto()?.getOrThrow()
        )
    }

    fun toCurrency() = catching { this::country.required.currency }
}