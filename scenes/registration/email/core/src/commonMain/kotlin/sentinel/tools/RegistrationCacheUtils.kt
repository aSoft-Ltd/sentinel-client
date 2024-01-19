@file:Suppress("NOTHING_TO_INLINE")

package sentinel.tools

import keep.Cache
import sentinel.params.EmailSignUpParams

@PublishedApi
internal const val KEY_SIGN_UP_PARAMS = "sentinel.registration.sign.up.params"

// --------------------------Sign Up Params --------------------------
inline fun Cache.save(params: EmailSignUpParams) = save(KEY_SIGN_UP_PARAMS, params, EmailSignUpParams.serializer())

inline fun Cache.loadEmailSignUpParams() = load(KEY_SIGN_UP_PARAMS, EmailSignUpParams.serializer())

inline fun Cache.removeEmailSignUpParams() = remove(KEY_SIGN_UP_PARAMS)