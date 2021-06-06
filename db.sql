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
  FOREIGN KEY (user_id) REFERENCES unitusers(user_id)
);

-- Add initial entries.

INSERT INTO
  users (id, username, password, admin)
VALUES
  ('bdfc9ecf-5bbe-4e5e-9c05-e42167ef25bd', 'admin', '$2a$10$ip/7v4Bf9c.tiiyw7solvOfMAK4PrPtnzD57Qx986f8ddnbWddiZy', 1),
  ('45c3c36b-d77b-490b-b24a-c245c2f865ba', 'Mitchell', '$2a$10$eQFfKOvSyJk4BXSxeC3kpu0e6pi7au8zv4zodPkHweLUBLrAAMWrO', 0),
  ('11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', 'Lewis', '$2a$10$VvUx/7wDYSJ0yeX.6xwUZuPczqBJlsnEpuCATDfGLsupr0k3W6VG6', 0),
  ('515b5522-b8b6-4fb0-9f64-099ced9cd288', 'Timothy', '$2a$10$VvUx/7wDYSJ0yeX.6xwUZuPczqBJlsnEpuCATDfGLsupr0k3W6VG6', 1),
  ('d57c2ff0-592f-4107-849e-f3cc571395b9', 'Samuel', '$2a$10$VvUx/7wDYSJ0yeX.6xwUZuPczqBJlsnEpuCATDfGLsupr0k3W6VG6', 0),
  ('96814e6b-f875-49d1-bfd8-f523b124ade5', 'Charlotte', '$2a$10$VvUx/7wDYSJ0yeX.6xwUZuPczqBJlsnEpuCATDfGLsupr0k3W6VG6', 0),
  ('495e92e4-b039-46c9-b17d-839a32431bc4', 'Max', '$2a$10$VvUx/7wDYSJ0yeX.6xwUZuPczqBJlsnEpuCATDfGLsupr0k3W6VG6', 0),
  ('49a39b3e-7645-46a9-9450-bc9ce822ab00', 'Sarah', '$2a$10$VvUx/7wDYSJ0yeX.6xwUZuPczqBJlsnEpuCATDfGLsupr0k3W6VG6', 0);

INSERT INTO
  units (id, name, credits)
VALUES
  ('b7313567-395b-424b-b130-ea413f8fba1a', 'Animation', 10000),
  ('cdf64dd1-a541-4b1f-a71b-8f5fa191defa', 'Processing', 10000),
  ('f9ccfcdb-4de9-4302-be7c-a0cbd6a944fa', 'Accounting', 10000),
  ('a0a7d0de-c7b5-4150-b9a9-c93e34f89c79', 'Engineering', 10000),
  ('4114eede-5732-4fcc-93cc-c94aa6b20542', 'QA', 10000),
  ('ea5bddad-d974-4c9b-9c75-8b2fb392cdb5', 'Sales', 10000),
  ('bd609f6d-baa9-476d-b50d-e833f40bbec7', 'Simulation', 10000);

INSERT INTO
  unitusers (user_id, unit_id)
VALUES
  ('11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', 'bd609f6d-baa9-476d-b50d-e833f40bbec7'),
  ('11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', '4114eede-5732-4fcc-93cc-c94aa6b20542'),
  ('45c3c36b-d77b-490b-b24a-c245c2f865ba', 'a0a7d0de-c7b5-4150-b9a9-c93e34f89c79'),
  ('45c3c36b-d77b-490b-b24a-c245c2f865ba', 'b7313567-395b-424b-b130-ea413f8fba1a'),
  ('45c3c36b-d77b-490b-b24a-c245c2f865ba', 'cdf64dd1-a541-4b1f-a71b-8f5fa191defa'),
  ('495e92e4-b039-46c9-b17d-839a32431bc4', 'a0a7d0de-c7b5-4150-b9a9-c93e34f89c79'),
  ('515b5522-b8b6-4fb0-9f64-099ced9cd288', 'f9ccfcdb-4de9-4302-be7c-a0cbd6a944fa'),
  ('96814e6b-f875-49d1-bfd8-f523b124ade5', 'f9ccfcdb-4de9-4302-be7c-a0cbd6a944fa'),
  ('d57c2ff0-592f-4107-849e-f3cc571395b9', 'ea5bddad-d974-4c9b-9c75-8b2fb392cdb5');

