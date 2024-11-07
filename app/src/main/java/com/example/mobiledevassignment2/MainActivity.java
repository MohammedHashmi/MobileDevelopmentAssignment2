package com.example.mobiledevassignment2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText editTextAddress, editTextLatitude, editTextLongitude;
    private EditText editTextQueryAddress, editTextDeleteAddress;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseHelper = new DatabaseHelper(this);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        editTextQueryAddress = findViewById(R.id.editTextQueryAddress);
        editTextDeleteAddress = findViewById(R.id.editTextDeleteAddress);
        textViewResult = findViewById(R.id.textViewResult);


        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocation();
            }
        });


        Button btnQuery = findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryLocationByAddress();
            }
        });


        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLocationByAddress();
            }
        });
    }


    private void addLocation() {
        String address = editTextAddress.getText().toString();
        String latitudeStr = editTextLatitude.getText().toString();
        String longitudeStr = editTextLongitude.getText().toString();

        if (address.isEmpty() || latitudeStr.isEmpty() || longitudeStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude = Double.parseDouble(latitudeStr);
        double longitude = Double.parseDouble(longitudeStr);

        boolean isInserted = databaseHelper.addLocation(address, latitude, longitude);
        if (isInserted) {
            Toast.makeText(this, "Location '" + address + "' has been successfully added to the database", Toast.LENGTH_SHORT).show();
            clearAddLocationFields();
        } else {
            Toast.makeText(this, "Failed to add location '" + address + "' to the database", Toast.LENGTH_SHORT).show();
        }
    }


    private void clearAddLocationFields() {
        editTextAddress.setText("");
        editTextLatitude.setText("");
        editTextLongitude.setText("");
    }


    private void queryLocationByAddress() {
        String address = editTextQueryAddress.getText().toString();
        if (address.isEmpty()) {
            Toast.makeText(this, "Please enter an address to query", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = databaseHelper.getLocationByAddress(address);
        if (cursor.moveToFirst()) {
            double latitude = cursor.getDouble(0);
            double longitude = cursor.getDouble(1);
            textViewResult.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
        } else {
            textViewResult.setText("Location not found");
            Toast.makeText(this, "No location found with address '" + address + "'", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    private void deleteLocationByAddress() {
        String address = editTextDeleteAddress.getText().toString();
        if (address.isEmpty()) {
            Toast.makeText(this, "Please enter an address to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isDeleted = databaseHelper.deleteLocation(address);
        if (isDeleted) {
            Toast.makeText(this, "Location '" + address + "' has been successfully deleted from the database", Toast.LENGTH_SHORT).show();
            editTextDeleteAddress.setText("");
        } else {
            Toast.makeText(this, "No location found with address '" + address + "' to delete", Toast.LENGTH_SHORT).show();
        }
    }
}
