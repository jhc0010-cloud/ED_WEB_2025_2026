# Manual de instalacion

Responsable manual tecnico: Hector Garcia - G8.

## Requisitos

- Java 21.
- Maven Wrapper incluido en el proyecto.
- MySQL o MariaDB.
- Navegador moderno.

## Base de datos

Crear la base:

```sql
CREATE DATABASE IF NOT EXISTS clinica_veterinaria;
```

Configurar usuario y contrasena en:

```text
src/main/resources/application.properties
```

Valores actuales:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/clinica_veterinaria?useSSL=false&serverTimezone=Europe/Madrid
spring.datasource.username=root
spring.datasource.password=1234
```

El script `src/main/resources/schema.sql` crea la estructura si no existe. `DataInitializer` inserta roles, usuarios demo y datos basicos.

## Ejecucion desde terminal

Demo rapida con H2 en memoria:

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

Ejecucion contra MySQL:

```powershell
.\mvnw.cmd spring-boot:run
```

## Ejecucion desde IntelliJ

1. Abrir la carpeta `clinica-veterinaria`.
2. Esperar a que Maven sincronice.
3. Ejecutar `ClinicaVeterinariaApplication`.
4. Entrar en `http://localhost:8080/login.html`.

## Usuarios demo

| Rol | Usuario | Contrasena |
| --- | --- | --- |
| ADMIN | `admin` | `admin123` |
| AUXILIAR | `auxiliar` | `auxiliar123` |
| VETERINARIO | `vet` | `vet123` |
| CLIENTE | `cliente` | `cliente123` |

## Flujo de prueba recomendado

1. Entrar como `admin` y revisar perfiles/usuarios.
2. Entrar como `auxiliar` y crear cliente, mascota y cita.
3. Entrar como `vet` y revisar lista de espera.
4. Registrar consulta y, si procede, receta.
5. Entrar como `cliente` y consultar estado/historial/pagos.

## Problemas habituales

- Si no conecta con MySQL, revisar que el servicio este arrancado y que el usuario/contrasena coincidan. Para una demo rapida, usar el perfil `dev`.
- Si aparece `403`, el usuario autenticado no tiene el rol requerido para esa pantalla.
- Si ya existia una base antigua, comprobar que los usuarios tengan `rol_id`; el inicializador actual corrige los usuarios demo.
