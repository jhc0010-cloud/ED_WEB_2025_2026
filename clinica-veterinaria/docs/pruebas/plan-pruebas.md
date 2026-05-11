# Plan de pruebas

Responsable plan de pruebas: Hector Garcia - G8.

## Objetivo

Comprobar que los casos de uso principales funcionan de extremo a extremo.

## Casos

| ID | Caso | Perfil | Resultado esperado |
| --- | --- | --- | --- |
| P-01 | Login admin | ADMIN | Accede a dashboard admin. |
| P-02 | Gestion usuarios | ADMIN | Lista, crea, edita y elimina usuarios. |
| P-03 | Alta cliente | AUXILIAR | Cliente aparece en tabla. |
| P-04 | Alta mascota | AUXILIAR | Mascota queda asociada a cliente. |
| P-05 | Verificacion REIAC | AUXILIAR | Se guarda resultado `VALIDO` o `NO_ENCONTRADO`. |
| P-06 | Crear cita | AUXILIAR | Cita aparece en agenda. |
| P-06B | Solicitar cita disponible | CLIENTE | Cliente solo puede elegir huecos disponibles y la cita queda `PENDIENTE`. |
| P-06C | Confirmar cita pendiente | AUXILIAR | Auxiliar cambia la cita a `CONFIRMADA`. |
| P-07 | Lista de espera | VETERINARIO | Citas confirmadas aparecen ordenadas. |
| P-08 | Registrar consulta | VETERINARIO | Cita pasa a completada y consulta queda guardada. |
| P-09 | Crear receta | VETERINARIO | Receta queda asociada a consulta. |
| P-10 | Consultar estado | CLIENTE | Muestra citas y consultas del cliente. |
| P-11 | Pago simulado | CLIENTE | Pago queda registrado como `PAGADO`. |

## Criterio de aceptacion

El proyecto se considera entregable si:

- `.\mvnw.cmd test` finaliza con `BUILD SUCCESS`.
- Los cuatro usuarios demo pueden acceder a sus dashboards.
- El flujo cliente solicita -> auxiliar confirma -> veterinario atiende -> cliente paga se puede demostrar con datos demo.
