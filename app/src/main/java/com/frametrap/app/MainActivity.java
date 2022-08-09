package com.frametrap.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //readframedata("ken");

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
                move_name.setTextSize(25);
                move_name.setText(row[0]);
                currentRow.addView(move_name);
                for (int i = 1; i < (row.length-1); i++) {
                    TextView textview = new TextView(this);
                    textview.setGravity(Gravity.END);
                    textview.setTextSize(25);
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

    private List<Move> movelist = new ArrayList<>();

    private void readframedata(String character_name) {
        TableLayout table = (TableLayout) findViewById(R.id.table_moves);
        InputStream is = getResources().openRawResource(R.raw.ken);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line = "";
        try{
            while ((line = reader.readLine()) != null){
                TableRow currentRow = new TableRow(this);
                //table.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                String[] row = line.split(",");
                Move move = new Move();
                move.setMove_name(row[0]);
                TextView b = new TextView(this);
                b.setText(row[0]);
                //b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                currentRow.addView(b);
                if (row[1].length() > 0) {
                    move.setStartup(Integer.parseInt(row[1]));
                }else{
                    move.setStartup(0);
                }
                if (row[2].length() > 0) {
                    move.setActive(Integer.parseInt(row[2]));
                }else{
                    move.setActive(0);
                }
                if (row[3].length() > 0) {
                    move.setRecovery(Integer.parseInt(row[3]));
                }else{
                    move.setRecovery(0);
                }
                if (row[4].length() > 0) {
                    move.setOn_block(Integer.parseInt(row[4]));
                }else{
                    move.setOn_block(0);
                }
                move.setMove_type(row[5]);
                movelist.add(move);
                //table.addView(currentRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                Log.d("MyActivity", "Just created: " + move);
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }
}