package com.droidcon.authenticate.auth

sealed  class AuthUiState {
  object Success : AuthUiState()
  object Idle : AuthUiState()
  object Loading : AuthUiState()
  data class Failure(val throwable: Throwable?) : AuthUiState()
}