-- Creación de la tabla 'Usuario'
CREATE TABLE Usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    created TIMESTAMP,
    modified TIMESTAMP,
    last_login TIMESTAMP,
    token VARCHAR(255),
    isactive BOOLEAN
);

-- Creación de la tabla 'Telefono'
CREATE TABLE Telefono (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(255),
    citycode VARCHAR(255),
    countrycode VARCHAR(255),
    usuario_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);

-- Insertar un usuario en la tabla 'Usuario'
INSERT INTO Usuario (name, email, password, created, modified, last_login, isactive)
VALUES ('cristopher', 'ccanoles@bci.cl', '$2a$10$paDXb8SCbhAAogjr6ra2SeDwDMucvPPLgHYXKSLoV/FBR26SwRpN2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE);

-- Insertar un número de teléfono para el usuario cris
INSERT INTO Telefono (number, citycode, countrycode, usuario_id)
VALUES ('932677311', '1', '56', 1); 
