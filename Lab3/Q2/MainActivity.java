package com.example.l3q2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private CheckBox checkBoxServiceQuality, checkBoxPrice, checkBoxDelivery, checkBoxCustomerCare, checkBoxAvailability;
    private EditText editTextFeedback;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the UI elements
        ratingBar = findViewById(R.id.ratingBar);
        checkBoxServiceQuality = findViewById(R.id.checkBox);
        checkBoxPrice = findViewById(R.id.checkBox2);
        checkBoxDelivery = findViewById(R.id.checkBox3);
        checkBoxCustomerCare = findViewById(R.id.checkBox4);
        checkBoxAvailability = findViewById(R.id.checkBox5);
        editTextFeedback = findViewById(R.id.editTextText);
        buttonSubmit = findViewById(R.id.button);

        // Set OnClickListener for the Submit Feedback button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the rating
                float rating = ratingBar.getRating();

                // Get the checked items in CheckBoxes
                StringBuilder checkedItems = new StringBuilder();
                if (checkBoxServiceQuality.isChecked()) {
                    checkedItems.append("Service Quality\n");
                }
                if (checkBoxPrice.isChecked()) {
                    checkedItems.append("Price of Products\n");
                }
                if (checkBoxDelivery.isChecked()) {
                    checkedItems.append("Delivery\n");
                }
                if (checkBoxCustomerCare.isChecked()) {
                    checkedItems.append("Customer Care\n");
                }
                if (checkBoxAvailability.isChecked()) {
                    checkedItems.append("Availability\n");
                }

                // Get the feedback from EditText
                String feedback = editTextFeedback.getText().toString().trim();

                // Display the feedback in a toast (or process further, like saving to a database)
                String message = "Rating: " + rating + "\n" +
                        "Loved about us:\n" + checkedItems.toString() +
                        "Suggestions: " + feedback;

                // Display the message in a Toast
                Toast.makeText(MainActivity.this, "Feedback Submitted", Toast.LENGTH_LONG).show();
                clearForm();
                showFeedbackDialog(rating, checkedItems.toString(), feedback);
            }
        });
    }    private void showFeedbackDialog(float rating, String checkedItems, String feedback) {
        // Create the dialog message
        String message = "Rating: " + rating + "\n" +
                "Loved about us:\n" + checkedItems +
                "Suggestions: " + feedback;

        // Build the AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Feedback Submitted")
                .setMessage(message)
                .setPositiveButton("OK", null) // Button to dismiss the dialog
                .show();
    }
    private void clearForm() {
        // Reset the RatingBar to 0
        ratingBar.setRating(0);

        // Uncheck all the checkboxes
        checkBoxServiceQuality.setChecked(false);
        checkBoxPrice.setChecked(false);
        checkBoxDelivery.setChecked(false);
        checkBoxCustomerCare.setChecked(false);
        checkBoxAvailability.setChecked(false);

        // Clear the feedback text
        editTextFeedback.setText("");
    }
}
