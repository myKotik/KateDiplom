package com.example.onbording;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ShoppingCart extends AppCompatActivity {
    ImageView img_food, img_food1,img_food2;
    TextView  name,name1, name2, price, price1, price2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        img_food = findViewById(R.id.img_food);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int imageId = extras.getInt("img_food");
            img_food.setImageResource(imageId);
        }
        String txtName = getIntent().getStringExtra("name");
        String txtPrice = getIntent().getStringExtra("price");
        name.setText(name.getText().toString() + " " + txtName);
        price.setText(price.getText().toString() + " " + txtPrice);

    }


    public void food(View view) {
        Intent i = new Intent(this, MainActivity2.class);
        startActivity(i);
        finish();
    }
    public void profile(View view) {
        Intent i = new Intent(this, Profile.class);
        startActivity(i);
        finish();
    }
}