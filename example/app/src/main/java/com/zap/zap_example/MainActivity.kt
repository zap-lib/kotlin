package com.zap.zap_example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayButton = findViewById<Button>(R.id.displayButton)
        displayButton.setOnClickListener {
            if (!ServerService.isWorking.get()) {
                startService(Intent(this, ServerService::class.java))
            }

            startActivity(Intent(this, DisplayActivity::class.java))
        }

        val controllerButton = findViewById<Button>(R.id.controllerButton)
        controllerButton.setOnClickListener {
            if (!ClientService.isWorking.get()) {
                startService(Intent(this, ClientService::class.java))
            }
        }
    }
}