package com.example.giovanny.juegoconcu.Juego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.giovanny.juegoconcu.Figuras.Usuario;
import com.example.giovanny.juegoconcu.R;

import java.util.ArrayList;

public class JuegoActividad extends AppCompatActivity {

    MyGLSurfaceView mGLView;
    Usuario user;
    ArrayList<Usuario> adversarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        // Create a GLSurfaceView instance and set it
        Intent intent = getIntent();
        user= (Usuario) intent.getSerializableExtra("usuario");
        adversarios= (ArrayList<Usuario>) intent.getSerializableExtra("adversarios");
        // as the ContentView for this Activity
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mGLView = new MyGLSurfaceView(this,displaymetrics.heightPixels,displaymetrics.widthPixels,user,adversarios);
        setContentView(mGLView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }

}
