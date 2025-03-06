package com.nana.tabunganapp.model;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText emailEditText, phoneEditText;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        // Initialize Views
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        resetButton = findViewById(R.id.resetButton);

        // Reset Button Click Listener
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();

                // Validate Email and Phone Inputs
                if (!email.isEmpty() && isValidEmail(email)) {
                    // Simulate sending reset link via email
                    Toast.makeText(ResetPasswordActivity.this, "Reset link sent to " + email, Toast.LENGTH_SHORT).show();
                } else if (!phone.isEmpty() && isValidPhoneNumber(phone)) {
                    // Simulate sending OTP to phone number
                    Toast.makeText(ResetPasswordActivity.this, "OTP sent to " + phone, Toast.LENGTH_SHORT).show();
                } else {
                    // Show error if neither email nor phone is valid
                    Toast.makeText(ResetPasswordActivity.this, "Please enter a valid email or phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Email Validation (basic check)
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    // Phone Number Validation (basic check)
    private boolean isValidPhoneNumber(String phone) {
        // Basic phone validation (10-13 digits, starts with optional +)
        return phone.matches("\\+?\\d{10,13}");
    }
}
