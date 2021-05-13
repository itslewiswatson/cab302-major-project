DROP TABLE IF EXISTS db.unitusers;
DROP TABLE IF EXISTS db.unitassets;
DROP TABLE IF EXISTS db.trade;
DROP TABLE IF EXISTS db.user;
DROP TABLE IF EXISTS db.unit;
DROP TABLE IF EXISTS db.asset;

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

