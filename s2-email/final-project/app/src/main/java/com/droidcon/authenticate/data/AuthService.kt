package com.droidcon.authenticate.data

interface AuthService {
  suspend fun login(email: String, password: String): Result<Boolean>
  suspend fun signUp(email: String, password: String): Result<Boolean>
  suspend fun sendResetPasswordEmail(email: String)
  fun isUserLoggedIn() : Boolean
}

