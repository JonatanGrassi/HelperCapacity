package com.example.aplicacionsoa.presenter;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.telephony.SmsManager;
import androidx.core.app.ActivityCompat;

import com.example.aplicacionsoa.view.Activity_Verificacion;

public class PresenterVerificacion implements MvpVerificacion.Presenter {

    private String codigoDeConfirmacion;
    private final Activity_Verificacion activity_verificacion;

    public PresenterVerificacion(Activity_Verificacion activity_verificacion) {
        this.activity_verificacion = activity_verificacion;
    }

    @Override
    public void calcularNivelBateria() {
        IntentFilter intentFilterBateria = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent estadoBateria = activity_verificacion.getApplicationContext().registerReceiver(null,intentFilterBateria);
        int nivelDeBateria = estadoBateria.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int escalaBateria = estadoBateria.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        Integer porcentajeBateria = nivelDeBateria * 100 / escalaBateria;
        activity_verificacion.mostrarNivelBateria(porcentajeBateria);
    }

    @Override
    public void enviarSMS(String telefono) {
        if(telefono.isEmpty()) {
            activity_verificacion.mostrarMensaje("Debe ingresar un numero de telefono");
        }else {
            SmsManager sms = SmsManager.getDefault();
            int minCodigoVerificacion = 1000;
            int maxCodigoVerificacion = 9999;
            codigoDeConfirmacion = obtenerCodigoVerif(maxCodigoVerificacion, minCodigoVerificacion).toString();
            sms.sendTextMessage(telefono, null, codigoDeConfirmacion, null, null);
            activity_verificacion.mostrarMensaje("Codigo enviado, revise su mensajeria");
            activity_verificacion.habilitarIngresoCodigoSeguridad();
        }
    }

    @Override
    public void verificarCodigoSeguridad(String codigo) {
        // if(codigo.equals(codigoDeConfirmacion)) {
                   activity_verificacion.habilitarLogin();
        //}else {
        //    activity_verificacion.mostrarMensaje("Codigo de verificacion erroneo");
        //}
    }

    @Override
    public void pedirPermisoSMS() {
        if (ActivityCompat.checkSelfPermission(activity_verificacion, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity_verificacion,new String[] { Manifest.permission.SEND_SMS}, 1);
        }
    }

    public Integer obtenerCodigoVerif(int numMaximo,int numeroMinimo) {
        double result = Math.floor(Math.random() * numMaximo + 1);
        if(result<=numeroMinimo) {
            result += numeroMinimo;
        }
        return (int) result;
    }
}
