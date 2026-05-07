# Guia de trabajo por grupos: Spring Boot

Este documento explica paso a paso que debe hacer cada grupo para construir las funciones del proyecto **clinica-veterinaria** usando Spring Boot.

La idea es que todos los grupos trabajen con el mismo patron:

```text
Entidad / DTO
   |
   v
Repository
   |
   v
Service
   |
   v
Controller
   |
   v
Frontend HTML + JavaScript
   |
   v
Pruebas y documentacion
```

## 1. Normas comunes para todos los grupos

Antes de empezar una funcionalidad, cada grupo debe revisar:

- Que pantalla del frontend va a usar.
- Que datos necesita guardar o mostrar.
- Que entidad representa esos datos.
- Que endpoints necesita el JavaScript.
- Que permisos tiene que tener cada rol.
- Que errores puede haber.

Cada grupo debe entregar:

- Codigo Java funcionando.
- Pantalla HTML/JS conectada con la API.
- Validaciones basicas.
- Pruebas manuales documentadas.
- Documentacion de endpoints usados.

## 2. Estructura que debe respetar todo el mundo

### Controller

Recibe peticiones HTTP desde el navegador.

Ejemplo:

```java
@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<List<Cita>> listar() {
        return ResponseEntity.ok(citaService.listar());
    }
}
```

### Service

Contiene la logica de negocio.

Ejemplo:

```java
@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> listar() {
        return citaRepository.findAll();
    }
}
```

### Repository

Accede a la base de datos.

Ejemplo:

```java
public interface CitaRepository extends JpaRepository<Cita, Long> {
}
```

### Entity

Representa una tabla de la base de datos.

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
}
```

### DTO

Recibe datos desde el frontend sin exponer directamente la entidad.

Ejemplo:

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

## 3. Pasos para crear cualquier funcionalidad

### Paso 1: Definir que queremos hacer

Cada grupo debe escribir una frase clara.

Ejemplo:

```text
El grupo de citas va a permitir crear, listar, cancelar y completar citas veterinarias.
```

### Paso 2: Revisar o crear la entidad

Buscar en:

```text
src/main/java/com/clinica/veterinaria/entity
```

Si la entidad ya existe, se revisa si tiene todos los campos necesarios.

Ejemplo para citas:

```java
private LocalDateTime fechaHora;
private String motivo;

