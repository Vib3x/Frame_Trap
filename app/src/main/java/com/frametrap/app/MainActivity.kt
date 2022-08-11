package com.frametrap.app

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.frametrap.app.databinding.ActivityMainBinding
import com.frametrap.app.ui.home.HomeFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

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
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

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
        /*if (game == "CvS2") {
            supportActionBar!!.title = "Capcom vs. SNK 2"
        } else {
            supportActionBar!!.title = game
        }*/
        val characters = assets?.list(game + "_framedata")

        Arrays.sort(characters)

        val listview: LinearLayout = binding.listcharacters

        for (characterfile in characters!!) {
            val character: TextView = TextView(this)
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

    fun openmovelist(charactername: String?, game: String?) {
        val intent = Intent(this, MoveList::class.java)
        intent.putExtra(HomeFragment.EXTRA_CHARACTER_NAME, charactername)
        intent.putExtra(HomeFragment.EXTRA_GAME_NAME, game)
        startActivity(intent)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_CHARACTER_NAME = "com.frametrap.app.extra_character_name"
        const val EXTRA_GAME_NAME = "com.frametrap.app.extra_game_name"
    }
}