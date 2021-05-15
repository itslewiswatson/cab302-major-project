-- Use database

CREATE DATABASE IF NOT EXISTS db;
USE db;

-- Delete tables if already exist.

DROP TABLE IF EXISTS db.unitusers;
DROP TABLE IF EXISTS db.unitassets;
DROP TABLE IF EXISTS db.trade;
DROP TABLE IF EXISTS db.user;
DROP TABLE IF EXISTS db.unit;
DROP TABLE IF EXISTS db.asset;

-- Create tables.

CREATE TABLE IF NOT EXISTS user (
  username CHAR(255) NOT NULL,
  password CHAR(255) NOT NULL,
  admin BOOLEAN NOT NULL,
  PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS unit (
  name CHAR(255) NOT NULL,
  credits INT NOT NULL,
  PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS unitusers (
  username CHAR(255) NOT NULL,
  unitName CHAR(255) NOT NULL,
  PRIMARY KEY (username,unitName),
  FOREIGN KEY (username) REFERENCES user(username),
  FOREIGN KEY (unitName) REFERENCES unit(name)
);

CREATE TABLE IF NOT EXISTS asset (
  name CHAR(255) NOT NULL,
  PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS unitassets (
  unitName CHAR(255) NOT NULL,
  assetName CHAR(255) NOT NULL,
  quantity INT NOT NULL,
  PRIMARY KEY (unitName,assetName),
  FOREIGN KEY (unitName) REFERENCES unit(name),
  FOREIGN KEY (assetName) REFERENCES asset(name)
);

CREATE TABLE IF NOT EXISTS trade (
  id INT NOT NULL AUTO_INCREMENT,
  unitName CHAR(255) NOT NULL,
  assetName CHAR(255) NOT NULL,
  date DATE NOT NULL,
  type CHAR(4) NOT NULL,
  quantity INT NOT NULL,
  price INT NOT NULL,
  completed BOOLEAN NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (unitName) REFERENCES unit(name),
  FOREIGN KEY (assetName) REFERENCES asset(name)
);

-- Add initial entries.

INSERT INTO user (username, password, admin)
VALUES ('admin', '$2a$10$ip/7v4Bf9c.tiiyw7solvOfMAK4PrPtnzD57Qx986f8ddnbWddiZy', 1);

INSERT INTO user (username, password, admin)
VALUES ('Mitchell', '$2a$10$eQFfKOvSyJk4BXSxeC3kpu0e6pi7au8zv4zodPkHweLUBLrAAMWrO', 0);

INSERT INTO user (username, password, admin)
VALUES ('Lewis', '$2a$10$066D/ddR1OayyPI.Lwmt1eoydW1UBOmQgRN52CGYDnZtNq2IlfXf2', 0);

INSERT INTO unit (name, credits)
VALUES ('Animation', 10000);

INSERT INTO unit (name, credits)
VALUES ('Processing', 10000);

INSERT INTO unit (name, credits)
VALUES ('Simulation', 10000);

INSERT INTO unitusers (username, unitName)
VALUES ('Mitchell', 'Animation');

INSERT INTO unitusers (username, unitName)
VALUES ('Mitchell', 'Processing');

INSERT INTO unitusers (username, unitName)
VALUES ('Lewis', 'Simulation');

INSERT INTO asset (name)
VALUES ('GPU Hours');

INSERT INTO asset (name)
VALUES ('CPU Hours');

INSERT INTO unitassets (unitName, assetName, quantity)
VALUES ('Animation', 'GPU Hours', 250);

INSERT INTO unitassets (unitName, assetName, quantity)
VALUES ('Processing', 'CPU Hours', 2500);

INSERT INTO unitassets (unitName, assetName, quantity)
VALUES ('Simulation', 'CPU Hours', 500);

INSERT INTO trade (unitName, assetName, date, type, quantity, price, completed)
VALUES ('Animation', 'GPU Hours', '2021-05-15', 'BUY', '50', '1000', 0);

INSERT INTO trade (unitName, assetName, date, type, quantity, price, completed)
VALUES ('Processing', 'CPU Hours', '2021-05-15', 'SELL', '500', '5000', 0);

INSERT INTO trade (unitName, assetName, date, type, quantity, price, completed)
VALUES ('Simulation', 'CPU Hours', '2021-05-15', 'BUY', '100', '1000', 0);