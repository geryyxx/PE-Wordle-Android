package com.example.worlde;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Context;

public class SettingsActivity extends AppCompatActivity {

    EditText maxAttemptsInput;
    Switch darkModeCheck;
    RadioButton huRadio, enRadio;
    Button saveSettingsButton;
    TextView backButton;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        maxAttemptsInput = findViewById(R.id.maxAttemptsInput);
        darkModeCheck = findViewById(R.id.darkModeCheck);
        huRadio = findViewById(R.id.huRadio);
        enRadio = findViewById(R.id.enRadio);
        saveSettingsButton = findViewById(R.id.saveSettingsButton);
        backButton = findViewById(R.id.backButton);

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);

        int maxAttempts = prefs.getInt("max_attempts", 6);
        boolean darkMode = prefs.getBoolean("dark_mode", false);
        String language = prefs.getString("language", "hu");

        maxAttemptsInput.setText(String.valueOf(maxAttempts));
        darkModeCheck.setChecked(darkMode);

        if (language.equals("en")) {
            enRadio.setChecked(true);
        } else {
            huRadio.setChecked(true);
        }

        backButton.setOnClickListener(v -> finish());

        saveSettingsButton.setOnClickListener(v -> {
            int attempts;

            try {
                attempts = Integer.parseInt(maxAttemptsInput.getText().toString());
            } catch (Exception e) {
                attempts = 6;
            }

            if (attempts < 3) attempts = 3;
            if (attempts > 10) attempts = 10;

            String selectedLanguage;
            if (huRadio.isChecked()) {
                selectedLanguage = "hu";
            } else {
                selectedLanguage = "en";
            }

            prefs.edit()
                    .putInt("max_attempts", attempts)
                    .putBoolean("dark_mode", darkModeCheck.isChecked())
                    .putString("language", selectedLanguage)
                    .apply();

            if (darkModeCheck.isChecked()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            Toast.makeText(this, getString(R.string.settings_saved), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}