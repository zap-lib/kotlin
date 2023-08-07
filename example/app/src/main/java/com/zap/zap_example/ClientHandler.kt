package com.zap.zap_example

import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream

class ClientHandler(
    private val inputStream: DataInputStream,
    private val outputStream: DataOutputStream,
) : Thread() {
    override fun run() {
        while (true) {
            try {
                if (inputStream.available() > 0) {
                    Log.i(TAG, inputStream.readUTF())
                    outputStream.writeUTF("Hi, this is server")
                    sleep(1000L)
                }
            } catch (e: Exception) {
                inputStream.close()
                outputStream.close()
                throw e
            }
        }
    }

    companion object {
        private val TAG = ClientHandler::class.java.simpleName
    }
}