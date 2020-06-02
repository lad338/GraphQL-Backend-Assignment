CREATE TABLE IF NOT EXISTS widget (
  id              SERIAL PRIMARY KEY,
  value           VARCHAR(100) NOT NULL
);

INSERT INTO widget (value) values ('A');
INSERT INTO widget (value) values ('B');
INSERT INTO widget (value) values ('C');