  val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
    object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
      override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        Toast.makeText(
          context, context.getString(R.string.verification_successful), Toast.LENGTH_SHORT
        ).show()
        loading.value = false
      }

      override fun onVerificationFailed(exception: FirebaseException) {
        Toast.makeText(
          context,
          context.getString(R.string.verification_failed, exception.message),
          Toast.LENGTH_LONG
        ).show()
        loading.value = false
      }

      override fun onCodeSent(
        verificationId: String, resendingToken: PhoneAuthProvider.ForceResendingToken
      ) {
        super.onCodeSent(verificationId, resendingToken)
        verificationID.value = verificationId
        codeSent.value = true
        loading.value = false
      }
    }