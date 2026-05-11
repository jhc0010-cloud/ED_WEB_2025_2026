# Informe de pruebas

Responsable pruebas: Hector Garcia - G8.

Fecha de verificacion: 2026-05-08

## Comando ejecutado

```powershell
.\mvnw.cmd test
```

## Resultado

- Compilacion del backend: correcta.
- Compilacion de recursos estaticos: correcta.
- Tests ejecutados: 13.
- Fallos: 0.
- Errores: 0.
- Resultado Maven: `BUILD SUCCESS`.

## Tests automatizados

| Clase | Numero | Alcance |
| --- | ---: | --- |
| `AuthServiceTest` | 1 | Registro publico de cliente con usuario y ficha asociada. |
| `CitaServiceTest` | 4 | Fechas pasadas, conflictos de agenda, cita auxiliar confirmada y cita cliente pendiente. |
| `ConsultaServiceTest` | 2 | Rechazo de citas no confirmadas y cierre de cita con pago pendiente. |
| `PagoServiceTest` | 5 | Pago ya pagado, pago de otro cliente, metodo invalido, pago de cliente y confirmacion por auxiliar. |
| `RecetaServiceTest` | 1 | Permite varios medicamentos para una misma consulta. |

## Smoke test funcional realizado

Se comprobo el flujo principal contra la aplicacion levantada en `http://localhost:8080` con perfil `dev`:

1. Login como `cliente/cliente123`.
2. Consulta de huecos disponibles.
3. Solicitud de cita desde portal cliente.
4. La cita queda `PENDIENTE`.
5. Login como `auxiliar/auxiliar123`.
6. Confirmacion de la cita.
7. La cita queda `CONFIRMADA`.
8. Login como `vet/vet123`.
9. La cita aparece en lista de espera.
10. Registro de consulta.
11. La cita queda `COMPLETADA`.
12. Se genera un pago `PENDIENTE`.
13. Login como cliente y pago con `TARJETA`.
14. El pago queda `PAGADO`.

## Seguridad comprobada

- Un cliente no puede acceder a `/api/admin/usuarios`: respuesta `403`.
- Un cliente no puede acceder a `/api/auxiliar/dashboard`: respuesta `403`.
- Las paginas protegidas sin sesion devuelven `401` o redirigen al login segun el flujo web.
- Las APIs devuelven JSON cuando no hay permisos suficientes.

## Pruebas funcionales recomendadas para demo

### Administrador

1. Entrar con `admin/admin123`.
2. Abrir `/admin/usuarios.html`.
3. Crear, editar y eliminar un usuario de prueba.
4. Revisar `/admin/perfiles.html`.

### Auxiliar

1. Entrar con `auxiliar/auxiliar123`.
2. Crear cliente.
3. Crear mascota.
4. Verificar chip REIAC.
5. Crear una cita directa o confirmar una cita solicitada por cliente.

### Veterinario

1. Entrar con `vet/vet123`.
2. Abrir lista de espera.
3. Registrar consulta.
4. Crear receta si procede.

### Cliente

1. Entrar con `cliente/cliente123`.
2. Solicitar cita en hueco disponible.
3. Consultar estado e historial.
4. Pagar un pago pendiente.

## Riesgos pendientes

- REIAC es una simulacion local, no consulta un servicio externo real.
- La pasarela de pago es simulada.
- El perfil MySQL depende de credenciales locales configuradas en `application.properties`.
- No hay pruebas de navegador automatizadas por herramienta externa; la comprobacion realizada fue por peticiones HTTP reales y revision de frontend.
