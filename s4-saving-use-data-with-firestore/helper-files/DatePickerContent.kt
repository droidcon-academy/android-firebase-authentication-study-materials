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