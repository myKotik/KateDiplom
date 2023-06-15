package com.example.onbording;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.onbording.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Registration extends AppCompatActivity {

    Button btnSignIn, btnRegister;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference users;
    ConstraintLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        setContentView(R.layout.registration);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);
        root = findViewById(R.id.root_element);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");

        
        // КНОПКА ЗАРЕГЕСТРИРОВАТЬСЯ
        btnRegister.setOnClickListener(view -> showRegisterWindow());
        //КНОПКА ВОЙТИ
        btnSignIn.setOnClickListener(view -> showSignInWindow());
    }

    //ОКНО  ВОЙТИ
    private void showSignInWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Войти");
        dialog.setMessage("Введите все данные для входа");

        LayoutInflater inflater = LayoutInflater.from(this);
        View signInWindow = inflater.inflate(R.layout.sign_in_window, null);
        dialog.setView(signInWindow);


        final MaterialEditText email = signInWindow.findViewById(R.id.emailField);
        final MaterialEditText password = signInWindow.findViewById(R.id.passwordField);


        //КНОПКА ОТМЕНИТЬ КОТОРАЯ ВЫХОДИТ ИЗ ДИАЛОГОВОГО ОКНА
        dialog.setNegativeButton("отменить", (dialogInterface, i) -> dialogInterface.dismiss());

        //КНОПКА ДОБАВИТЬ КОТОРАЯ СМОТРИТ ЧТОБЫ ПОЛЯ БЫЛИ ЗАПОЛНЕНЫ
        dialog.setPositiveButton("Войти", (dialogInterface, i) -> {
            if (TextUtils.isEmpty(email.getText().toString())){
                Snackbar.make(root, "Введите вашу почту", Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (password.getText().toString().length() < 5){
                Snackbar.make(root, "Введите ваш пароль который будет более 5 символов", Snackbar.LENGTH_SHORT).show();
                return;
            }

            //РЕГИСТРАЦИЯ ПОЛЬЗОВАТЕЛЯ
            auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(Registration.this, MainActivity2.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(root, "Ошибка авторизации. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    });
        });

        dialog.show();
    }

    //ОКНО ЗАРЕГЕСТРИРОВАТЬСЯ
    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Зарегестрироваться");
        dialog.setMessage("Введите все данные для регистрации");

        LayoutInflater inflater = LayoutInflater.from(this);
        View registerWindow = inflater.inflate(R.layout.register_window, null);
        dialog.setView(registerWindow);


        final MaterialEditText email = registerWindow.findViewById(R.id.emailField);
        final MaterialEditText password = registerWindow.findViewById(R.id.passwordField);
        final MaterialEditText name = registerWindow.findViewById(R.id.nameField);
        final MaterialEditText phone = registerWindow.findViewById(R.id.phoneField);

        //КНОПКА ОТМЕНИТЬ КОТОРАЯ ВЫХОДИТ ИЗ ДИАЛОГОВОГО ОКНА
        dialog.setNegativeButton("отменить", (dialogInterface, i) -> dialogInterface.dismiss());

        //КНОПКА ДОБАВИТЬ КОТОРАЯ СМОТРИТ ЧТОБЫ ПОЛЯ БЫЛИ ЗАПОЛНЕНЫ
        dialog.setPositiveButton("Добавить", (dialogInterface, i) -> {
            if (TextUtils.isEmpty(email.getText().toString())){
                Snackbar.make(root, "Введите вашу почту", Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(name.getText().toString())){
                Snackbar.make(root, "Введите ваше имя", Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(phone.getText().toString())){
                Snackbar.make(root, "Введите ваш телефон", Snackbar.LENGTH_SHORT).show();
                return;
            } if (phone.getText().toString().length() < 11){
                Snackbar.make(root, "В вашем телефоне меньше цифр чем должно быть", Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (password.getText().toString().length() < 5){
                Snackbar.make(root, "Введите ваш пароль который будет более 5 символов", Snackbar.LENGTH_SHORT).show();
                return;
            }


            //РЕГИСТРАЦИЯ ПОЛЬЗОВАТЕЛЯ
            auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnSuccessListener(authResult -> {
                        User user = new User();
                        user.setEmail(email.getText().toString());
                        user.setName(name.getText().toString());
                        user.getPassword(password.getText().toString());
                        user.setPhone(phone.getText().toString());

                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user)
                                .addOnSuccessListener(unused -> Snackbar.make(root, "пользователь добавлен", Snackbar.LENGTH_SHORT).show());
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(root, "Ошибка входа. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();

                        }
                    });
        });
        Intent intent = new Intent(this, Profile.class);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("phone", phone.getText().toString());

        dialog.show();
    }
}