package com.droidcon.authenticate.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.droidcon.authenticate.R
import com.droidcon.authenticate.ui.composables.ProgressDialog
import com.droidcon.authenticate.ui.theme.Primary
import com.droidcon.authenticate.ui.theme.Secondary
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreen(
  viewModel: ProfileViewModel = hiltViewModel(),
  onSignOut: () -> Unit = {},
  onAccountDeleted: () -> Unit = {},
  onAddMultiFactor : () -> Unit = {}
) {
  Box(modifier = Modifier.fillMaxSize()) {
    val hostState = remember { SnackbarHostState() }

    Column(
      modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
    ) {
      val uiState by viewModel.uiState.collectAsState()

      UserProperty(label = stringResource(R.string.profile_userid_label), value = uiState.userId)
      Spacer(modifier = Modifier.height(8.dp))
      UserProperty(label = stringResource(id = R.string.email_address), value = uiState.userEmail.ifEmpty { stringResource(R.string.profile_name_not_set) })
      Spacer(modifier = Modifier.height(8.dp))
      UserProperty(label = stringResource(R.string.profile_display_name_label),
        value = uiState.userDisplayName.ifEmpty { stringResource(R.string.profile_name_not_set) },
        modifier = Modifier.clickable {
          viewModel.showDisplayNameDialog()
        })
      Spacer(modifier = Modifier.height(8.dp))
      UserProperty(label = stringResource(R.string.birthday_label),
        value = uiState.userBirthday.ifEmpty { stringResource(R.string.profile_name_not_set) },
        modifier = Modifier.clickable {
          viewModel.showDatePickerDialog()
        })

      Image(
        painter = painterResource(id = R.drawable.droidcon_logo_large),
        contentDescription = null,
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(bottom = 16.dp)
      )

      Button(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Primary),
        onClick = { viewModel.signOut(); onSignOut() }) {
        Icon(
          painter = painterResource(id = R.drawable.ic_baseline_exit_to_app_24),
          contentDescription = null
        )
        Text(
          text = stringResource(R.string.profile_sign_out),
          modifier = Modifier.weight(1f),
          textAlign = TextAlign.Center
        )
      }

      Button(
        modifier = Modifier
          .fillMaxWidth()
          .padding(4.dp),
        shape = RoundedCornerShape(4.dp),
        onClick = { viewModel.deleteAccount() },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_baseline_delete_24),
          contentDescription = null
        )
        Text(
          text = stringResource(R.string.profile_delete_account),
          modifier = Modifier.weight(1f),
          textAlign = TextAlign.Center
        )
      }

      Button(
        modifier = Modifier
          .fillMaxWidth()
          .padding(4.dp),
        shape = RoundedCornerShape(4.dp),
        onClick = { viewModel.sendEmailVerification() },
        colors = ButtonDefaults.buttonColors(containerColor = Secondary)
      ) {
        Icon(
          painter = painterResource(id = R.drawable.baseline_email_24), contentDescription = null
        )
        Text(
          text = stringResource(R.string.profile_send_email_verification),
          modifier = Modifier.weight(1f),
          textAlign = TextAlign.Center
        )
      }

      Button(
        modifier = Modifier
          .fillMaxWidth()
          .padding(4.dp),
        shape = RoundedCornerShape(4.dp),
        onClick = { onAddMultiFactor()  },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
      ) {
        Icon(
          painter = painterResource(id = R.drawable.phone_msg_24), contentDescription = null
        )
        Text(
          text = "Add Multi Factor Auth",
          modifier = Modifier.weight(1f),
          textAlign = TextAlign.Center
        )
      }

      if (uiState.isEmailSentSuccessfully == true) {
        val message = stringResource(R.string.profile_email_sent_successfully)
        hostState.showSnackBarMessage(message)
      }

      if (uiState.isAccountDeletedSuccessfully == true) {
        val message = stringResource(R.string.profile_account_deleted_successfully)
        hostState.showSnackBarMessage(message)
        onAccountDeleted()
      }

      if (uiState.isLoading) {
        ProgressDialog()
      }

      if (uiState.showDisplayNameDialog) {
        UserDisplayNameDialog(uiState, viewModel)
      }

      if (uiState.isDisplayNameUpdatedSuccessfully == true) {
        val message = stringResource(R.string.profile_name_was_updated_successfully)
        hostState.showSnackBarMessage(message)
      }

      val datePickerState = rememberDatePickerState()
      if (uiState.showDatePickerDialog) {
        DatePickerDialog(onDismissRequest = {
          viewModel.hideDatePickerDialog()
        }, confirmButton = {
          TextButton(onClick = {
            viewModel.requestBirthdayUpdate(Date(datePickerState.selectedDateMillis ?: 0))
          }) {
            Text(text = stringResource(R.string.ok))
          }
        }, dismissButton = {
          TextButton(onClick = { viewModel.hideDatePickerDialog() }) {
            Text(text = stringResource(R.string.cancel))
          }
        }) {
          DatePicker(state = datePickerState)
        }
      }
    }
    SnackbarHost(
      hostState = hostState, modifier = Modifier.align(Alignment.BottomEnd)
    ) { snackBarData ->
      Snackbar(snackbarData = snackBarData)
    }
  }

}

@Composable
private fun SnackbarHostState.showSnackBarMessage(
  message: String
) {
  LaunchedEffect(true) {
    showSnackbar(message)
  }
}

@Composable
fun UserProperty(modifier: Modifier = Modifier, label: String, value: String) {
  Column(modifier = modifier) {
    Text(
      text = label, style = MaterialTheme.typography.titleMedium
    )
    Text(
      text = value, style = MaterialTheme.typography.bodyMedium
    )
  }
}

@Composable
private fun UserDisplayNameDialog(profileUiState: ProfileUiState, viewModel: ProfileViewModel) {
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
          text = stringResource(R.string.profile_change_name_label),
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
          fontSize = 20.sp,
          color = Secondary,
          fontWeight = FontWeight.Black
        )

        Text(
          text = stringResource(R.string.profile_update_your_display_name),
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
          fontSize = 16.sp
        )

        OutlinedTextField(
          value = profileUiState.userUpdateDisplayName,
          colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Secondary,
            focusedLabelColor = Secondary,
          ),
          isError = profileUiState.hasUserDisplayNameError,
          onValueChange = { viewModel.updateUserDisplayName(it) },
          modifier = Modifier.padding(horizontal = 8.dp),
          label = { Text(text = stringResource(R.string.profile_new_name)) },
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row {
          OutlinedButton(
            onClick = viewModel::hideDisplayNameDialog,
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
            onClick = viewModel::requestUserDisplayNameUpdate,
          ) {
            Text(text = stringResource(R.string.profile_update_label))
          }
        }
      }
    }
  }
}

