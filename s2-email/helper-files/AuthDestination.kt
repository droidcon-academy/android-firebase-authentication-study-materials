package com.droidcon.authenticate

sealed class AuthDestination(val route: String) {
  object Auth : AuthDestination("auth")
  object Login : AuthDestination("login")
  object SignUp : AuthDestination("signup")
}