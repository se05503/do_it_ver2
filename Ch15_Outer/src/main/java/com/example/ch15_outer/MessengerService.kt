package com.example.ch15_outer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.Toast


class MessengerService : Service() {

    lateinit var messenger: Messenger
    internal class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ): Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            /*
            외부에서 서비스에 데이터를 전달할 때 자동으로 호출됨
            parameter(msg) = 외부에서 전달받은 데이터
            msg.what = 어떤 성격의 데이터인지 구분
            msg.obj = 전달된 데이터
            */
            when(msg.what) {
                10 -> Toast.makeText(applicationContext, "${msg.obj}", Toast.LENGTH_LONG).show()
                20 -> Toast.makeText(applicationContext, "${msg.obj}", Toast.LENGTH_LONG).show()
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        messenger = Messenger(IncomingHandler(this))
        return messenger.binder
    }
}