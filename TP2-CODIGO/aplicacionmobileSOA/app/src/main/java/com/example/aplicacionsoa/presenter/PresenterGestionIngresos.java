package com.example.aplicacionsoa.presenter;

import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.aplicacionsoa.ClasesUtilitarias.Local;
import com.example.aplicacionsoa.ClasesUtilitarias.Utilitarias;
import com.example.aplicacionsoa.model.Http_Conection_Service_POST_EVENTOS;
import com.example.aplicacionsoa.model.Http_conection_Service_PUT;
import com.example.aplicacionsoa.view.activity_gestionarIngresos;

import org.json.JSONException;
import org.json.JSONObject;

public class PresenterGestionIngresos implements MvpGestionarIngresos.Presenter, SensorEventListener {

    private activity_gestionarIngresos view;
    private String token = PresenterRegistro.token;
    private String token_refresh = PresenterRegistro.token_refresh;
    private SensorManager sensorManager;
    private Sensor sensorProximidad;
    private Local tipoLocal;
    private Double metrosCuadrados;
    private String nombreLocal;
    private Integer contadorPersonas = 0;
    private Integer cantidadMaximaPersonas;
    public static final String ACTIONBROADCASTEVENTOS = "com.example.aplicacionsoa.presenter.intentfilter.RTA_SERVIDOR_REG_EVENTOS";
    public static final String ACTIONBROADCASTTOKEN = "com.example.aplicacionsoa.presenter.intentfilter.RTA_SERVIDOR_REG_ACTUALIZAR_TOKEN";
    public static final String URI_EVENTO = "http://so-unlam.net.ar/api/api/event";
    public static final String URI_REFRESH = "http://so-unlam.net.ar/api/api/refresh";
    ReceptorRespuestaServidorEventos broadcastEventos;
    ReceptorRespuestaServidorRefreshToken broadcastActualizarToken;
    boolean broadCastEventoRegistred;
    boolean broadCastRefreshRegistred;
    private float proximidad;
    private boolean seRegistroEvento = false;

    public PresenterGestionIngresos(activity_gestionarIngresos view,SensorManager sensorManager)
    {
        this.view = view;
        this.sensorManager = sensorManager;
        broadCastEventoRegistred=false;
        broadCastRefreshRegistred =false;
    }

    private void registrarEventoSensorProximidad() {
        if(Utilitarias.comprobarConexion(view)) {
            configurarBroadCastRecieverEventos();
            Intent reg2 = new Intent(view, Http_Conection_Service_POST_EVENTOS.class);
            reg2.putExtra("URI", URI_EVENTO);
            reg2.putExtra("JSON", getJsonEvento("PROD", "Detecto Sensor", "Se detecto cercania con un sensor de proximidad").toString());
            reg2.putExtra("token", token);
            reg2.putExtra("pathBroadcast", ACTIONBROADCASTEVENTOS);
            view.startService(reg2);
        }
    }

    @Override
    public void obtenerInformacionLocal() {
        metrosCuadrados = view.getIntent().getExtras().getDouble("metrosCuadrados");
        Bundle bund = view.getIntent().getExtras().getBundle("eleccion");
        tipoLocal =(Local) bund.getSerializable("eleccion");
        nombreLocal = view.getIntent().getExtras().getString("nombreLocal");
        view.setearTipoDeLocal(tipoLocal.getTipoDeLocal());
        cantidadMaximaPersonas = tipoLocal.calcularAforo(metrosCuadrados);
        view.setearProgressBar(cantidadMaximaPersonas);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void resetearContador() {
        contadorPersonas = 0;
        view.actualizarTextViewContador(contadorPersonas.toString());
        view.actualizarProgressBar(contadorPersonas);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void aumentarContador() {
        if(contadorPersonas < cantidadMaximaPersonas) {
            contadorPersonas += 1;
            view.actualizarTextViewContador(contadorPersonas.toString());
            view.actualizarProgressBar(contadorPersonas);
        }
        else
        {
            view.notificarCapacidadTotal();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void restarContador() {
        if(contadorPersonas > 0)
        {
            contadorPersonas-=1;
            view.actualizarTextViewContador(contadorPersonas.toString());
            view.actualizarProgressBar(contadorPersonas);
        }
    }

    @Override
    public void escucharSensor() {
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_FASTEST);
        sensorProximidad = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    public void dejarDeSensarSensores() {
        sensorManager.unregisterListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        proximidad = event.values[0];
        if(proximidad < sensorProximidad.getMaximumRange()){
            if (!seRegistroEvento){
                view.inhabilitarBotones();
                registrarEventoSensorProximidad();
                seRegistroEvento = true;
            }
        }else {
            if(seRegistroEvento){
                view.habilitarBotones();
                seRegistroEvento = false;
            }
        }
    }

    @NonNull
    public JSONObject getJsonEvento(String env, String tipoEvento, String descripcion) {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("env", env);
            obj.put("type_events", tipoEvento);
            obj.put("description", descripcion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private void configurarBroadCastRecieverEventos() {
        IntentFilter filtro = new IntentFilter(ACTIONBROADCASTEVENTOS);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastEventos = new ReceptorRespuestaServidorEventos(this);
        view.registerReceiver(broadcastEventos,filtro);
        broadCastEventoRegistred = true;
    }

    private void configurarBroadCastActualizarToken() {
        IntentFilter filtro = new IntentFilter(ACTIONBROADCASTTOKEN);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastActualizarToken = new ReceptorRespuestaServidorRefreshToken(this);
        view.registerReceiver(broadcastActualizarToken,filtro);
        broadCastRefreshRegistred = true;
    }

    public void falloTokenExpirado()
    {
        if(Utilitarias.comprobarConexion(view)) {
            configurarBroadCastActualizarToken();
            Intent reg = new Intent(view, Http_conection_Service_PUT.class);
            reg.putExtra("URI", URI_REFRESH);
            reg.putExtra("token_refresh", token_refresh);
            reg.putExtra("pathBroadcast", ACTIONBROADCASTTOKEN);
            view.startService(reg);
        }
    }

    @Override
    public void desregistrarBroadcastEventos() {
        if(broadCastEventoRegistred) {
            view.unregisterReceiver(broadcastEventos);
            broadCastEventoRegistred = false;
        }
    }

    @Override
    public void desregistrarBroadcastRefresh() {
        if(broadCastRefreshRegistred) {
            view.unregisterReceiver(broadcastActualizarToken);
            broadCastRefreshRegistred = false;
        }
    }

    @Override
    public void reenviarRegistroEvento() {
        registrarEventoSensorProximidad();
    }

    @Override
    public void registracionExitosa() {
        view.mostrarRegistracionEventoExitosa();
    }

    @Override
    public void actualizarTokens(String token,String token_refresh) {
        this.token = token;
        this.token_refresh =token_refresh;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
