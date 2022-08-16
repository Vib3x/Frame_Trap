package com.frametrap.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*


class MoveList : AppCompatActivity() {
    private lateinit var movelistRecyclerView: RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var context: Context
    private lateinit var editText: EditText
    private lateinit var movelistrecyclerviewadapter: MoveListRecyclerViewAdapter
    private lateinit var movelist: ArrayList<MoveModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_list)

        val intent = intent
        val characterfile = intent.getStringExtra(MainActivity.EXTRA_CHARACTER_FILE)
        val dir = intent.getStringExtra(MainActivity.EXTRA_GAME_DIR)

        context = this
        toolbar = findViewById(R.id.toolbar_movelist)
        movelistRecyclerView = findViewById(R.id.move_list_recyclerview)
        editText = findViewById(R.id.movelist_edittext)

        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.setPadding(0, 200, 0, 0)

        editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }
        })

        loadmovelist(this, dir, characterfile)
    }

    private fun loadmovelist(cntxt: Context, dir: String?, characterfile:String?){
        var inputStream: InputStream? = null
        try {
            inputStream = assets.open("$dir/$characterfile")
            val reader = inputStream.bufferedReader()
            movelist = ArrayList()
            var line: String
            while (true) {
                line = reader.readLine() ?: break
                val row = line.split("\t").toTypedArray()
                val move = MoveModel(row[0], row[1], row[2], row[3], row[4], row[5])
                movelist.add(move)
            }
            movelistrecyclerviewadapter = MoveListRecyclerViewAdapter(movelist)
            movelistRecyclerView.setHasFixedSize(true)
            movelistRecyclerView.adapter = movelistrecyclerviewadapter
            movelistRecyclerView.apply { layoutManager = LinearLayoutManager(cntxt) }
        } catch (ex: IOException) {
            Toast.makeText(this, "Characterfile does not exist!", Toast.LENGTH_SHORT).show()
        } finally {
            inputStream?.close()
        }
    }

    private fun filter(text: String) {
        val filtedList = ArrayList<MoveModel>()
        movelist.forEach {
            if (it.movename.lowercase().contains(text.lowercase()) or it.movetype.lowercase().contains(text.lowercase())) {
                filtedList.add(it)
            }
        }
        movelistrecyclerviewadapter.filterList(filtedList)
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) { result = resources.getDimensionPixelSize(resourceId) }
        return result
    }
}