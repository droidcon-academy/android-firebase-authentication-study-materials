package com.droidcon.authenticate.utils

object FieldsValidationUtil {

    fun isEmailNotValid(emailAddress: String) =
        emailAddress.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()

    fun isPasswordNotValid(password: String) =
        password.isEmpty() || !Patterns.PASSWORD_PATTERN.matcher(password).matches()
}