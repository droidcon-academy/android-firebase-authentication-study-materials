package com.droidcon.authenticate.profile

data class ProfileUiState(
  val userId: String = "",
  val userEmail: String = "",
  val userDisplayName: String = "",
  val userBirthday: String = "",
  val isLoading: Boolean = false,
  val isEmailSentSuccessfully : Boolean? = null,
  val isAccountDeletedSuccessfully : Boolean? = null,
  val showDisplayNameDialog: Boolean = false,
  val showDatePickerDialog: Boolean = false,
  val userUpdateDisplayName: String = "",
  val hasUserDisplayNameError: Boolean = false,
  val isDisplayNameUpdatedSuccessfully : Boolean? = null,
)
