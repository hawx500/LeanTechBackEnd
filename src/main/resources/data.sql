DROP TABLE IF EXISTS candidate;

CREATE TABLE candidate (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  address VARCHAR(250) NOT NULL,
  cellphone VARCHAR(250) NOT NULL,
  city_name VARCHAR(250) NOT NULL
);

INSERT INTO candidate (name, last_name, address, cellphone, city_name) VALUES
('ana','rodriguez','Calle 12357', '55557', 'Bogota');

DROP TABLE IF EXISTS position;

CREATE TABLE position (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL
);

INSERT INTO position (name) VALUES ('Developer');

DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  fk_candidate NUMBER NOT NULL,
  fk_position NUMBER NOT NULL,
  salary NUMBER NOT NULL,
  FOREIGN KEY (fk_candidate) REFERENCES candidate(id),
  FOREIGN KEY (fk_position) REFERENCES position(id)
);


INSERT INTO employee (fk_candidate, fk_position, salary) VALUES (1,1,5000);

