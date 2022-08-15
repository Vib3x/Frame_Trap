package com.frametrap.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class MoveList : AppCompatActivity() {
    private lateinit var movelistRecyclerView: RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var context: Context

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
        val movelist: ArrayList<MoveModel> = ArrayList()
        var line: String
        while (true) {
            line = ir.readLine() ?: break
            val row = line.split("\t").toTypedArray()
            val move = MoveModel(row[0], row[1], row[2], row[3], row[4], row[5])
            movelist.add(move)
        }
        val movelistrecyclerviewadapter = MoveListRecyclerViewAdapter(movelist)
        movelistRecyclerView.setHasFixedSize(true)
        movelistRecyclerView.adapter = movelistrecyclerviewadapter
        movelistRecyclerView.apply { layoutManager = LinearLayoutManager(context) }
    }
    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) { result = resources.getDimensionPixelSize(resourceId) }
        return result
    }
}