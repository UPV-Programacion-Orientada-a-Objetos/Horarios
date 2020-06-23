package com.spolancom.DataBase.Mysql;

import com.spolancom.DataBase.BaseDeDatosExceptions.ConexionFallidaException;
import com.spolancom.DataBase.ConnectionDBs;
import com.spolancom.HorariosConfiguracion;

import java.sql.*;

public class ConnectToMysql implements ConnectionDBs {

    private Connection connection;
    private HorariosConfiguracion config;

    public ConnectToMysql(HorariosConfiguracion c) throws ConexionFallidaException {
        this.config = c;
        this.connection = this.connectarAMySQL();
    }

    private Connection connectarAMySQL() throws ConexionFallidaException {
        Connection tmpCon;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            tmpCon = DriverManager.getConnection(this.config.obtenerConextionString(),this.config.obtenerUsuario(),this.config.obtenerContrasena());
            if(tmpCon != null){
                return tmpCon;
            }
        } catch (SQLException e) {
            throw new ConexionFallidaException("No se ha podido conectar a la base de datos");
        }
        catch (ClassNotFoundException e) {
            System.err.println("Error fatal... consulte el manual");
            System.exit(-1);
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
