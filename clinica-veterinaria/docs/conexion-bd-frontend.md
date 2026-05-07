# Como se conecta la base de datos con el frontend

Este documento explica como viajan los datos desde **MySQL** hasta una pantalla HTML del proyecto **clinica-veterinaria**.

El frontend **no se conecta directamente** a la base de datos. La conexion siempre pasa por Spring Boot.

## 1. Flujo completo

```text
Base de datos MySQL
   |
   v
Entity
   |
   v
Repository
   |
   v
Service
   |
   v
Controller REST
   |
   v
JavaScript con fetch()
   |
   v
HTML
```

Ejemplo con citas:

```text
Tabla citas
   |
   v
Cita.java
   |
   v
CitaRepository.java
   |
   v
CitaService.java
   |
   v
CitaController.java
   |
   v
/api/citas
   |
   v
fetch("/api/citas")
   |
   v
auxiliar/citas.html
```

## 2. Paso 1: configurar la conexion con MySQL

La conexion se configura en:

```text
src/main/resources/application.properties
```

Ejemplo actual del proyecto:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/clinica_veterinaria?useSSL=false&serverTimezone=Europe/Madrid
spring.datasource.username=root
spring.datasource.password=1234
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Que significa cada linea:

| Propiedad | Funcion |
| --- | --- |
| `spring.datasource.url` | Indica donde esta la base de datos MySQL |
| `spring.datasource.username` | Usuario de MySQL |
| `spring.datasource.password` | Contrasena de MySQL |
| `spring.datasource.driver-class-name` | Driver que permite a Java hablar con MySQL |
| `spring.jpa.hibernate.ddl-auto=update` | Hibernate actualiza tablas segun las entidades |
| `spring.jpa.show-sql=true` | Muestra SQL en consola para depurar |

Importante: el navegador nunca usa estos datos. Solo los usa Spring Boot.

## 3. Paso 2: crear la entidad

Una entidad representa una tabla de la base de datos.

Ejemplo:

```java
@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;
    private String motivo;

    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.PENDIENTE;

    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }
}
```

La anotacion `@Table(name = "citas")` indica que esta clase se guarda en la tabla `citas`.

Los getters son importantes porque Spring convierte los objetos Java a JSON para enviarlos al frontend.

## 4. Paso 3: crear el repository

El repository es la capa que habla con la base de datos.

Archivo:

```text
src/main/java/com/clinica/veterinaria/repository/CitaRepository.java
```

Ejemplo:

```java
public interface CitaRepository extends JpaRepository<Cita, Long> {
}
```

Al extender `JpaRepository`, Spring ya crea automaticamente metodos como:

| Metodo | Funcion |
| --- | --- |
| `findAll()` | Trae todos los registros |
| `findById(id)` | Busca por id |
| `save(objeto)` | Guarda o actualiza |
| `deleteById(id)` | Borra por id |

Ejemplo:

```java
citaRepository.findAll();
```

Eso equivale a pedirle a la base de datos todas las citas.

## 5. Paso 4: crear el service

El service contiene la logica de negocio.

Archivo:

```text
src/main/java/com/clinica/veterinaria/service/CitaService.java
```

Ejemplo actual:

```java
@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public Object findAll() {
        return citaRepository.findAll();
    }
}
```

Mejor version recomendada:

```java
@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> findAll() {
        return citaRepository.findAll();
    }
}
```

La diferencia es que `List<Cita>` es mas claro que `Object`.

## 6. Paso 5: crear el controller REST

El controller crea una ruta HTTP para que el frontend pueda pedir datos.

Archivo:

```text
src/main/java/com/clinica/veterinaria/controller/CitaController.java
```

Ejemplo actual:

```java
@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(citaService.findAll());
    }
}
```

Que hace:

| Parte | Funcion |
| --- | --- |
| `@RestController` | Indica que devuelve datos, normalmente JSON |
| `@RequestMapping("/api/citas")` | Define la ruta base |
| `@GetMapping` | Atiende peticiones GET |
| `ResponseEntity.ok(...)` | Devuelve respuesta HTTP 200 con datos |

Cuando el navegador llama a:

```text
GET /api/citas
```

Spring responde con JSON:

```json
[
  {
    "id": 1,
    "fechaHora": "2026-04-21T10:30:00",
    "motivo": "Revision anual",
    "estado": "PENDIENTE"
  }
]
```

## 7. Paso 6: llamar a la API desde JavaScript

El frontend usa `fetch()` para pedir datos al backend.

El proyecto ya tiene esta funcion comun:

Archivo:

```text
src/main/resources/static/js/common/api.js
```

Codigo:

```javascript
async function apiGet(url) {
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error(`Error HTTP ${response.status}`);
    }
    return response.json();
}
```

Esta funcion:

1. Llama a una URL.
2. Comprueba si hubo error.
3. Convierte la respuesta JSON en datos JavaScript.

## 8. Paso 7: pintar los datos en HTML

Pantalla:

```text
src/main/resources/static/auxiliar/citas.html
```

Ejemplo recomendado de HTML:

```html
<main class="container py-4">
    <h1>Citas</h1>

    <div class="panel-card">
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Fecha</th>
                    <th>Motivo</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody id="tablaCitas"></tbody>
        </table>
    </div>
</main>

<script src="/js/common/api.js"></script>
<script src="/js/auxiliar/citas.js"></script>
```

Es importante cargar primero:

```html
<script src="/js/common/api.js"></script>
```

Y despues:

```html
<script src="/js/auxiliar/citas.js"></script>
```

Porque `citas.js` puede usar la funcion `apiGet`.

## 9. Paso 8: JavaScript de citas

Archivo:

```text
src/main/resources/static/js/auxiliar/citas.js
```

