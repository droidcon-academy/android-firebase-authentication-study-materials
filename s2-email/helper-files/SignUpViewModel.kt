package com.droidcon.authenticate.auth.signup

import androidx.lifecycle.ViewModel
import com.droidcon.authenticate.R
import com.droidcon.authenticate.utils.FieldsValidationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

  private val _signUpUiState = MutableStateFlow(SignUpUiState())
  val signUpUiState = _signUpUiState.asStateFlow()

  fun updateConfirmPassword(confirmPassword: String) {
    _signUpUiState.value = _signUpUiState.value.copy(confirmPassword = confirmPassword.trim())
  }

  fun updateSignUpPassword(password: String) {
    _signUpUiState.value = _signUpUiState.value.copy(password = password.trim())
  }

  fun updateSignUpEmail(email: String) {
    _signUpUiState.value = _signUpUiState.value.copy(email = email.trim())
  }

  fun signUp() {
    if (!isSignUpInputsValid()) return
    _signUpUiState.value = _signUpUiState.value.copy(signUpState = AuthUiState.Loading)
    // TODO perform the sign up
  }

  private fun isSignUpInputsValid(): Boolean {
    if (FieldsValidationUtil.isEmailNotValid(_signUpUiState.value.email)) {
      _signUpUiState.value = _signUpUiState.value.copy(hasEmailError = true)
      return false
    } else {
      _signUpUiState.value = _signUpUiState.value.copy(hasEmailError = false)
    }
    if (FieldsValidationUtil.isPasswordNotValid(_signUpUiState.value.password)) {
      _signUpUiState.value = _signUpUiState.value.copy(
        hasPasswordError = true,
        passwordError = R.string.error_valid_password
      )
      return false
    } else {
      _signUpUiState.value = _signUpUiState.value.copy(hasPasswordError = false)
    }

    if (_signUpUiState.value.password != _signUpUiState.value.confirmPassword) {
      _signUpUiState.value = _signUpUiState.value.copy(
        hasPasswordError = true,
        passwordError = R.string.error_password_mismatch
      )
      return false
    } else {
      _signUpUiState.value = _signUpUiState.value.copy(hasPasswordError = false)
    }
    return true
  }
}



