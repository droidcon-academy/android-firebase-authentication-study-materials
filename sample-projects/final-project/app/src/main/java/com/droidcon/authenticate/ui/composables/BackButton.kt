package com.droidcon.authenticate.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.droidcon.authenticate.ui.theme.Secondary

@Composable
fun BackButton(modifier: Modifier = Modifier, onBackClicked: () -> Unit) {
  IconButton(
    onClick = onBackClicked,
    modifier = modifier
      .padding(16.dp)
      .clip(CircleShape)
      .background(Secondary)
  ) {
    Icon(
      imageVector = Icons.Default.ArrowBack,
      contentDescription = null,
      modifier = Modifier.size(64.dp)
    )
  }
}