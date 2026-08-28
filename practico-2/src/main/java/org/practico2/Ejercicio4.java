package org.practico2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio4 {
    private static String DB_URL = "jdbc:mysql://localhost:3306/Escuela";

   public static void main(String[] args) {
        String unicaConsulta = """
                    SELECT m.cedula, p.nombre, p.apellido
                    FROM Maestras m
                    JOIN Personas p ON m.cedula = p.cedula
                    JOIN Alumnos a ON m.cedula = a.cedulaMaestra
                    GROUP BY m.cedula, p.nombre, p.apellido
                    ORDER BY COUNT(a.cedula) DESC
                    LIMIT 1
                """;

        String selectCedulaMaestras = """
                    SELECT cedula
                    FROM Maestras
                """;

        String selectCantAlumnosMaestra = """
                    SELECT COUNT(*) AS cantidad
                    FROM Alumnos
                    WHERE cedulaMaestra = ?
                """;

        String selectDatosMaestra = """
                    SELECT m.cedula, p.nombre, p.apellido
                    FROM Maestras m
                    JOIN Personas p ON m.cedula = p.cedula
                    WHERE m.cedula = ?
                """;
    
        try(Connection con = DriverManager.getConnection(DB_URL, "root", "");
            Statement stmt = con.createStatement();
            PreparedStatement pstmtCant = con.prepareStatement(selectCantAlumnosMaestra);
            PreparedStatement pstmtDatos = con.prepareStatement(selectDatosMaestra);
            ResultSet rsUnica = stmt.executeQuery(unicaConsulta);
            ) {
            if (rsUnica.next()) {
                System.out.println("Salida con una con una sola consulta: " + datosMaestra(rsUnica));
            } else {
                System.out.println("No hay maestras en el sistema");
            }

            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            con.setAutoCommit(false);

            ResultSet maestras = stmt.executeQuery(selectCedulaMaestras);
 
            int cedulaMayor = -1;
            int mayorCantidad = 0;

            while (maestras.next()) {
                int cedulaActual = maestras.getInt("cedula");
              
                pstmtCant.setInt(1, cedulaActual);
               
                try (ResultSet cantAlumnos = pstmtCant.executeQuery();) {
                    cantAlumnos.next();
                    int cantidadActual = cantAlumnos.getInt("cantidad");
                    
                    if(cantidadActual > mayorCantidad) {
                        mayorCantidad = cantidadActual;
                        cedulaMayor = cedulaActual;
                    }
                }
            }

            if (cedulaMayor != -1) {
                try (ResultSet datosMaestra = pstmtDatos.executeQuery()) {
                    if (datosMaestra.next()) {
                        System.out.println("Salida con tres consultas: " + datosMaestra(datosMaestra));
                    }
                }
            } else {
                System.out.println("No hay maestras en el sistema");
            }
           
            con.commit();
        } catch (SQLException e) {
            e.getStackTrace();
        }
   } 

   private static String datosMaestra(ResultSet resultSet) throws SQLException {
        String cedula = resultSet.getString("cedula");
        String nombre = resultSet.getString("nombre");
        String apellido = resultSet.getString("apellido");

        return cedula + " | " + nombre + " | " + apellido;
   }
}


