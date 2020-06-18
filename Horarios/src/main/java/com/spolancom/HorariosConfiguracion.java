package com.spolancom;

import java.nio.file.FileSystemException;

public class HorariosConfiguracion extends Configuracion {

    public HorariosConfiguracion(String ruta) throws FileSystemException {
        super(ruta);
    }

    public int obtenerPuerto() {
        return Integer.parseInt(this.obtenValor("puerto"));
    }

    public String obtenerConextionString() {
        return "jdbc:mysql://" + this.obtenValor("host") + ":" + this.obtenValor("puerto") + "/" + this.obtenValor("base_de_datos");
    }

    public String obtenerUsuario() {
        return this.obtenValor("usuario");
    }

    public String obtenerContrasena() {
        return (this.obtenValor("password").compareTo("None") == 0) ? null : this.obtenValor("password");
    }
}
