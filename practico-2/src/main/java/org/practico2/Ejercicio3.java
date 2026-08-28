package org.practico2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Ejercicio3 {
    private static String DB_URL = "jdbc:mysql://localhost:3306/Escuela";

   public static void main(String[] args) {

        try(Connection con = DriverManager.getConnection(DB_URL, "root", "");
            Statement statement = con.createStatement();
            Scanner scanner = new Scanner(System.in);) {
        
            boolean close = false;
            
            while (!close) {
                System.out.print("Ingrese comando: ");
                String command = scanner.nextLine();
                
                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Hasta la proxima");
                    close = false;
                    break;
                }

                if (command.toLowerCase().contains("select")) {
                    System.out.println("Solo se pueden ejecutar consulas de modificacion");
                    continue;
                }

                try {
                    int affectedRows = statement.executeUpdate(command);
                    System.out.println("Cantidad de filas afectadas: " + affectedRows);
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch(SQLException e) {
            e.getStackTrace();
        }
   } 
}
