zz# Documentacion API REST

Base URL local: `http://localhost:8080`

La API usa sesion de Spring Security. Primero se inicia sesion desde `/login.html` o se registra un cliente desde `/register.html`.

## Autenticacion

| Metodo | Ruta | Descripcion |
| --- | --- | --- |
| `POST` | `/api/auth/register` | Registra un usuario cliente. |
| `POST` | `/api/auth/login` | Login JSON alternativo. |
| `POST` | `/login` | Login de formulario Spring Security. |
| `GET` | `/logout` | Cierra sesion. |

Ejemplo `POST /api/auth/register`:

```json
{
  "nombre": "Marta",
  "apellidos": "Perez",
  "dni": "12345678A",
  "telefono": "600000000",
  "direccion": "Calle Mayor 1",
  "email": "marta@example.com",
  "username": "marta",
  "password": "marta123"
}
```

El registro crea el usuario con rol `CLIENTE` y tambien su ficha de cliente asociada.

## Administracion

| Metodo | Ruta | Rol | Descripcion |
| --- | --- | --- | --- |
| `GET` | `/api/admin/roles` | ADMIN | Lista perfiles. |
| `GET` | `/api/admin/usuarios` | ADMIN | Lista usuarios. |
| `POST` | `/api/admin/usuarios` | ADMIN | Crea usuario. |
| `PUT` | `/api/admin/usuarios/{id}` | ADMIN | Actualiza usuario. |
| `DELETE` | `/api/admin/usuarios/{id}` | ADMIN | Elimina usuario. |

Ejemplo usuario:

```json
{
  "nombre": "Laura",
  "apellidos": "Martin",
  "email": "laura@clinica.local",
  "username": "laura",
  "password": "laura123",
  "rolId": 2
}
```

## Clientes

| Metodo | Ruta | Descripcion |
| --- | --- | --- |
| `GET` | `/api/clientes` | Lista clientes. Acepta `?q=texto`. |
| `GET` | `/api/clientes/{id}` | Detalle de cliente. |
| `POST` | `/api/clientes` | Alta cliente. |
| `PUT` | `/api/clientes/{id}` | Modifica cliente. |
| `DELETE` | `/api/clientes/{id}` | Baja cliente. |
| `GET` | `/api/clientes/{id}/citas` | Citas de un cliente. |
| `GET` | `/api/clientes/{id}/consultas` | Consultas de un cliente. |

## Mascotas y REIAC

| Metodo | Ruta | Descripcion |
| --- | --- | --- |
| `GET` | `/api/mascotas` | Lista mascotas. Acepta `?clienteId=1`. |
| `POST` | `/api/mascotas` | Alta mascota. |
| `PUT` | `/api/mascotas/{id}` | Modifica mascota. |
| `PUT` | `/api/mascotas/{mascotaId}/veterinario/{veterinarioId}` | Asigna veterinario. |
| `DELETE` | `/api/mascotas/{id}` | Baja logica. |
| `GET` | `/api/reiac` | Historico de verificaciones. |
| `POST` | `/api/reiac/verificar?chip=CHIP001` | Verificacion REIAC simulada. |

## Citas, consultas, recetas y pagos

| Metodo | Ruta | Descripcion |
| --- | --- | --- |
| `GET` | `/api/citas` | Lista citas. Acepta `?clienteId=1`. |
| `GET` | `/api/citas/disponibles` | Lista huecos disponibles. Acepta `?veterinarioId=1`. |
| `GET` | `/api/citas/espera/{veterinarioId}` | Lista de espera confirmada. |
| `POST` | `/api/citas` | Crea cita desde auxiliar. Queda `CONFIRMADA`. |
| `PUT` | `/api/citas/{id}/estado?estado=CONFIRMADA` | Cambia estado de cita. Lo usa auxiliar para confirmar solicitudes pendientes. |
| `GET` | `/api/consultas` | Lista consultas. Acepta `?clienteId=1`. |
| `POST` | `/api/consultas` | Registra consulta y completa la cita. |
| `GET` | `/api/recetas` | Lista recetas. |
| `POST` | `/api/recetas` | Crea una linea de receta para una consulta. Una consulta puede tener varios medicamentos. |
| `GET` | `/api/pagos` | Lista pagos. Acepta `?clienteId=1`. |
| `POST` | `/api/pagos` | Registra pago simulado. |
| `PUT` | `/api/pagos/{id}/confirmar?metodoPago=EFECTIVO` | Confirma un pago desde recepcion/auxiliar. |

## Portal cliente

| Metodo | Ruta | Descripcion |
| --- | --- | --- |
| `GET` | `/api/portal/resumen` | Resumen privado del cliente autenticado. |
| `GET` | `/api/portal/mascotas` | Mascotas del cliente autenticado. |
| `GET` | `/api/portal/citas` | Citas del cliente autenticado. |
| `GET` | `/api/portal/citas/disponibles` | Huecos disponibles para solicitar cita. Acepta `?veterinarioId=1`. |
| `POST` | `/api/portal/citas` | Solicita una cita. Queda `PENDIENTE` hasta que la confirme auxiliar. |
| `GET` | `/api/portal/consultas` | Consultas del cliente autenticado. |
| `GET` | `/api/portal/recetas` | Recetas del cliente autenticado. |
| `GET` | `/api/portal/pagos` | Pagos del cliente autenticado. |
| `PUT` | `/api/portal/pagos/{id}/pagar?metodoPago=TARJETA` | Marca como pagado un pago pendiente propio. |

Ejemplo `POST /api/citas`:

```json
{
  "fechaHora": "2026-05-08T10:30:00",
  "motivo": "Revision anual",
  "mascotaId": 1,
  "veterinarioId": 1
}
```

Ejemplo `POST /api/consultas`:

```json
{
  "citaId": 1,
  "sintomas": "Cansancio leve",
  "diagnostico": "Estado general correcto",
  "tratamiento": "Revision en 6 meses"
}
```

Ejemplo `POST /api/pagos`:

```json
{
  "clienteId": 1,
  "consultaId": 1,
  "importe": 45.00,
  "metodoPago": "TARJETA"
}
```
