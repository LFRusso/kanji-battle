package com.example.kanjibattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    public JSONArray kanji = new JSONArray();
    public int correct;
    public int mode; // Question mode (kun yomi | on yomi)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        String filename = intent.getStringExtra("GAME_DATA");
        this.kanji = readJSON(filename);

        this.correct = getRandomIdx(4);
        this.mode = getRandomIdx(2);
        try {
            buildQuestion();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getRandomIdx(int max) {
        final int random = new Random().nextInt(max);
        return random;
    }

    public void buildQuestion() throws JSONException {
        TextView main = findViewById(R.id.main_txt);
        TextView question = findViewById(R.id.question);
        ArrayList<TextView> answers = new ArrayList<>();

        TextView answer1 = findViewById(R.id.a_1);
        answers.add(answer1);
        TextView answer2 = findViewById(R.id.a_2);
        answers.add(answer2);
        TextView answer3 = findViewById(R.id.a_3);
        answers.add(answer3);
        TextView answer4 = findViewById(R.id.a_4);
        answers.add(answer4);

        JSONObject obj;
        int random;
        int max = this.kanji.length();
        for (int i=0; i<4; i++) {
            random = getRandomIdx(max);
            obj = (JSONObject) this.kanji.get(random);
            String readingsStr="";
            if (i==this.correct) {
                main.setText(obj.getString("kanji"));
            }
            switch (this.mode) {
                case 0:
                    readingsStr = obj.getString("readings_on");
                    question.setText("音読み\nOn'yomi");
                    break;
                case 1:
                    readingsStr = obj.getString("readings_kun");
                    question.setText("訓読み\nKun'yomi");
                    break;
            }

            readingsStr = readingsStr.replace("[", "");
            readingsStr = readingsStr.replace("]", "");
            readingsStr = readingsStr.replace("\"", "");
            String[] readings = readingsStr.split(",");

            answers.get(i).setText(readings[getRandomIdx(readings.length)]);
        }
    }

    public void check(View v) {
        Button userAnswer = (Button) v;
        Log.e("alo",((Integer)userAnswer.getId()).toString());


        ArrayList<TextView> answers = new ArrayList<>();
        TextView answer1 = findViewById(R.id.a_1);
        answers.add(answer1);
        TextView answer2 = findViewById(R.id.a_2);
        answers.add(answer2);
        TextView answer3 = findViewById(R.id.a_3);
        answers.add(answer3);
        TextView answer4 = findViewById(R.id.a_4);
        answers.add(answer4);
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