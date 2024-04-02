package com.droidcon.authenticate.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.authenticate.R
import com.droidcon.authenticate.ui.theme.Primary
import com.droidcon.authenticate.ui.theme.Secondary
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun AuthScreen(
  onSignUpClicked: () -> Unit = {},
  onLoginClicked: () -> Unit = {},
  onPhoneNumberClicked: () -> Unit = {},
  onAuthWithProviderCompleted: () -> Unit = {},
  firebaseAuth: FirebaseAuth
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
  ) {

    val context = LocalContext.current
    val oneTapClient = remember { Identity.getSignInClient(context) }
    val coroutineScope = rememberCoroutineScope()
    val googleLauncher = rememberLauncherForActivityResult(
      contract = ActivityResultContracts.StartIntentSenderForResult(),
      onResult = {
        if (it.resultCode == Activity.RESULT_OK) {
          val credentials = oneTapClient.getSignInCredentialFromIntent(it.data)
          if (credentials.googleIdToken != null) {
            val firebaseCredentials = GoogleAuthProvider.getCredential(credentials.googleIdToken, null)
            coroutineScope.launch {
              try {
                val result = firebaseAuth.signInWithCredential(firebaseCredentials).await()
                if (result.user != null) onAuthWithProviderCompleted()
              } catch (e: Exception) {
                println(e)
              }
            }
          }
        }
      }
    )

    val loginManager = LoginManager.getInstance()
    val callbackManager = remember {
      CallbackManager.Factory.create()
    }
    val facebookLauncher = rememberLauncherForActivityResult(
      contract = loginManager.createLogInActivityResultContract(callbackManager, null),
      onResult = {}
    )

    DisposableEffect(key1 = Unit, effect = {

      loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
        override fun onCancel() {

        }

        override fun onError(error: FacebookException) {
          println(error.message)
        }

        override fun onSuccess(result: LoginResult) {
          coroutineScope.launch {
            val token = result.accessToken.token
            val credentials = FacebookAuthProvider.getCredential(token)
            val authResult = firebaseAuth.signInWithCredential(credentials).await()
            if (authResult.user != null) onAuthWithProviderCompleted()
          }
        }
      })
      onDispose {
        loginManager.unregisterCallback(callbackManager)
      }
    })

    Image(
      painter = painterResource(id = R.drawable.droidcon_logo_large),
      contentDescription = null,
      modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(16.dp)
    )

    AuthButton(
      onClick = onLoginClicked,
      text = stringResource(R.string.login),
      colors = ButtonDefaults.buttonColors(containerColor = Primary)
    )

    AuthButton(
      onClick = onPhoneNumberClicked,
      text = stringResource(R.string.phone_number_auth),
      colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
    )

    AuthButton(
      onClick = onSignUpClicked,
      text = stringResource(R.string.sign_up),
      colors = ButtonDefaults.buttonColors(containerColor = Secondary)
    )

    Divider(thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp))

    AuthButton(
      onClick = {
        coroutineScope.launch {
          try {
            val result = firebaseAuth.signInAnonymously().await()
            if (result.user != null) onAuthWithProviderCompleted()
          } catch (e: Exception) {
            println(e)
          }
        }
      },
      iconRes = R.drawable.ic_person,
      text = stringResource(R.string.login_as_a_guest),
      colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    )

    AuthButton(
      onClick = {
        val request = createSignInRequest()
        coroutineScope.launch {
          try {
            val result = oneTapClient.beginSignIn(request).await()
            googleLauncher.launch(
              IntentSenderRequest.Builder(result.pendingIntent).build()
            )
          } catch (e: Exception) {
            println(e)
          }
        }
      },
      iconRes = R.drawable.ic_google_logo,
      text = stringResource(R.string.login_with_google),
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDB4437))
    )

    AuthButton(
      onClick = {
          facebookLauncher.launch(listOf("email", "public_profile", "user_birthday"))
                },
      iconRes = R.drawable.ic_facebook_logo,
      text = stringResource(R.string.login_with_facebook),
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3b5998))
    )

    AuthButton(
      onClick = {
        val provider = OAuthProvider.newBuilder("twitter.com").apply {
          addCustomParameter("lang", "en")
        }

        firebaseAuth.pendingAuthResult?.addOnSuccessListener {
          if (it.user != null) onAuthWithProviderCompleted
        }

        firebaseAuth.startActivityForSignInWithProvider(context as Activity, provider.build()).addOnSuccessListener {
          if (it.user != null) onAuthWithProviderCompleted()
        }
      },
      iconRes = R.drawable.ic_twitter_logo,
      text = stringResource(R.string.login_with_twitter),
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00acee))
    )
  }
}

fun createSignInRequest(): BeginSignInRequest {
  return BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
      .setSupported(true)
      .setServerClientId("396294925306-h5o0rfu2di39vhh5fjhr2adbcssu7nmv.apps.googleusercontent.com")
      .build()
  ).build()
}

@Composable
private fun AuthButton(
  colors: ButtonColors = ButtonDefaults.buttonColors(),
  onClick: () -> Unit,
  @DrawableRes iconRes: Int? = null,
  text: String
) {
  Button(
    onClick = onClick,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 4.dp)
      .height(64.dp),
    colors = colors
  ) {
    if (iconRes != null) Icon(
      painter = painterResource(id = iconRes), contentDescription = null
    )
    Text(
      text = text, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontSize = 20.sp
    )
  }
}