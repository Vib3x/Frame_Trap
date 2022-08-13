package com.frametrap.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frametrap.app.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var prefeditor: SharedPreferences.Editor
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var gamespinner: Spinner
    private lateinit var game: String
    private lateinit var characterRecyclerView: RecyclerView
    private lateinit var context: Context


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
        context = this
        sharedPreferences = getSharedPreferences("LastSetting", Context.MODE_PRIVATE)
        prefeditor=sharedPreferences.edit()
        gamespinner = navView.menu.findItem(R.id.nav_game_spinner).actionView as Spinner
        characterRecyclerView = findViewById(R.id.characters_recyclerview)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //setup game selection spinner and save selection
        val lastSelection: Int = sharedPreferences.getInt("lastSelection", 0)
        gamespinner.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, arrayOf("Capcom vs. SNK 2", "Jojo's Bizarre Adventure"))
        gamespinner.setSelection(lastSelection)
        gamespinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (id) {
                    0L -> {
                        supportActionBar!!.title = "Capcom vs. SNK 2"
                        game = "CvS2"
                    }
                    1L -> {
                        supportActionBar!!.title = "Jojo's Bizarre Adventure"
                        game = "jojoban"
                    }
                    else -> {
                        // in case nothing is selected
                        supportActionBar!!.title = "Capcom vs. SNK 2"
                        game = "CvS2"
                    }
                }
                //Load Characterlist
                val characterfiles: Array<String>? = assets?.list(game + "_framedata")
                val characterrecyclerviewadapter = CharacterRecyclerViewAdapter(characterfiles)
                characterRecyclerView.adapter = characterrecyclerviewadapter
                characterRecyclerView.apply { layoutManager = GridLayoutManager(context, 2) }
                //Set OnClickListeners
                characterrecyclerviewadapter.setOnItemClickListener(object : CharacterRecyclerViewAdapter.OnItemClickListener{
                    override fun onItemClick(position: Int) {
                        //Switch view to movelist
                        val intent = Intent(context, MoveList::class.java)
                        intent.putExtra(EXTRA_CHARACTER_NAME, characterfiles?.get(position))
                        intent.putExtra(EXTRA_GAME_NAME, game)
                        startActivity(intent)
                    }

                })
                //save selection
                prefeditor.putInt("lastSelection", position).commit()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_about -> {
                    val intent = Intent(this, AboutActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
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