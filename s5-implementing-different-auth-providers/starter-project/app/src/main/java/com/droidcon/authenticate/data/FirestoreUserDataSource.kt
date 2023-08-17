package com.droidcon.authenticate.data

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date

class FirestoreUserDataSource(
  private val firestore: FirebaseFirestore,
  private val firebaseAuth: FirebaseAuth,
) : UserDataSource {

  override suspend fun getUserInformation(): User {
    val userId = firebaseAuth.currentUser?.uid ?: return User.NULL
    val userDocument = firestore.collection("users").document(userId).get().await()
    val user =  userDocument.toObject(UserFirestore::class.java)?.toUser() ?: User.NULL
    println(user)
    return user
  }

  override suspend fun updateBirthday(birthday: Date) {
    val userId = firebaseAuth.currentUser?.uid ?: return
    firestore.collection("users").document(userId).update(
      mapOf(
        "birthday" to Timestamp(birthday)
      )
    ).await()
  }

  override suspend fun addUser(userId: String, email: String) {
    firestore.collection("users").document(userId).set(
      UserFirestore(userId, email, null)
    ).await()
  }
}

data class UserFirestore(
  val id: String = "", val email: String = "", val birthday: Timestamp? = null
) {
  fun toUser(): User {
    return User(id, email, birthday?.toDate())
  }
}