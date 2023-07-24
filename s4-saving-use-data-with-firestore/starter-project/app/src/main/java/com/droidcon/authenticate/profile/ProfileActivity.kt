package com.droidcon.authenticate.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.droidcon.authenticate.auth.AuthenticationActivity
import com.droidcon.authenticate.ui.theme.AuthenticateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AuthenticateTheme {
        ProfileScreen(onSignOut = { goToAuthScreen() }, onAccountDeleted = { goToAuthScreen() })
      }
    }
  }


  private fun goToAuthScreen() {
    startActivity(Intent(this, AuthenticationActivity::class.java))
    finish()
  }
}