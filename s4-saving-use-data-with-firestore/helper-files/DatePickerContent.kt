// Use these imports 
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.DatePickerDialog 
import androidx.compose.material3.DatePicker
import java.util.Date


// Add this code 
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
