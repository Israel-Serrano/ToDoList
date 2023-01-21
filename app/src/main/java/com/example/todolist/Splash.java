package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Objects;

public class Splash extends AppCompatActivity implements Animation.AnimationListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Oculta la action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView splash_cu = findViewById(R.id.splash_cu);
        TextView splash_te = findViewById(R.id.splash_te);
        TextView splash_tasks = findViewById(R.id.splash_tasks);

        Animation cu_animation = AnimationUtils.loadAnimation(this,R.anim.cu_anim);
        Animation te_animation = AnimationUtils.loadAnimation(this,R.anim.te_anim);
        Animation tasks_animation = AnimationUtils.loadAnimation(this,R.anim.tasks_anim);

        splash_cu.startAnimation(cu_animation);
        splash_te.startAnimation(te_animation);
        splash_tasks.startAnimation(tasks_animation);

        tasks_animation.setAnimationListener(this);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(Splash.this,Login.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}