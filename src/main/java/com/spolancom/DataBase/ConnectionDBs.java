package com.spolancom.DataBase;

import java.sql.ResultSet;

public interface ConnectionDBs {
    ResultSet EjecutaNoTransaccional(String sql);
    int EjecutarTransaccional(String sql);
}
