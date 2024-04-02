package com.droidcon.authenticate.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidcon.authenticate.R
import com.droidcon.authenticate.auth.AuthenticationActivity
import com.droidcon.authenticate.data.AuthService
import com.droidcon.authenticate.profile.ProfileActivity
import com.droidcon.authenticate.ui.theme.AuthenticateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

  @Inject
  lateinit var authService: AuthService

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AuthenticateTheme {
        LaunchedEffect(key1 = true) {
          delay(2000)
          checkUserState()
        }
        SplashScreen()
      }
    }
  }
  private fun checkUserState() {
    if (authService.isUserLoggedIn()) {
      startActivity(Intent(this, ProfileActivity::class.java))
    } else {
      startActivity(Intent(this, AuthenticationActivity::class.java))
    }
    finish()
  }
}

@Preview(showBackground = true)
@Composable
fun SplashScreen() {
  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Image(
      modifier = Modifier.size(128.dp),
      painter = painterResource(id = R.drawable.logo),
      contentDescription = "Logo Icon"
    )
  }
}