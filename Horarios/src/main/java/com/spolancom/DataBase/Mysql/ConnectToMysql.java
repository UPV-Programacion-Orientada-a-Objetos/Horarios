package com.spolancom.DataBase.Mysql;

import com.spolancom.DataBase.ConnectionDBs;

import java.sql.ResultSet;

public class ConnectToMysql implements ConnectionDBs {
    @Override
    public ResultSet EjecutaNoTransaccional(String sql) {
        return null;
    }

    @Override
    public int EjecutarTransaccional(String sql) {
        return 0;
    }
}
