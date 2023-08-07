package com.zap.zap_example

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean

class ClientService : Service() {
    private val runnable = Runnable {
        val socket = Socket(IP, PORT)
        val inputStream = DataInputStream(socket.getInputStream())
        val outputStream = DataOutputStream(socket.getOutputStream())

        try {
            while (isWorking.get()) {
                outputStream.writeUTF("Hi, this is client")
                Log.i(TAG, inputStream.readUTF())
                Thread.sleep(1000L)
            }
        } catch (e: Exception) {
            inputStream.close()
            outputStream.close()
            throw e
        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        isWorking.set(true)
        Thread(runnable).start()
    }

    override fun onDestroy() {
        isWorking.set(false)
    }

    companion object {
        val isWorking = AtomicBoolean(false)
        private val TAG = ServerService::class.java.simpleName
        private val IP = InetAddress.getByName("192.168.0.30")
        private const val PORT = 9876
    }
}