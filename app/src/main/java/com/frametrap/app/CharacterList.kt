package com.frametrap.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.view.Gravity
import android.content.Intent
import android.view.View
import java.io.IOException
import java.util.*

class CharacterList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        val game = "CvS2"
        var characters: Array<String>? = null
        if (game == "CvS2") {
            supportActionBar!!.title = "Capcom vs. SNK 2"
        } else {
            supportActionBar!!.title = game
        }
        try {
            characters = assets.list(game + "_framedata")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Arrays.sort(characters)
        val listview = findViewById<View>(R.id.list_characters) as LinearLayout
        for (characterfile in characters!!) {
            val character = TextView(this)
            val charactername = characterfile.substring(0, characterfile.indexOf("."))
            character.text = charactername
            character.textSize = 32f
            character.setPadding(0, 10, 0, 10)
            character.gravity = Gravity.CENTER
            character.isClickable = true
            listview.addView(character)
            character.setOnClickListener { openmovelist(charactername, game) }
        }
    }

    fun openmovelist(character_name: String?, game: String?) {
        val intent = Intent(this, MoveList::class.java)
        intent.putExtra(EXTRA_CHARACTER_NAME, character_name)
        intent.putExtra(EXTRA_GAME_NAME, game)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_CHARACTER_NAME = "com.frametrap.app.extra_character_name"
        const val EXTRA_GAME_NAME = "com.frametrap.app.extra_game_name"
    }
}