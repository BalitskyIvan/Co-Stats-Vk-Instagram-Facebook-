package balitskyivan.presentation.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import balitskyivan.presentation.di.Injector

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.initAppComponent(this)
    }

}