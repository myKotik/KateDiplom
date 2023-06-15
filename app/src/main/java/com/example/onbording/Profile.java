package com.example.onbording;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    TextView ur_name, ur_email, ur_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ur_name = findViewById(R.id.ur_name);
        ur_email = findViewById(R.id.ur_email);
        ur_phone = findViewById(R.id.ur_phone);

        String txtName = getIntent().getStringExtra("ur_name");
        String txtemail = getIntent().getStringExtra("ur_email");
        String txtphone = getIntent().getStringExtra("ur_phone");
        ur_name.setText(ur_name.getText().toString() + " " + txtName);
        ur_email.setText(ur_email.getText().toString() + " " + txtemail);
        ur_phone.setText(ur_phone.getText().toString() + " " + txtphone);
    }

    public void Basket(View view) {
        Intent i = new Intent(this, ShoppingCart.class);
        startActivity(i);
        finish();
    }
    public void food(View view) {
        Intent i = new Intent(this, MainActivity2.class);
        startActivity(i);
        finish();
    }
}