package com.zap.zap_example

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean

class ServerService : Service() {
    private var serverSocket: ServerSocket? = null
    private val runnable = Runnable {
        var socket: Socket? = null
        try {
            serverSocket = ServerSocket(PORT)

            while (isWorking.get()) {
                serverSocket?.let {
                    socket = it.accept()

                    val inputStream = DataInputStream(socket?.getInputStream())
                    val outputStream = DataOutputStream(socket?.getOutputStream())

                    ClientHandler(inputStream, outputStream).start()
                }
            }
        } catch (e: Exception) {
            socket?.close()
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
        private const val PORT = 9876
    }
}