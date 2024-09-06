package com.example.ch15_outer

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyMessengerService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}