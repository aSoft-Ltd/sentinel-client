@file:JsExport

package sentinel

import hormone.HasApi
import keep.Cacheable
import lexi.Logable
import kotlinx.JsExport

interface RegistrationSceneConfig<out A> : HasApi<A>, Logable, Cacheable