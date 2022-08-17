package com.frametrap.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.frametrap.app.databinding.ActivityMainBinding
import java.io.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var prefeditor: SharedPreferences.Editor
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var gamespinner: Spinner
    private lateinit var dir: String
    private lateinit var characterrecyclerviewadapter: CharacterRecyclerViewAdapter
    private lateinit var characterlist: ArrayList<CharacterModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.contentMain.toolbar)

        sharedPreferences = getSharedPreferences("LastSetting", Context.MODE_PRIVATE)
        prefeditor = sharedPreferences.edit()
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupNavigationListener()

        binding.contentMain.charactersRecyclerview.layoutManager = GridLayoutManager(this, 2)
        binding.contentMain.charactersRecyclerview.setHasFixedSize(true)

        setupgameselector()
    }

    private fun setupNavigationListener(){
        binding.navView.setNavigationItemSelectedListener {
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

    private fun setupgameselector(){
        gamespinner = binding.navView.menu.findItem(R.id.nav_game_spinner).actionView as Spinner
        gamespinner.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, arrayOf("Persona 4 Arena Ultimax 2.0", "Capcom vs. SNK 2"))
        gamespinner.setSelection(sharedPreferences.getInt("lastSelection", 0))
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
                prefeditor.putInt("lastSelection", position).commit()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                //fallback
                supportActionBar!!.title = "Persona 4 Arena Ultimax 2.0"
                dir = "p4au"
            }
        }
    }

    private fun loadcharacterlist() {
        fillcharacterlist()
        characterrecyclerviewadapter = CharacterRecyclerViewAdapter(characterlist)
        binding.contentMain.charactersRecyclerview.adapter = characterrecyclerviewadapter
        //Set OnClickListeners
        characterrecyclerviewadapter.setOnItemClickListener(object : CharacterRecyclerViewAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) { openmovelist(characterlist[position].file) }
        })
    }

    private fun fillcharacterlist(){
        characterlist = ArrayList()
        var inputStream: InputStream? = null
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
        } catch (ex: IOException) {
            Toast.makeText(this, "Characterlist file does not exist!", Toast.LENGTH_SHORT).show()
        } finally {
            inputStream?.close()
        }
    }

    private fun openmovelist(characterfile: String){
        val intent = Intent(this, MoveList::class.java)
        intent.putExtra(EXTRA_CHARACTER_FILE, characterfile)
        intent.putExtra(EXTRA_GAME_DIR, dir)
        startActivity(intent)
    }

    private fun filter(text : String){
        val filteredList = ArrayList<CharacterModel>()
        characterlist.forEach {
            if (it.name_West.lowercase().contains(text.lowercase()) or it.name_JP.lowercase().contains(text.lowercase())) {
                filteredList.add(it)
            }
        }
        characterrecyclerviewadapter.filterList(filteredList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val search = menu.findItem(R.id.action_search)
        val searchview = search.actionView as SearchView
        searchview.queryHint = "Type to search"
        /*searchview.isIconifiedByDefault = true
        searchview.isFocusable = true
        searchview.isIconified = false
        searchview.requestFocusFromTouch()*/

        searchview.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filter(p0.toString())
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
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