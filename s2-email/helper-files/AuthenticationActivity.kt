package com.droidcon.authenticate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droidcon.authenticate.ui.theme.AuthenticateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AuthenticateTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = AuthDestination.Auth.route) {
          composable(AuthDestination.Auth.route) {
            AuthScreen(
              onLoginClicked = { navController.navigate(AuthDestination.Login.route) },
              onSignUpClicked = { navController.navigate(AuthDestination.SignUp.route) }
            )
          }
          composable(AuthDestination.Login.route) {
            LoginScreen(
              onBackClicked = { navController.popBackStack() },
              onLoginSuccess = { goToProfile() },
            )
          }
          composable(AuthDestination.SignUp.route) {
            SignUpScreen(
              onBackClicked = { navController.popBackStack() },
              onSignUpSuccess = { goToProfile() },
            )
          }
        }
      }
    }
  }

  private fun goToProfile() {
    startActivity(Intent(this@AuthenticationActivity, ProfileActivity::class.java))
    finish()
  }
}