package org.practico2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio5 {

    private static String DB_URL = "jdbc:mysql://localhost:3306/Escuela";

    public static void main(String[] args) {
       try(Connection con = DriverManager.getConnection(DB_URL, "root", "");
            CallableStatement cstmt = con.prepareCall("{CALL Escuela.BorrarMaestra(?)}")) {

            con.setAutoCommit(false);

            try {
                int cedula = 1000001;
                cstmt.setInt(1, 1000001);
                boolean tieneResultSet = cstmt.execute();

                List<Integer> alumnos = new ArrayList<>();

                if (tieneResultSet) {
                    try (ResultSet rs = cstmt.getResultSet()) {
                        while (rs.next()) {
                            alumnos.add(rs.getInt("cedula"));
                        }
                    }
                }

                String grupo = null;

                if (cstmt.getMoreResults()) {
                    try (ResultSet rs = cstmt.getResultSet()) {
                        if (rs.next()) {
                            grupo = rs.getString("grupo");
                        }
                    }
                }

                String nombre = null;
                String apellido = null;

                if (cstmt.getMoreResults()) {
                    try (ResultSet rs = cstmt.getResultSet()) {
                        if (rs.next()) {
                            nombre = rs.getString("nombre");
                            apellido = rs.getString("apellido");
                        }
                    }
                }

                System.out.println("Maestra: " + cedula);
                System.out.println("Nombre: " + nombre + " " + apellido);
                System.out.println("Grupo: " + grupo);
                System.out.println("Alumnos:");

                for (Integer alumno : alumnos) {
                    System.out.println("    " + alumno);
                }
            } catch (Exception e) {
                con.rollback();
                e.getStackTrace();
            }
        } catch (SQLException e) {
            e.getStackTrace();
        } 
    }
}
