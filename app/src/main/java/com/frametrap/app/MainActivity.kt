package com.frametrap.app

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.frametrap.app.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "not functional yet", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val popupmenu = PopupMenu(this, findViewById(R.id.nav_game))
        popupmenu.menuInflater.inflate(R.menu.game_menu, popupmenu.menu)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_game -> {
                    true
                }
                R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_about -> {
                    true
                }
                else -> false
            }
        }

        val game = "CvS2"
        if (game == "CvS2") {
            supportActionBar!!.title = "Capcom vs. SNK 2"
        } else {
            supportActionBar!!.title = game
        }
        val characters = assets?.list(game + "_framedata")

        Arrays.sort(characters)

        val listview = findViewById<View>(R.id.list_characters) as LinearLayout

        for (characterfile in characters!!) {
            val character = TextView(this)
            val charactername = characterfile.substring(0, characterfile.indexOf("."))
            character.text = charactername
            character.textSize = 32f
            character.setTextColor(getColor(R.color.white))
            character.setPadding(0, 10, 0, 10)
            character.gravity = Gravity.CENTER
            character.isClickable = true
            listview.addView(character)
            character.setOnClickListener { openmovelist(charactername, game) }
        }
    }

    private fun openmovelist(charactername: String?, game: String?) {
        val intent = Intent(this, MoveList::class.java)
        intent.putExtra(EXTRA_CHARACTER_NAME, charactername)
        intent.putExtra(EXTRA_GAME_NAME, game)
        startActivity(intent)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_CHARACTER_NAME = "com.frametrap.app.extra_character_name"
        const val EXTRA_GAME_NAME = "com.frametrap.app.extra_game_name"
    }
}