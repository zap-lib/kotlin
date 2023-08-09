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
            startActivity(Intent(this, DisplayActivity::class.java))
        }

        val controllerButton = findViewById<Button>(R.id.controllerButton)
        controllerButton.setOnClickListener {
            startActivity(Intent(this, ControllerActivity::class.java))
        }
    }
}