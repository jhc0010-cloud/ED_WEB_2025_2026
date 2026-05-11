CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    rol_id BIGINT,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(120),
    direccion VARCHAR(255),
    usuario_id BIGINT UNIQUE,
    CONSTRAINT fk_cliente_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS veterinarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    dni VARCHAR(20) UNIQUE,
    numero_colegiado VARCHAR(50) NOT NULL UNIQUE,
    especialidad VARCHAR(100) NOT NULL,
    usuario_id BIGINT UNIQUE,
    CONSTRAINT fk_veterinario_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS mascotas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raza VARCHAR(100),
    fecha_nacimiento DATE,
    sexo VARCHAR(20),
    chip VARCHAR(50) UNIQUE,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    reiac_verificado BOOLEAN NOT NULL DEFAULT FALSE,
    cliente_id BIGINT NOT NULL,
    veterinario_id BIGINT,
    CONSTRAINT fk_mascota_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    CONSTRAINT fk_mascota_veterinario FOREIGN KEY (veterinario_id) REFERENCES veterinarios(id)
);

CREATE TABLE IF NOT EXISTS citas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(255),
    estado VARCHAR(30) NOT NULL,
    mascota_id BIGINT NOT NULL,
    veterinario_id BIGINT NOT NULL,
    CONSTRAINT fk_cita_mascota FOREIGN KEY (mascota_id) REFERENCES mascotas(id),
    CONSTRAINT fk_cita_veterinario FOREIGN KEY (veterinario_id) REFERENCES veterinarios(id)
);

CREATE TABLE IF NOT EXISTS consultas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sintomas TEXT,
    diagnostico TEXT,
    tratamiento TEXT,
    estado VARCHAR(30) NOT NULL,
    cita_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_consulta_cita FOREIGN KEY (cita_id) REFERENCES citas(id)
);

CREATE TABLE IF NOT EXISTS recetas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    medicamento VARCHAR(150) NOT NULL,
    dosis VARCHAR(100),
    indicaciones TEXT,
    consulta_id BIGINT NOT NULL,
    CONSTRAINT fk_receta_consulta FOREIGN KEY (consulta_id) REFERENCES consultas(id)
);

CREATE TABLE IF NOT EXISTS pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    importe DECIMAL(10,2) NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    fecha_pago DATETIME,
    cliente_id BIGINT NOT NULL,
    consulta_id BIGINT,
    CONSTRAINT fk_pago_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    CONSTRAINT fk_pago_consulta FOREIGN KEY (consulta_id) REFERENCES consultas(id)
);

CREATE TABLE IF NOT EXISTS admisiones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_ingreso DATETIME,
    observaciones TEXT
);

CREATE TABLE IF NOT EXISTS verificaciones_reiac (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_microchip VARCHAR(80),
    resultado VARCHAR(80),
    fecha_consulta DATETIME
);
