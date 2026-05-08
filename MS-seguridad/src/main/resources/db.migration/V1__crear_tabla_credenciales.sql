
CREATE TABLE IF NOT EXISTS credenciales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar un usuario admin por defecto (password: admin123)
-- El hash es un ejemplo de BCrypt para la contraseña 'admin123'
INSERT INTO credenciales (usuario_id, email, password_hash, rol)
VALUES (1, 'admin@marketexpress.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.7uqqvS2', 'ADMIN');