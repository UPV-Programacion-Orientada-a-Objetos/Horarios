package com.spolancom.Modelo;

import com.spolancom.DataBase.ConnectionDBs;
import com.spolancom.HorariosConfiguracion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Carrera extends CapaModelo {
    private Integer idcarrera;
    private String nombre_carrera;
    private boolean exists;

    public Carrera(HorariosConfiguracion hc, Integer idcarrera) {
        super(hc);
        this.idcarrera = idcarrera;

        try {
            if (this.consultaExistencia(idcarrera)) {

            }
        }
    }

    public Carrera(HorariosConfiguracion hc, Integer idcarrera, String nombre_carrera) {
        super(hc);
        this.idcarrera = idcarrera;
        this.nombre_carrera = nombre_carrera;
    }

    private boolean consultaExistencia(Integer idcarrera) {
        String sql = "SELECT COUNT(*) AS contador FROM carrera WHERE idcarrera = " + idcarrera;
        boolean ban = false;

        try {
            ResultSet count = this.miConexion.EjecutaNoTransaccional(sql);
            ban = (count.getInt("contador") == 1);
        } catch (SQLException throwables) {
            ban = false;
        }

        return ban;
    }

    private boolean consultaExistencia(Integer idcarrera, String nombre) {
        String sql = "SELECT COUNT(*) AS contador FROM carrera WHERE idcarrera = " + idcarrera + " AND nombre_carrera = '" + nombre + "'" ;
        boolean ban = false;

        try {
            ResultSet count = this.miConexion.EjecutaNoTransaccional(sql);
            ban = (count.getInt("contador") == 1);
        } catch (SQLException throwables) {
            ban = false;
        }

        return ban;
    }

    public String getNombre_carrera() {
        return this.nombre_carrera;
    }

    public Integer getIdcarrera() {
        return idcarrera;
    }

    public void setIdcarrera(Integer idcarrera) {
        this.idcarrera = idcarrera;
    }

    public void setNombre_carrera(String nombre_carrera) {
        this.nombre_carrera = nombre_carrera;
    }
}
