# Responsables por modulo y fichero

Responsable documentacion: Giulshen Hueso - G8.

Este documento consolida el reparto indicado en los documentos originales `Planidicacion y Tareas.docx` y `Reparto de Equipos.docx`.

## Equipos

| Grupo | Area | Jefe / responsable principal | Integrantes documentados |
| --- | --- | --- | --- |
| G1 | Gestion, planificacion, integracion y QA | Juan Hakram Huertas Chergui | Javier Barrera Zurera, Justo Cenit Ruiz, Samuel |
| G2 | Base de datos, entidades y repositorios | Rafael Santiago de la Torre Jimenez | Miguel Angel, Cristian, Gabriel, Daniel |
| G3 | Login, seguridad, admin y perfiles | Jesus Enrique de la Torre Jimenez | David Martin Marti |
| G4 | Auxiliar, clientes, citas y recepcion | Gonzalo Viedma | Blas Vilar Martin, German Galvez Aranda, Achraf Akhmoul |
| G5 | Veterinario, consultas y recetas | Francisco Fernandez | Jorge Garcia Moreno, Jonathan Carvajal Reynaga |
| G6 | Mascotas, asignacion veterinaria y REIAC | Jorge Nunez | Adrian Barbos, Carlos Posadas, Marcos, Eduard |
| G7 | Cliente, estado, historial y pagos | David Iglesias Borland | Theo |
| G8 | Documentacion, manuales y pruebas | Giulshen Hueso | Reda El QOURCHI, Hector Garcia, David Martinez |

## Reparto por carpetas

| Carpeta / modulo | Responsable |
| --- | --- |
| `src/main/java/com/clinica/veterinaria/config` | G1 y G3 |
| `src/main/java/com/clinica/veterinaria/controller` | G1, con apoyo del grupo funcional de cada flujo |
| `src/main/java/com/clinica/veterinaria/dto` | G1 |
| `src/main/java/com/clinica/veterinaria/entity` | G2 |
| `src/main/java/com/clinica/veterinaria/repository` | G2 |
| `src/main/java/com/clinica/veterinaria/security` | G3 |
| `src/main/java/com/clinica/veterinaria/service` | G1, G4, G5, G6 y G7 segun modulo |
| `src/main/resources/static/admin` | G3 |
| `src/main/resources/static/auxiliar` | G4 y G6 |
| `src/main/resources/static/veterinario` | G5 |
| `src/main/resources/static/cliente` | G7 |
| `src/main/resources/static/js/common` | G3 y G1 |
| `src/main/resources/static/css` | G3 |
| `src/main/resources/schema.sql` | G2 |
| `docs/**` | G8 |

## Reparto detallado recomendado

| Tipo de fichero | Persona asignada |
| --- | --- |
| Configuracion global, integracion y manejadores backend | Juan Hakram Huertas Chergui |
| Seguridad, login, admin y rutas protegidas | Jesus Enrique de la Torre Jimenez |
| Entidades JPA | Rafael Santiago de la Torre Jimenez |
| Repositorios y script SQL | Miguel Angel, Cristian, Gabriel y Daniel |
| Pantallas y JS de auxiliar/clientes/citas/pagos | Gonzalo Viedma, Blas Vilar Martin, German Galvez Aranda y Achraf Akhmoul |
| Pantallas y JS de mascotas/REIAC | Jorge Nunez, Adrian Barbos, Carlos Posadas, Marcos y Eduard |
| Pantallas y JS de veterinario/consultas/recetas | Francisco Fernandez, Jorge Garcia Moreno y Jonathan Carvajal Reynaga |
| Pantallas y JS de cliente/estado/historial/pagos | David Iglesias Borland y Theo |
| Manuales, memoria, pruebas y documentacion final | Giulshen Hueso, Reda El QOURCHI, Hector Garcia y David Martinez |

## Criterio usado para comentarios en codigo

Los ficheros principales incluyen comentarios de responsable con este formato:

```text
Responsable: Nombre Apellidos - Grupo, modulo.
```

En ficheros de backend se usa:

```text
Responsable backend: Nombre Apellidos - Grupo, modulo.
```

En ficheros de documentacion se indica el responsable al inicio del documento.
