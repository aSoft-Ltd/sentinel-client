@file:JsExport

package sentinel.params

import kotlinx.serialization.Serializable
import sentinel.OnBoardingAccountType
import kotlinx.JsExport

@Serializable
data class AccountTypeOnBoardingParams(
    var type: OnBoardingAccountType?
)