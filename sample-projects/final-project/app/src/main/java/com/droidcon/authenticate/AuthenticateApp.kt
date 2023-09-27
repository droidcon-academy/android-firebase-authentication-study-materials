package com.droidcon.authenticate

import android.app.Application
import com.droidcon.authenticate.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AuthenticateApp : Application() {

	override fun onCreate() {
		super.onCreate()
		if (BuildConfig.DEBUG){
			Timber.plant(Timber.DebugTree())
		}
	}
}
