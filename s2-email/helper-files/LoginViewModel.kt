package com.droidcon.authenticate.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.authenticate.auth.AuthUiState
import com.droidcon.authenticate.data.AuthService
import com.droidcon.authenticate.utils.FieldsValidationUtil
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

  private val _loginUiState = MutableStateFlow(LoginUiState())
  val loginUiState = _loginUiState.asStateFlow()

  fun updateEmail(email: String) {
    _loginUiState.value = _loginUiState.value.copy(email = email.trim())
  }

  fun updatePassword(password: String) {
    _loginUiState.value = _loginUiState.value.copy(password = password.trim())
  }

  private fun isLoginInputsValid(): Boolean {
    if (FieldsValidationUtil.isEmailNotValid(_loginUiState.value.email)) {
      _loginUiState.value = _loginUiState.value.copy(hasEmailError = true)
      return false
    } else {
      _loginUiState.value = _loginUiState.value.copy(hasEmailError = false)
    }
    if (FieldsValidationUtil.isPasswordNotValid(_loginUiState.value.password)) {
      _loginUiState.value = _loginUiState.value.copy(hasPasswordError = true)
      return false
    } else {
      _loginUiState.value = _loginUiState.value.copy(hasPasswordError = false)
    }
    return true
  }

  fun login() {
    if (!isLoginInputsValid()) return
    _loginUiState.value = _loginUiState.value.copy(loginState = AuthUiState.Loading)
    // TODO perform the login
  }

  fun sendPasswordResetEmail() {
    if (FieldsValidationUtil.isEmailNotValid(_loginUiState.value.forgetPasswordEmail)) {
      _loginUiState.value = _loginUiState.value.copy(hasForgetPasswordEmailError = true)
      return
    } else {
      _loginUiState.value = _loginUiState.value.copy(hasForgetPasswordEmailError = false)
    }
    _loginUiState.value =
      _loginUiState.value.copy(showForgetPasswordDialog = false, loginState = AuthUiState.Loading)
    // perform the forget password
  }

  fun showPasswordDialog() {
    _loginUiState.value = _loginUiState.value.copy(showForgetPasswordDialog = true)
  }

  fun hidePasswordDialog() {
    _loginUiState.value = _loginUiState.value.copy(showForgetPasswordDialog = false)
  }

  fun updateForgetPasswordEmail(email: String) {
    _loginUiState.value = _loginUiState.value.copy(forgetPasswordEmail = email.trim())
  }
}