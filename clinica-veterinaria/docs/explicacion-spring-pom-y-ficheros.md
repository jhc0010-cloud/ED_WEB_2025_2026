# Explicacion de Spring Boot, pom.xml y ficheros del proyecto

Este documento explica como funciona Spring Boot en el proyecto **clinica-veterinaria**, que dependencias se usan en el `pom.xml` y para que sirve cada carpeta o fichero principal.

## 1. Que es Spring Boot en este proyecto

Spring Boot es el framework que permite crear y ejecutar la aplicacion Java de la clinica veterinaria sin tener que configurar manualmente un servidor web, la conexion con la base de datos o muchas piezas internas de Spring.

En este proyecto Spring Boot se encarga de:

- Arrancar la aplicacion desde `ClinicaVeterinariaApplication.java`.
- Crear un servidor web en el puerto `8080`.
- Servir paginas HTML, CSS y JavaScript desde `src/main/resources/static`.
- Exponer endpoints REST bajo rutas como `/api/clientes`, `/api/citas` o `/api/auth`.
- Conectar con una base de datos MySQL.
- Gestionar entidades JPA como `Cliente`, `Mascota`, `Cita`, `Consulta`, `Pago`, etc.
- Aplicar seguridad con usuarios, roles y restricciones de acceso.
- Inyectar automaticamente clases entre capas, por ejemplo un controlador recibe un servicio y un servicio recibe un repositorio.

## 2. Flujo general de una peticion

El proyecto sigue una arquitectura por capas:

```text
Navegador
   |
   v
HTML / JavaScript en static
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
Base de datos MySQL
```

Ejemplo con citas:

1. El navegador carga una pagina como `auxiliar/citas.html`.
2. Su JavaScript puede llamar a `/api/citas`.
3. `CitaController` recibe la peticion HTTP.
4. `CitaController` llama a `CitaService`.
5. `CitaService` llama a `CitaRepository`.
6. `CitaRepository` usa Spring Data JPA para consultar la tabla relacionada con la entidad `Cita`.
7. La respuesta vuelve al navegador en formato JSON.

## 3. Anotaciones importantes de Spring

| Anotacion | Donde aparece | Para que sirve |
| --- | --- | --- |
| `@SpringBootApplication` | `ClinicaVeterinariaApplication.java` | Marca la clase principal. Activa autoconfiguracion, escaneo de componentes y arranque de Spring Boot. |
| `@RestController` | Controladores API | Indica que la clase responde a peticiones HTTP devolviendo datos, normalmente JSON o texto. |
| `@Controller` | `ViewController.java` | Indica que la clase devuelve vistas o redirecciones. |
| `@RequestMapping` | Controladores | Define la ruta base del controlador, por ejemplo `/api/clientes`. |
| `@GetMapping` | Controladores | Atiende peticiones HTTP GET. Se usa para listar datos o mostrar informacion. |
| `@PostMapping` | `AuthController.java` | Atiende peticiones HTTP POST. Se usa para login y registro. |
| `@RequestBody` | `AuthController.java` | Lee el cuerpo JSON de la peticion y lo convierte en un DTO Java. |
| `@Service` | Servicios | Marca clases de logica de negocio para que Spring las pueda inyectar. |
| `@Entity` | Entidades | Indica que una clase representa una tabla de la base de datos. |
| `@Configuration` | `SecurityConfig.java` | Marca una clase que define configuracion de Spring. |
| `@Bean` | `SecurityConfig.java` | Registra objetos gestionados por Spring, como la cadena de seguridad o el codificador de contrasenas. |
| `@RestControllerAdvice` | `GlobalExceptionHandler.java` | Centraliza el tratamiento de errores de la API. |

## 4. Explicacion del pom.xml

El fichero `pom.xml` es el archivo de configuracion de Maven. Maven sirve para:

- Definir los datos del proyecto.
- Descargar dependencias.
- Compilar el codigo Java.
- Ejecutar tests.
- Empaquetar la aplicacion.
- Ejecutar plugins como el de Spring Boot.

### Datos principales del proyecto

| Elemento | Valor | Explicacion |
| --- | --- | --- |
| `groupId` | `com.clinica` | Identifica el grupo u organizacion del proyecto. |
| `artifactId` | `clinica-veterinaria` | Nombre tecnico del artefacto generado por Maven. |
| `version` | `0.0.1-SNAPSHOT` | Version actual en desarrollo. |
| `name` | `clinica-veterinaria` | Nombre visible del proyecto. |
| `java.version` | `21` | Version de Java usada para compilar y ejecutar. |

