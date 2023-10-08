package sentinel.exceptions

class UserDidNotBeginRegistrationException(
    val email: String
) : RegistrationException("User with email ($email) has not began registration yet")