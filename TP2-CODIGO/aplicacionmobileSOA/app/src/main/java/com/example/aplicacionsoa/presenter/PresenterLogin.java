package com.example.aplicacionsoa.presenter;

import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import com.example.aplicacionsoa.ClasesUtilitarias.Utilitarias;
import com.example.aplicacionsoa.model.PreferenciasCompartidas;
import com.example.aplicacionsoa.model.Http_Conection_Service_POST;
import com.example.aplicacionsoa.view.Activity_Login;

import org.json.JSONException;
import org.json.JSONObject;

public class PresenterLogin implements MvpLogin_Registro.Presenter{

    private Activity_Login viewLogin;

    ReceptorRespuestaServidor broadcast;
    public static final String ACTIONBROADCAST = "com.example.aplicacionsoa.presenter.intentfilter.RTA_SERVIDOR_LOGIN";
    private IntentFilter filtro;
    public static final String URI_LOGIN = "http://so-unlam.net.ar/api/api/login";
    static String token_refresh;
    static String token;
    private boolean isRegisterBroadcast = false;
    private PreferenciasCompartidas preferencias;

    public PresenterLogin(Activity_Login viewLogin) {
        this.viewLogin = viewLogin;
        this.preferencias = new PreferenciasCompartidas(viewLogin,"METRICAS");
    }

    @Override
    public void iniciarServicio(JSONObject obj) {
        Intent reg = new Intent(viewLogin, Http_Conection_Service_POST.class);
        reg.putExtra("URI",URI_LOGIN);
        reg.putExtra("JSON",obj.toString());
        reg.putExtra("pathBroadcast",ACTIONBROADCAST);
        viewLogin.startService(reg);
        viewLogin.comunicarRequestEnProceso();
    }

    @Override
    public void configurarBroadCastReciever() {
        filtro = new IntentFilter(ACTIONBROADCAST);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        broadcast = new ReceptorRespuestaServidor(this);
        viewLogin.registerReceiver(broadcast,filtro);
        isRegisterBroadcast=true;
    }

    @Override
    public void liberarRecursos() {
        //if(Utilitarias.isMyServiceRunning(Http_Conection_Service_POST.class,viewLogin)) {
         //   viewLogin.stopService(new Intent(viewLogin, Http_Conection_Service_POST.class));
        //}
        if(isRegisterBroadcast) {
            viewLogin.unregisterReceiver(broadcast);
            isRegisterBroadcast=false;
        }
    }

    @Override
    public boolean comprobarConexion() {
        return Utilitarias.comprobarConexion(viewLogin);

    }


    @Override
    public void comunicarRespuestaExitosa() {
        viewLogin.mostrarResultadoConexion("Se ha logueado exitosamente");
        viewLogin.iniciarAplicacion();
        liberarRecursos();
        //newIntent.putExtra("token_refresh",token_refresh);
        //newIntent.putExtra("token",token);

    }

    public void falloRegistroEvento(String msjError)
    {

    }

    @Override
    public void comunicarRespuestaFallida(String msj) {
        preferencias.guardarSesionFallida();
        viewLogin.mostrarResultadoConexion(msj);
        liberarRecursos();
    }

    @NonNull
    public JSONObject getJsonObject(String mail,String contrasenia) {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("email", mail);
            obj.put("password", contrasenia);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
