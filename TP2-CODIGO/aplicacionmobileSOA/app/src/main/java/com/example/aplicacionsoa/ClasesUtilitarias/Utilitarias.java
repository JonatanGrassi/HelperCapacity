package com.example.aplicacionsoa.ClasesUtilitarias;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utilitarias {

    public static boolean comprobarConexion(AppCompatActivity activity)
    {
        boolean conexion = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            conexion = true;
        return conexion;
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass,AppCompatActivity activity)
    {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName()))
            {
                return true;
            }
        } return false;
    }

    public static StringBuilder convertInputStreamToString(InputStreamReader inputStream) throws IOException {
        BufferedReader br = new BufferedReader(inputStream);
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line + "\n");
        }
        br.close();
        return result;
    }

}
