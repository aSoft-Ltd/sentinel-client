@file:JsExport

package sentinel.params

import kotlinx.serialization.Serializable
import sentinel.OnBoardingAccountType
import kotlin.js.JsExport

@Serializable
data class AccountTypeOnBoardingParams(
    var type: OnBoardingAccountType?
)