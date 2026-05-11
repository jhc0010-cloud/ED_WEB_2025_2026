# Diccionario de datos

Diccionario basado en las entidades JPA y el esquema usado por la aplicacion.

## `roles`

| Campo | Tipo | Obligatorio | Descripcion |
| --- | --- | --- | --- |
| `id` | BIGINT | Si | Identificador del rol. |
| `nombre` | VARCHAR(50) | Si | Codigo tecnico: `ROLE_ADMIN`, `ROLE_AUXILIAR`, `ROLE_VETERINARIO`, `ROLE_CLIENTE`. |
| `descripcion` | VARCHAR(100) | No | Nombre visible del rol. |

## `usuarios`

| Campo | Tipo | Obligatorio | Descripcion |
| --- | --- | --- | --- |
| `id` | BIGINT | Si | Identificador del usuario. |
| `nombre` | VARCHAR(100) | Si | Nombre. |
| `apellidos` | VARCHAR(150) | Si | Apellidos. |
| `email` | VARCHAR(120) | Si | Email unico. |
| `username` | VARCHAR(50) | Si | Nombre de usuario unico para login. |
| `password` | VARCHAR(255) | Si | Contrasena cifrada con BCrypt. |
| `activo` | BOOLEAN | Si | Indica si puede iniciar sesion. |
| `rol_id` | BIGINT | Si | Rol asignado. |

## `clientes`

| Campo | Tipo | Obligatorio | Descripcion |
| --- | --- | --- | --- |
| `id` | BIGINT | Si | Identificador del cliente. |
| `nombre` | VARCHAR(100) | Si | Nombre del propietario. |
| `apellidos` | VARCHAR(150) | Si | Apellidos del propietario. |
| `dni` | VARCHAR(20) | Si | Documento identificativo. |
| `telefono` | VARCHAR(30) | Si | Telefono de contacto. |
| `email` | VARCHAR(120) | Si | Email de contacto. |
| `direccion` | VARCHAR(255) | No | Direccion postal. |
| `usuario_id` | BIGINT | No | Cuenta de usuario asociada al portal cliente. |

## `veterinarios`

| Campo | Tipo | Obligatorio | Descripcion |
| --- | --- | --- | --- |
| `id` | BIGINT | Si | Identificador del veterinario. |
| `nombre` | VARCHAR(100) | Si | Nombre. |
| `apellidos` | VARCHAR(150) | Si | Apellidos. |
| `dni` | VARCHAR(20) | Si | Documento identificativo. |
| `numero_colegiado` | VARCHAR(50) | Si | Numero profesional. |
| `especialidad` | VARCHAR(100) | No | Especialidad clinica. |
| `usuario_id` | BIGINT | No | Cuenta de usuario asociada al area veterinaria. |

## `mascotas`

| Campo | Tipo | Obligatorio | Descripcion |
| --- | --- | --- | --- |
| `id` | BIGINT | Si | Identificador de la mascota. |
| `nombre` | VARCHAR(100) | Si | Nombre del animal. |
| `especie` | VARCHAR(80) | Si | Especie. |
| `raza` | VARCHAR(80) | No | Raza. |
| `fecha_nacimiento` | DATE | No | Fecha de nacimiento aproximada. |
| `sexo` | VARCHAR(20) | No | Sexo. |
| `chip` | VARCHAR(60) | No | Codigo de microchip. |
| `activa` | BOOLEAN | Si | Permite baja logica. |
| `reiac_verificado` | BOOLEAN | Si | Resultado de verificacion REIAC almacenado. |
| `cliente_id` | BIGINT | Si | Propietario. |
| `veterinario_id` | BIGINT | No | Veterinario asignado. |

## `citas`

| Campo | Tipo | Obligatorio | Descripcion |
| --- | --- | --- | --- |
| `id` | BIGINT | Si | Identificador de la cita. |
| `fecha_hora` | DATETIME | Si | Fecha y hora de la cita. |
| `motivo` | VARCHAR(255) | Si | Motivo indicado por auxiliar o cliente. |
| `estado` | VARCHAR(30) | Si | `PENDIENTE`, `CONFIRMADA`, `CANCELADA`, `COMPLETADA`. |
| `mascota_id` | BIGINT | Si | Mascota atendida. |
| `veterinario_id` | BIGINT | Si | Veterinario asignado. |

## `consultas`

| Campo | Tipo | Obligatorio | Descripcion |
| --- | --- | --- | --- |
| `id` | BIGINT | Si | Identificador de la consulta. |
| `sintomas` | TEXT | Si | Sintomas observados. |
| `diagnostico` | TEXT | Si | Diagnostico del veterinario. |
| `tratamiento` | TEXT | No | Tratamiento indicado. |
| `estado` | VARCHAR(30) | Si | `ABIERTA`, `EN_PROCESO`, `CERRADA`. |
| `cita_id` | BIGINT | Si | Cita que genera la consulta. |

## `recetas`

| Campo | Tipo | Obligatorio | Descripcion |
| --- | --- | --- | --- |
| `id` | BIGINT | Si | Identificador de la receta. |
| `medicamento` | VARCHAR(150) | Si | Medicamento recetado. |
| `dosis` | VARCHAR(150) | Si | Dosis indicada. |
| `indicaciones` | TEXT | No | Instrucciones de administracion. |
| `consulta_id` | BIGINT | Si | Consulta asociada. |

## `pagos`

| Campo | Tipo | Obligatorio | Descripcion |
| --- | --- | --- | --- |
| `id` | BIGINT | Si | Identificador del pago. |
| `importe` | DECIMAL(10,2) | Si | Importe del servicio. |
| `metodo_pago` | VARCHAR(40) | No | `TARJETA`, `BIZUM`, `TRANSFERENCIA` o `EFECTIVO`. |
| `estado` | VARCHAR(30) | Si | `PENDIENTE`, `PAGADO`, `RECHAZADO`. |
| `fecha_pago` | DATETIME | No | Fecha en la que se marca como pagado. |
| `cliente_id` | BIGINT | Si | Cliente responsable del pago. |
| `consulta_id` | BIGINT | No | Consulta facturada. |

## `verificaciones_reiac`

| Campo | Tipo | Obligatorio | Descripcion |
| --- | --- | --- | --- |
| `id` | BIGINT | Si | Identificador de la verificacion. |
| `codigo_microchip` | VARCHAR(60) | Si | Chip consultado. |
| `resultado` | VARCHAR(50) | Si | Resultado simulado: `VALIDO` o `NO_ENCONTRADO`. |
| `fecha_consulta` | DATETIME | Si | Momento de la verificacion. |

## `admisiones`

| Campo | Tipo | Obligatorio | Descripcion |
| --- | --- | --- | --- |
| `id` | BIGINT | Si | Identificador de admision. |
| `fecha_ingreso` | DATETIME | Si | Momento de entrada. |
| `observaciones` | TEXT | No | Comentarios de recepcion. |
