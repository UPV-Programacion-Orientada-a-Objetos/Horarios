package com.spolancom.DataBase.SQLite;

import com.spolancom.DataBase.ConnectionDBs;
import com.spolancom.HorariosConfiguracion;
import java.sql.SQLException;

import java.sql.*;

public class ConnectToSQLite implements ConnectionDBs {

    private Connection connection;
    private HorariosConfiguracion config;

    public ConnectToSQLite(HorariosConfiguracion c) {
        this.config = c;
        this.connection = conectarASQLite();
    }

    private Connection conectarASQLite() {
        String url = config.obtenerConextionString();
        try {
            System.out.println(url);
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            try {
                if (!url.contains("memory")) {

                    System.err.println("La rua: " + url + "No funciona. Intentando conectar a memoria");
                    return DriverManager.getConnection("jdbc:sqlite::memory:");
                }
                else {
                    throw new SQLException("No se ha podido conectar en ninguna instancia");
                }
            } catch (SQLException throwables) {
                System.err.println("No se ha podido conectar en ninguna instancia");
                System.exit(-2);
            }

        }

        return null;
    }

    @Override
    public ResultSet EjecutaNoTransaccional(String sql) throws SQLException {
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(sql);
    }

    @Override
    public int EjecutarTransaccional(String sql) throws SQLException {
        Statement statement = this.connection.createStatement();
        return  statement.executeUpdate(sql);
    }

    public String toString() {
        return this.connection.toString();
    }
}
