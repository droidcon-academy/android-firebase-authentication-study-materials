package com.droidcon.authenticate.data

import com.google.firebase.auth.FirebaseUser

interface AuthService {
  suspend fun login(email: String, password: String): Result<Boolean>
  suspend fun signUp(email: String, password: String): Result<Boolean>
  suspend fun sendResetPasswordEmail(email: String)
  fun isUserLoggedIn() : Boolean
  fun signOut()
  suspend fun deleteAccount(): Result<Boolean>
  suspend fun sendEmailVerification(): Result<Boolean>
  fun getUser(): FirebaseUser?
  suspend fun updateDisplayName(name: String) : Result<Boolean>
}

