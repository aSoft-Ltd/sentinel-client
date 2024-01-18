package sentinel

import koncurrent.Later

interface AuthenticationApi : AuthenticationScheme {

    fun signOut(): Later<UserSession>
    fun session(): Later<UserSession>
}