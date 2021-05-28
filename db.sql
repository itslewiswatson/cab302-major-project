-- Use database

CREATE DATABASE IF NOT EXISTS db;
USE db;

-- Delete tables if already exist.

DROP TABLE IF EXISTS db.unitusers;
DROP TABLE IF EXISTS db.unitassets;
DROP TABLE IF EXISTS db.trades;
DROP TABLE IF EXISTS db.users;
DROP TABLE IF EXISTS db.units;
DROP TABLE IF EXISTS db.assets;

-- Create tables.

CREATE TABLE IF NOT EXISTS users (
  id VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  admin BOOLEAN NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS units (
  id VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  credits INT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS unitusers (
  user_id VARCHAR(255) NOT NULL,
  unit_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (user_id, unit_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (unit_id) REFERENCES units(id)
);

CREATE TABLE IF NOT EXISTS assets (
  id VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  date_added DATE NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS unitassets (
  unit_id VARCHAR(255) NOT NULL,
  asset_id VARCHAR(255) NOT NULL,
  quantity INT NOT NULL,
  PRIMARY KEY (unit_id, asset_id),
  FOREIGN KEY (unit_id) REFERENCES units(id),
  FOREIGN KEY (asset_id) REFERENCES assets(id)
);

CREATE TABLE IF NOT EXISTS trades (
  id VARCHAR(255) NOT NULL,
  unit_id VARCHAR(255) NOT NULL,
  asset_id VARCHAR(255) NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  date_listed DATE NOT NULL,
  type VARCHAR(4) NOT NULL,
  quantity INT NOT NULL,
  price INT NOT NULL,
  quantity_filled INT NOT NULL,
  date_filled DATE,
  PRIMARY KEY (id),
  FOREIGN KEY (unit_id) REFERENCES units(id),
  FOREIGN KEY (asset_id) REFERENCES assets(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Add initial entries.

INSERT INTO
  users (id, username, password, admin)
VALUES
  ('bdfc9ecf-5bbe-4e5e-9c05-e42167ef25bd', 'admin', '$2a$10$ip/7v4Bf9c.tiiyw7solvOfMAK4PrPtnzD57Qx986f8ddnbWddiZy', 1),
  ('45c3c36b-d77b-490b-b24a-c245c2f865ba', 'Mitchell', '$2a$10$eQFfKOvSyJk4BXSxeC3kpu0e6pi7au8zv4zodPkHweLUBLrAAMWrO', 0),
  ('11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', 'Lewis', '$2a$10$VvUx/7wDYSJ0yeX.6xwUZuPczqBJlsnEpuCATDfGLsupr0k3W6VG6', 0);

INSERT INTO
  units (id, name, credits)
VALUES
  ('b7313567-395b-424b-b130-ea413f8fba1a', 'Animation', 10000),
  ('cdf64dd1-a541-4b1f-a71b-8f5fa191defa', 'Processing', 10000),
  ('bd609f6d-baa9-476d-b50d-e833f40bbec7', 'Simulation', 10000);

INSERT INTO
  unitusers (user_id, unit_id)
VALUES
  ('45c3c36b-d77b-490b-b24a-c245c2f865ba', 'b7313567-395b-424b-b130-ea413f8fba1a'),
  ('45c3c36b-d77b-490b-b24a-c245c2f865ba', 'cdf64dd1-a541-4b1f-a71b-8f5fa191defa'),
  ('11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', 'bd609f6d-baa9-476d-b50d-e833f40bbec7');

INSERT INTO
  assets (id, name, date_added)
VALUES
  ('b2f27a46-c324-42d0-a242-36b88c279fff', 'GPU Hours', NOW()),
  ('4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', 'CPU Hours', NOW());

INSERT INTO
  unitassets (unit_id, asset_id, quantity)
VALUES
  ('b7313567-395b-424b-b130-ea413f8fba1a', 'b2f27a46-c324-42d0-a242-36b88c279fff', 250),
  ('cdf64dd1-a541-4b1f-a71b-8f5fa191defa', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', 2500),
  ('bd609f6d-baa9-476d-b50d-e833f40bbec7', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', 500);

INSERT INTO
    trades (id, unit_id, asset_id, user_id, date_listed, type, quantity, price, quantity_filled, date_filled)
VALUES
    ('ecb9a5c3-d12a-444a-b571-be8908794116', 'b7313567-395b-424b-b130-ea413f8fba1a', 'b2f27a46-c324-42d0-a242-36b88c279fff', '11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', NOW(), 'BUY', 50223, 4, 1, NULL),
    ('07238804-48e5-46f6-9af5-f699c7684fb3', 'bd609f6d-baa9-476d-b50d-e833f40bbec7', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', '11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', NOW(), 'SELL', 194838, 7, 1, NULL),
    ('5e66c645-132e-442f-a5cd-89937b7aa938', 'b7313567-395b-424b-b130-ea413f8fba1a', 'b2f27a46-c324-42d0-a242-36b88c279fff', '11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', NOW(), 'SELL', 138839, 9, 1, NULL),
    ('8fa7949e-94eb-4dd0-9d40-51f035eb7795', 'bd609f6d-baa9-476d-b50d-e833f40bbec7', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', '11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', NOW(), 'BUY', 110920, 3, 1, NULL),
    ('a727fb51-8ba6-4746-8cc2-f748544b1a55', 'bd609f6d-baa9-476d-b50d-e833f40bbec7', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', '11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', NOW(), 'BUY', 110920, 3, 1, NOW());
