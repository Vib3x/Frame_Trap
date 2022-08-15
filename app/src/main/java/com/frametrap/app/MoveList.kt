package com.frametrap.app

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList


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

        toolbar.setNavigationOnClickListener { onBackPressed()}
        toolbar.setPadding(0, 200, 0, 0)

        val ir = BufferedReader(InputStreamReader(assets.open("$dir/$characterfile")))
        movelist = ArrayList()
        var line: String
        while (true) {
            line = ir.readLine() ?: break
            val row = line.split("\t").toTypedArray()
            val move = MoveModel(row[0], row[1], row[2], row[3], row[4], row[5])
            movelist.add(move)
        }
        movelistrecyclerviewadapter = MoveListRecyclerViewAdapter(movelist)
        movelistRecyclerView.setHasFixedSize(true)
        movelistRecyclerView.adapter = movelistrecyclerviewadapter
        movelistRecyclerView.apply { layoutManager = LinearLayoutManager(context) }

        editText = findViewById(R.id.movelist_edittext)
        editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }
        })
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