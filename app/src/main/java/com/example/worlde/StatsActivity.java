package com.example.worlde;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        TextView backButton = findViewById(R.id.backButton);
        TextView gamesValue = findViewById(R.id.gamesValue);
        TextView winsValue = findViewById(R.id.winsValue);
        TextView lossesValue = findViewById(R.id.lossesValue);
        TextView winrateValue = findViewById(R.id.winrateValue);
        TextView averageValue = findViewById(R.id.averageValue);

        backButton.setOnClickListener(v -> finish());

        SharedPreferences prefs = getSharedPreferences("wordle_stats", MODE_PRIVATE);

        int games = prefs.getInt("games", 0);
        int wins = prefs.getInt("wins", 0);
        int losses = prefs.getInt("losses", 0);
        int totalAttempts = prefs.getInt("total_attempts", 0);

        int percent = 0;
        if (games > 0) {
            percent = (wins * 100) / games;
        }

        double average = 0;
        if (games > 0) {
            average = (double) totalAttempts / games;
        }

        gamesValue.setText(String.valueOf(games));
        winsValue.setText(String.valueOf(wins));
        lossesValue.setText(String.valueOf(losses));
        winrateValue.setText(percent + "%");
        averageValue.setText(String.format("%.1f", average));
    }
}