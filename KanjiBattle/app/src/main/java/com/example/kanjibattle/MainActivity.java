package com.example.kanjibattle;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public JSONArray kanjiN1 = new JSONArray();
    public JSONArray kanjiN2 = new JSONArray();
    public JSONArray kanjiN3 = new JSONArray();
    public JSONArray kanjiN4 = new JSONArray();
    public JSONArray kanjiN5 = new JSONArray();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupData();
        setupLevels();
    }

    public void setupLevels() {
        LinearLayout wrapping = findViewById(R.id.wrapping);

        int[] sizes = {this.kanjiN1.length(), this.kanjiN2.length(), this.kanjiN3.length(), this.kanjiN4.length(),this.kanjiN5.length()};
        for(int i=5; i>=1; i--) {
            Button btn = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,   // width
                    300);  // height

            btn.setText("JLPT N"+i+"\t("+sizes[i-1]+" Kanji)");
            btn.setId(i);
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    try {
                        playLevel(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            wrapping.addView(btn, params);
        }
    }


    /*
        Launch a given level activity
     */
    public void playLevel(View v) throws JSONException {
        Button lvlBtn = (Button) v;
        Intent i = new Intent(this, GameActivity.class);
        switch (lvlBtn.getId()) {
            case 1:
                i.putExtra("GAME_DATA", this.kanjiN1.toString());
                break;
            case 2:
                i.putExtra("GAME_DATA", this.kanjiN2.toString());
                break;
            case 3:
                i.putExtra("GAME_DATA", this.kanjiN3.toString());
                break;
            case 4:
                i.putExtra("GAME_DATA", this.kanjiN4.toString());
                break;
            case 5:
                i.putExtra("GAME_DATA", this.kanjiN5.toString());
                break;
        }
        startActivity(i);
    }


    public void setupData() {
        this.kanjiN1 = readJSON("n1.json");
        this.kanjiN2 = readJSON("n2.json");
        this.kanjiN3 = readJSON("n3.json");
        this.kanjiN4 = readJSON("n4.json");
        this.kanjiN5 = readJSON("n5.json");
    }

    public JSONArray readJSON(String filename) {
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "utf-8");
            JSONArray jsonArray = new JSONArray(json);
            return jsonArray;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}