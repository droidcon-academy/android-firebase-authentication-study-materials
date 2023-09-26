package com.droidcon.authenticate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.droidcon.authenticate.composables.BackButton
import com.droidcon.authenticate.composables.PasswordInputField
import com.droidcon.authenticate.composables.ProgressDialog
import com.droidcon.authenticate.composables.droidconColors
import com.droidcon.authenticate.ui.theme.Primary
import com.droidcon.authenticate.ui.theme.PrimaryVariant
import com.droidcon.authenticate.ui.theme.Secondary

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
  onBackClicked: () -> Unit = {},
  onLoginSuccess: () -> Unit = {},
  viewModel: LoginViewModel = hiltViewModel()
) {
  val uiState by viewModel.loginUiState.collectAsState()
  val hostState = remember { SnackbarHostState() }
  Box(modifier = Modifier.fillMaxSize()) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.Center,
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_baseline_login_24),
        contentDescription = null,
        modifier = Modifier
          .size(256.dp)
          .padding(bottom = 24.dp)
          .align(Alignment.CenterHorizontally),
        tint = PrimaryVariant
      )

      Text(
        text = stringResource(R.string.welcome_back_label),
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 8.dp),
        color = Secondary
      )

      Text(
        text = stringResource(R.string.please_login_to_your_account_label),
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 16.dp)
      )

      TextField(value = uiState.email,
        isError = uiState.hasEmailError,
        onValueChange = { viewModel.updateEmail(email = it) },
        label = { Text(text = stringResource(R.string.email_address)) },
        keyboardOptions = KeyboardOptions.Default.copy(
          keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
        ),
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp),
        colors = droidconColors(),
        supportingText = {
          if (uiState.hasEmailError) Text(text = stringResource(R.string.error_valid_email_address))
        })

      PasswordInputField(
        passwordValue = uiState.password,
        onPasswordValueChange = viewModel::updatePassword,
        hasPasswordError = uiState.hasPasswordError,
        passwordError = stringResource(R.string.error_valid_password),
        label = stringResource(R.string.password)
      )

      Text(
        text = stringResource(R.string.forgot_password_label),
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
          .padding(vertical = 16.dp)
          .clickable { viewModel.showPasswordDialog() }
          .align(Alignment.CenterHorizontally),
      )

      Button(
        onClick = {
          viewModel.login()
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Primary)
      ) {
        Text(text = stringResource(id = R.string.login), fontSize = 18.sp)
      }

      if (uiState.showForgetPasswordDialog) {
        ForgetPasswordDialog(uiState, viewModel)
      }

      when (val state = uiState.loginState) {
        is AuthUiState.Failure -> {
          LaunchedEffect(key1 = Unit) {
            hostState.showSnackbar(state.throwable?.message.toString())
          }
        }

        AuthUiState.Idle -> {}
        AuthUiState.Loading -> ProgressDialog()
        AuthUiState.Success -> onLoginSuccess()
      }
    }
    SnackbarHost(
      hostState = hostState, modifier = Modifier.align(Alignment.BottomEnd)
    ) { snackBarData ->
      Snackbar(snackbarData = snackBarData)
    }
    BackButton(modifier = Modifier.align(Alignment.TopStart), onBackClicked)
  }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ForgetPasswordDialog(uiState: LoginUiState, viewModel: LoginViewModel) {
  Dialog(onDismissRequest = { }) {
    Card(
      shape = RoundedCornerShape(8.dp),
      modifier = Modifier.padding(8.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
      Column(
        Modifier
          .background(Color.White)
          .padding(4.dp)
      ) {
        Text(
          text = stringResource(id = R.string.forgot_password_label),
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
          fontSize = 20.sp,
          color = Secondary,
          fontWeight = FontWeight.Black
        )

        Text(
          text = stringResource(R.string.send_email_reset_link_label),
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
          fontSize = 16.sp
        )

        OutlinedTextField(value = uiState.forgetPasswordEmail,
          colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Secondary,
            focusedLabelColor = Secondary
          ),
          isError = uiState.hasForgetPasswordEmailError,
          onValueChange = { viewModel.updateForgetPasswordEmail(it) },
          modifier = Modifier.padding(horizontal = 8.dp),
          label = { Text(text = stringResource(id = R.string.email_address)) },
          supportingText = {
            if (uiState.hasForgetPasswordEmailError) Text(text = stringResource(R.string.error_valid_email_address))
          }

        )
        Spacer(modifier = Modifier.height(4.dp))

        Row {
          OutlinedButton(
            onClick = viewModel::hidePasswordDialog,
            modifier = Modifier
              .fillMaxWidth()
              .padding(4.dp)
              .weight(1f),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Secondary)
          ) {
            Text(text = stringResource(R.string.cancel))
          }

          Button(
            modifier = Modifier
              .fillMaxWidth()
              .padding(4.dp)
              .weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Secondary),
            onClick = viewModel::sendPasswordResetEmail,
          ) {
            Text(text = stringResource(R.string.send))
          }
        }
      }
    }
  }
}