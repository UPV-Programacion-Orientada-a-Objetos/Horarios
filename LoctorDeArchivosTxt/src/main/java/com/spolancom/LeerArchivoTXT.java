package com.spolancom;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.Vector;

public class LeerArchivoTXT {

    private String ruta;
    private String nombreArchivo;
    private File Archivo;

    private FileReader fileReader;
    private BufferedReader bufferedReader;

    public LeerArchivoTXT() throws FileSystemException{
        this.ruta = "src/main/resources/mi_archivo.txt";
        this.Archivo = new File(this.ruta);

        this.existeOCrealo(Archivo, false);
    }

    public LeerArchivoTXT(boolean creacion) throws FileSystemException{
        this.ruta = "src/main/resources/mi_archivo.txt";
        this.Archivo = new File(this.ruta);

        this.existeOCrealo(Archivo, creacion);
    }

    public LeerArchivoTXT(String ruta) throws FileSystemException {
        this.ruta = ruta;
        this.Archivo = new File(ruta);

        this.existeOCrealo(Archivo, false);

    }

    public LeerArchivoTXT(String ruta, boolean creacion) throws FileSystemException {
        this.ruta = ruta;
        this.Archivo = new File(ruta);

        this.existeOCrealo(Archivo, creacion);

    }

    public LeerArchivoTXT(File archivo) throws FileSystemException {
        this.Archivo = archivo;
        try {
            if (this.existeOCrealo(archivo, false)) {
                this.ruta = archivo.getCanonicalPath();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LeerArchivoTXT(File archivo, boolean creacion) throws FileSystemException {
        this.Archivo = archivo;
        try {
            if (this.existeOCrealo(archivo, creacion)) {
                this.ruta = archivo.getCanonicalPath();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean existeOCrealo(File arch, boolean creacion) throws FileSystemException {
        boolean ban = false;
        if (!arch.exists()) {
            try {
                if (creacion) {
                    arch.createNewFile();
                    ban = true;
                }
            }
            catch (IOException e) {
                System.err.println("El archivo " + ruta + " no se puede crear");
                ban = false;
                throw new FileSystemException("El archivo " + ruta + " no se puede crear");
            }
        }
        else {
            ban = true;
        }

        return ban;
    }

    /**
     * Método para leer contenido y retonar un vector con este contenido
     * @return
     */
    public Vector<String> leerContenido(boolean ignoreEmpty) {
        Vector<String> fileContent = new Vector<>();

        try (FileReader fr = new FileReader(this.Archivo);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (ignoreEmpty) {
                    if (!line.isEmpty()) {
                        fileContent.add(line);
                    }
                }
                else {
                    fileContent.add(line);
                }

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return fileContent;
    }

    public Vector<String> leerContenido(String ignoreInit, boolean ignoreEmpty) {
        Vector<String> fileContent = new Vector<>();

        try (FileReader fr = new FileReader(this.Archivo);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(ignoreInit)) {
                    if (ignoreEmpty) {
                        if (!line.isEmpty()) {
                            fileContent.add(line);
                        }
                    }
                    else {
                        fileContent.add(line);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        return fileContent;
    }

    public void iniciaLectura() throws FileNotFoundException {
        this.fileReader = new FileReader(this.Archivo);
        this.bufferedReader = new BufferedReader(this.fileReader);
    }

    public String leerSiguienteLinea(String ignoreInit, boolean ignoreEmpty) throws IOException {
        String line = this.bufferedReader.readLine();
        boolean ban = false;

        if (line != null) {
            while(line.startsWith(ignoreInit) || line.isEmpty() == ignoreEmpty) {
                line = this.bufferedReader.readLine();

                if (line == null) {
                    ban = true;
                    break;
                }
            }
        }
        if (ban || line == null) {
            if (this.bufferedReader != null) this.bufferedReader.close();
            if (this.fileReader != null) this.fileReader.close();
        }
        return line;
    }
}