-- Some data setup for testing and demonstration purposes
-- * It assumes that the database is empty

-- Resources

INSERT INTO Funcionário (nome, setor, turno, salário, idade)
VALUES ("João", "Administração", "Manhã", "R$ 3000,00", 27);

INSERT INTO Funcionário (nome, setor, turno, salário, idade)
VALUES ("Maria", "Administração", "Tarde", "R$ 3000,00", 25);

INSERT INTO Funcionário (nome, setor, turno, salário, idade)
VALUES ("José", "TI", "Tarde", "R$ 4000,00", 26);

INSERT INTO Funcionário (nome, setor, turno, salário, idade)
VALUES ("Carla", "TI", "Manhã", "R$ 5000,00", 30);

INSERT INTO Máquina (nome, setor, modelo)
VALUES ("Computador 1", "TI", "Dell");

INSERT INTO Máquina (nome, setor, modelo)
VALUES ("Computador 2", "TI", "MacBook");

INSERT INTO Máquina (nome, setor, modelo)
VALUES ("Impressora 1", "Administração", "Epson");

-- Occurrences

INSERT INTO Funcionário_Occurrence_ (idType, idResource, details, date)
VALUES (1, 1, ".", "2018-08-09");

INSERT INTO Funcionário_Occurrence_ (idType, idResource, details, date)
VALUES (2, 1, ".", "2018-09-06");

INSERT INTO Funcionário_Occurrence_ (idType, idResource, details, date)
VALUES (2, 3, ".", "2018-08-23");

INSERT INTO Funcionário_Occurrence_ (idType, idResource, details, date)
VALUES (3, 4, ".", "2018-10-02");

INSERT INTO Máquina_Occurrence_ (idType, idResource, details, date)
VALUES (4, 1, ".", "2018-10-02");