package com.droidcon.authenticate.di


import com.droidcon.authenticate.data.AuthService
import com.droidcon.authenticate.data.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

  @Singleton
  @Provides
  fun provideFirebaseAuth() : FirebaseAuth {
    return FirebaseAuth.getInstance()
  }

  @Singleton
  @Provides
  fun provideFirebaseAuthService(firebaseAuth: FirebaseAuth) : AuthService {
    return FirebaseAuthService(firebaseAuth)
  }
}