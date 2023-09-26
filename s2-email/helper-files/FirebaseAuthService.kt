package com.droidcon.authenticate.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthService(
	private val  firebaseAuth: FirebaseAuth
) : AuthService {

	override suspend fun login(email: String, password: String): Result<Boolean> {
		return try {
			val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
			Result.success(result.user != null)
		} catch (e: Exception) {
			Result.failure(e)
		}
	}

	override suspend fun signUp(email: String, password: String): Result<Boolean> {
		return try {
			val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
			Result.success(result.user != null)
		} catch (e: Exception) {
			Result.failure(e)
		}
	}

	override suspend fun sendResetPasswordEmail(email: String) {
		firebaseAuth.sendPasswordResetEmail(email).await()
	}

	override fun isUserLoggedIn(): Boolean {
		return firebaseAuth.currentUser != null
	}
}