package com.spolancom.DataBase;

import com.spolancom.DataBase.BaseDeDatosExceptions.ConexionFallidaException;
import com.spolancom.DataBase.Mysql.ConnectToMysql;
import com.spolancom.DataBase.Postgres.ConnectToPostgres;
import com.spolancom.DataBase.SQLite.ConnectToSQLite;
import com.spolancom.HorariosConfiguracion;

import java.sql.Connection;

public class ConexionBaseDeDatos {

    public static ConnectionDBs obtenerInstancia(HorariosConfiguracion config) {

        ConnectionDBs tmpCon;

        try {
            switch (config.obtenGestor()) {
                case MYSQL:
                    tmpCon = new ConnectToMysql(config);
                    break;
                case POSTGRES:
                    tmpCon = new ConnectToPostgres(config);
                    break;
                default:
                    tmpCon = new ConnectToSQLite(config);
            }
        }
        catch (ConexionFallidaException e) {
            tmpCon = new ConnectToSQLite(config);
        }

        return tmpCon;
    }
}
