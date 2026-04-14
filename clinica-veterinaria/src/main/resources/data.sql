INSERT INTO roles (nombre, descripcion)
SELECT 'ROLE_ADMIN', 'Administrador'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'ROLE_ADMIN');

INSERT INTO roles (nombre, descripcion)
SELECT 'ROLE_AUXILIAR', 'Auxiliar'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'ROLE_AUXILIAR');

INSERT INTO roles (nombre, descripcion)
SELECT 'ROLE_VETERINARIO', 'Veterinario'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'ROLE_VETERINARIO');

INSERT INTO roles (nombre, descripcion)
SELECT 'ROLE_CLIENTE', 'Cliente'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'ROLE_CLIENTE');

