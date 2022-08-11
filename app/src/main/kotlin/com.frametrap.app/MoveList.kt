package com.frametrap.app

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MoveList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_list)
        val intent = intent
        val charactername = intent.getStringExtra(CharacterList.EXTRA_CHARACTER_NAME)
        val game = intent.getStringExtra(CharacterList.EXTRA_GAME_NAME)
        supportActionBar!!.title = charactername

        val table = findViewById<View>(R.id.table_moves) as TableLayout
        val ir = BufferedReader(InputStreamReader(assets.open(game + "_framedata/" + charactername + ".csv")))

        try {
            var line: String
            while (true) {
                line = ir.readLine() ?: break
                val currentRow = TableRow(this)
                val row = line.split(",").toTypedArray()

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

                table.addView(currentRow)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}