package com.example.onbording;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.onbording.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {

    TextView  name, price;
    Button btnActTwo, foodBtn;
    ImageView img_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnActTwo = findViewById(R.id.btnActTwo);
        btnActTwo = findViewById(R.id.btnadd1);
        btnActTwo = findViewById(R.id.btnadd2);
        foodBtn = findViewById(R.id.foodF);
        img_food = findViewById(R.id.img_food);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);


        btnActTwo.setOnClickListener(view -> showFoodWindow());

        //txt = findViewById(R.id.textView2);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create() )
                .build();


        APICall apiCall = retrofit.create(APICall.class);

        Call<DataModel> call = apiCall.getData();

        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (!response.isSuccessful()) {
                   // txt.setText("Code: " + response.code());
                    return;
                }

                String jsony="";
                jsony = "id = "+ response.body().getId() + "\n" +
                 "name = " + response.body().getName();
                //jsony = "name = "+ response.body().getName();
                //txt.append(jsony);
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
               // txt.setText(t.getMessage());
            }
        });


    }

    private void showFoodWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Выбранная позиция");

        LayoutInflater inflater = LayoutInflater.from(this);
        View FoodWindow = inflater.inflate(R.layout.food_window, null);
        dialog.setView(FoodWindow);

        //КНОПКА ОТМЕНИТЬ КОТОРАЯ ВЫХОДИТ ИЗ ДИАЛОГОВОГО ОКНА
        dialog.setNegativeButton("отменить", (dialogInterface, i) -> dialogInterface.dismiss());
        dialog.show();
    }
//ПЕРЕНЕСТИ ДАННЫЕ НА ДРУГУЮ АКТИВИТИ
    public void goNewView(View v){
        switch (v.getId()) {
            case R.id.btnActTwo:
                // Говорим между какими Activity будет происходить связь
                Intent intent = new Intent(this, ShoppingCart.class);

                // указываем первым параметром ключ, а второе значение
                // по ключу мы будем получать значение с Intent
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("price", price.getText().toString());
                intent.putExtra("img_food", R.drawable.food1);

                // показываем новое Activity
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void Basket(View view) {
        Intent i = new Intent(this, ShoppingCart.class);
        startActivity(i);
        finish();
    }

    public void profile(View view) {
        Intent i = new Intent(this, Profile.class);
        startActivity(i);
        finish();
    }
}