### Parent de Spring Boot

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.3.5</version>
</parent>
```

Este bloque hereda configuracion recomendada de Spring Boot. Gracias a el no hace falta indicar manualmente la version de cada dependencia de Spring, porque Spring Boot ya trae un conjunto compatible de versiones.

### Dependencias usadas

| Dependencia | Para que sirve en el proyecto |
| --- | --- |
| `spring-boot-starter-web` | Permite crear una aplicacion web y API REST. Incluye Spring MVC, JSON con Jackson y servidor embebido Tomcat. Se usa en los controladores como `ClienteController`, `CitaController` o `AuthController`. |
| `spring-boot-starter-security` | Anade autenticacion, autorizacion, login, logout, roles y proteccion de rutas. Se configura en `SecurityConfig.java` y se apoya en `CustomUserDetailsService.java`. |
| `spring-boot-starter-data-jpa` | Permite trabajar con base de datos usando entidades, repositorios y JPA/Hibernate. Se usa en clases como `ClienteRepository`, `MascotaRepository`, `CitaRepository`, etc. |
| `spring-boot-starter-validation` | Permite validar datos de entrada con anotaciones de validacion. Es util para DTOs como `LoginRequest`, `RegistroRequest`, `CitaRequest`, etc., aunque ahora el proyecto tiene validaciones basicas o pendientes de ampliar. |
| `spring-boot-starter-thymeleaf` | Permite usar plantillas Thymeleaf en `src/main/resources/templates`. En este proyecto la mayor parte del frontend esta en `static`, pero la dependencia queda disponible para vistas dinamicas. |
| `mysql-connector-j` | Driver JDBC para que Java pueda conectarse a MySQL. Se usa en tiempo de ejecucion con la URL configurada en `application.properties`. |
| `spring-boot-starter-test` | Incluye herramientas de test como JUnit, Mockito y utilidades de Spring Boot Test. Tiene scope `test`, por eso solo se usa al ejecutar pruebas. |
| `spring-security-test` | Anade utilidades para probar seguridad, usuarios simulados y roles en tests. Tambien tiene scope `test`. |

### Plugin de Spring Boot

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

Este plugin permite ejecutar y empaquetar la aplicacion Spring Boot con Maven. Por ejemplo, permite usar comandos como `mvn spring-boot:run` o generar un `.jar` ejecutable.

## 5. Configuracion de la aplicacion

### `src/main/resources/application.properties`

Configura el comportamiento principal de Spring Boot:

- `spring.application.name=clinica-veterinaria`: nombre de la aplicacion.
- `server.port=8080`: puerto donde se levanta el servidor.
- `spring.datasource.url`: URL de conexion a MySQL.
- `spring.datasource.username` y `spring.datasource.password`: credenciales de la base de datos.
- `spring.datasource.driver-class-name`: driver JDBC de MySQL.
- `spring.jpa.hibernate.ddl-auto=update`: Hibernate actualiza el esquema de la base de datos segun las entidades.
- `spring.jpa.show-sql=true`: muestra las consultas SQL por consola.
- `spring.jpa.open-in-view=false`: evita mantener la sesion de persistencia abierta durante toda la vista.
- `spring.sql.init.mode=always`: ejecuta scripts SQL de inicializacion si existen.
- `spring.thymeleaf.cache=false`: desactiva cache de plantillas durante desarrollo.

## 6. Estructura general de carpetas

| Ruta | Funcion |
| --- | --- |
| `.mvn/wrapper` | Contiene el wrapper de Maven para poder ejecutar Maven aunque no este instalado globalmente. |
| `database` | Scripts SQL de base de datos y consultas utiles. |
| `docs` | Documentacion del proyecto. |
| `src/main/java` | Codigo Java del backend. |
| `src/main/resources` | Configuracion, SQL de arranque, frontend estatico y plantillas. |
| `src/test/java` | Estructura preparada para pruebas automatizadas. |
| `target` | Carpeta generada al compilar. No se edita a mano. |

## 7. Ficheros de raiz

| Fichero | Funcion |
| --- | --- |
| `.gitignore` | Indica a Git que archivos o carpetas no debe versionar. |
| `mvnw` | Script para ejecutar Maven Wrapper en Linux/macOS. |
| `mvnw.cmd` | Script para ejecutar Maven Wrapper en Windows. |
| `pom.xml` | Configura Maven, Spring Boot, dependencias y plugins. |
| `README.md` | Explicacion inicial del proyecto, tecnologias y pasos de ejecucion. |
| `clinica-veterinaria.iml` | Fichero propio de IntelliJ IDEA con configuracion del modulo. |

## 8. Backend Java

### Clase principal

| Fichero | Funcion |
| --- | --- |
| `ClinicaVeterinariaApplication.java` | Punto de entrada de la aplicacion. Ejecuta `SpringApplication.run(...)` y arranca Spring Boot. |

### Paquete `config`

| Fichero | Funcion |
| --- | --- |
| `SecurityConfig.java` | Define la seguridad web: rutas publicas, rutas protegidas por rol, login, logout, HTTP Basic, gestion de acceso denegado y codificador BCrypt para contrasenas. |
| `CustomAccessDeniedHandler.java` | Personaliza la respuesta cuando un usuario autenticado intenta acceder a una zona para la que no tiene permisos. |
| `DataInitializer.java` | Se ejecuta al arrancar la aplicacion. Crea roles iniciales y un usuario administrador si no existen. |

### Paquete `controller`

Los controladores son la entrada HTTP al backend.

| Fichero | Ruta principal | Funcion |
| --- | --- | --- |
| `AdminController.java` | `/api/admin` | Devuelve informacion basica del panel de administracion. |
| `AuthController.java` | `/api/auth` | Gestiona login y registro mediante peticiones POST. Usa `LoginRequest`, `RegistroRequest` y `AuthService`. |
| `AuxiliarController.java` | `/api/auxiliar` | Devuelve informacion basica del panel de auxiliar. |
| `CitaController.java` | `/api/citas` | Lista citas usando `CitaService`. |
| `ClienteController.java` | `/api/clientes` | Lista clientes usando `ClienteService`. |
| `ConsultaController.java` | `/api/consultas` | Lista consultas usando `ConsultaService`. |
| `MascotaController.java` | `/api/mascotas` | Lista mascotas usando `MascotaService`. |
| `PagoController.java` | `/api/pagos` | Lista pagos usando `PagoService`. |
| `ReiacController.java` | `/api/reiac` | Lista verificaciones REIAC usando `ReiacService`. |
| `VeterinarioController.java` | `/api/veterinario` | Devuelve informacion basica del panel de veterinario. |
| `ViewController.java` | `/` | Redirige la raiz de la aplicacion a `index.html`. |

### Paquete `dto`

Los DTOs son objetos usados para recibir datos desde el frontend sin exponer directamente las entidades.

| Fichero | Funcion |
| --- | --- |
| `CitaRequest.java` | Datos necesarios para crear o modificar una cita. |
| `ClienteRequest.java` | Datos recibidos para crear o modificar un cliente. |
| `ConsultaRequest.java` | Datos recibidos para registrar o modificar una consulta veterinaria. |
| `LoginRequest.java` | Datos de inicio de sesion, como usuario y contrasena. |
| `MascotaRequest.java` | Datos recibidos para crear o modificar una mascota. |
| `PagoRequest.java` | Datos recibidos para crear o modificar un pago. |
| `RegistroRequest.java` | Datos de registro de usuario. |
| `UsuarioRequest.java` | Datos para operaciones relacionadas con usuarios. |

### Paquete `entity`

Las entidades representan tablas de la base de datos. Spring Data JPA y Hibernate las usan para guardar, leer y relacionar datos.

| Fichero | Funcion |
| --- | --- |
| `Admision.java` | Representa una admision o entrada de una mascota/cliente en el flujo de atencion. |
| `Cita.java` | Representa una cita veterinaria. |
| `Cliente.java` | Representa a un cliente de la clinica. |
| `Consulta.java` | Representa una consulta veterinaria realizada a una mascota. |
| `EstadoCita.java` | Enum con los posibles estados de una cita. |
| `EstadoConsulta.java` | Enum con los posibles estados de una consulta. |
| `EstadoPago.java` | Enum con los posibles estados de un pago. |
| `Mascota.java` | Representa una mascota asociada a un cliente. |
| `Pago.java` | Representa un pago realizado o pendiente. |
| `Receta.java` | Representa una receta generada en una consulta. |
| `Rol.java` | Representa un rol de seguridad, por ejemplo `ROLE_ADMIN` o `ROLE_CLIENTE`. |
| `Usuario.java` | Representa un usuario que puede autenticarse en la aplicacion. |
| `VerificacionReiac.java` | Representa una verificacion relacionada con REIAC. |
| `Veterinario.java` | Representa un veterinario de la clinica. |

### Paquete `exception`

| Fichero | Funcion |
| --- | --- |
| `BusinessException.java` | Excepcion para errores de negocio, por ejemplo una operacion no valida. |
| `ResourceNotFoundException.java` | Excepcion para indicar que no se ha encontrado un recurso. |
| `GlobalExceptionHandler.java` | Captura excepciones y devuelve respuestas HTTP controladas, como 404, 400 o 500. |

### Paquete `repository`

Los repositorios son interfaces que extienden `JpaRepository`. Spring Data JPA crea automaticamente la implementacion en tiempo de ejecucion.

| Fichero | Funcion |
| --- | --- |
| `AdmisionRepository.java` | Acceso a datos de `Admision`. |
| `CitaRepository.java` | Acceso a datos de `Cita`. |
| `ClienteRepository.java` | Acceso a datos de `Cliente`. |
| `ConsultaRepository.java` | Acceso a datos de `Consulta`. |
| `MascotaRepository.java` | Acceso a datos de `Mascota`. |
| `PagoRepository.java` | Acceso a datos de `Pago`. |
| `RecetaRepository.java` | Acceso a datos de `Receta`. |
| `RolRepository.java` | Acceso a datos de `Rol`. Incluye busquedas utiles para seguridad o inicializacion. |
| `UsuarioRepository.java` | Acceso a datos de `Usuario`. Incluye busqueda por nombre de usuario para login. |
| `VerificacionReiacRepository.java` | Acceso a datos de `VerificacionReiac`. |
| `VeterinarioRepository.java` | Acceso a datos de `Veterinario`. |

### Paquete `security`

| Fichero | Funcion |
| --- | --- |
| `CustomUserDetailsService.java` | Integra los usuarios de la base de datos con Spring Security. Busca un usuario por username. |
| `UserDetailsImpl.java` | Adapta la entidad `Usuario` al formato que Spring Security necesita para autenticar y autorizar. |

### Paquete `service`

Los servicios contienen la logica de negocio y hacen de puente entre controladores y repositorios.

| Fichero | Funcion |
| --- | --- |
| `AdmisionService.java` | Gestiona operaciones de admisiones usando `AdmisionRepository`. |
| `AuthService.java` | Gestiona las operaciones de login y registro. Actualmente devuelve mensajes de plantilla. |
| `CitaService.java` | Gestiona operaciones de citas usando `CitaRepository`. |
| `ClienteService.java` | Gestiona operaciones de clientes usando `ClienteRepository`. |
| `ConsultaService.java` | Gestiona operaciones de consultas usando `ConsultaRepository`. |
| `MascotaService.java` | Gestiona operaciones de mascotas usando `MascotaRepository`. |
| `PagoService.java` | Gestiona operaciones de pagos usando `PagoRepository`. |
| `RecetaService.java` | Gestiona operaciones de recetas usando `RecetaRepository`. |
| `ReiacService.java` | Gestiona operaciones de verificaciones REIAC usando `VerificacionReiacRepository`. |
| `UsuarioService.java` | Gestiona operaciones de usuarios usando `UsuarioRepository`. |
| `VeterinarioService.java` | Gestiona operaciones de veterinarios usando `VeterinarioRepository`. |

## 9. Recursos y frontend

### Ficheros de recursos

| Fichero | Funcion |
| --- | --- |
| `src/main/resources/application.properties` | Configuracion de Spring Boot, servidor, base de datos, JPA y Thymeleaf. |
| `src/main/resources/schema.sql` | Script SQL de estructura de base de datos. |
| `src/main/resources/data.sql` | Script SQL de datos iniciales. |
| `src/main/resources/templates/.gitkeep` | Mantiene la carpeta `templates` en Git aunque este vacia. |

### Paginas HTML principales

| Fichero o carpeta | Funcion |
| --- | --- |
| `static/index.html` | Pagina principal tras entrar a la aplicacion. |
| `static/login.html` | Pantalla de inicio de sesion. |
| `static/register.html` | Pantalla de registro. |
| `static/admin/*.html` | Paginas del perfil administrador: dashboard, perfiles y usuarios. |
| `static/auxiliar/*.html` | Paginas del perfil auxiliar: admision, citas, clientes, mascotas, REIAC y dashboard. |
| `static/cliente/*.html` | Paginas del perfil cliente: citas, estado, historial, pagos y dashboard. |
| `static/veterinario/*.html` | Paginas del perfil veterinario: dashboard, lista de espera, consulta y recetas. |
| `static/error/*.html` | Paginas personalizadas para errores 401, 403, 404, 500 y error general. |

### CSS

| Fichero | Funcion |
| --- | --- |
| `static/css/styles.css` | Estilos generales de la aplicacion. |
| `static/css/alerts.css` | Estilos de avisos, mensajes o alertas. |
| `static/css/error.css` | Estilos especificos para paginas de error. |

### JavaScript comun

| Fichero | Funcion |
| --- | --- |
| `static/js/common/api.js` | Funciones comunes para comunicarse con la API. |
| `static/js/common/auth.js` | Logica comun de autenticacion en frontend. |
| `static/js/common/alerts.js` | Gestion de mensajes y alertas visuales. |
| `static/js/common/logger.js` | Utilidades de registro o trazas en navegador. |
| `static/js/common/error-monitor.js` | Gestion o seguimiento de errores en frontend. |

### JavaScript por perfil

| Carpeta | Funcion |
| --- | --- |
| `static/js/admin` | Scripts de dashboard, perfiles y usuarios del administrador. |
| `static/js/auxiliar` | Scripts para admision, citas, clientes, mascotas, REIAC y dashboard del auxiliar. |
| `static/js/cliente` | Scripts para citas, dashboard, estado, historial y pagos del cliente. |
| `static/js/veterinario` | Scripts para consulta, dashboard, lista de espera y recetas del veterinario. |

## 10. Base de datos y documentacion

| Fichero o carpeta | Funcion |
| --- | --- |
| `database/base_datos_clinica_veterinaria_comentada.sql` | Script SQL comentado de la base de datos. |
| `database/consultas_utiles.sql` | Consultas SQL utiles para comprobar o trabajar con datos. |
| `docs/api/documantacion-api-rest.md` | Documentacion de la API REST. |
| `docs/bd/base_datos_clinica_veterinaria_comentada.md` | Explicacion documentada de la base de datos. |
| `docs/bd/diccionario-datos.md` | Diccionario de datos del proyecto. |
| `docs/diseno/diagrama-clases.md` | Diagrama de clases. |
| `docs/diseno/diagrama-entidad-relacion.md` | Diagrama entidad-relacion. |
| `docs/diseno/modelo-bd.md` | Explicacion del modelo de base de datos. |
| `docs/instalacion/manual-git-intellij-equipos.md` | Guia de Git e IntelliJ para trabajar en equipo. |
| `docs/instalacion/manual-instalacion.md` | Guia de instalacion del proyecto. |
| `docs/Memoria/memoria-proyecto.md` | Memoria general del proyecto. |
| `docs/pruebas/informe-pruebas.md` | Informe de pruebas. |
| `docs/pruebas/plan-pruebas.md` | Plan de pruebas. |
| `docs/pruebas/proyecto_clinica_veterinaria_errores.md` | Registro o explicacion de errores detectados. |

## 11. Tests

| Ruta | Funcion |
| --- | --- |
| `src/test/java/com/clinica/veterinaria/controller/.gitkeep` | Reserva la carpeta para tests de controladores. |
| `src/test/java/com/clinica/veterinaria/service/.gitkeep` | Reserva la carpeta para tests de servicios. |
| `src/test/java/com/clinica/veterinaria/repository/.gitkeep` | Reserva la carpeta para tests de repositorios. |

Actualmente esas carpetas estan preparadas, pero no contienen clases de prueba reales.

## 12. Carpeta target

La carpeta `target` se genera automaticamente al compilar el proyecto con Maven o desde IntelliJ. Contiene clases `.class`, recursos copiados y metadatos de compilacion.

No se debe modificar manualmente porque Maven puede borrarla y volver a crearla en cualquier compilacion.

## 13. Resumen rapido

- Spring Boot arranca la aplicacion y configura automaticamente servidor, controladores, servicios, repositorios, seguridad y base de datos.
- Maven usa `pom.xml` para descargar dependencias y construir el proyecto.
- Los controladores reciben peticiones HTTP.
- Los servicios contienen la logica de negocio.
- Los repositorios acceden a MySQL mediante JPA.
- Las entidades representan tablas.
- Los DTOs reciben datos del frontend.
- `SecurityConfig` controla permisos por rol.
- El frontend esta en `src/main/resources/static` y se sirve directamente desde Spring Boot.
