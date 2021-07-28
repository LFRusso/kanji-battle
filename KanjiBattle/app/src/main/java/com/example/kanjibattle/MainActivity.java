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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupLevels();
    }

    public void setupLevels() {
        LinearLayout wrapping = findViewById(R.id.wrapping);

        int[] sizes = {2211, 979, 612, 245, 79};
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
        Bundle extras = getIntent().getExtras();
        Intent i = new Intent(this, GameActivity.class);
        Usuario player = new Usuario();
        if(extras != null) {
            player = (Usuario) extras.getSerializable("usuarioSerialize");
            Log.e("asasa",(Integer.toString(player.getNivel())));
        }
        i.putExtra("usuarioSerialize", player);
        switch (lvlBtn.getId()) {
            case 1:
                i.putExtra("GAME_DATA", "n1.json");
                break;
            case 2:
                i.putExtra("GAME_DATA", "n2.json");
                break;
            case 3:
                i.putExtra("GAME_DATA", "n3.json");
                break;
            case 4:
                i.putExtra("GAME_DATA", "n4.json");
                break;
            case 5:
                i.putExtra("GAME_DATA", "n5.json");
                break;
        }
        startActivity(i);
    }
}