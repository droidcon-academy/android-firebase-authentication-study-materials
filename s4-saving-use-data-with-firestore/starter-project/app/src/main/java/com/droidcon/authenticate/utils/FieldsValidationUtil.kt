package com.droidcon.authenticate.utils

object FieldsValidationUtil {

    fun isEmailNotValid(emailAddress: String): Boolean {
        println("got email: $emailAddress ${!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()}")
        return emailAddress.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
    }

    fun isPasswordNotValid(password: String) =
        password.isEmpty() || !Patterns.PASSWORD_PATTERN.matcher(password).matches()
}