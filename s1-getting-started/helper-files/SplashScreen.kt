@Preview(showBackground = true)
@Composable
fun SplashScreen() {
  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Image(
      modifier = Modifier.size(128.dp),
      painter = painterResource(id = R.drawable.logo),
      contentDescription = "Logo Icon"
    )
  }
}