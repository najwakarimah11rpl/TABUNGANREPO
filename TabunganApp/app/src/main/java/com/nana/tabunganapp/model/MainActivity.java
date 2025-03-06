package com.nana.tabunganapp.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.nana.tabunganapp.R;

public class MainActivity extends AppCompatActivity {

    private TextView heroHeading, heroSubheading;
    private Button ctaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        heroHeading = findViewById(R.id.heroHeading);
        heroSubheading = findViewById(R.id.heroSubheading);
        ctaButton = findViewById(R.id.ctaButton);

        // Set dynamic content
        heroHeading.setText("Selamat Datang di NANABUNG!");
        heroSubheading.setText("Menabung dengan Keamanan dan Kemudahan Terjamin, Hidup Lebih Keren!");

        // Handle CTA button click
        ctaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Login Activity or another activity as per the flow
                Intent intent = new Intent(MainActivity.this, LoginActivity.class); // Use LoginActivity for proper naming
                startActivity(intent);
            }
        });
    }
}
