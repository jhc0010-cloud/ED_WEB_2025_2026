# Manual de usuario - Cliente

Responsable funcional: David Iglesias Borland - G7.

## Objetivo del rol

El cliente usa el portal privado para consultar sus mascotas, pedir citas disponibles, revisar el estado clinico, ver recetas y pagar servicios pendientes.

## Acceso

1. Abrir `http://localhost:8080/login.html`.
2. Usuario demo: `cliente`.
3. Contrasena demo: `cliente123`.
4. Al iniciar sesion se abre `/cliente/dashboard.html`.

## Registro de nuevo cliente

Ruta publica: `/register.html`.

1. Rellenar nombre, apellidos, email, DNI, telefono, direccion, usuario y contrasena.
2. Pulsar `Crear cuenta`.
3. El sistema crea usuario con rol cliente y ficha de cliente asociada.
4. Iniciar sesion desde `/login.html`.

## Pantallas disponibles

| Pantalla | Uso |
| --- | --- |
| Dashboard | Resumen de citas, consultas y pagos. |
| Citas | Solicitar una visita en huecos disponibles. |
| Estado | Consultar estado de citas y consultas. |
| Historial | Ver consultas cerradas, tratamientos y recetas. |
| Pagos | Consultar y pagar servicios pendientes. |

## Solicitar cita

Ruta: `/cliente/citas.html`.

1. Seleccionar mascota.
2. Seleccionar veterinario.
3. Elegir uno de los huecos disponibles.
4. Escribir motivo.
5. Pulsar `Solicitar cita`.

La cita queda `PENDIENTE`. El auxiliar debe confirmarla antes de que el veterinario pueda atenderla.

## Consultar estado

Ruta: `/cliente/estado.html`.

Permite revisar:

- Fecha de cada cita.
- Mascota asociada.
- Veterinario.
- Estado de la cita.
- Consulta asociada si ya fue atendida.

## Historial y recetas

Ruta: `/cliente/historial.html`.

El cliente puede ver:

- Sintomas registrados.
- Diagnostico.
- Tratamiento.
- Recetas emitidas.
- Varios medicamentos para una misma consulta si el veterinario los ha anadido.

## Pagos

Ruta: `/cliente/pagos.html`.

### Pagar servicio pendiente

1. Seleccionar un pago pendiente.
2. Elegir metodo de pago.
3. Pulsar `Confirmar pago`.
4. El pago pasa a `PAGADO`.

El auxiliar tambien puede confirmar pagos desde recepcion si el cliente paga presencialmente.

## Errores habituales

| Situacion | Solucion |
| --- | --- |
| No hay mascotas disponibles | Contactar con la clinica para registrar una mascota. |
| No hay huecos de cita | Probar otro veterinario o fecha posterior. |
| No aparecen recetas | El veterinario todavia no ha emitido receta para esa consulta. |
| No hay pagos pendientes | No existe deuda pendiente o ya fue pagada. |

## Cierre de sesion

Pulsar `Cerrar sesion` o abrir `/logout`.
