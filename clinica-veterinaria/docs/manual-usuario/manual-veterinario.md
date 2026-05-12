# Manual de usuario - Veterinario

Responsable funcional: Francisco Fernandez - G5.

## Objetivo del rol

El veterinario atiende citas confirmadas, registra consultas clinicas y emite recetas. Su trabajo empieza cuando el auxiliar confirma una cita.

## Acceso

1. Abrir `http://localhost:8080/login.html`.
2. Usuario demo: `vet`.
3. Contrasena demo: `vet123`.
4. Al iniciar sesion se abre `/veterinario/dashboard.html`.

## Pantallas disponibles

| Pantalla | Uso |
| --- | --- |
| Dashboard | Resumen del area veterinaria. |
| Lista de espera | Citas confirmadas asignadas al veterinario. |
| Consulta | Registro de sintomas, diagnostico y tratamiento. |
| Recetas | Emision de medicamentos asociados a consultas. |

## Lista de espera

Ruta: `/veterinario/lista-espera.html`.

1. Revisar las citas `CONFIRMADA`.
2. Identificar mascota, cliente y motivo.
3. Pulsar `Atender` para abrir el formulario de consulta.

Solo aparecen citas confirmadas. Las citas pendientes no deben ser atendidas.

## Registrar consulta

Ruta: `/veterinario/consulta-form.html`.

1. Seleccionar una cita confirmada.
2. Rellenar sintomas.
3. Rellenar diagnostico.
4. Rellenar tratamiento.
5. Pulsar guardar.

Al guardar:

- La consulta queda registrada.
- La cita pasa a `COMPLETADA`.
- Se genera un pago pendiente para el cliente.

## Emitir recetas

Ruta: `/veterinario/recetas.html`.

Una consulta puede tener varios medicamentos. La pantalla permite construir una tabla antes de guardar.

### Anadir medicamentos

1. Seleccionar la consulta.
2. Escribir medicamento.
3. Escribir dosis.
4. Escribir indicaciones.
5. Pulsar `Anadir medicamento`.
6. Repetir para cada medicamento necesario.

### Guardar receta

1. Revisar la tabla de medicamentos pendientes.
2. Quitar cualquier linea incorrecta si hace falta.
3. Pulsar `Guardar receta`.
4. Cada medicamento se guarda como una linea de receta asociada a la misma consulta.

## Errores habituales

| Situacion | Solucion |
| --- | --- |
| No hay citas en lista de espera | El auxiliar debe confirmar citas pendientes. |
| No puedo guardar consulta | Revisar campos obligatorios y que la cita este confirmada. |
| No puedo guardar receta | Anadir al menos un medicamento con dosis. |
| Error 403 | La sesion no corresponde a veterinario/admin. |

## Cierre de sesion

Pulsar `Cerrar sesion` o abrir `/logout`.
