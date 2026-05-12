# Errores, riesgos y mejoras

## Errores corregidos durante la integracion

| Area | Problema | Solucion aplicada |
| --- | --- | --- |
| Registro | El registro creaba usuario pero no ficha de cliente. | `AuthService` crea `Usuario` y `Cliente` asociado. |
| Citas cliente | El cliente podia confirmar de forma implicita su cita. | Las citas creadas desde portal cliente quedan `PENDIENTE`. |
| Confirmacion de cita | No quedaba claro quien confirmaba la cita. | La confirmacion se realiza desde el flujo auxiliar con `/api/citas/{id}/estado`. |
| Disponibilidad | El cliente no veia huecos disponibles de forma guiada. | Se anadio `/api/portal/citas/disponibles` y selector en frontend. |
| Agenda | Podian aparecer conflictos de horario para un veterinario. | `CitaService` bloquea solapamientos salvo citas canceladas. |
| Consulta | Se podia registrar consulta sobre citas no confirmadas. | `ConsultaService` exige cita `CONFIRMADA`. |
| Pagos | Faltaban reglas de pago propio y metodo permitido. | `PagoService` valida propietario, estado y metodo. |
| Errores API | Los errores de validacion eran poco claros para frontend. | `GlobalExceptionHandler` devuelve JSON con campos invalidos. |
| Permisos | Algunas rutas API podian responder HTML en vez de JSON. | `CustomAccessDeniedHandler` devuelve JSON para `/api/**`. |
| Frontend | Habia pantallas poco consistentes visualmente. | CSS comun y layout por rol mejorados. |
| Documentacion | Documentos de diseno estaban vacios. | Se completan diagramas y README tecnicos. |

## Riesgos aceptados

- REIAC esta simulado, no conectado a un servicio oficial externo.
- Los pagos son simulados, sin pasarela real.
- El proyecto usa sesiones de Spring Security, no JWT.
- El perfil `dev` usa H2 en memoria: los datos se pierden al reiniciar.
- En MySQL, si existe una base antigua, puede ser necesario recrearla para evitar diferencias de esquema.

## Mejoras futuras

1. Anadir pruebas de controlador con `MockMvc`.
2. Anadir pruebas end-to-end de navegador para flujos principales.
3. Incorporar paginacion y filtros avanzados en tablas grandes.
4. Sustituir REIAC simulado por integracion real si se dispone de API.
5. Guardar historico de cambios de estado de cita.
6. Separar frontend en componentes si el proyecto crece.
7. Anadir auditoria de usuario que crea o modifica cada registro.

## Estado actual

El proyecto compila, tiene pruebas unitarias basicas y permite demostrar el flujo:

```text
Cliente solicita cita -> Auxiliar confirma -> Veterinario atiende -> Cliente paga
```
