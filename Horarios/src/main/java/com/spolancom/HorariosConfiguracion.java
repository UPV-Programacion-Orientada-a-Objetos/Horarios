package com.spolancom;

import com.spolancom.Archivos.TipoDeArchivosValidos;
import com.spolancom.ConfiguracionException.LlaveNoEncontradaException;
import com.spolancom.DataBase.Gestores;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;

public class HorariosConfiguracion extends Configuracion {

    public HorariosConfiguracion(String ruta) throws FileSystemException {
        super(ruta);
    }

    public String obtenerBaseDeDatos() {
        return this.obtenValor("base_de_datos");
    }

    public String obtenerHost() {
        return this.obtenValor("host");
    }

    public int obtenerPuerto() {
        return Integer.parseInt(this.obtenValor("puerto"));
    }

    public String obtenerConextionString() {
        String connStr = null;
        switch (this.obtenGestor()) {
            case MYSQL:
                connStr = "jdbc:mysql://" + this.obtenValor("host") + ":" + this.obtenValor("puerto") + "/" + this.obtenValor("base_de_datos");
                break;
            default:
                connStr = "jdbc:sqlite:" + this.generarCadenaConexionSQLite();
                break;
        }
        return connStr;
    }

    private String generarCadenaConexionSQLite() {
        String tmp;
        try {
            String rutaTmp = this.obtenValor("ruta_temporal_db");

            if (rutaTmp.compareTo("MEMORY") == 0) {
                throw new LlaveNoEncontradaException();
            }
            else {
                File tmpFile = new File(rutaTmp);

                if (tmpFile.exists()) {
                    if (tmpFile.isDirectory()) {
                        tmp = tmpFile.getPath() + "/temporary.db";
                    }
                    else {
                            tmp = tmpFile.getCanonicalPath();
                    }
                }
                else {
                    throw new LlaveNoEncontradaException();
                }
            }
        }
        catch (LlaveNoEncontradaException | IOException e) {
            tmp = ":memory:";
        }

        return tmp;
    }

    public String obtenerUsuario() {
        return this.obtenValor("usuario");
    }

    public String obtenerContrasena() {
        return (this.obtenValor("password").compareTo("None") == 0) ? null : this.obtenValor("password");
    }

    public Gestores obtenGestor() {
        Gestores tmpGest;

        if (this.obtenValor("gestor").compareTo("Mysql") == 0) {
            tmpGest = Gestores.MYSQL;
        } else if (this.obtenValor("gestor").compareTo("Postgres") == 0) {
            tmpGest = Gestores.POSTGRES;
        }
        else {
            tmpGest = Gestores.NONE_DB;
        }

        return tmpGest;
    }

    public TipoDeArchivosValidos obtenerTipoDeArchivos() {
        TipoDeArchivosValidos tmp;
        if (this.obtenValor("tipo_de_archivos").compareTo("excel") == 0) {
            tmp = TipoDeArchivosValidos.EXCEL;
        } else {
            tmp = TipoDeArchivosValidos.TXT;
        }

        return tmp;
    }

    public File obtenerRutaDeArchivos() {
        return new File(this.obtenValor("ruta_archivos"));
    }
}
