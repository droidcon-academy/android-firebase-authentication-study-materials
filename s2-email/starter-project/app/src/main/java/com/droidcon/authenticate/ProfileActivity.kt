package com.droidcon.authenticate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.droidcon.authenticate.ui.theme.AuthenticateTheme

class ProfileActivity: ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AuthenticateTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
          Text(text = "Hello from Profile screen")
        }
      }
    }
  }
}