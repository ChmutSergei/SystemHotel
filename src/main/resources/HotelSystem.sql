CREATE DATABASE HotelSystem2  CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE HotelSystem2;
CREATE TABLE Contacts(id INT PRIMARY KEY AUTO_INCREMENT, email CHAR(20), telephone CHAR(20), country CHAR(20),
                      city CHAR(20), address CHAR(120), zip CHAR (10));
CREATE TABLE Users(id INT PRIMARY KEY AUTO_INCREMENT, login CHAR(20), password CHAR(30), name CHAR(20),lastname CHAR(20),
                   role CHAR (15),contact_id INT, FOREIGN KEY (contact_id) REFERENCES Contacts(id) ON DELETE  CASCADE  ON UPDATE CASCADE);
CREATE TABLE Rooms(id INT PRIMARY KEY AUTO_INCREMENT, roomNumber INT,type CHAR(15),bedType INT,
                   price DOUBLE, description TEXT);
CREATE TABLE Reservation (id INT PRIMARY KEY AUTO_INCREMENT, user_id INT, room_id INT,checkIn DATE,checkOut DATE, date DATE,
  FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE  CASCADE  ON UPDATE CASCADE,
  FOREIGN KEY (room_id) REFERENCES Rooms(id) ON DELETE  CASCADE  ON UPDATE CASCADE);
INSERT INTO Users (login, password , name , lastname, role) VALUES ("admin", "tZxnvxlqR1gZHkL3ZnDOug==","Valeriy", "Manager", "admin");
INSERT INTO Contacts(email, telephone, country, city, address, zip) VALUES ("cl@tu.by","37529424454","Belarus","Minsk","some adr","220000");
INSERT INTO Contacts(email, telephone, country, city, address, zip) VALUES ("d@tut.by","37533454","Russia","Smolensk","some adr","220000");
INSERT INTO Contacts(email, telephone, country, city, address, zip) VALUES ("rt@tut.by","05424454","Belarus","Vitebsk","some adr","220000");
INSERT INTO Users (login, password, name, lastname, role, contact_id) VALUES ("user1", "gdyb21LQTcIANtvYMT7QVQ==","Sergei", "Leshko", "user",1);
INSERT INTO Users (login, password, name, lastname, role, contact_id) VALUES ("user2", "gdyb21LQTcIANtvYMT7QVQ==","Anya", "Leshko", "user",1);
INSERT INTO Users (login, password, name, lastname, role, contact_id) VALUES ("user3", "gdyb21LQTcIANtvYMT7QVQ==","Sasha", "Leshko", "user",1);
INSERT INTO Users (login, password, name, lastname, role, contact_id) VALUES ("user4", "gdyb21LQTcIANtvYMT7QVQ==","Vanya", "Puko", "user",2);
INSERT INTO Users (login, password, name, lastname, role, contact_id) VALUES ("user5", "gdyb21LQTcIANtvYMT7QVQ==","Sveta", "Puko", "user",2);
INSERT INTO Users (login, password, name, lastname, role, contact_id) VALUES ("user6", "gdyb21LQTcIANtvYMT7QVQ==","Jenya", "Derevyanko", "user",3);
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (1,'Супер Люкс',3,128.9,
        'Приятный и уютный номер распологается на втором этаже с видом во двор.');
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (2,'Люкс',2,58.9,
        'Уютный номер распологается на втором этаже с видом во двор.');
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (3,'Супер Люкс',2,115.9,
        'Приятный и уютный номер распологается на втором этаже с видом во двор.');
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (4,'Люкс',3,109.9,
        'Уютный номер распологается на втором этаже с видом во двор.');
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (5,'Люкс',1,89.9,
        'Приятный и уютный номер распологается на первом этаже с видом на паркинг');
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (6,'Люкс',1,79.9,
        'Приятный и уютный номер распологается на первом этаже с видом на паркинг');
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (7,'Люкс',1,82.9,
        'Приятный и уютный номер распологается на первом этаже с видом на паркинг');
INSERT INTO Rooms(roomNumber, type, bedType, price,  description)
VALUES (8,'СуперЛюкс',1,89.9,
        'Приятный и уютный номер распологается на первом этаже с видом на паркинг');
INSERT INTO Rooms(roomNumber, type, bedType, price,  description)
VALUES (9,'СуперЛюкс',4,189.5,
        'Приятный и уютный номер распологается на первом этаже с видом на город');
INSERT INTO Reservation(room_id, user_id,checkIn,checkOut, date) VALUES (2,2,'2018-09-03','2018-09-05','2018-07-10');
INSERT INTO Reservation(room_id, user_id,checkIn,checkOut, date) VALUES (3,3,'2018-09-05','2018-09-06','2018-07-10');
INSERT INTO Reservation(room_id, user_id,checkIn,checkOut, date) VALUES (4,4,'2018-09-05','2018-09-07','2018-07-10');
INSERT INTO Reservation(room_id, user_id,checkIn,checkOut, date) VALUES (5,5,'2018-09-02','2018-09-05','2018-07-10');
INSERT INTO Reservation(room_id, user_id,checkIn,checkOut, date) VALUES (6,6,'2018-09-05','2018-06-07','2018-07-10');
INSERT INTO Reservation(room_id, user_id,checkIn,checkOut, date) VALUES (7,7,'2018-09-05','2018-06-07','2018-07-10');


