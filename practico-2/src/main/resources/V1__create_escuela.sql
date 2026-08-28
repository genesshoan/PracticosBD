CREATE DATABASE Escuela;

USE Escuela;

CREATE TABLE Personas 
(
    cedula INT, 
    nombre VARCHAR(45), 
    apellido VARCHAR(45),
);
CREATE TABLE Maestras 
(
    cedula INT, 
    grupo VARCHAR(45)

    CONSTRAINT fk_maestras_personas FOREIGN KEY (cedula) REFERENCES Personas(cedula)
);

CREATE TABLE Alumnos 
(
    cedula INT, 
    cedulaMaestra INT,

    CONSTRAINT fk_alumnos_personas FOREIGN KEY (cedula) REFERENCES Personas(cedula)
    CONSTRAINT fk_alumnos_maestras FOREIGN KEY (cedulaMaestra) REFERENCES Maestras(cedula)
);





