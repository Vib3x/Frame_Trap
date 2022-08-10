package com.frametrap.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class move_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_list);

        Intent intent = getIntent();
        String character_name = intent.getStringExtra(character_list.EXTRA_CHARACTER_NAME);
        String game = intent.getStringExtra(character_list.EXTRA_GAME_NAME);

        getSupportActionBar().setTitle(character_name);
        TableLayout table = (TableLayout) findViewById(R.id.table_moves);
        //InputStream is = getResources().open(getResources().getIdentifier(character_name, "raw", this.getPackageName()));
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(getAssets().open(game + "_framedata/" + character_name + ".csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(is);
        try {
            String line;
            while(((line = reader.readLine()) != null)){
                TableRow currentRow = new TableRow(this);
                String[] row = line.split(",");

                TextView move_name = new TextView(this);
                move_name.setGravity(Gravity.START);
                move_name.setPadding(25,10,0,10);
                move_name.setTextSize(20);
                move_name.setTypeface(null, Typeface.BOLD);
                move_name.setText(row[0]);
                currentRow.addView(move_name);

                TextView startup = new TextView(this);
                startup.setGravity(Gravity.END);
                startup.setPadding(0,10,25,10);
                startup.setTextSize(20);
                startup.setText(row[1]);
                currentRow.addView(startup);

                TextView active = new TextView(this);
                active.setGravity(Gravity.END);
                active.setPadding(0,10,25,10);
                active.setTextSize(20);
                active.setText(row[2]);
                currentRow.addView(active);

                TextView recovery = new TextView(this);
                recovery.setGravity(Gravity.END);
                recovery.setPadding(0,10,25,10);
                recovery.setTextSize(20);
                recovery.setText(row[3]);
                currentRow.addView(recovery);

                TextView onblock = new TextView(this);
                onblock.setGravity(Gravity.END);
                onblock.setPadding(0,10,25,10);
                onblock.setTextSize(20);
                onblock.setText(row[4]);
                currentRow.addView(onblock);

                table.addView(currentRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}