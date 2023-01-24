package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    @SuppressLint("SetTextI18n")
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
            }else if(!email.contains("@") && !email.contains(".") && !email.contains(" ")){
                    emailText.setError("Email no válido");
            }else if(password.isEmpty()){
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

                                Toast toast = new Toast(getApplicationContext());
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast,
                                        (ViewGroup) findViewById(R.id.lytLayout));
                                TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                                txtMsg.setText("Fallo de autenticación.");
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
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
            }else if(!email.contains("@") && !email.contains(".") && !email.contains(" ")){
                emailText.setError("Email no válido");
            }else if(password.isEmpty()){
                passText.setError("Campo vacío");
            }else if(password.length() < 6){
                passText.setError("Contraseña inferior a 6 caracteres");
            }else {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast toast = new Toast(getApplicationContext());
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast,
                                        (ViewGroup) findViewById(R.id.lytLayout));
                                TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                                txtMsg.setText("Usuario registrado correctamente.");
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.

                                Toast toast = new Toast(getApplicationContext());
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast,
                                        (ViewGroup) findViewById(R.id.lytLayout));
                                TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                                txtMsg.setText("Fallo al registrar usuario.");
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });

    }
}