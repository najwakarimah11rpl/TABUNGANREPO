package com.nana.tabunganapp.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.nana.tabunganapp.R;

public class HomeActivity extends AppCompatActivity {

    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home); // Layout yang digunakan di home.xml

        welcomeText = findViewById(R.id.welcomeText);

        // Mendapatkan nama pengguna dari Intent
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");

        // Menampilkan pesan selamat datang dengan nama pengguna
        if (userName != null) {
            welcomeText.setText("Welcome, " + userName + "!");
        } else {
            welcomeText.setText("Welcome!");
        }
    }
}
