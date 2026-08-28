package org.practico2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    private static String SERVER_URL = "jdbc:mysql://localhost:3306";
    private static String DB_URL = SERVER_URL + "/Escuela";

    public static void main(String[] args) {
        String createDatabase = "CREATE DATABASE IF NOT EXISTS Escuela";
        String useDatabase = "USE Escuela";

        String createPersonas = """
                CREATE TABLE Personas
                ( 
                    cedula INT PRIMARY KEY,
                    nombre VARCHAR(45),
                    apellido VARCHAR(45)
                ) 
            """;
                
        String createMaestras = """
                CREATE TABLE Maestras 
                (
                    cedula INT PRIMARY KEY,  
                    grupo VARCHAR(45),
                
                    CONSTRAINT fk_maestras FOREIGN KEY (cedula) REFERENCES Personas(cedula)
                )
            """;

        String createAlumnos = """
                CREATE TABLE Alumnos 
                (
                    cedula INT PRIMARY KEY, 
                    cedulaMaestra INT,
                    
                    CONSTRAINT fk_alumnos_personas FOREIGN KEY (cedula) REFERENCES Personas(cedula),
                    CONSTRAINT fk_alumnos_maestras FOREIGN KEY (cedulaMaestra) REFERENCES Maestras(cedula)
                )
            """;
                
        try(Connection con = DriverManager.getConnection(SERVER_URL, "root", "");
            Statement statement = con.createStatement()) {
                
            statement.executeUpdate(createDatabase);
        } catch(SQLException e) {
            e.getStackTrace();
        }

        try(Connection con = DriverManager.getConnection(DB_URL, "root", "");
            Statement statement = con.createStatement()) {
                
            statement.executeUpdate(createPersonas);
            statement.executeUpdate(createMaestras);
            statement.executeUpdate(createAlumnos);
        } catch(SQLException e) {
            e.getStackTrace();
        }
    }
}
