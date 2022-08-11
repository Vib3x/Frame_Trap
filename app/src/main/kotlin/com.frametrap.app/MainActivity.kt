package com.frametrap.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<View>(R.id.button_cvs2) as Button
        button.setOnClickListener { opencharacterlist() }
    }

    private fun opencharacterlist() {
        val intent = Intent(this, CharacterList::class.java)
        startActivity(intent)
    }
}