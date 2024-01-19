@file:Suppress("NOTHING_TO_INLINE")

package sentinel.tools

import keep.Cache
import sentinel.params.UserAccountParams
import sentinel.params.EmailVerificationParams

@PublishedApi
internal const val KEY_VERIFICATION_PARAMS = "sentinel.registration.verification.params"

inline fun Cache.save(params: EmailVerificationParams) = save(KEY_VERIFICATION_PARAMS, params, EmailVerificationParams.serializer())

inline fun Cache.loadEmailVerificationParams() = load(KEY_VERIFICATION_PARAMS, EmailVerificationParams.serializer())

inline fun Cache.removeEmailVerificationParams() = remove(KEY_VERIFICATION_PARAMS)


// -------------------------- Verification Params ---------------------

@PublishedApi
internal const val KEY_ACCOUNT_PARAMS = "sentinel.registration.account.params"


inline fun Cache.save(params: UserAccountParams) = save(KEY_ACCOUNT_PARAMS, params, UserAccountParams.serializer())

inline fun Cache.loadUserAccountParams() = load(KEY_ACCOUNT_PARAMS, UserAccountParams.serializer())

inline fun Cache.removeUserAccountParams() = remove(KEY_ACCOUNT_PARAMS)