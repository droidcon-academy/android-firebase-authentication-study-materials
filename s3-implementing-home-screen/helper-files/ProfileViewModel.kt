package com.droidcon.authenticate.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.authenticate.data.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

  private val _uiState = MutableStateFlow(ProfileUiState())
  val uiState = _uiState.asStateFlow()

  // TODO Load info init

  fun signOut() {
    // TODO implement sign out
  }

  fun sendEmailVerification() {
    // TODO implement sending email
  }

  fun deleteAccount() {
    // TODO implement deleting account
  }

  fun updateUserDisplayName(name: String) {
    _uiState.value = _uiState.value.copy(
      userUpdateDisplayName = name, hasUserDisplayNameError = name.isDisplayNameInvalid()
    )
  }

  private fun String.isDisplayNameInvalid() = isEmpty() || length < 3

  fun requestUserDisplayNameUpdate() {
    val name = _uiState.value.userUpdateDisplayName
    if (name.isDisplayNameInvalid()) return
    _uiState.value = _uiState.value.copy(isLoading = true, showDisplayNameDialog = false)
    // TODO implement updating user name
  }

  fun showDisplayNameDialog() {
    _uiState.value = _uiState.value.copy(showDisplayNameDialog = true)
  }

  fun hideDisplayNameDialog() {
    _uiState.value = _uiState.value.copy(showDisplayNameDialog = false)
  }
}