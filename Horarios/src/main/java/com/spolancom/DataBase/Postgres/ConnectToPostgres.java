package com.spolancom.DataBase.Postgres;

import com.spolancom.DataBase.BaseDeDatosExceptions.ConexionFallidaException;
import com.spolancom.DataBase.ConnectionDBs;
import com.spolancom.HorariosConfiguracion;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToPostgres implements ConnectionDBs {

    private Connection connection;
    private HorariosConfiguracion config;

    public ConnectToPostgres(HorariosConfiguracion c) throws ConexionFallidaException {
        this.config = c;
        this.connection = this.connectarAPostgres();
    }

    private Connection connectarAPostgres() throws ConexionFallidaException {
        PGSimpleDataSource source = new PGSimpleDataSource();
        String[] serversNames = {config.obtenerHost()};

        source.setServerNames(serversNames);
        source.setDatabaseName(config.obtenerBaseDeDatos());
        source.setUser(config.obtenerUsuario());
        // source.setPassword("xxx");
        source.setLoginTimeout(10);

        Connection conn = null;

        try {
            return source.getConnection();
            // System.out.println(conn);
        }
        catch(SQLException e)
        {
            throw new ConexionFallidaException("No se ha podido conectar a la base de datos");
        }
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
