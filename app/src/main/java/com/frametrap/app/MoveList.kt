package com.frametrap.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.frametrap.app.databinding.ActivityMoveListBinding
import java.io.IOException
import java.io.InputStream
import kotlin.collections.ArrayList


class MoveList : AppCompatActivity() {
    private lateinit var binding: ActivityMoveListBinding
    private lateinit var movelistrecyclerviewadapter: MoveListRecyclerViewAdapter
    private lateinit var movelist: ArrayList<MoveModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMovelist)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val characterfile = intent.getStringExtra(MainActivity.EXTRA_CHARACTER_FILE)
        val dir = intent.getStringExtra(MainActivity.EXTRA_GAME_DIR)

        binding.toolbarMovelist.setNavigationOnClickListener { onBackPressed() }

        /*binding.movelistEdittext.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }
        })*/
        showmovelist(this, dir, characterfile)
    }

    private fun showmovelist(cntxt: Context, dir: String?, characterfile:String?){
        fillmovelist(dir, characterfile)
        movelistrecyclerviewadapter= MoveListRecyclerViewAdapter(movelist)
        binding.moveListRecyclerview.setHasFixedSize(true)
        binding.moveListRecyclerview.adapter = movelistrecyclerviewadapter
        binding.moveListRecyclerview.apply { layoutManager = LinearLayoutManager(cntxt) }
    }

    private fun fillmovelist(dir: String?, characterfile: String?){
        movelist = ArrayList()
        var inputStream: InputStream? = null
        try{
            inputStream = assets.open("$dir/$characterfile")
            val reader = inputStream.bufferedReader()
            var line: String
            while (true) {
                line = reader.readLine() ?: break
                val row = line.split("\t").toTypedArray()
                val move = MoveModel(row[0], row[1], row[2], row[3], row[4], row[5])
                movelist.add(move)
            }
        } catch (ex: IOException) {
            Toast.makeText(this, "Characterfile does not exist!", Toast.LENGTH_SHORT).show()
        } finally {
            inputStream?.close()
        }
    }

    private fun filter(text: String) {
        val filteredList = ArrayList<MoveModel>()
        movelist.forEach {
            if (it.movename.lowercase().contains(text.lowercase()) or it.movetype.lowercase().contains(text.lowercase())) {
                filteredList.add(it)
            }
        }
        movelistrecyclerviewadapter.filterList(filteredList)
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

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) { result = resources.getDimensionPixelSize(resourceId) }
        return result
    }
}