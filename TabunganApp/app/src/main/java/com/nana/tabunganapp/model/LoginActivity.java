package com.nana.tabunganapp.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nana.tabunganapp.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, phoneNumberEditText, otpEditText;
    private Button loginButton, backButton, otpButton;
    private TextView messageTextView;

    private int loginAttempts = 0; // Track the number of login attempts
    private String generatedOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);  // Use the correct layout for LoginActivity

        // Initialize Views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
//        otpEditText = findViewById(R.id.otpEditText);
//        loginButton = findViewById(R.id.loginButton);
        backButton = findViewById(R.id.backButton);
//        otpButton = findViewById(R.id.otpButton);

        // Login Button Click Listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String phoneNumber = phoneNumberEditText.getText().toString().trim();

                // Validation checks
                if (email.isEmpty() || !email.contains("@")) {
                    Toast.makeText(LoginActivity.this, "Email tidak valid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!phoneNumber.isEmpty() && !phoneNumber.matches("\\+?\\d{10,13}")) {
                    Toast.makeText(LoginActivity.this, "Nomor telepon tidak valid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty() || password.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Password harus lebih dari 6 karakter", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Simple brute-force protection: limit the number of login attempts
                if (loginAttempts >= 5) {
                    Toast.makeText(LoginActivity.this, "Terlalu banyak percobaan login. Coba lagi nanti.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If phone number is provided, start OTP verification process
                if (!phoneNumber.isEmpty()) {
                    sendOtp(phoneNumber);
                } else {
                    // Use email and password if phone number is not provided
                    String hashedPassword = hashPassword(password);

                    if (isValidLogin(email, hashedPassword)) {
                        // Reset login attempts on successful login
                        loginAttempts = 0;
                        // Login successful
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();  // Close the login activity after starting the welcome screen
                    } else {
                        loginAttempts++;
                        Toast.makeText(LoginActivity.this, "Email atau password salah", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Back Button Click Listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity
            }
        });

        // OTP Button Click Listener
        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpEntered = otpEditText.getText().toString().trim();
                if (otpEntered.equals(generatedOtp)) {
                    // OTP verification successful
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "OTP salah. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Hash the password using SHA-256 (use bcrypt or PBKDF2 in production for better security)
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Simulated backend check (replace this with a real API call)
    private boolean isValidLogin(String email, String hashedPassword) {
        // Here, you would send the email and hashed password to your server and check if it matches the database.
        // This is a dummy check for demonstration purposes
        return email.equals("user@example.com") && hashedPassword.equals(hashPassword("userpassword"));
    }

    // Simulate sending OTP (In real implementation, integrate with an SMS provider)
    private void sendOtp(String phoneNumber) {
        // In a real app, integrate with an SMS service (like Firebase, Twilio) to send an OTP to the phone number
        generatedOtp = "123456";  // For demonstration purposes, set a static OTP
        Toast.makeText(LoginActivity.this, "OTP terkirim ke " + phoneNumber, Toast.LENGTH_SHORT).show();
    }
}
