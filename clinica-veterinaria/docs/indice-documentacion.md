# Indice de documentacion

Este indice resume el contenido de la carpeta `docs` y ayuda a localizar cada documento de la entrega.

## Documentos generales

| Documento | Contenido |
| --- | --- |
| `README_BACKEND.md` | Arquitectura del backend, capas, seguridad, reglas de negocio y pruebas. |
| `README_FRONTEND.md` | Estructura del frontend, pantallas, scripts comunes y responsive. |
| `responsables.md` | Reparto de responsables por modulo. |
| `entrega-final.md` | Resumen de entrega, funcionalidades, pruebas y evidencias. |

## Manuales de usuario

| Documento | Contenido |
| --- | --- |
| `manual-usuario/indice-manuales.md` | Indice de manuales por rol. |
| `manual-usuario/manual-admin.md` | Uso del panel de administrador. |
| `manual-usuario/manual-auxiliar.md` | Uso del panel auxiliar, citas, pagos e impresion. |
| `manual-usuario/manual-veterinario.md` | Uso de lista de espera, consultas y recetas. |
| `manual-usuario/manual-cliente.md` | Uso del portal cliente, citas, recetas e historial. |

## API

| Documento | Contenido |
| --- | --- |
| `api/documantacion-api-rest.md` | Rutas REST, ejemplos JSON, roles y portal cliente. |

## Base de datos

| Documento | Contenido |
| --- | --- |
| `bd/diccionario-datos.md` | Tablas, campos, tipos y descripcion funcional. |
| `bd/base_datos_clinica_veterinaria_comentada.md` | Referencia SQL comentada. |
| `bd/mock-data-mysql.sql` | Datos de prueba para insertar en MySQL. |

## Diseno

| Documento | Contenido |
| --- | --- |
| `diseno/diagrama-clases.md` | Diagrama Mermaid de clases principales. |
| `diseno/diagrama-entidad-relacion.md` | Diagrama Mermaid entidad-relacion. |
| `diseno/modelo-bd.md` | Modelo final usado por Spring Boot. |

## Instalacion y trabajo en equipo

| Documento | Contenido |
| --- | --- |
| `instalacion/manual-instalacion.md` | Requisitos, ejecucion con H2/MySQL y usuarios demo. |
| `instalacion/manual-git-intellij-equipos.md` | Flujo de trabajo con Git, IntelliJ, ramas `feature`, `develop` y `main`. |

## Memoria

| Documento | Contenido |
| --- | --- |
| `Memoria/memoria-proyecto.md` | Objetivo, arquitectura, decisiones de integracion y estado de entrega. |

## Pruebas

| Documento | Contenido |
| --- | --- |
| `pruebas/plan-pruebas.md` | Casos de prueba por perfil. |
| `pruebas/informe-pruebas.md` | Resultado de pruebas automatizadas y smoke test funcional. |
| `pruebas/proyecto_clinica_veterinaria_errores.md` | Errores corregidos, riesgos y mejoras futuras. |

## Flujo principal documentado

```text
Cliente solicita cita disponible
Auxiliar confirma cita pendiente
Veterinario atiende cita confirmada
Cliente consulta historial y paga
```

## Comando de verificacion

```powershell
.\mvnw.cmd test
```
