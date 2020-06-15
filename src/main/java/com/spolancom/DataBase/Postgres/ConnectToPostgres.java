package com.spolancom.DataBase.Postgres;

import com.spolancom.DataBase.ConnectionDBs;

import java.sql.ResultSet;

public class ConnectToPostgres implements ConnectionDBs {
    @Override
    public ResultSet EjecutaNoTransaccional(String sql) {
        return null;
    }

    @Override
    public int EjecutarTransaccional(String sql) {
        return 0;
    }
}
