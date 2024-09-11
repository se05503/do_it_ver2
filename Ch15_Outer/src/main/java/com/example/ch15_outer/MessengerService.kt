package com.example.ch15_outer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.Toast


class MessengerService : Service() {

    class MessengerBinder : Binder() {
        fun temp(num: Int):Int {
            return num*num
        }
    }

    override fun onBind(intent: Intent): IBinder {
        // Return the communication channel to the service.
        // bindService() 함수로 서비스를 실행하면 호출됨
        // 서비스를 실행한 곳에 IBinder 인터페이스를 구현한 객체 바인딩(전달)
        return MessengerBinder()
    }
}