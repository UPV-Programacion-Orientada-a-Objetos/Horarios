package com.spolancom.Modelo;

import com.spolancom.DataBase.ConexionBaseDeDatos;
import com.spolancom.DataBase.ConnectionDBs;
import com.spolancom.HorariosConfiguracion;

public class CapaModelo {
    protected ConnectionDBs miConexion;

    public CapaModelo (HorariosConfiguracion hc) {
        miConexion = ConexionBaseDeDatos.obtenerInstancia(hc);
    }
}
