package com.droidcon.authenticate.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
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

	override fun signOut() {
		firebaseAuth.signOut()
	}

	override fun getUser(): FirebaseUser? {
		return firebaseAuth.currentUser
	}

	override suspend fun updateDisplayName(name: String): Result<Boolean> {
		return resultFor {
			firebaseAuth.currentUser?.updateProfile(
				UserProfileChangeRequest.Builder()
					.setDisplayName(name)
					.build()
			)?.await()
			firebaseAuth.currentUser?.reload()?.await()
		}
	}

	override suspend fun deleteAccount(): Result<Boolean> {
		return resultFor {
			firebaseAuth.currentUser?.delete()?.await()
		}
	}

	override suspend fun sendEmailVerification(): Result<Boolean> {
		return resultFor {
			firebaseAuth.currentUser?.sendEmailVerification()?.await()
		}
	}

	private suspend fun resultFor(body: suspend () -> Unit) : Result<Boolean>{
		return try {
			body()
			Result.success(true)
		} catch (exception: Exception) {
			Result.failure(exception)
		}
	}
}
