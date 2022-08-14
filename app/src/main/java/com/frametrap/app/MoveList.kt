package com.frametrap.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader


class MoveList : AppCompatActivity() {
    private lateinit var movelistRecyclerView: RecyclerView
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_list)
        val intent = intent
        val characterfile = intent.getStringExtra(MainActivity.EXTRA_CHARACTER_NAME)
        val game = intent.getStringExtra(MainActivity.EXTRA_GAME_NAME)
        supportActionBar!!.title = characterfile?.dropLast(4)

        //val table = findViewById<View>(R.id.table_moves) as TableLayout
        val ir = BufferedReader(InputStreamReader(assets.open(game + "_framedata/" + characterfile)))
        movelistRecyclerView = findViewById(R.id.move_list_recyclerview)
        context = this
        val movelist: ArrayList<MoveModel> = ArrayList()
        var line: String
        while (true) {
            line = ir.readLine() ?: break
            //val currentRow = TableRow(this)
            val row = line.split(",").toTypedArray()
            val move = MoveModel(row[0], row[1], row[2], row[3], row[4])
            movelist.add(move)
        }
        val view: TextView = findViewById<TextView>(R.id.move_name_header)
        view.measure(0,0)
        val movenamewidth: Int = view.measuredWidth
        val startupwidth: Int = findViewById<TextView>(R.id.startup_header).measuredWidth
        val activewidth: Int = findViewById<TextView>(R.id.active_header).measuredWidth
        val recoverywidth: Int = findViewById<TextView>(R.id.recovery_header).measuredWidth
        val onblockwidth: Int = findViewById<TextView>(R.id.onblock_header).measuredWidth
        Log.d("MoveList", "movenamewidth: " + movenamewidth)
        Log.d("MoveList", "startupwidth: " + startupwidth)
        val movelistrecyclerviewadapter = MoveListRecyclerViewAdapter(movelist, movenamewidth, startupwidth, activewidth, recoverywidth, onblockwidth)
        movelistRecyclerView.setHasFixedSize(true)
        movelistRecyclerView.adapter = movelistrecyclerviewadapter
        movelistRecyclerView.apply { layoutManager = LinearLayoutManager(context) }

                /*
                val movename = TextView(this)
                movename.gravity = Gravity.START
                movename.setPadding(25, 10, 0, 10)
                movename.textSize = 20f
                movename.setTypeface(null, Typeface.BOLD)
                movename.text = row[0]
                currentRow.addView(movename)

                val startup = TextView(this)
                startup.gravity = Gravity.END
                startup.setPadding(0, 10, 25, 10)
                startup.textSize = 20f
                startup.text = row[1]
                currentRow.addView(startup)

                val active = TextView(this)
                active.gravity = Gravity.END
                active.setPadding(0, 10, 25, 10)
                active.textSize = 20f
                active.text = row[2]
                currentRow.addView(active)

                val recovery = TextView(this)
                recovery.gravity = Gravity.END
                recovery.setPadding(0, 10, 25, 10)
                recovery.textSize = 20f
                recovery.text = row[3]
                currentRow.addView(recovery)

                val onblock = TextView(this)
                onblock.gravity = Gravity.END
                onblock.setPadding(0, 10, 25, 10)
                onblock.textSize = 20f
                onblock.text = row[4]
                currentRow.addView(onblock)

                table.addView(currentRow)*/


    }
}