package com.droidcon.authenticate

import android.annotation.SuppressLint
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
import com.droidcon.authenticate.ui.theme.AuthenticateTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AuthenticateTheme {
        LaunchedEffect(true) {
          delay(2000)
          checkUserState()
        }
        SplashScreen()
      }
    }
  }

  private fun checkUserState() {
    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
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