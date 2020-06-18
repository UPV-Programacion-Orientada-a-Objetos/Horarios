package com.spolancom;

import com.spolancom.ConfiguracionException.ArchivoDeConfiguracionMalFormadoException;
import com.spolancom.ConfiguracionException.LlaveNoEncontradaException;

import java.nio.file.FileSystemException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public abstract class Configuracion {
    private HashMap<String, String> claves;

    public Configuracion() {
        this.claves = new HashMap<>();
    }

    public Configuracion(Vector<String> llavesYValores) {
        this();

        String llave_valor[];
        for (String line: llavesYValores) {
            llave_valor = line.split("=");

            if (llave_valor.length != 2) {
                throw new ArchivoDeConfiguracionMalFormadoException();
            }

            this.agregaLlaveYValor(llave_valor[0].trim(), llave_valor[1].trim());
        }
    }

    public Configuracion(String ruta) throws FileSystemException {
        this(new LeerArchivoTXT(ruta).leerContenido("#", true));
    }

    public void agregaLlaveYValor(String k, String v) {
        this.claves.put(k, v);
    }

    public String obtenValor(String llave) {
        if (this.claves.containsKey(llave)) {
            return this.claves.get(llave);
        }

        throw new LlaveNoEncontradaException();
    }
}
