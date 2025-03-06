package com.nana.tabunganapp.model;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.nana.tabunganapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class saldo extends AppCompatActivity {

    private double saldo = 0.0;
    private List<Transaction> transactionHistory = new ArrayList<>();

    private EditText amountInput;
    private TextView saldoText, errorMessage;
    private TableLayout transactionTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saldo);  // Link to your XML layout

        amountInput = findViewById(R.id.amount_input);
        saldoText = findViewById(R.id.saldo);
        errorMessage = findViewById(R.id.error_message);
        transactionTable = findViewById(R.id.transaction_table);

        Button depositButton = findViewById(R.id.deposit_button);
        Button withdrawButton = findViewById(R.id.withdraw_button);
        Button backButton = findViewById(R.id.back_button);

        depositButton.setOnClickListener(v -> handleDeposit());
        withdrawButton.setOnClickListener(v -> handleWithdraw());
        backButton.setOnClickListener(v -> finish()); // Close the activity or navigate back

        updateSaldoDisplay();
        updateTransactionHistory();
    }

    private void handleDeposit() {
        String input = amountInput.getText().toString();
        if (TextUtils.isEmpty(input)) {
            showError("Masukkan jumlah uang yang valid!");
            return;
        }

        double amount = Double.parseDouble(input);
        if (amount > 0) {
            saldo += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            updateSaldoDisplay();
            updateTransactionHistory();
            amountInput.setText("");  // Clear input field
        } else {
            showError("Masukkan jumlah uang yang valid!");
        }
    }

    private void handleWithdraw() {
        String input = amountInput.getText().toString();
        if (TextUtils.isEmpty(input)) {
            showError("Masukkan jumlah uang yang valid!");
            return;
        }

        double amount = Double.parseDouble(input);
        if (amount > 0 && amount <= saldo) {
            saldo -= amount;
            transactionHistory.add(new Transaction("Withdraw", amount));
            updateSaldoDisplay();
            updateTransactionHistory();
            amountInput.setText("");  // Clear input field
        } else {
            showError("Saldo tidak cukup!");
        }
    }

    private void showError(String message) {
        errorMessage.setText(message);
    }

    private void updateSaldoDisplay() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        saldoText.setText("Saldo Tabungan: Rp " + decimalFormat.format(saldo));
    }

    private void updateTransactionHistory() {
        transactionTable.removeAllViews();  // Clear previous rows
        if (transactionHistory.isEmpty()) {
            TableRow row = new TableRow(this);
            TextView noDataText = new TextView(this);
            noDataText.setText("Tidak ada riwayat transaksi.");
            row.addView(noDataText);
            transactionTable.addView(row);
        } else {
            for (Transaction transaction : transactionHistory) {
                TableRow row = new TableRow(this);

                TextView dateText = new TextView(this);
                dateText.setText(transaction.getDate());
                row.addView(dateText);

                TextView typeText = new TextView(this);
                typeText.setText(transaction.getType());
                row.addView(typeText);

                TextView amountText = new TextView(this);
                amountText.setText("Rp " + transaction.getAmount());
                row.addView(amountText);

                transactionTable.addView(row);
            }
        }
    }

    private static class Transaction {
        private final String type;
        private final double amount;
        private final String date;

        public Transaction(String type, double amount) {
            this.type = type;
            this.amount = amount;
            this.date = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
        }

        public String getType() {
            return type;
        }

        public double getAmount() {
            return amount;
        }

        public String getDate() {
            return date;
        }
    }
}