Ejemplo:

```javascript
document.addEventListener("DOMContentLoaded", () => {
    cargarCitas();
});

async function cargarCitas() {
    try {
        const citas = await apiGet("/api/citas");
        pintarCitas(citas);
    } catch (error) {
        console.error(error);
        alert("No se han podido cargar las citas");
    }
}

function pintarCitas(citas) {
    const tabla = document.getElementById("tablaCitas");
    tabla.innerHTML = "";

    if (citas.length === 0) {
        tabla.innerHTML = `
            <tr>
                <td colspan="4">No hay citas registradas</td>
            </tr>
        `;
        return;
    }

    citas.forEach((cita) => {
        tabla.innerHTML += `
            <tr>
                <td>${cita.id}</td>
                <td>${cita.fechaHora ?? ""}</td>
                <td>${cita.motivo ?? ""}</td>
                <td>${cita.estado ?? ""}</td>
            </tr>
        `;
    });
}
```

## 10. Resumen de la conexion

Cuando se abre la pagina de citas:

1. El navegador carga `auxiliar/citas.html`.
2. El HTML carga `api.js` y `citas.js`.
3. `citas.js` ejecuta `fetch("/api/citas")`.
4. Spring recibe la peticion en `CitaController`.
5. `CitaController` llama a `CitaService`.
6. `CitaService` llama a `CitaRepository`.
7. `CitaRepository` consulta MySQL.
8. MySQL devuelve los datos.
9. Spring convierte las citas a JSON.
10. JavaScript recibe el JSON.
11. JavaScript pinta los datos en la tabla HTML.

## 11. Ejemplo de crear datos desde el frontend

Para crear una cita, el flujo es parecido pero usando `POST`.

### HTML del formulario

```html
<form id="formCita">
    <label for="fechaHora">Fecha y hora</label>
    <input id="fechaHora" type="datetime-local" class="form-control">

    <label for="motivo">Motivo</label>
    <input id="motivo" type="text" class="form-control">

    <button type="submit" class="btn btn-primary mt-3">Guardar</button>
</form>
```

### JavaScript

```javascript
document.getElementById("formCita").addEventListener("submit", async (event) => {
    event.preventDefault();

    const datos = {
        fechaHora: document.getElementById("fechaHora").value,
        motivo: document.getElementById("motivo").value
    };

    const response = await fetch("/api/citas", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(datos)
    });

    if (!response.ok) {
        alert("No se ha podido crear la cita");
        return;
    }

    alert("Cita creada correctamente");
    cargarCitas();
});
```

### DTO en Java

```java
public class CitaRequest {
    private LocalDateTime fechaHora;
    private String motivo;

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
```

### Controller con POST

```java
@PostMapping
public ResponseEntity<Cita> crear(@RequestBody CitaRequest request) {
    return ResponseEntity.ok(citaService.crear(request));
}
```

### Service con save

```java
public Cita crear(CitaRequest request) {
    Cita cita = new Cita();
    cita.setFechaHora(request.getFechaHora());
    cita.setMotivo(request.getMotivo());
    cita.setEstado(EstadoCita.PENDIENTE);

    return citaRepository.save(cita);
}
```

## 12. Que debe hacer cada grupo

Cada grupo debe repetir este mismo patron con su parte:

| Grupo | Pantalla | Endpoint | Tabla / entidad |
| --- | --- | --- | --- |
| Clientes | `auxiliar/clientes.html` | `/api/clientes` | `Cliente` |
| Mascotas | `auxiliar/mascotas.html` | `/api/mascotas` | `Mascota` |
| Citas | `auxiliar/citas.html` | `/api/citas` | `Cita` |
| Consultas | `veterinario/consulta-form.html` | `/api/consultas` | `Consulta` |
| Pagos | `cliente/pagos.html` | `/api/pagos` | `Pago` |
| REIAC | `auxiliar/reiac.html` | `/api/reiac` | `VerificacionReiac` |

Para cada uno:

1. Revisar entidad.
2. Revisar repository.
3. Crear metodo en service.
4. Crear endpoint en controller.
5. Llamar al endpoint desde JavaScript.
6. Pintar datos en HTML.
7. Probar en navegador.

## 13. Errores comunes

### Error 404

Significa que la ruta no existe.

Revisar:

- La URL del `fetch`.
- El `@RequestMapping` del controller.
- El `@GetMapping`, `@PostMapping`, etc.

### Error 403

Significa que el usuario no tiene permisos.

Revisar:

- `SecurityConfig.java`.
- El rol del usuario.
- La ruta que se esta intentando abrir.

### Error 500

Significa que hay error en backend.

Revisar:

- Consola de IntelliJ.
- Si la base de datos esta encendida.
- Si la entidad tiene getters.
- Si falta algun campo obligatorio.

### La tabla sale vacia

Puede pasar por varias razones:

- No hay datos en MySQL.
- El endpoint devuelve una lista vacia.
- El JavaScript no encuentra el `id` correcto del HTML.
- No se ha cargado `api.js` antes del JS de la pantalla.

## 14. Checklist final

Antes de decir que la conexion funciona, comprobar:

- MySQL esta arrancado.
- La base de datos `clinica_veterinaria` existe.
- Spring Boot arranca sin errores.
- En consola se ven consultas SQL.
- El endpoint responde en el navegador.
- El JavaScript llama a la URL correcta.
- El HTML tiene los `id` que usa JavaScript.
- Los datos aparecen en pantalla.

## 15. Idea clave

El frontend no toca MySQL.

El frontend habla con Spring Boot mediante rutas como:

```text
/api/citas
/api/clientes
/api/mascotas
/api/pagos
```

Spring Boot es el puente entre la web y la base de datos.
