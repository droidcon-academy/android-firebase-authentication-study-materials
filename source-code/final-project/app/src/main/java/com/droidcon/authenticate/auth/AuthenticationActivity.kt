package com.droidcon.authenticate.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droidcon.authenticate.profile.ProfileActivity
import com.droidcon.authenticate.auth.login.LoginScreen
import com.droidcon.authenticate.auth.signup.SignUpScreen
import com.droidcon.authenticate.ui.theme.AuthenticateTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

  @Inject
  lateinit var firebaseAuth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AuthenticateTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = AuthDestination.Auth.route) {
          composable(AuthDestination.Auth.route) {
            AuthScreen(
              onLoginClicked = { navController.navigate(AuthDestination.Login.route) },
              onSignUpClicked = { navController.navigate(AuthDestination.SignUp.route) },
              onPhoneNumberClicked = { navController.navigate(AuthDestination.PhoneNumber.route) },
              firebaseAuth = firebaseAuth,
              onAuthWithProviderCompleted = {goToProfile()}
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
          composable(AuthDestination.PhoneNumber.route) {
            PhoneAuthScreen(
              onBackClicked = { navController.popBackStack() },
              onPhoneAuthSuccess = { goToProfile() },
              firebaseAuth = firebaseAuth
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