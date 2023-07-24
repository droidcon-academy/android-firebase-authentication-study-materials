private fun formatBirthday(birthday: Date?) : String {
  val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
  return if (birthday != null) simpleDateFormat.format(brithday) else "Not Set"
}