package com.droidcon.authenticate

data class LoginUiState(
  val email: String = "",
  val hasEmailError: Boolean = false,
  val password: String = "",
  val hasPasswordError: Boolean = false,
  val loginState: AuthUiState = AuthUiState.Idle,
  val showForgetPasswordDialog: Boolean = false,
  val forgetPasswordEmail: String = "",
  val hasForgetPasswordEmailError: Boolean = false
)