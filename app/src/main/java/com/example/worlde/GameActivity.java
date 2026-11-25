package com.example.worlde;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private EditText inputWord;
    private Button guessButton;
    private Button newGameButton;
    private LinearLayout resultsLayout;
    private TextView statusText;

    private String secretWord;
    private int attempts = 0;
    private int maxAttempts = 6;
    private boolean gameOver = false;

    private int cellSize;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        inputWord = findViewById(R.id.inputWord);
        guessButton = findViewById(R.id.guessButton);
        newGameButton = findViewById(R.id.newGameButton);
        resultsLayout = findViewById(R.id.resultsLayout);
        statusText = findViewById(R.id.statusText);

        TextView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
        maxAttempts = settings.getInt("max_attempts", 6);

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        cellSize = (screenWidth - 140) / 5;

        if (cellSize < 60) {
            cellSize = 60;
        }

        startNewGame();

        guessButton.setOnClickListener(v -> {
            String guess = inputWord.getText().toString().trim().toUpperCase();

            if (guess.length() != 5) {
                Toast.makeText(this, getString(R.string.error_length), Toast.LENGTH_SHORT).show();
                return;
            }

            checkGuess(guess);
            inputWord.setText("");
        });

        newGameButton.setOnClickListener(v -> {
            startNewGame();
        });
    }


    private void startNewGame() {
        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
        String language = settings.getString("language", "hu");

        String[] words = WordList.getWords(language);

        Random random = new Random();
        secretWord = words[random.nextInt(words.length)];
        attempts = 0;
        gameOver = false;

        drawEmptyRows();

        statusText.setText(getString(R.string.start_text));
        guessButton.setEnabled(true);
    }

    private void drawEmptyRows() {
        resultsLayout.removeAllViews();

        for (int r = 0; r < maxAttempts; r++) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER);
            row.setPadding(0, 4, 0, 4);

            for (int i = 0; i < 5; i++) {
                TextView box = new TextView(this);
                box.setText("");
                box.setTextSize(24);
                box.setGravity(Gravity.CENTER);
                box.setTextColor(Color.WHITE);
                box.setBackgroundColor(Color.rgb(245, 245, 245));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cellSize, cellSize);
                params.setMargins(3, 3, 3, 3);
                box.setLayoutParams(params);

                row.addView(box);
            }

            resultsLayout.addView(row);
        }
    }

    private void checkGuess(String guess) {
        if (gameOver || attempts >= maxAttempts) {
            return;
        }

        LinearLayout row = makeGuessRow(guess);

        if (attempts < resultsLayout.getChildCount()) {
            resultsLayout.removeViewAt(attempts);
            resultsLayout.addView(row, attempts);
        }

        attempts++;

        if (guess.equals(secretWord)) {
            statusText.setText(getString(R.string.win) + " " + secretWord);
            guessButton.setEnabled(false);
            gameOver = true;
            saveStats(true);

        } else if (attempts >= maxAttempts) {
            statusText.setText(getString(R.string.lose) + " " + secretWord);
            guessButton.setEnabled(false);
            gameOver = true;
            saveStats(false);

        } else {
            int left = maxAttempts - attempts;
            if (left < 0) left = 0;
            statusText.setText(getString(R.string.remaining) + " " + left);
        }
    }

    private LinearLayout makeGuessRow(String guess) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.setPadding(0, 4, 0, 4);

        for (int i = 0; i < 5; i++) {
            TextView box = new TextView(this);

            box.setText(String.valueOf(guess.charAt(i)));
            box.setTextSize(24);
            box.setTextColor(Color.WHITE);
            box.setGravity(Gravity.CENTER);
            box.setTypeface(null, Typeface.NORMAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cellSize, cellSize);
            params.setMargins(3, 3, 3, 3);
            box.setLayoutParams(params);

            if (guess.charAt(i) == secretWord.charAt(i)) {
                box.setBackgroundColor(Color.rgb(76, 175, 80));
            } else if (secretWord.contains(String.valueOf(guess.charAt(i)))) {
                box.setBackgroundColor(Color.rgb(255, 193, 7));
            } else {
                box.setBackgroundColor(Color.rgb(117, 117, 117));
            }

            row.addView(box);
        }

        return row;
    }

    private void saveStats(boolean won) {
        SharedPreferences prefs = getSharedPreferences("wordle_stats", MODE_PRIVATE);

        int games = prefs.getInt("games", 0);
        int wins = prefs.getInt("wins", 0);
        int losses = prefs.getInt("losses", 0);
        int totalAttempts = prefs.getInt("total_attempts", 0);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("games", games + 1);
        editor.putInt("total_attempts", totalAttempts + attempts);

        if (won) {
            editor.putInt("wins", wins + 1);
        } else {
            editor.putInt("losses", losses + 1);
        }

        editor.apply();
    }

}