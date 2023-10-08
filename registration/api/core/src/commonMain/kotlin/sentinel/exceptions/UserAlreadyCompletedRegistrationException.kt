package sentinel.exceptions

class UserAlreadyCompletedRegistrationException(
    val email: String
) : RegistrationException("User with email ($email) has already completed registration")