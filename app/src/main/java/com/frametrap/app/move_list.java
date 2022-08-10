package com.frametrap.app;

import androidx.appcompat.app.AppCompatActivity;

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

        TableLayout table = (TableLayout) findViewById(R.id.table_moves);
        InputStream is = getResources().openRawResource(R.raw.ken);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        try {
            String line;
            while(((line = reader.readLine()) != null)){
                TableRow currentRow = new TableRow(this);
                //table.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                String[] row = line.split(",");
                TextView move_name = new TextView(this);
                move_name.setGravity(Gravity.START);
                move_name.setPadding(25,10,0,10);
                move_name.setTextSize(32);
                move_name.setText(row[0]);
                currentRow.addView(move_name);
                for (int i = 1; i < (row.length-1); i++) {
                    TextView textview = new TextView(this);
                    textview.setGravity(Gravity.END);
                    textview.setPadding(0,10,25,10);
                    textview.setTextSize(32);
                    textview.setText(row[i]);
                    currentRow.addView(textview);
                }
                //b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                table.addView(currentRow);
                //, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}