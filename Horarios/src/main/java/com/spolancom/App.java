package com.spolancom;

import java.nio.file.FileSystemException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            HorariosConfiguracion hc = new HorariosConfiguracion("src/main/resources/configuracion.txt");

            System.out.println(hc.obtenerConextionString());
        } catch (FileSystemException e) {
            e.printStackTrace();
        }
    }
}
