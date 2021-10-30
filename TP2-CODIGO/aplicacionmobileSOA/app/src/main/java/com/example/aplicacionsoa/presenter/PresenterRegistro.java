package com.example.aplicacionsoa.presenter;

import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import com.example.aplicacionsoa.model.PreferenciasCompartidas;
import com.example.aplicacionsoa.model.Http_Conection_Service_POST;
import com.example.aplicacionsoa.ClasesUtilitarias.Utilitarias;
import com.example.aplicacionsoa.view.Activity_Register;

import org.json.JSONException;
import org.json.JSONObject;

public class PresenterRegistro implements MvpLogin_Registro.Presenter {
    private Activity_Register viewRegistro;
    ReceptorRespuestaServidor broadcast;
    public static final String ACTIONBROADCAST = "com.example.aplicacionsoa.presenter.intentfilter.RTA_SERVIDOR_REGISTRO";
    private IntentFilter filtro;
    public static final String URI_REGISTER = "http://so-unlam.net.ar/api/api/register";
    static String token_refresh;
    static String token;
    private boolean isRegisterBroadcast = false;
    private PreferenciasCompartidas preferencias;


    public PresenterRegistro(Activity_Register viewRegistro) {
        this.viewRegistro = viewRegistro;
        this.preferencias = new PreferenciasCompartidas(viewRegistro,"METRICAS");
    }

    @Override
    public void iniciarServicio(JSONObject obj) {
        Intent reg = new Intent(viewRegistro, Http_Conection_Service_POST.class);
        reg.putExtra("URI",URI_REGISTER);
        reg.putExtra("JSON",obj.toString());
        reg.putExtra("pathBroadcast",ACTIONBROADCAST);
        viewRegistro.startService(reg);
        viewRegistro.comunicarRequestEnProceso();
    }

    @NonNull
    public JSONObject getJsonObject(String env, String nombre, String apellido, String mail, String dni, String contrasenia, String comision, String grupo) {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("env", env);
            obj.put("name", nombre);
            obj.put("lastname", apellido);
            obj.put("dni", dni);
            obj.put("email", mail);
            obj.put("password", contrasenia);
            obj.put("commission", comision);
            obj.put("group", grupo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }


    @Override
    public void configurarBroadCastReciever() {
        filtro = new IntentFilter(ACTIONBROADCAST);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        broadcast = new ReceptorRespuestaServidor(this);
        viewRegistro.registerReceiver(broadcast,filtro);
        isRegisterBroadcast = true;
    }

    @Override
    public void liberarRecursos() {
        //if(Utilitarias.isMyServiceRunning(Http_Conection_Service_POST.class,viewRegistro)) {
          //  viewRegistro.stopService(new Intent(viewRegistro, Http_Conection_Service_POST.class));
        //}
       if(isRegisterBroadcast) {
           viewRegistro.unregisterReceiver(broadcast);
           isRegisterBroadcast=false;
       }
    }

    @Override
    public boolean comprobarConexion() {
       return Utilitarias.comprobarConexion(viewRegistro);
    }



    @Override
    public void comunicarRespuestaExitosa() {
        preferencias.guardarRegistro();
        viewRegistro.mostrarResultadoConexion("Se ha registrado correctamente");
        viewRegistro.iniciarAplicacion();
        liberarRecursos();
        //newIntent.putExtra("token_refresh",token_refresh);
        //newIntent.putExtra("token",token);

    }

    @Override
    public void comunicarRespuestaFallida(String msj) {
        viewRegistro.mostrarResultadoConexion(msj);
        liberarRecursos();
    }

}
