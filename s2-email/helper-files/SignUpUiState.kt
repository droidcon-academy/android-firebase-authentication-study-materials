package com.droidcon.authenticate.signup

data class SignUpUiState(
  val email: String = "",
  val hasEmailError: Boolean = false,
  val password: String = "",
  val hasPasswordError: Boolean = false,
  val confirmPassword: String = "",
  val signUpState: AuthUiState = AuthUiState.Idle,
  val passwordError: Int? = null
)