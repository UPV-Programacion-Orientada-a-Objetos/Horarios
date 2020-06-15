package com.spolancom.DataBase.SQLite;

import com.spolancom.DataBase.ConnectionDBs;

import java.sql.ResultSet;

public class ConnectToSQLite implements ConnectionDBs {
    @Override
    public ResultSet EjecutaNoTransaccional(String sql) {
        return null;
    }

    @Override
    public int EjecutarTransaccional(String sql) {
        return 0;
    }
}
