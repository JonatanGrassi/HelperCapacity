package com.example.aplicacionsoa.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionsoa.R;
import com.example.aplicacionsoa.presenter.MvpOpcionesApp;
import com.example.aplicacionsoa.presenter.PresenterActivityOpcionesApp;

import org.json.JSONObject;

public class activity_opciones_app extends AppCompatActivity implements MvpOpcionesApp.View {
    private TextView nombreLocal;
    private Button botonGestionar;
    private Button botonConsultarTips;
    private Button botonConsultarAforo;
    private Button botonMostrarMetricas;
    private MvpOpcionesApp.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_app);
        botonConsultarAforo = (Button) findViewById(R.id.BottonConsultarAforo);
        botonConsultarTips = (Button) findViewById(R.id.buttonConsultarRecomendaciones);
        botonGestionar = (Button) findViewById(R.id.ButtonGestionarIngresos);
        nombreLocal = (TextView) findViewById(R.id.textViewNombreLocal);
        botonMostrarMetricas = (Button)findViewById(R.id.botonMostrarMetricas);
        botonConsultarAforo.setOnClickListener(HandlerConsultarAforo);
        botonConsultarTips.setOnClickListener(HandlerConsultarTips);
        botonGestionar.setOnClickListener(HandlerGestionar);
        botonMostrarMetricas.setOnClickListener(HandlerMostrarMetricas);

        presenter = new PresenterActivityOpcionesApp(this);
        presenter.obtenerNombreLocal();
    }

    private View.OnClickListener HandlerConsultarAforo = (V) ->
    {
        Intent intentAforo = new Intent(this,activity_consultar_aforo.class);
        presenter.configurarIntent(intentAforo);
        startActivity(intentAforo);

    };

    private View.OnClickListener HandlerConsultarTips = (V) ->
    {
        Intent intentRecomendaciones = new Intent(this,activity_consultar_recomendaciones.class);
        presenter.configurarIntent(intentRecomendaciones);
        startActivity(intentRecomendaciones);
    };

    private View.OnClickListener HandlerGestionar = (V) ->
    {
        Intent intentGestion = new Intent(this,activity_gestionarIngresos.class);
        presenter.configurarIntent(intentGestion);
        startActivity(intentGestion);
    };

    private View.OnClickListener HandlerMostrarMetricas = (V) -> {
        Intent intentMetricas = new Intent(this,Activity_Metricas.class);
        startActivity(intentMetricas);
    };

    @Override
    public void setearNombreDeLocal(String nombre) {
        nombreLocal.setText(nombre);
    }
}