@file:JsExport
@file:Suppress("OPT_IN_USAGE")

package sentinel

import kotlinx.serialization.Serializable
import kotlinx.JsExport

@Serializable
enum class OnBoardingAccountType {
    Business, Individual
}