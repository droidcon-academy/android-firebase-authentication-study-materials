// Use the following imports 
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale 


private fun formatBirthday(birthday: Date?) : String {
  val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
  return if (birthday != null) simpleDateFormat.format(birthday) else "Not Set"
}