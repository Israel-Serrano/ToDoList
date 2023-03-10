package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore dataBase;
    String emailUsuario;

    ListView listViewTasks;
    List<String> taskList = new ArrayList<>();
    List<String> taskListId = new ArrayList<>();
    ArrayAdapter<String> mAdapterTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBase = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();
        listViewTasks = findViewById(R.id.listaVistas);

        //actualizar la UI con las tareas del usuario logueado
        actualizarUI();


    }
    private void actualizarUI(){
        dataBase.collection("Tareas")
                .whereEqualTo("emailUsuario", emailUsuario)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {

                            return;
                        }

                        taskList.clear();
                        taskListId.clear();

                        for (QueryDocumentSnapshot doc : value) {
                            taskListId.add(doc.getId());
                            taskList.add(doc.getString("nombreTarea"));
                        }

                        if(taskList.size()== 0){
                            listViewTasks.setAdapter(null);
                        }else{
                            mAdapterTasks = new ArrayAdapter<>(MainActivity.this, R.layout.item_task, R.id.nombreTarea, taskList);
                            listViewTasks.setAdapter(mAdapterTasks);
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.addTask:
                //activar el cuadro de dialogo para a??adir tarea
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Nueva tarea")
                        .setMessage("??Qu?? quieres hacer a continuaci??n?")
                        .setView(taskEditText)
                        .setPositiveButton("A??adir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //a??adir tarea a la BDD

                                String miTarea = taskEditText.getText().toString();

                                Map<String, Object> tarea = new HashMap<>();
                                tarea.put("nombreTarea", miTarea);
                                tarea.put("emailUsuario", emailUsuario);

                                dataBase.collection("Tareas").add(tarea);
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create();
                dialog.show();
                //Toast.makeText(this, "Tarea a??adida", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                //cierre de sesi??n de Firebase
                mAuth.signOut();
                onBackPressed();
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }

    public void taskDelete(View view){
        View parent = (View) view.getParent();
        TextView textViewTask = parent.findViewById(R.id.nombreTarea);
        String task = textViewTask.getText().toString();
        int position = taskList.indexOf(task);

        dataBase.collection("Tareas").document(taskListId.get(position)).delete();

        Toast toast = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                findViewById(R.id.lytLayout));
        TextView txtMsg = layout.findViewById(R.id.txtMensaje);
        txtMsg.setText(R.string.task_ok);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void taskModify(View view){
        View parent = (View) view.getParent();
        TextView textViewTask = parent.findViewById(R.id.nombreTarea);
        String task = textViewTask.getText().toString();

        //final EditText taskEditText = (EditText) textViewTask.getEditableText();
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Modificar tarea")
                .setMessage("Cambiar \"" + task + "\" por: ")
                .setView(taskEditText)
                .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //modificar tarea en la BDD

                        String miTarea = taskEditText.getText().toString();
                        int position = taskList.indexOf(task);

                        dataBase.collection("Tareas").document(taskListId.get(position)).update("nombreTarea", miTarea);

                        Toast toast = new Toast(getApplicationContext());
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast,
                                findViewById(R.id.lytLayout));
                        TextView txtMsg = layout.findViewById(R.id.txtMensaje);
                        txtMsg.setText(R.string.modify_task);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();



    }
}