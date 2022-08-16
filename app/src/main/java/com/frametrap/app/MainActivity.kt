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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frametrap.app.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import java.io.*


class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var prefeditor: SharedPreferences.Editor
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var gamespinner: Spinner
    private lateinit var dir: String
    private lateinit var characterRecyclerView: RecyclerView
    private lateinit var context: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        /*binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "not functional yet", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

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
        gamespinner.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, arrayOf("Persona 4 Arena Ultimax 2.0", "Capcom vs. SNK 2"))
        gamespinner.setSelection(lastSelection)
        gamespinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (id) {
                    0L -> {
                        supportActionBar!!.title = "Persona 4 Arena Ultimax 2.0"
                        dir = "p4au"
                    }
                    1L -> {
                        supportActionBar!!.title = "Capcom vs. SNK 2"
                        dir = "cvs2"
                    }
                }
                loadcharacterlist()
                //save selection
                prefeditor.putInt("lastSelection", position).commit()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                //fallback
                supportActionBar!!.title = "Persona 4 Arena Ultimax 2.0"
                dir = "p4au"
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

    private fun loadcharacterlist() {
        var inputStream: InputStream? = null
        val characterlist: ArrayList<CharacterModel> = ArrayList()
        try {
            inputStream = assets.open("$dir/characters.tsv")
            val reader = inputStream.bufferedReader()
            var line: String
            while (true) {
                line = reader.readLine() ?: break
                val row = line.split("\t").toTypedArray()
                val character = CharacterModel(row[0], row[1], row[2])
                characterlist.add(character)
            }
            val characterrecyclerviewadapter = CharacterRecyclerViewAdapter(characterlist)
            characterRecyclerView.adapter = characterrecyclerviewadapter
            characterRecyclerView.apply { layoutManager = GridLayoutManager(context, 2) }
            //Set OnClickListeners
            characterrecyclerviewadapter.setOnItemClickListener(object : CharacterRecyclerViewAdapter.OnItemClickListener{
                override fun onItemClick(position: Int) { openmovelist(characterlist[position].file) }
            })
        } catch (ex: IOException) {
            Toast.makeText(this, "Characterfile does not exist!", Toast.LENGTH_SHORT).show()
        } finally {
            inputStream?.close()
        }
    }

    private fun openmovelist(characterfile: String){
        val intent = Intent(context, MoveList::class.java)
        intent.putExtra(EXTRA_CHARACTER_FILE, characterfile)
        intent.putExtra(EXTRA_GAME_DIR, dir)
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
        const val EXTRA_CHARACTER_FILE = "com.frametrap.app.extra_character_file"
        const val EXTRA_GAME_DIR = "com.frametrap.app.extra_game_dir"
    }
}