INSERT INTO
  assets (id, name, date_added)
VALUES
  ('b2f27a46-c324-42d0-a242-36b88c279fff', 'GPU Hours', '2020-02-12'),
  ('5df5d01a-39b0-4313-910e-692984e5e0ec', 'CPU Hours', '2020-03-01'),
  ('5d4e2de1-fe49-4515-8a2d-937cca7a51ac', 'Free Food Tokens', '2020-04-13'),
  ('4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', 'CFD Hours', '2020-05-10');

INSERT INTO
  unitassets (unit_id, asset_id, quantity)
VALUES
  ('b7313567-395b-424b-b130-ea413f8fba1a', 'b2f27a46-c324-42d0-a242-36b88c279fff', 60),
  ('cdf64dd1-a541-4b1f-a71b-8f5fa191defa', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', 2500),
  ('a0a7d0de-c7b5-4150-b9a9-c93e34f89c79', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', 1987),
  ('bd609f6d-baa9-476d-b50d-e833f40bbec7', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', 500);

INSERT INTO
   trades (id, unit_id, asset_id, user_id, date_listed, type, quantity, price, quantity_filled, date_filled)
VALUES
   ('ecb9a5c3-d12a-444a-b571-be8908794116', 'b7313567-395b-424b-b130-ea413f8fba1a', 'b2f27a46-c324-42d0-a242-36b88c279fff', '11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', '2021-04-02', 'BUY', 100, 4, 100, '2021-04-02'),
   ('8620a21b-43e3-4685-88e0-d39705608af5', 'b7313567-395b-424b-b130-ea413f8fba1a', 'b2f27a46-c324-42d0-a242-36b88c279fff', '11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', '2021-04-07', 'SELL', 40, 5, 40, '2021-04-07'),
   ('07238804-48e5-46f6-9af5-f699c7684fb3', 'bd609f6d-baa9-476d-b50d-e833f40bbec7', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', '11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', '2021-04-10', 'BUY', 500, 7, 500, '2021-04-11'),
   ('b29a7acf-4cdf-47e1-8ab6-0153af5c63ec', 'bd609f6d-baa9-476d-b50d-e833f40bbec7', '5d4e2de1-fe49-4515-8a2d-937cca7a51ac', '11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', '2021-04-10', 'BUY', 10, 20, 5, '2021-04-11'),
   ('9555652f-0249-429b-b28b-67cae018363d', 'a0a7d0de-c7b5-4150-b9a9-c93e34f89c79', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', '495e92e4-b039-46c9-b17d-839a32431bc4', '2021-04-10', 'SELL', 50, 5, 50, '2021-04-11'),
   ('80f6ff58-6e9b-44d5-8c42-273a0d4ea097', 'f9ccfcdb-4de9-4302-be7c-a0cbd6a944fa', '5df5d01a-39b0-4313-910e-692984e5e0ec', '96814e6b-f875-49d1-bfd8-f523b124ade5', '2021-04-10', 'SELL', 120, 1, 120, '2021-04-10'),
   ('c29de9da-3064-4acb-8505-c1cd6c6c4267', 'bd609f6d-baa9-476d-b50d-e833f40bbec7', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', '11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', '2021-05-02', 'SELL', 500, 30, 500, '2021-05-12'),
   ('d428b9c8-68f7-4bbb-9917-dcfb2932581f', 'a0a7d0de-c7b5-4150-b9a9-c93e34f89c79', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', '515b5522-b8b6-4fb0-9f64-099ced9cd288', '2021-05-14', 'BUY', 100, 27, 0, NULL),
   ('e39e9d1b-ad20-4368-be1a-088697f7b721', 'ea5bddad-d974-4c9b-9c75-8b2fb392cdb5', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', 'd57c2ff0-592f-4107-849e-f3cc571395b9', '2021-05-16', 'BUY', 150, 27, 0, NULL),
   ('0d090200-999c-4af7-bb62-facc80400a69', 'bd609f6d-baa9-476d-b50d-e833f40bbec7', '4a1d4bf3-339d-48ba-a5fa-461ef2b13e81', '11b7526a-a6e3-4f38-bdf4-51e1f5e727e0', '2021-05-16', 'BUY', 10, 25, 0, NULL);
