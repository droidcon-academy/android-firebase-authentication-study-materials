package com.droidcon.authenticate.multifactor

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.authenticate.R
import com.droidcon.authenticate.ui.composables.BackButton
import com.droidcon.authenticate.ui.composables.droidconColors
import com.droidcon.authenticate.ui.theme.Primary
import com.droidcon.authenticate.ui.theme.PrimaryVariant
import com.droidcon.authenticate.ui.theme.Secondary
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@Composable
fun MultiFactorPhoneScreen(
  onBackClicked: () -> Unit = {},
  firebaseAuth: FirebaseAuth,
) {
  var phoneNumber by remember { mutableStateOf("") }
  var otp by remember { mutableStateOf("") }
  val verificationID = remember { mutableStateOf("") }
  val codeSent = remember { mutableStateOf(false) }
  val loading = remember { mutableStateOf(false) }
  val coroutineScope = rememberCoroutineScope()
  val context = LocalContext.current

  val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
    object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
      override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        Toast.makeText(
          context, context.getString(R.string.verification_successful), Toast.LENGTH_SHORT
        ).show()
        loading.value = false
      }

      override fun onVerificationFailed(exception: FirebaseException) {
        Toast.makeText(
          context,
          context.getString(R.string.verification_failed, exception.message),
          Toast.LENGTH_LONG
        ).show()
        loading.value = false
      }

      override fun onCodeSent(
        verificationId: String, resendingToken: PhoneAuthProvider.ForceResendingToken
      ) {
        super.onCodeSent(verificationId, resendingToken)
        verificationID.value = verificationId
        codeSent.value = true
        loading.value = false
      }
    }

  Box(modifier = Modifier.fillMaxSize()) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.Center,
    ) {
      Icon(
        painter = painterResource(id = R.drawable.phone_msg_24),
        contentDescription = null,
        modifier = Modifier
          .size(256.dp)
          .padding(bottom = 24.dp)
          .align(Alignment.CenterHorizontally),
        tint = PrimaryVariant
      )
      val titleHeader = "Add MultiFactor Auth"
      Text(
        text = titleHeader,
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 8.dp),
        color = Secondary
      )

      val subTitle = "Please Provide your phone number for Multi Factor"
      Text(
        text = subTitle,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 16.dp)
      )
      TextField(
        enabled = !codeSent.value && !loading.value,
        value = phoneNumber,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { if (it.length <= 10) phoneNumber = it },
        placeholder = { Text(text = stringResource(R.string.enter_your_phone_number)) },
        modifier = Modifier.fillMaxWidth(),
        supportingText = {
          Text(
            text = "${phoneNumber.length} / 10",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
          )
        },
        colors = droidconColors(),
      )

      AnimatedVisibility(
        visible = !codeSent.value,
      ) {
        Button(
          colors = ButtonDefaults.buttonColors(containerColor = Primary),
          enabled = !loading.value && !codeSent.value,
          onClick = {
            if (phoneNumber.isPhoneNumberNotValid()) {
              Toast.makeText(
                context, R.string.please_enter_a_valid_phone_number, Toast.LENGTH_SHORT
              ).show()
            } else {
              loading.value = true
              coroutineScope.launch {
                firebaseAuth.sendPhoneVerificationSMS(
                  callbacks,
                  context as Activity,
                  "+44$phoneNumber",
                )
              }
            }
          },
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Text(text = stringResource(R.string.send_sms), modifier = Modifier.padding(8.dp))
        }
      }

      AnimatedVisibility(visible = codeSent.value) {
        Column {
          TextField(enabled = !loading.value,
            value = otp,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { if (it.length <= 6) otp = it },
            placeholder = { Text(text = stringResource(R.string.otp)) },
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
              Text(
                text = "${otp.length} / 6",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
              )
            })

          Spacer(modifier = Modifier.height(10.dp))

          Button(
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            enabled = !loading.value,
            onClick = {
              if (otp.isOtpNotValid()) {
                Toast.makeText(context, R.string.please_enter_a_correct_otp, Toast.LENGTH_SHORT)
                  .show()
              } else {
                loading.value = true
                val credential = PhoneAuthProvider.getCredential(verificationID.value, otp)
                // TODO add the logic for enrolling multi factor
              }
            },
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)
          ) {
            Text(text = stringResource(R.string.check_otp), modifier = Modifier.padding(8.dp))
          }
        }
      }
    }

    BackButton(modifier = Modifier.align(Alignment.TopStart), onBackClicked)
  }

  if (loading.value) {
    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
  }
}

private fun FirebaseAuth.sendPhoneVerificationSMS(
  callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
  activity: Activity,
  phoneNumber: String,
) {

  val options = PhoneAuthOptions.newBuilder(this)
      .setTimeout(60L, TimeUnit.SECONDS)
      .setActivity(activity)
      .setCallbacks(callbacks)
      .setPhoneNumber(phoneNumber)

  PhoneAuthProvider.verifyPhoneNumber(options.build())
}


private fun String.isPhoneNumberNotValid() = this.isEmpty() || this.length < 10

private fun String.isOtpNotValid() = this.isEmpty() || this.length < 6

