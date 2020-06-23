package com.spolancom.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ConnectionDBs {
    ResultSet EjecutaNoTransaccional(String sql) throws SQLException;
    int EjecutarTransaccional(String sql) throws SQLException;
    String toString();
}