@Enumerated(EnumType.STRING)
private EstadoCita estado = EstadoCita.PENDIENTE;
```

### Paso 3: Revisar o crear el DTO

Buscar en:

```text
src/main/java/com/clinica/veterinaria/dto
```

El DTO debe contener solo los datos que envia el frontend.

Ejemplo:

```java
public class ClienteRequest {
    private String dni;
    private String telefono;
    private String direccion;
}
```

### Paso 4: Crear metodos en Repository si hacen falta

Buscar en:

```text
src/main/java/com/clinica/veterinaria/repository
```

Spring Data JPA ya trae:

- `findAll()`
- `findById(id)`
- `save(objeto)`
- `deleteById(id)`

Si hace falta una busqueda personalizada, se puede crear por nombre.

Ejemplo:

```java
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
```

Ejemplo posible para clientes:

```java
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByDni(String dni);
}
```

### Paso 5: Programar la logica en Service

Buscar en:

```text
src/main/java/com/clinica/veterinaria/service
```

Aqui van las reglas.

Ejemplo:

```java
public Cita crear(CitaRequest request) {
    if (request.getFechaHora() == null) {
        throw new BusinessException("La fecha de la cita es obligatoria");
    }

    if (request.getMotivo() == null || request.getMotivo().isBlank()) {
        throw new BusinessException("El motivo de la cita es obligatorio");
    }

    Cita cita = new Cita();
    cita.setFechaHora(request.getFechaHora());
    cita.setMotivo(request.getMotivo());
    cita.setEstado(EstadoCita.PENDIENTE);

    return citaRepository.save(cita);
}
```

### Paso 6: Exponer endpoints en Controller

Buscar en:

```text
src/main/java/com/clinica/veterinaria/controller
```

Ejemplo completo de endpoints basicos:

```java
@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<List<Cita>> listar() {
        return ResponseEntity.ok(citaService.listar());
    }

    @PostMapping
    public ResponseEntity<Cita> crear(@RequestBody CitaRequest request) {
        return ResponseEntity.ok(citaService.crear(request));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Cita> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.cancelar(id));
    }
}
```

### Paso 7: Conectar el frontend

Buscar las pantallas en:

```text
src/main/resources/static
```

Ejemplo de llamada con JavaScript:

```javascript
async function cargarCitas() {
    const response = await fetch("/api/citas");

    if (!response.ok) {
        throw new Error("No se han podido cargar las citas");
    }

    const citas = await response.json();
    console.log(citas);
}
```

Ejemplo de envio de formulario:

```javascript
async function crearCita() {
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
        alert("Error al crear la cita");
        return;
    }

    alert("Cita creada correctamente");
    cargarCitas();
}
```

### Paso 8: Probar

Cada grupo debe comprobar:

- La aplicacion arranca.
- La pantalla carga.
- El endpoint responde.
- Los datos se guardan.
- Los errores se muestran de forma clara.
- Los permisos funcionan segun el rol.

Comando recomendado:

```bash
mvn spring-boot:run
```

URL local:

```text
http://localhost:8080/login.html
```

## 4. Grupo 1: Autenticacion, usuarios y roles

### Objetivo

Gestionar login, registro, usuarios y permisos.

### Ficheros principales

| Tipo | Ficheros |
| --- | --- |
| Entidades | `Usuario.java`, `Rol.java` |
| Repositorios | `UsuarioRepository.java`, `RolRepository.java` |
| Servicios | `AuthService.java`, `UsuarioService.java` |
| Seguridad | `SecurityConfig.java`, `CustomUserDetailsService.java`, `UserDetailsImpl.java` |
| Controladores | `AuthController.java`, `AdminController.java` |
| Frontend | `login.html`, `register.html`, `admin/usuarios.html`, `admin/perfiles.html` |

### Pasos

1. Revisar que `Usuario` contiene nombre, apellidos, email, username, password y activo.
2. Revisar que `Rol` contiene nombre y descripcion.
3. Crear relacion real entre usuarios y roles si se decide implementar permisos completos.
4. Modificar `UserDetailsImpl` para devolver los roles reales del usuario.
5. Completar registro de usuarios en `AuthService`.
6. Cifrar siempre la contrasena con `PasswordEncoder`.
7. Probar login, logout y acceso denegado.

### Ejemplo de relacion usuarios-roles

```java
@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(
    name = "usuarios_roles",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id")
)
private Set<Rol> roles = new HashSet<>();
```

### Ejemplo para devolver roles reales

```java
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return usuario.getRoles().stream()
            .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
            .toList();
}
```

### Checklist

- El usuario admin puede entrar en `/admin`.
- Un auxiliar no puede entrar en `/admin`.
- Las contrasenas no se guardan en texto plano.
- El registro valida email y username repetidos.
- La pantalla muestra errores si el login falla.

## 5. Grupo 2: Clientes y mascotas

### Objetivo

Gestionar clientes y sus mascotas.

### Ficheros principales

| Tipo | Ficheros |
| --- | --- |
| Entidades | `Cliente.java`, `Mascota.java` |
| DTOs | `ClienteRequest.java`, `MascotaRequest.java` |
| Repositorios | `ClienteRepository.java`, `MascotaRepository.java` |
| Servicios | `ClienteService.java`, `MascotaService.java` |
| Controladores | `ClienteController.java`, `MascotaController.java` |
| Frontend | `auxiliar/clientes.html`, `auxiliar/cliente-form.html`, `auxiliar/mascotas.html`, `auxiliar/mascota-form.html` |

### Pasos

1. Revisar campos actuales de `Cliente`: dni, telefono y direccion.
2. Revisar campos actuales de `Mascota`: nombre, especie, raza y fechaNacimiento.
3. Decidir si una mascota debe estar relacionada con un cliente.
4. Crear endpoints para listar, crear, editar y borrar clientes.
5. Crear endpoints para listar, crear, editar y borrar mascotas.
6. Conectar formularios HTML con `fetch`.
7. Mostrar mensajes de exito y error.

### Ejemplo de relacion cliente-mascotas

```java
@ManyToOne
@JoinColumn(name = "cliente_id")
private Cliente cliente;
```

Este campo iria en `Mascota.java`.

### Ejemplo de crear cliente

```java
public Cliente crear(ClienteRequest request) {
    if (request.getDni() == null || request.getDni().isBlank()) {
        throw new BusinessException("El DNI es obligatorio");
    }

    Cliente cliente = new Cliente();
    cliente.setDni(request.getDni());
    cliente.setTelefono(request.getTelefono());
    cliente.setDireccion(request.getDireccion());

    return clienteRepository.save(cliente);
}
```

### Endpoints recomendados

| Metodo | Ruta | Funcion |
| --- | --- | --- |
| GET | `/api/clientes` | Listar clientes |
| GET | `/api/clientes/{id}` | Ver cliente |
| POST | `/api/clientes` | Crear cliente |
| PUT | `/api/clientes/{id}` | Editar cliente |
| DELETE | `/api/clientes/{id}` | Borrar cliente |
| GET | `/api/mascotas` | Listar mascotas |
| POST | `/api/mascotas` | Crear mascota |

### Checklist

- No se crean clientes sin DNI.
- No se crean mascotas sin nombre.
- Se puede ver el listado desde la pantalla.
- Se puede crear desde formulario.
- Si hay relacion cliente-mascota, se guarda correctamente.

## 6. Grupo 3: Citas y admision

### Objetivo

Gestionar la agenda de citas y la admision inicial.

### Ficheros principales

| Tipo | Ficheros |
| --- | --- |
| Entidades | `Cita.java`, `Admision.java`, `EstadoCita.java` |
| DTOs | `CitaRequest.java` |
| Repositorios | `CitaRepository.java`, `AdmisionRepository.java` |
| Servicios | `CitaService.java`, `AdmisionService.java` |
| Controladores | `CitaController.java`, `AuxiliarController.java` |
| Frontend | `auxiliar/citas.html`, `auxiliar/admision.html` |

### Pasos

1. Revisar los estados de cita: `PENDIENTE`, `CONFIRMADA`, `CANCELADA`, `COMPLETADA`.
2. Crear listado de citas.
3. Crear formulario para nueva cita.
4. Crear accion para confirmar cita.
5. Crear accion para cancelar cita.
6. Crear accion para marcar cita como completada.
7. Registrar admision si la cita pasa a atencion.

### Ejemplo de cambiar estado de cita

```java
public Cita cancelar(Long id) {
    Cita cita = citaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));

    if (cita.getEstado() == EstadoCita.COMPLETADA) {
        throw new BusinessException("No se puede cancelar una cita completada");
    }

    cita.setEstado(EstadoCita.CANCELADA);
    return citaRepository.save(cita);
}
```

### Endpoints recomendados

| Metodo | Ruta | Funcion |
| --- | --- | --- |
| GET | `/api/citas` | Listar citas |
| POST | `/api/citas` | Crear cita |
| PUT | `/api/citas/{id}/confirmar` | Confirmar cita |
| PUT | `/api/citas/{id}/cancelar` | Cancelar cita |
| PUT | `/api/citas/{id}/completar` | Completar cita |
| POST | `/api/admisiones` | Crear admision |

### Checklist

- No se puede crear una cita sin fecha.
- No se puede cancelar una cita completada.
- El listado muestra fecha, motivo y estado.
- Los botones cambian el estado correcto.
- La admision queda registrada con fecha y observaciones.

## 7. Grupo 4: Consultas veterinarias y recetas

### Objetivo

Gestionar consultas medicas, diagnosticos, tratamientos y recetas.

### Ficheros principales

| Tipo | Ficheros |
| --- | --- |
| Entidades | `Consulta.java`, `Receta.java`, `EstadoConsulta.java` |
| DTOs | `ConsultaRequest.java` |
| Repositorios | `ConsultaRepository.java`, `RecetaRepository.java` |
| Servicios | `ConsultaService.java`, `RecetaService.java` |
| Controladores | `ConsultaController.java`, `VeterinarioController.java` |
| Frontend | `veterinario/consulta-form.html`, `veterinario/lista-espera.html`, `veterinario/recetas.html` |

### Pasos

1. Revisar estados de consulta: `ABIERTA`, `EN_PROCESO`, `CERRADA`.
2. Mostrar lista de consultas abiertas.
3. Crear formulario para registrar diagnostico.
4. Crear formulario para registrar tratamiento.
5. Permitir cerrar consulta.
6. Crear receta asociada a la consulta si hace falta.
7. Mostrar recetas generadas.

### Ejemplo de cerrar consulta

```java
public Consulta cerrar(Long id) {
    Consulta consulta = consultaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Consulta no encontrada"));

    if (consulta.getDiagnostico() == null || consulta.getDiagnostico().isBlank()) {
        throw new BusinessException("No se puede cerrar una consulta sin diagnostico");
    }

    consulta.setEstado(EstadoConsulta.CERRADA);
    return consultaRepository.save(consulta);
}
```

### Ejemplo de receta

```java
public Receta crearReceta(String medicamento, String dosis, String indicaciones) {
    Receta receta = new Receta();
    receta.setMedicamento(medicamento);
    receta.setDosis(dosis);
    receta.setIndicaciones(indicaciones);
    return recetaRepository.save(receta);
}
```

### Endpoints recomendados

| Metodo | Ruta | Funcion |
| --- | --- | --- |
| GET | `/api/consultas` | Listar consultas |
| POST | `/api/consultas` | Crear consulta |
| PUT | `/api/consultas/{id}` | Actualizar consulta |
| PUT | `/api/consultas/{id}/cerrar` | Cerrar consulta |
| POST | `/api/recetas` | Crear receta |
| GET | `/api/recetas` | Listar recetas |

### Checklist

- Una consulta no se cierra sin diagnostico.
- Se puede guardar tratamiento.
- Las recetas tienen medicamento, dosis e indicaciones.
- El veterinario ve solo pantallas de veterinario.
- La lista de espera se actualiza.

## 8. Grupo 5: Pagos y area cliente

### Objetivo

Gestionar pagos y permitir al cliente consultar citas, historial y estado.

### Ficheros principales

| Tipo | Ficheros |
| --- | --- |
| Entidades | `Pago.java`, `EstadoPago.java`, `Cita.java`, `Consulta.java` |
| DTOs | `PagoRequest.java` |
| Repositorios | `PagoRepository.java`, `CitaRepository.java`, `ConsultaRepository.java` |
| Servicios | `PagoService.java`, `CitaService.java`, `ConsultaService.java` |
| Controladores | `PagoController.java`, `ClienteController.java` |
| Frontend | `cliente/pagos.html`, `cliente/citas.html`, `cliente/historial.html`, `cliente/estado.html` |

### Pasos

1. Revisar estados de pago: `PENDIENTE`, `PAGADO`, `RECHAZADO`.
2. Crear listado de pagos.
3. Crear accion para marcar pago como pagado.
4. Crear accion para marcar pago como rechazado.
5. Mostrar al cliente sus citas.
6. Mostrar al cliente su historial.
7. Mostrar mensajes claros si no hay datos.

### Ejemplo de pagar

```java
public Pago marcarComoPagado(Long id) {
    Pago pago = pagoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));

    if (pago.getEstado() == EstadoPago.PAGADO) {
        throw new BusinessException("El pago ya estaba completado");
    }

    pago.setEstado(EstadoPago.PAGADO);
    return pagoRepository.save(pago);
}
```

### Ejemplo JavaScript para pintar pagos

```javascript
function pintarPagos(pagos) {
    const tabla = document.getElementById("tablaPagos");
    tabla.innerHTML = "";

    pagos.forEach((pago) => {
        tabla.innerHTML += `
            <tr>
                <td>${pago.id}</td>
                <td>${pago.importe}</td>
                <td>${pago.metodoPago}</td>
                <td>${pago.estado}</td>
            </tr>
        `;
    });
}
```

### Endpoints recomendados

| Metodo | Ruta | Funcion |
| --- | --- | --- |
| GET | `/api/pagos` | Listar pagos |
| POST | `/api/pagos` | Crear pago |
| PUT | `/api/pagos/{id}/pagado` | Marcar como pagado |
| PUT | `/api/pagos/{id}/rechazado` | Marcar como rechazado |
| GET | `/api/cliente/citas` | Ver citas del cliente |
| GET | `/api/cliente/historial` | Ver historial del cliente |

### Checklist

- Los pagos muestran importe, metodo y estado.
- No se puede pagar dos veces el mismo pago.
- El cliente ve informacion clara.
- La pantalla no se rompe si no hay pagos.
- Los errores se muestran con alertas.

## 9. Grupo 6: REIAC, errores, pruebas y documentacion

### Objetivo

Gestionar verificaciones REIAC y apoyar al resto con control de errores, pruebas y documentacion.

### Ficheros principales

| Tipo | Ficheros |
| --- | --- |
| Entidades | `VerificacionReiac.java` |
| Repositorios | `VerificacionReiacRepository.java` |
| Servicios | `ReiacService.java` |
| Controladores | `ReiacController.java` |
| Errores | `GlobalExceptionHandler.java`, `BusinessException.java`, `ResourceNotFoundException.java` |
| Frontend | `auxiliar/reiac.html`, `error/*.html`, `js/common/alerts.js`, `js/common/error-monitor.js` |
| Docs | `docs/api`, `docs/pruebas`, `docs/bd` |

### Pasos

1. Crear formulario de consulta REIAC.
2. Guardar codigo de microchip, resultado y fecha.
3. Listar verificaciones realizadas.
4. Revisar que los errores de negocio devuelven respuestas claras.
5. Documentar endpoints de todos los grupos.
6. Crear plan de pruebas manuales.
7. Registrar errores detectados y solucionados.

### Ejemplo de verificacion REIAC

```java
public VerificacionReiac verificar(String codigoMicrochip) {
    if (codigoMicrochip == null || codigoMicrochip.isBlank()) {
        throw new BusinessException("El codigo de microchip es obligatorio");
    }

    VerificacionReiac verificacion = new VerificacionReiac();
    verificacion.setCodigoMicrochip(codigoMicrochip);
    verificacion.setResultado("Pendiente de integracion externa");
    verificacion.setFechaConsulta(LocalDateTime.now());

    return verificacionReiacRepository.save(verificacion);
}
```

### Ejemplo de error global

```java
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
}
```

### Checklist

- REIAC no permite codigo vacio.
- Se guarda fecha de consulta.
- Los errores 400, 403, 404 y 500 tienen respuesta clara.
- La documentacion de API esta actualizada.
- El plan de pruebas recoge casos de todos los grupos.

## 10. Reparto recomendado de carpetas

| Grupo | Responsable de |
| --- | --- |
| Grupo 1 | Seguridad, usuarios, roles, login y registro |
| Grupo 2 | Clientes y mascotas |
| Grupo 3 | Citas y admision |
| Grupo 4 | Consultas veterinarias y recetas |
| Grupo 5 | Pagos y area cliente |
| Grupo 6 | REIAC, errores, pruebas y documentacion |

## 11. Orden recomendado de trabajo

### Fase 1: Preparar datos

Cada grupo revisa sus entidades y DTOs.

Resultado esperado:

- Campos claros.
- Getters y setters necesarios.
- Relaciones decididas.
- Estados enumerados revisados.

### Fase 2: Backend basico

Cada grupo implementa:

- Repository.
- Service.
- Controller.
- Endpoints basicos.

Resultado esperado:

- La API responde.
- Se puede listar.
- Se puede crear.
- Se controlan errores simples.

### Fase 3: Frontend

Cada grupo conecta:

- HTML.
- JavaScript.
- Formularios.
- Tablas.
- Alertas.

Resultado esperado:

- La pantalla muestra datos reales.
- Los formularios envian datos al backend.
- Los errores se ven en pantalla.

### Fase 4: Seguridad

Se revisa que cada pantalla solo sea accesible para su rol.

Resultado esperado:

- Admin entra en todo.
- Auxiliar entra en zona auxiliar.
- Veterinario entra en zona veterinario.
- Cliente entra en zona cliente.
- Si no hay permisos, se muestra error 403.

### Fase 5: Pruebas y documentacion

Cada grupo documenta:

- Endpoints creados.
- Datos de entrada.
- Datos de salida.
- Pruebas realizadas.
- Errores conocidos.

## 12. Plantilla para documentar un endpoint

Cada endpoint debe documentarse asi:

````markdown
### Crear cita

- Metodo: POST
- Ruta: /api/citas
- Rol permitido: ADMIN, AUXILIAR
- Body:

```json
{
  "fechaHora": "2026-04-21T10:30:00",
  "motivo": "Revision anual"
}
```

- Respuesta correcta:

```json
{
  "id": 1,
  "fechaHora": "2026-04-21T10:30:00",
  "motivo": "Revision anual",
  "estado": "PENDIENTE"
}
```

- Errores posibles:
  - 400 si faltan datos.
  - 403 si el usuario no tiene permisos.
  - 500 si hay error inesperado.
````

## 13. Plantilla para pruebas manuales

Cada grupo debe rellenar una tabla como esta:

| Caso | Pasos | Resultado esperado | Resultado real | Estado |
| --- | --- | --- | --- | --- |
| Crear cita correcta | Entrar en auxiliar, abrir citas, rellenar formulario y guardar | La cita aparece en el listado | Pendiente | Pendiente |
| Crear cita sin fecha | Enviar formulario sin fecha | Aparece error de validacion | Pendiente | Pendiente |
| Cancelar cita | Pulsar cancelar en una cita pendiente | Estado pasa a CANCELADA | Pendiente | Pendiente |

## 14. Errores comunes que hay que evitar

- Poner logica de negocio en el controller.
- Usar entidades directamente para todos los formularios sin DTO.
- Guardar contrasenas sin cifrar.
- Crear rutas sin proteger por rol.
- Repetir codigo en varios servicios.
- No comprobar si un `id` existe antes de modificar.
- No mostrar errores en el frontend.
- Tocar archivos de otro grupo sin avisar.
- Modificar la carpeta `target`.

## 15. Checklist final de cada grupo

Antes de entregar, cada grupo debe confirmar:

- La aplicacion arranca sin errores.
- Su pantalla carga correctamente.
- Sus endpoints responden.
- Sus datos se guardan en MySQL.
- Sus validaciones funcionan.
- Sus errores se controlan.
- Sus rutas tienen permisos correctos.
- Su documentacion esta actualizada.
- Han probado al menos un caso correcto y un caso incorrecto.

## 16. Resumen final

Cada grupo debe trabajar una parte funcional, pero todos deben seguir la misma forma de construir:

```text
1. Definir datos
2. Crear o revisar entidad
3. Crear DTO
4. Crear repository
5. Programar service
6. Crear controller
7. Conectar frontend
8. Probar
9. Documentar
```

Si todos siguen este patron, el proyecto sera mas facil de unir, explicar, mantener y presentar.
