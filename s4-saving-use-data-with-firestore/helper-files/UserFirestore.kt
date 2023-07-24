import com.google.firebase.Timestamp

data class UserFirestore(
  val id: String = "", val email: String = "", val birthday: Timestamp? = null
) {
  fun toUser(): User {
    return User(id, email, birthday?.toDate())
  }
}