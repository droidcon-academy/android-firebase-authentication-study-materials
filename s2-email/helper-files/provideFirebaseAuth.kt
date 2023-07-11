@Singleton
@Provides
fun provideFirebaseAuthService(firebaseAuth: FirebaseAuth) : AuthService {
  return FirebaseAuthService(firebaseAuth)
}