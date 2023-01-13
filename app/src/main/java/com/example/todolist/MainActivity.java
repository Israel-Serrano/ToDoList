package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.mas:
                //activar el cuadro de dialogo para añadir tarea
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Nueva tarea")
                        .setMessage("¿Qué quieres hacer a continuación")
                        .setView(taskEditText)
                        .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //añadir tarea a la BDD

                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create();
                dialog.show();
                //Toast.makeText(this, "Tarea añadida", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                //cierre de sesión de Firebase
                startActivity(new Intent(MainActivity.this,Login.class));
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }
}