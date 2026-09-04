package org.practico2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Ejercicio6 {

private static String DB_URL = "jdbc:mysql://localhost:3306/";

    public static void main(String[] args) {

        try {
            Connection con = DriverManager.getConnection(DB_URL, "root", "");

            DatabaseMetaData metaData = con.getMetaData();

            System.out.println("Bases de datos existentes:");
            ResultSet catalogos = metaData.getCatalogs();
            while (catalogos.next()) {
                String nombreBD = catalogos.getString("TABLE_CAT");
                System.out.println(nombreBD);
            }
            catalogos.close();

            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);

            System.out.print("Ingrese nombre de la BD a consultar: ");
            String bdElegida = br.readLine();

            System.out.println("Tablas de la BD seleccionada:");

            ResultSet columnas = metaData.getColumns(bdElegida, null, null, null);

            String tablaActual = null;

            while (columnas.next()) {
                String tabla = columnas.getString("TABLE_NAME");
                String columna = columnas.getString("COLUMN_NAME");
                String tipo = columnas.getString("TYPE_NAME");
                int tamaño = columnas.getInt("COLUMN_SIZE");

                if (!tabla.equals(tablaActual)) {
                    if (tablaActual != null) {
                        System.out.println(); 
                    }
                    System.out.println("Tabla - " + tabla);
                    System.out.print("Columnas - " + columna + " (" + tipo + " " + tamaño + ")");
                    tablaActual = tabla;
                } else {
                    System.out.print(" - " + columna + " (" + tipo + " " + tamaño + ")");
                }
            }
            System.out.println();

            columnas.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error de lectura: " + e.getMessage());
        }
    }
}

/* 
Para hacer este ejercicio, consulte la API de Java correspondiente a la interface 
DatabaseMetaData del paquete java.sql y busque información en Internet acerca de su funcionamiento. 

Se desea escribir una aplicación en Java que proporcione un listado en consola de 
la siguiente información estadística de la instalación de MySQL: 

 Nombres de las bases de datos existentes en el DBMS. 
 Nombres de las tablas de una base de datos determinada, incluyendo los nombres de sus 
columnas, sus tipos de datos y sus tamaños correspondientes. 

Lo primero que hará el programa será brindar un listado de todos los nombres de las bases de 
datos existentes, el cual deberá tener el siguiente formato: 

Bases de datos existentes: 

information_schema 
escuela 
prueba 
etc. 

Posteriormente, el programa solicitará al usuario que ingrese el nombre de una de las bases de 
datos y listará la información correspondiente a sus tablas según el siguiente formato: 

Ingrese nombre de la BD a consultar: escuela 
Tablas de la BD seleccionada: 
Tabla - alumnos 
Columnas - cedula (INT 10) – cedulaMaestra (INT 10) 
Tabla - maestras 
Columnas - cedula (INT 10) – grupo (VARCHAR 45) 
etc. 

Se pide: Escribir un programa Main en Java que implemente la aplicación solicitada. 

Observación: Para poder leer texto de la entrada estándar (consola) utilice los mismos flujos de entrada/salida indicados para el ejercicio
*/ 
