DROP TABLE IF EXISTS credenciales;

CREATE TABLE credenciales (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id  BIGINT       NOT NULL UNIQUE,        -- referencia al ms-usuarios
    email       VARCHAR(255) NOT NULL UNIQUE,        -- coincide con Credencial.email
    password_hash VARCHAR(255) NOT NULL,             -- coincide con Credencial.passwordHash
    rol         VARCHAR(50)  NOT NULL DEFAULT 'CLIENTE',
    activo      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- Usuario admin de prueba
-- password_hash corresponde a 'admin123' encriptado con BCrypt
INSERT INTO credenciales (usuario_id, email, password_hash, rol)
VALUES (1, 'admin@marketexpress.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.7uqqvS2', 'ADMIN');