private suspend fun resultFor(body: suspend () -> Unit) : Result<Boolean>{
  return try {
    body()
    Result.success(true)
  } catch (exception: Exception) {
    Result.failure(exception)
  }
}
