package com.droidcon.authenticate.data

import java.util.Date

interface UserDataSource {
  suspend fun getUserInformation(): User
  suspend fun addUser(userId: String, email: String)
  suspend fun updateBirthday(birthday: Date)
}

