package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInput;
    private Button buttonEncrypt;
    private TextView textViewEncrypted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextInput = findViewById(R.id.editTextText);
        buttonEncrypt = findViewById(R.id.button);
        textViewEncrypted = findViewById(R.id.textView);

        buttonEncrypt.setOnClickListener(v -> {
            String inputText = editTextInput.getText().toString();

            if (inputText.isEmpty()) {
                Toast.makeText(this, "Please enter some text", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Encrypt the input text
                String encryptedText = EncryptionUtil.encrypt(inputText);
                textViewEncrypted.setText(encryptedText);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Encryption failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}