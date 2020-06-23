package com.spolancom;

import com.spolancom.DataBase.ConexionBaseDeDatos;
import com.spolancom.DataBase.ConnectionDBs;

import java.nio.file.FileSystemException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            HorariosConfiguracion hc = new HorariosConfiguracion("src/main/resources/configuracion.txt");
            ConnectionDBs test = ConexionBaseDeDatos.obtenerInstancia(hc);

            String sql = "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';";
            //String sql = "SELECT * FROM carrera";
            try {
                ResultSet resultSet = test.EjecutaNoTransaccional(sql);

                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } catch (FileSystemException e) {
            e.printStackTrace();
        }
    }
}
