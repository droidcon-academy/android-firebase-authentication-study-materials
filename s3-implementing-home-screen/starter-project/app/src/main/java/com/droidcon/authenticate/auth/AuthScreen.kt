package com.droidcon.authenticate.auth

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.authenticate.R
import com.droidcon.authenticate.ui.theme.Primary
import com.droidcon.authenticate.ui.theme.Secondary

@Preview(showBackground = true)
@Composable
fun AuthScreen(onSignUpClicked: () -> Unit = {}, onLoginClicked: () -> Unit = {}) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
  ) {

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
      onClick = onSignUpClicked,
      text = stringResource(R.string.sign_up),
      colors = ButtonDefaults.buttonColors(containerColor = Secondary)
    )

    Divider(thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp))

    AuthButton(
      onClick = { /* TODO Handle Google login button click */ },
      iconRes = R.drawable.ic_person,
      text = stringResource(R.string.login_as_a_guest),
      colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    )

    AuthButton(
      onClick = { /* TODO Handle Google login button click */ },
      iconRes = R.drawable.ic_google_logo,
      text = stringResource(R.string.login_with_google),
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDB4437))
    )

    AuthButton(
      onClick = { /* TODO Handle Facebook login button click */ },
      iconRes = R.drawable.ic_facebook_logo,
      text = stringResource(R.string.login_with_facebook),
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3b5998))
    )

    AuthButton(
      onClick = { /* TODO Handle Twitter login button click */ },
      iconRes = R.drawable.ic_twitter_logo,
      text = stringResource(R.string.login_with_twitter),
      colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00acee))
    )
  }
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