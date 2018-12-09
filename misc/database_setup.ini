CREATE DATABASE GRD;
use GRD;

CREATE TABLE Occurrence_Type (
    id INT AUTO_INCREMENT,
    name VARCHAR(80) NOT NULL,
    model VARCHAR(80) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Funcionário (
    id INT AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    setor VARCHAR(80) NOT NULL,
    turno VARCHAR(80) NOT NULL,
    salário VARCHAR(80) NOT NULL,
    idade INT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE Máquina (
    id INT AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    setor VARCHAR(80) NOT NULL,
    modelo VARCHAR(80) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE Funcionário_Occurrence_ (
    id INT AUTO_INCREMENT,
    idType INT NOT NULL,
    idResource INT NOT NULL,
    details VARCHAR(80),
    date DATE,
    PRIMARY KEY (id)
);

CREATE TABLE Máquina_Occurrence_ (
    id INT AUTO_INCREMENT,
    idType INT NOT NULL,
    idResource INT NOT NULL,
    details VARCHAR(80),
    date DATE,
    PRIMARY KEY (id)
);

INSERT INTO Occurrence_Type (name, model)
VALUES ("Atraso", "Funcionário");

INSERT INTO Occurrence_Type (name, model)
VALUES ("Falta", "Funcionário");

INSERT INTO Occurrence_Type (name, model)
VALUES ("Acidente", "Funcionário");

INSERT INTO Occurrence_Type (name, model)
VALUES ("Defeito", "Máquina");
