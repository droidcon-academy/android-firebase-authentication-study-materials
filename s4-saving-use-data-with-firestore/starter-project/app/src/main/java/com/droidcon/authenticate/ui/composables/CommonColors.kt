package com.droidcon.authenticate.ui.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.droidcon.authenticate.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun droidconColors(): TextFieldColors {
  return TextFieldDefaults.textFieldColors(
    containerColor = Color(0xffEAECF0),
    focusedIndicatorColor = Secondary,
    focusedLabelColor = Secondary
  )
}