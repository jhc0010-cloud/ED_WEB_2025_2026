# README Backend

Responsable documentacion tecnica: Reda El QOURCHI - G8.

## Resumen

El backend esta construido con Spring Boot y expone una API REST protegida por Spring Security. Su objetivo es centralizar la logica de negocio de la clinica veterinaria: usuarios, clientes, mascotas, citas, consultas, recetas, pagos y verificacion REIAC simulada.

## Tecnologias

- Java 21.
- Spring Boot 3.3.5.
- Spring Web.
- Spring Security.
- Spring Data JPA.
- Bean Validation con Jakarta Validation.
- MySQL/MariaDB para entrega.
- H2 en memoria para demo rapida con perfil `dev`.
- JUnit 5 y Mockito para pruebas unitarias.

## Estructura de paquetes

```text
src/main/java/com/clinica/veterinaria
|-- config       Configuracion general, seguridad y datos iniciales
|-- controller   Controladores REST
|-- dto          Objetos de entrada/salida de la API
|-- entity       Entidades JPA
|-- exception    Excepciones y manejador global
|-- repository   Repositorios Spring Data JPA
|-- security     Adaptacion de usuario a Spring Security
`-- service      Logica de negocio
```

## Capas

### Controller

Recibe peticiones HTTP, valida DTO con `@Valid` y delega en servicios. No contiene reglas de negocio complejas.

Controladores principales:

- `AuthController`: registro, login JSON y usuario actual.
- `AdminController`: roles y usuarios.
- `ClienteController`: gestion auxiliar de clientes.
- `MascotaController`: mascotas, asignacion de veterinario y baja logica.
- `CitaController`: agenda, huecos disponibles, lista de espera y cambio de estado.
- `ConsultaController`: registro de consulta.
- `RecetaController`: recetas asociadas a consultas.
- `PagoController`: pagos desde perfil auxiliar/admin.
- `PortalClienteController`: API privada del cliente autenticado.
- `ReiacController`: verificacion simulada de microchip.

### Service

Contiene las reglas importantes:

- Una cita no puede crearse en una fecha pasada.
- No puede haber solapamiento de citas para el mismo veterinario salvo citas canceladas.
- Las citas creadas por auxiliar quedan `CONFIRMADA`.
- Las citas solicitadas por cliente quedan `PENDIENTE`.
- Solo el auxiliar/admin puede confirmar citas desde `/api/citas/{id}/estado`.
- El veterinario solo atiende citas `CONFIRMADA`.
- Al registrar una consulta se completa la cita y se crea un pago `PENDIENTE`.
- El cliente solo puede pagar pagos propios.
- Los metodos de pago permitidos son `TARJETA`, `BIZUM`, `TRANSFERENCIA` y `EFECTIVO`.
- El registro publico crea usuario `CLIENTE` y ficha `Cliente`.

### Repository

Usa Spring Data JPA para consultas a base de datos. Hay repositorios para roles, usuarios, clientes, veterinarios, mascotas, citas, consultas, recetas, pagos, admisiones y verificaciones REIAC.

### Entity

El modelo usa entidades JPA con relaciones:

- `Usuario` pertenece a `Rol`.
- `Cliente` puede tener `Usuario`.
- `Veterinario` puede tener `Usuario`.
- `Mascota` pertenece a `Cliente` y puede tener `Veterinario`.
- `Cita` une `Mascota` y `Veterinario`.
- `Consulta` depende de `Cita`.
- `Receta` depende de `Consulta`.
- `Pago` depende de `Cliente` y opcionalmente de `Consulta`.

## Seguridad

Las rutas quedan protegidas por rol:

| Ruta | Roles |
| --- | --- |
| `/api/admin/**` | `ADMIN` |
| `/api/auxiliar/**` | `ADMIN`, `AUXILIAR` |
| `/api/clientes/**`, `/api/mascotas/**`, `/api/reiac/**` | `ADMIN`, `AUXILIAR` |
| `/api/citas/**` | `ADMIN`, `AUXILIAR`, `VETERINARIO` |
| `/api/consultas/**`, `/api/recetas/**` | `ADMIN`, `VETERINARIO` |
| `/api/portal/**` | `ADMIN`, `CLIENTE` |
| `/api/veterinarios/**` | Usuarios autenticados de los cuatro perfiles |

El login de formulario se realiza en `/login`. Las APIs devuelven errores JSON cuando el usuario no tiene permisos.

## Validacion y errores

Los DTO usan anotaciones como `@NotBlank`, `@NotNull`, `@Email`, `@Positive` y `@Future`. El manejador global devuelve:

- `400` para datos invalidos.
- `404` cuando no existe un recurso.
- `409` para reglas de negocio incumplidas.
- `500` para errores inesperados.

## Datos iniciales

`DataInitializer` crea roles, usuarios demo y datos base para poder probar la aplicacion.

Usuarios:

| Rol | Usuario | Contrasena |
| --- | --- | --- |
| ADMIN | `admin` | `admin123` |
| AUXILIAR | `auxiliar` | `auxiliar123` |
| VETERINARIO | `vet` | `vet123` |
| CLIENTE | `cliente` | `cliente123` |

## Ejecucion

Demo rapida con H2:

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

Entrega con MySQL:

```powershell
.\mvnw.cmd spring-boot:run
```

## Pruebas

```powershell
.\mvnw.cmd test
```

La suite actual cubre reglas de negocio de citas, consultas, pagos y registro de clientes.
