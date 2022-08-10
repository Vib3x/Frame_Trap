package com.frametrap.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class character_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        List<String> characters = new ArrayList<String>();
        characters.add("Ken");
        characters.add("Akuma");
        characters.add("Terry");
        characters.add("Geese Howard");
        characters.add("Ryu");
        characters.add("Yun");
        characters.add("Iori");
        characters.add("Eagle");
        characters.add("Dan");
        characters.add("Sakura");
        characters.add("M. Bison");
        characters.add("Vega");
        characters.add("Sagat");
        characters.add("Kyo");
        characters.add("Blanka");
        characters.add("Dalsim");
        characters.add("Guile");
        characters.add("Rugal");


        Collections.sort(characters);
        LinearLayout list_view = (LinearLayout) findViewById(R.id.list_characters);

        for (String character_name : characters) {
            TextView character = new TextView(this);
            character.setText(character_name);
            character.setTextSize(32);
            character.setPadding(0,10,0,10);
            character.setGravity(Gravity.CENTER);
            character.setClickable(true);
            list_view.addView(character);

            character.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openmovelist(character_name);
                }
            });
        }
    }
    public void openmovelist(String character_name) {
        Intent intent = new Intent(this, move_list.class);
        startActivity(intent);
    }
}