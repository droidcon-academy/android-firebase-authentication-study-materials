package com.droidcon.authenticate.data

import java.util.Date

data class User(
  val id: String,
  val email: String,
  val birthday: Date?
) {
  companion object {
    val NULL: User = User("NO_ID", "NO_EMAIL", Date(0))
  }
}