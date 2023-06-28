package com.droidcon.authenticate.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.droidcon.authenticate.R
import com.droidcon.authenticate.ui.composables.droidconColors

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PasswordInputField(
  passwordValue: String,
  onPasswordValueChange: (String) -> Unit,
  hasPasswordError: Boolean = false,
  passwordError: String? = null,
  label: String
) {
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  TextField(
    value = passwordValue,
    onValueChange = onPasswordValueChange,
    isError = hasPasswordError,
    label = { Text(text = label) },
    keyboardOptions = KeyboardOptions.Default.copy(
      keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
    ),
    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
    colors = droidconColors(),
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    trailingIcon = {
      val resource = if (passwordVisible) R.drawable.ic_invisible else R.drawable.ic_visible

      IconButton(onClick = { passwordVisible = !passwordVisible }) {
        Icon(painter = painterResource(id = resource), null)
      }
    },
    supportingText = if (hasPasswordError && passwordError != null) {
      { Text(text = passwordError) }
    } else null
  )
}