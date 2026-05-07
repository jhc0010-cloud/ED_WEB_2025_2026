# Esquema de base de datos y roles

Este documento resume el esquema de base de datos de la aplicacion de clinica veterinaria y los roles de acceso definidos en el proyecto.

## Configuracion de base de datos

La aplicacion esta configurada para usar MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/clinica_veterinaria?useSSL=false&serverTimezone=Europe/Madrid
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
```

Con `spring.jpa.hibernate.ddl-auto=update`, Hibernate puede crear o actualizar tablas a partir de las entidades JPA. Ademas, `schema.sql` y `data.sql` se ejecutan al iniciar la aplicacion porque `spring.sql.init.mode=always`.

## Tablas definidas en schema.sql

### roles

Tabla que almacena los perfiles de acceso disponibles en la aplicacion.

| Campo | Tipo | Restricciones | Descripcion |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | Identificador unico del rol |
| nombre | VARCHAR(50) | NOT NULL, UNIQUE | Nombre tecnico del rol |
| descripcion | VARCHAR(100) | NOT NULL | Nombre legible del rol |

```sql
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(100) NOT NULL
);
```

### usuarios

Tabla principal de usuarios autenticables.

| Campo | Tipo | Restricciones | Descripcion |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | Identificador unico del usuario |
| nombre | VARCHAR(100) | NOT NULL | Nombre del usuario |
| apellidos | VARCHAR(150) | NOT NULL | Apellidos del usuario |
| email | VARCHAR(120) | NOT NULL, UNIQUE | Correo electronico |
| username | VARCHAR(50) | NOT NULL, UNIQUE | Nombre de usuario para login |
| password | VARCHAR(255) | NOT NULL | Contrasena cifrada con BCrypt |
| activo | BOOLEAN | NOT NULL, DEFAULT TRUE | Indica si la cuenta esta activa |

```sql
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);
```

## Tablas generadas por entidades JPA

Ademas de las tablas anteriores, el proyecto contiene entidades JPA que Hibernate puede materializar en la base de datos.

| Entidad | Tabla | Campos principales |
| --- | --- | --- |
| Admision | admisiones | id, fechaIngreso, observaciones |
| Cita | citas | id, fechaHora, motivo, estado |
| Cliente | clientes | id, dni, telefono, direccion |
| Consulta | consultas | id, diagnostico, tratamiento, estado |
| Mascota | mascotas | id, nombre, especie, raza, fechaNacimiento |
| Pago | pagos | id, importe, metodoPago, estado |
| Receta | recetas | id, medicamento, dosis, indicaciones |
| VerificacionReiac | verificaciones_reiac | id, codigoMicrochip, resultado, fechaConsulta |
| Veterinario | veterinarios | id, numeroColegiado, especialidad |

## Estados enumerados

Algunas tablas guardan estados como texto mediante `EnumType.STRING`.

### EstadoCita

| Valor | Uso |
| --- | --- |
| PENDIENTE | Cita creada y pendiente de gestion |
| CONFIRMADA | Cita confirmada |
| CANCELADA | Cita anulada |
| COMPLETADA | Cita finalizada |

### EstadoConsulta

| Valor | Uso |
| --- | --- |
| ABIERTA | Consulta iniciada |
| EN_PROCESO | Consulta en seguimiento |
| CERRADA | Consulta finalizada |

### EstadoPago

| Valor | Uso |
| --- | --- |
| PENDIENTE | Pago pendiente |
| PAGADO | Pago completado |
| RECHAZADO | Pago rechazado |

## Roles iniciales

Los roles se insertan desde `data.sql` y tambien se crean desde `DataInitializer` si la tabla esta vacia.

| Rol | Descripcion | Uso previsto |
| --- | --- | --- |
| ROLE_ADMIN | Administrador | Acceso completo a las zonas administrativas |
| ROLE_AUXILIAR | Auxiliar | Gestion operativa de clientes, mascotas, citas, admision y REIAC |
| ROLE_VETERINARIO | Veterinario | Gestion de consultas, recetas y lista de espera |
| ROLE_CLIENTE | Cliente | Consulta de citas, historial, estado y pagos propios |

```sql
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
```

## Usuario inicial

El inicializador crea un usuario administrador si no existe un usuario con `username = 'admin'`.

| Campo | Valor |
| --- | --- |
| nombre | Administrador |
| apellidos | Sistema |
| username | admin |
| email | admin@clinica.local |
| password inicial | admin123 |
| activo | true |

La contrasena se guarda cifrada mediante `BCryptPasswordEncoder`.

## Permisos por rutas

La seguridad se define en `SecurityConfig`.

| Ruta | Acceso |
| --- | --- |
| `/`, `/index.html`, `/login.html`, `/register.html` | Publico |
| `/css/**`, `/js/**`, `/error/**` | Publico |
| `/admin/**` | ROLE_ADMIN |
| `/auxiliar/**` | ROLE_ADMIN, ROLE_AUXILIAR |
| `/veterinario/**` | ROLE_ADMIN, ROLE_VETERINARIO |
| `/cliente/**` | ROLE_ADMIN, ROLE_CLIENTE |
| Cualquier otra ruta | Usuario autenticado |

## Observaciones importantes

- Actualmente la tabla `roles` existe y se inicializa, pero la entidad `Usuario` no tiene una relacion persistida con `Rol`.
- `UserDetailsImpl` devuelve siempre `ROLE_ADMIN` como autoridad del usuario autenticado.
- Si se quiere un sistema real de permisos por usuario, falta anadir una relacion entre `usuarios` y `roles`, por ejemplo con una tabla intermedia `usuarios_roles`.

## Propuesta de relacion usuarios-roles

Para asignar roles reales a cada usuario se podria anadir la siguiente tabla:

```sql
CREATE TABLE IF NOT EXISTS usuarios_roles (
    usuario_id BIGINT NOT NULL,
    rol_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, rol_id),
    CONSTRAINT fk_usuarios_roles_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_usuarios_roles_rol
        FOREIGN KEY (rol_id) REFERENCES roles(id)
);
```

Con esta relacion, un usuario podria tener uno o varios roles y Spring Security podria cargar sus autoridades desde base de datos.
