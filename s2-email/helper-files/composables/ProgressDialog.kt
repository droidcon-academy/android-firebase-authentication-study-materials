package com.droidcon.authenticate.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.droidcon.authenticate.R
import com.droidcon.authenticate.theme.Secondary

@Preview
@Composable
fun ProgressDialog(text: String = stringResource(R.string.please_wait_label)) {
  Dialog(
    onDismissRequest = { },
    DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier.background(Color.White, shape = RoundedCornerShape(8.dp))
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .padding(16.dp)
          .wrapContentSize()
      ) {
        CircularProgressIndicator(color = Secondary)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text)
      }
    }
  }
}