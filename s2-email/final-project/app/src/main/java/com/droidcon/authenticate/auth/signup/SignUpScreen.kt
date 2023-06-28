package com.droidcon.authenticate.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.droidcon.authenticate.auth.AuthUiState
import com.droidcon.authenticate.R
import com.droidcon.authenticate.ui.composables.BackButton
import com.droidcon.authenticate.ui.composables.PasswordInputField
import com.droidcon.authenticate.ui.composables.ProgressDialog
import com.droidcon.authenticate.ui.composables.droidconColors
import com.droidcon.authenticate.ui.theme.Primary
import com.droidcon.authenticate.ui.theme.PrimaryVariant
import com.droidcon.authenticate.ui.theme.Secondary

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
  onBackClicked: () -> Unit = {},
  onSignUpSuccess: () -> Unit = {},
  viewModel: SignUpViewModel = hiltViewModel()
) {
  val uiState by viewModel.signUpUiState.collectAsState()
  Box(modifier = Modifier.fillMaxSize()) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.Center,
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_baseline_signup),
        contentDescription = null,
        modifier = Modifier
          .size(256.dp)
          .padding(bottom = 24.dp)
          .align(Alignment.CenterHorizontally),
        tint = PrimaryVariant
      )

      Text(
        text = stringResource(R.string.sign_up_welcome_label),
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 8.dp),
        color = Secondary
      )

      Text(
        text = stringResource(R.string.sign_up_fill_information_label),
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 16.dp)
      )

      TextField(value = uiState.email,
        onValueChange = { viewModel.updateSignUpEmail(email = it) },
        label = { Text(text = stringResource(R.string.email_address)) },
        keyboardOptions = KeyboardOptions.Default.copy(
          keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
        ),
        isError = uiState.hasEmailError,
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp),
        colors = droidconColors(),
        supportingText = {
          if (uiState.hasEmailError) Text(text = stringResource(R.string.error_valid_email_address))
        })


      PasswordInputField(
        passwordValue = uiState.password,
        onPasswordValueChange = viewModel::updateSignUpPassword,
        hasPasswordError = uiState.hasPasswordError,
        passwordError = uiState.passwordError?.let { stringResource(id = it) },
        label = stringResource(R.string.password)
      )

      PasswordInputField(
        passwordValue = uiState.confirmPassword,
        onPasswordValueChange = viewModel::updateConfirmPassword,
        hasPasswordError = uiState.hasPasswordError,
        label = stringResource(R.string.confirm_password)
      )

      Button(
        onClick = { viewModel.signUp() },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Primary)
      ) {
        Text(text = stringResource(R.string.sign_up), fontSize = 18.sp)
      }
    }
    BackButton(modifier = Modifier.align(Alignment.TopStart), onBackClicked)
    val hostState = remember { SnackbarHostState() }

    SnackbarHost(
      hostState = hostState, modifier = Modifier.align(Alignment.BottomEnd)
    ) { snackBarData ->
      Snackbar(snackbarData = snackBarData)
    }

    when (val signUpState = uiState.signUpState) {
      is AuthUiState.Failure -> {
        LaunchedEffect(key1 = Unit) {
          hostState.showSnackbar(signUpState.throwable?.message.toString())
        }
      }

      AuthUiState.Idle -> {}
      AuthUiState.Loading -> ProgressDialog()
      AuthUiState.Success -> onSignUpSuccess()
    }
  }
}

