package com.droidcon.authenticate.multifactor

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.droidcon.authenticate.profile.ProfileActivity
import com.droidcon.authenticate.ui.theme.AuthenticateTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.MultiFactorResolver
import com.google.firebase.auth.PhoneMultiFactorInfo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MultiFactorActivity : ComponentActivity() {

  @Inject
  lateinit var firebaseAuth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AuthenticateTheme {
        val resolver : MultiFactorResolver? = intent.getParcelableExtra("resolver")

        MultiFactorPhoneScreen(
          onBackClicked = {finish()},
          firebaseAuth = firebaseAuth,
          onAddingMultiFactorSuccess = {finish()},
          session = resolver?.session,
          hint = resolver?.hints?.get(0) as PhoneMultiFactorInfo?,
          onVerifyMultiFactor = { assertion ->
            resolver?.resolveSignIn(assertion)?.addOnSuccessListener {
              if (it.user != null) goToProfileScreen()
            }?.addOnFailureListener {
              println(it)
              finish()
            }
          }
        )
      }
    }
  }

  private fun goToProfileScreen() {
    startActivity(Intent(this, ProfileActivity::class.java))
    finish()
  }
}