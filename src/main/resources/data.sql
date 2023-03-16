-- create salle table
CREATE TABLE Salle (
   salle_name VARCHAR(50) NOT NULL,
   capacity VARCHAR(50) NOT NULL,
   PRIMARY KEY (salle_name)
);

-- create salle table
CREATE TABLE Equipement (
   id INT NOT NULL,
   name VARCHAR(50) NOT NULL,
   salle_name VARCHAR(20) NOT NULL,
   PRIMARY KEY (id),
   Foreign key (salle_name) references salle(salle_name)
);

-- create salle table
CREATE TABLE Reservation (
   id INT NOT NULL,
   reunion_name VARCHAR(50) NOT NULL,
   salle_name VARCHAR(20) NOT NULL,
   start_date timestamp,
   end_date timestamp,
   PRIMARY KEY (id),
   Foreign key (salle_name) references salle(salle_name)
);

--Salle Etage:1
INSERT INTO Salle (salle_name, capacity) VALUES ('E1001', 23);
INSERT INTO Salle (salle_name, capacity) VALUES ('E1002', 10);
INSERT INTO Salle (salle_name, capacity) VALUES ('E1003', 8);
INSERT INTO Salle (salle_name, capacity) VALUES ('E1004', 4);
--Salle Etage:2
INSERT INTO Salle (salle_name, capacity) VALUES ('E2001', 4);
INSERT INTO Salle (salle_name, capacity) VALUES ('E2002', 15);
INSERT INTO Salle (salle_name, capacity) VALUES ('E2003', 7);
INSERT INTO Salle (salle_name, capacity) VALUES ('E2004', 9);
--Salle Etage:3
INSERT INTO Salle (salle_name, capacity) VALUES ('E3001', 13);
INSERT INTO Salle (salle_name, capacity) VALUES ('E3002', 8);
INSERT INTO Salle (salle_name, capacity) VALUES ('E3003', 9);
INSERT INTO Salle (salle_name, capacity) VALUES ('E3004', 4);

--Equipements salles Etage:1
INSERT INTO Equipement (id, name, salle_name) VALUES (1, 'NEANT', 'E1001');
INSERT INTO Equipement (id, name, salle_name) VALUES (2, 'ECRAN', 'E1002');
INSERT INTO Equipement (id, name, salle_name) VALUES (3, 'PIEUVRE', 'E1003');
INSERT INTO Equipement (id, name, salle_name) VALUES (4, 'TABLEAU', 'E1004');
--Equipements salles Etage:2
INSERT INTO Equipement (id, name, salle_name) VALUES (5, 'NEANT', 'E2001');
INSERT INTO Equipement (id, name, salle_name) VALUES (6, 'ECRAN', 'E2002');
INSERT INTO Equipement (id, name, salle_name) VALUES (7, 'WEBCAM', 'E2002');
INSERT INTO Equipement (id, name, salle_name) VALUES (8, 'NEANT', 'E2003');
INSERT INTO Equipement (id, name, salle_name) VALUES (9, 'TABLEAU', 'E2004');
--Equipements salles Etage:3
INSERT INTO Equipement (id, name, salle_name) VALUES (10, 'ECRAN', 'E3001');
INSERT INTO Equipement (id, name, salle_name) VALUES (11, 'WEBCAM', 'E3001');
INSERT INTO Equipement (id, name, salle_name) VALUES (12, 'PIEUVRE', 'E3001');
INSERT INTO Equipement (id, name, salle_name) VALUES (13, 'NEANT', 'E3002');
INSERT INTO Equipement (id, name, salle_name) VALUES (14, 'ECRAN', 'E3003');
INSERT INTO Equipement (id, name, salle_name) VALUES (15, 'PIEUVRE', 'E3003');
INSERT INTO Equipement (id, name, salle_name) VALUES (16, 'TABLEAU', 'E3003');
INSERT INTO Equipement (id, name, salle_name) VALUES (17, 'NEANT', 'E3004');



