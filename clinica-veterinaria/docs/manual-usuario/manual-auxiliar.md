# Manual de usuario - Auxiliar

Responsable funcional: Gonzalo Viedma - G4, con apoyo de Jorge Nunez - G6 para mascotas y REIAC.

## Objetivo del rol

El auxiliar gestiona la recepcion de la clinica: clientes, mascotas, citas, verificacion REIAC, pagos y documentos imprimibles.

## Acceso

1. Abrir `http://localhost:8080/login.html`.
2. Usuario demo: `auxiliar`.
3. Contrasena demo: `auxiliar123`.
4. Al iniciar sesion se abre `/auxiliar/dashboard.html`.

## Pantallas disponibles

| Pantalla | Uso |
| --- | --- |
| Dashboard | Resumen de actividad de recepcion. |
| Clientes | Alta, busqueda, modificacion y baja de clientes. |
| Mascotas | Alta, edicion, baja logica y asignacion veterinaria. |
| Citas | Crear citas, confirmar solicitudes pendientes e imprimir justificantes. |
| Pagos | Confirmar pagos e imprimir recibos y recetas. |
| REIAC | Verificacion simulada de microchip. |

## Gestion de clientes

Ruta: `/auxiliar/clientes.html`.

1. Usar el formulario para registrar nombre, apellidos, DNI, telefono, email y direccion.
2. Pulsar `Guardar`.
3. Usar el buscador para filtrar clientes.
4. Editar o dar de baja desde las acciones de la tabla.

## Gestion de mascotas

Ruta: `/auxiliar/mascotas.html`.

1. Seleccionar cliente propietario.
2. Rellenar nombre, especie, raza, fecha de nacimiento, sexo y chip.
3. Asignar veterinario si corresponde.
4. Guardar la mascota.
5. Usar las acciones de la tabla para editar, asignar veterinario o dar de baja.

## Verificacion REIAC

Ruta: `/auxiliar/reiac.html`.

1. Introducir el codigo de microchip.
2. Pulsar verificar.
3. Revisar el resultado.
4. El sistema guarda la consulta en el historico de verificaciones.

## Gestion de citas

Ruta: `/auxiliar/citas.html`.

### Crear cita directa

1. Seleccionar mascota.
2. Seleccionar veterinario.
3. Indicar fecha y hora.
4. Escribir motivo.
5. Pulsar `Crear cita`.

Las citas creadas por auxiliar quedan confirmadas.

### Confirmar cita solicitada por cliente

1. Localizar una cita con estado `PENDIENTE`.
2. Pulsar `Confirmar`.
3. La cita pasa a `CONFIRMADA` y aparece en la lista de espera del veterinario.

### Imprimir justificante de cita

1. Localizar la cita.
2. Pulsar `Imprimir`.
3. El navegador abre el justificante y muestra el dialogo de impresion.

## Pagos y documentos

Ruta: `/auxiliar/pagos.html`.

### Confirmar pago

1. Seleccionar el metodo de pago.
2. Localizar un pago `PENDIENTE`.
3. Pulsar `Confirmar`.
4. El pago pasa a `PAGADO`.

### Imprimir recibo

1. Localizar el pago.
2. Pulsar `Recibo`.
3. Imprimir o guardar el documento desde el navegador.

### Imprimir receta

1. Revisar la tabla de recetas emitidas.
2. Pulsar `Imprimir`.
3. El navegador genera una receta imprimible.

## Errores habituales

| Situacion | Solucion |
| --- | --- |
| No aparece una mascota | Comprobar que esta activa y asociada a un cliente. |
| No puedo confirmar una cita | Revisar que este en estado `PENDIENTE`. |
| Un pago ya no se puede confirmar | Probablemente ya esta `PAGADO`. |
| Error 403 | La sesion no corresponde a auxiliar/admin. |

## Cierre de sesion

Pulsar `Cerrar sesion` o abrir `/logout`.
