package com.droidcon.authenticate.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.authenticate.data.AuthService
import com.droidcon.authenticate.data.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
  private val authService: AuthService,
  private val userDataSource: UserDataSource
) : ViewModel() {

  private val _uiState = MutableStateFlow(ProfileUiState())
  val uiState = _uiState.asStateFlow()

  init {
    val user = authService.getUser()
    viewModelScope.launch {
      val userInformation = userDataSource.getUserInformation()
      val userBirthday = formatBirthday(userInformation.birthday)
      _uiState.value = _uiState.value.copy(
        userEmail = user?.email.orEmpty(),
        userId = user?.uid.orEmpty(),
        userDisplayName = user?.displayName.orEmpty(),
        userBirthday = userBirthday
      )
    }
  }

  fun signOut() {
    authService.signOut()
  }

  fun sendEmailVerification() {
    _uiState.value = _uiState.value.copy(isLoading = true)
    viewModelScope.launch {
      val result = authService.sendEmailVerification()
      _uiState.value = _uiState.value.copy(isLoading = false, isEmailSentSuccessfully = result.isSuccess)
    }
  }

  fun deleteAccount() {
    _uiState.value = _uiState.value.copy(isLoading = true)
    viewModelScope.launch {
      val result = authService.deleteAccount()
      _uiState.value = _uiState.value.copy(isLoading = false, isAccountDeletedSuccessfully = result.isSuccess)
    }
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
    viewModelScope.launch {
      val result = authService.updateDisplayName(name)
      _uiState.value = _uiState.value.copy(
        isLoading = false,
        isDisplayNameUpdatedSuccessfully = result.isSuccess,
        userDisplayName = name
      )
    }
  }

  fun showDisplayNameDialog() {
    _uiState.value = _uiState.value.copy(showDisplayNameDialog = true)
  }

  fun hideDisplayNameDialog() {
    _uiState.value = _uiState.value.copy(showDisplayNameDialog = false)
  }

  fun showDatePickerDialog() {
    _uiState.value = _uiState.value.copy(showDatePickerDialog = true)
  }

  fun hideDatePickerDialog() {
    _uiState.value = _uiState.value.copy(showDatePickerDialog = false)
  }

  fun requestBirthdayUpdate(birthday: Date) {
    _uiState.value = _uiState.value.copy(isLoading = true, showDatePickerDialog = false)
    viewModelScope.launch {
      userDataSource.updateBirthday(birthday)
      _uiState.value = _uiState.value.copy(
        isLoading = false,
        userBirthday = formatBirthday(birthday)
      )
    }
  }

  private fun formatBirthday(birthday: Date?) : String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return if (birthday != null) simpleDateFormat.format(birthday) else "Not Set"
  }
}