package com.example.ecommerceapp.di

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate(){
        super.onCreate()
        // Initialize firebase
        FirebaseApp.initializeApp(this)
    }
}