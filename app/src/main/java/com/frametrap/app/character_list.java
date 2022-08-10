package com.frametrap.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class character_list extends AppCompatActivity {
    public static final String EXTRA_CHARACTER_NAME = "com.frametrap.app.extra_character_name";
    public static final String EXTRA_GAME_NAME = "com.frametrap.app.extra_game_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);


        String game = "CvS2";
        String[] characters = null;

        if (game.equals("CvS2")) {
            getSupportActionBar().setTitle("Capcom vs. SNK 2");
        }else{
            getSupportActionBar().setTitle(game);
        }

        try {
            characters = getAssets().list(game + "_framedata");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Arrays.sort(characters);
        LinearLayout list_view = (LinearLayout) findViewById(R.id.list_characters);

        for (String character_file : characters) {
            TextView character = new TextView(this);
            String character_name = character_file.substring(0, character_file.indexOf("."));
            character.setText(character_name);
            character.setTextSize(32);
            character.setPadding(0,10,0,10);
            character.setGravity(Gravity.CENTER);
            character.setClickable(true);
            list_view.addView(character);

            character.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openmovelist(character_name, game);
                }
            });
        }
    }
    public void openmovelist(String character_name, String game) {
        Intent intent = new Intent(this, move_list.class);
        intent.putExtra(EXTRA_CHARACTER_NAME, character_name);
        intent.putExtra(EXTRA_GAME_NAME, game);
        startActivity(intent);
    }
}