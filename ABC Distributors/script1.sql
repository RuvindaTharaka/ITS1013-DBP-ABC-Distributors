DROP DATABASE IF EXSISTS ABC;
CREATE DATABASE IF NOT EXISTS ABC;
USE ABC;
DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
	custID VARCHAR(6),
	custTitle VARCHAR(5),
	custName VARCHAR(30),
	custAddress VARCHAR(30),
	city VARCHAR(20),
	province VARCHAR(20),
	postalCode VARCHAR(9),
	CONSTRAINT PRIMARY KEY (custID)
);
SHOW DATABASES;
DESCRIBE Customer;
DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
	orderID VARCHAR(6),
	orderDate TEXT,
	custID VARCHAR(6),
	CONSTRAINT PRIMARY KEY (orderID),
	CONSTRAINT FOREIGN KEY (custID) REFERENCES Customer(custID)ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW DATABASES;
DESCRIBE `Order`;
DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
	itemCode VARCHAR(6),
	description VARCHAR(50),
	packSize VARCHAR(20),
	unitPrice DECIMAL(6,2),
	qtyOnHand INT(5),
	CONSTRAINT PRIMARY KEY (itemCode)
	
);
SHOW TABLES;
DESCRIBE Item;
DROP TABLE IF EXISTS OrderDetail;
CREATE TABLE IF NOT EXISTS OrderDetail(
	orderID VARCHAR(6),
	itemCode VARCHAR(6),
	orderQTY INT(11),
	discount DECIMAL(6,2),
	CONSTRAINT PRIMARY KEY (orderID,itemCode)
);
SHOW TABLES;
DESCRIBE OrderDetail;
