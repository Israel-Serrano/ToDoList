package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {

    Button botonLogin;
    TextView botonRegistro;
    EditText emailText, passText;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.cajaCorreo);
        passText = findViewById(R.id.cajaPass);

        botonLogin = findViewById(R.id.botonLogin);
        botonLogin.setOnClickListener(view -> {
            //LOGIN EN FIREBASE
            String email = emailText.getText().toString();
            String password = passText.getText().toString();

            if(email.isEmpty()){
                emailText.setError("Campo vacío");
            }else if(password.isEmpty()) {
                passText.setError("Campo vacío");
            }else {

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        });

        botonRegistro = findViewById(R.id.botonRegistro);
        botonRegistro.setOnClickListener(view -> {
            //CREAR USUARIO EN FIREBASE
            String email = emailText.getText().toString();
            String password = passText.getText().toString();

            if(email.isEmpty()){
                emailText.setError("Campo vacío");
            }else if(password.isEmpty()) {
                passText.setError("Campo vacío");
            }else {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(Login.this, "Usuario registrado", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(Login.this, "User creation failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}