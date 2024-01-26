package sentinel.fields

import geo.GeoLocation

interface OnboardingLocationOutput {
    var location: GeoLocation?
}