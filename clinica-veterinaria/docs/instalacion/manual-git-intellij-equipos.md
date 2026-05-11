# Manual de trabajo con Git e IntelliJ para equipos

## Objetivo

Este manual explica como debe trabajar cada estudiante del proyecto en IntelliJ IDEA y cual es el flujo que deben seguir los jefes de equipo para integrar cambios en `develop` y, al final, en `main`.

El flujo recomendado del proyecto es este:

1. Cada estudiante trabaja en su rama `feature`.
2. El jefe de equipo revisa e integra el trabajo del grupo en `develop`.
3. Cuando `develop` esta estable y comprobado, se sube a `main`.

## Estructura de ramas

En este repositorio se esta usando este esquema:

- `main`: version final y estable del proyecto.
- `develop`: rama de integracion del trabajo del curso.
- `feature/Gx-Nombre-sprint1`: rama personal de cada estudiante.

Ejemplos reales del repositorio:

- `feature/G1-JHuertas-sprint1`
- `feature/G2-CristianAlvarez-sprint1`
- `feature/G6-CarlosPosadas-sprint1`

## Parte 1. Primeros pasos para cualquier estudiante

### 1. Descargar el proyecto desde GitHub en IntelliJ

1. Abrir IntelliJ IDEA.
2. Pulsar en `Get from VCS`.
3. Pegar la URL del repositorio.
4. Elegir la carpeta de destino.
5. Pulsar `Clone`.

URL del repositorio:

```text
https://github.com/jhc0010-cloud/ED_WEB_2025_2026.git
```

### 2. Comprobar que el proyecto abre bien

1. Esperar a que IntelliJ termine de cargar Maven.
2. Comprobar en la parte superior o inferior que no haya errores de sincronizacion.
3. Revisar que aparece el proyecto `clinica-veterinaria`.

### 3. Cambiar a tu rama personal

Cada estudiante debe trabajar en su propia rama `feature`, no directamente en `develop` ni en `main`.

Pasos en IntelliJ:

1. Pulsar en el nombre de la rama actual, abajo a la derecha.
2. Buscar tu rama personal.
3. Seleccionar `Checkout`.

Ejemplo:

```text
feature/G4-GonzaloViedma-sprint1
```

Si tu rama todavia no aparece:

1. Ve al panel `Git`.
2. Pulsa `Fetch`.
3. Vuelve al selector de ramas.
4. Busca `origin/feature/...`.
5. Haz `Checkout as New Local Branch`.

## Parte 2. Como debe trabajar cada estudiante

### 1. Antes de empezar cada sesion

Siempre hay que actualizar la rama antes de tocar codigo.

Pasos:

1. Estando en tu rama personal, abre `Git` > `Pull`.
2. Descarga los ultimos cambios de tu rama remota.
3. Si el jefe de equipo ha dicho que actualiceis desde `develop`, haced primero un `Update Project`.

Recomendacion:

- No empezar a programar si hace muchos dias que no actualizas tu rama.

### 2. Hacer cambios en tu parte del proyecto

Cada alumno debe tocar solo la parte que le corresponde.

Buenas practicas:

- No modificar archivos de otros compañeros sin avisar.
- No borrar codigo ajeno porque "parece que no se usa".
- Probar el proyecto antes de subir cambios.
- Hacer cambios pequeños y claros.

### 3. Revisar los archivos modificados

Antes de guardar el trabajo:

1. Abre `Git` > `Commit`.
2. Revisa la lista de archivos cambiados.
3. Comprueba que solo estas subiendo lo tuyo.

No subas:

- Archivos temporales.
- Configuraciones personales si no son necesarias.
- Cambios accidentales en otras carpetas.

### 4. Hacer commit

Cuando ya tengas una parte terminada:

1. Ve a `Git` > `Commit`.
2. Escribe un mensaje claro.
3. Pulsa `Commit` o `Commit and Push`.

Ejemplos de mensajes:

```text
Añadido formulario de mascotas
Corregida validacion de citas
Implementado listado de clientes
```

### 5. Subir la rama a GitHub

Si no has usado `Commit and Push`:

1. Ve a `Git` > `Push`.
2. Revisa que estas subiendo tu rama `feature`.
3. Pulsa `Push`.

## Parte 3. Normas importantes para estudiantes

### Lo que si debes hacer

- Trabajar siempre en tu rama personal.
- Hacer `commit` frecuentes.
- Avisar al jefe de equipo cuando acabes una tarea.
- Actualizar tu rama antes de seguir trabajando.

### Lo que no debes hacer

- No trabajar directamente en `main`.
- No trabajar directamente en `develop`, salvo que el profesor o el jefe de equipo lo indique.
- No mezclar trabajo de varios compañeros en un mismo commit.
- No hacer `push` de codigo sin revisar.

## Parte 4. Trabajo del jefe de equipo

El jefe de equipo es quien integra el trabajo de su grupo y comprueba que no se rompa el proyecto.

## 1. Actualizar `develop`

Antes de hacer merges:

1. Cambiar a la rama `develop`.
2. Hacer `Pull` de `origin/develop`.
3. Comprobar que el proyecto compila y abre correctamente.

Pasos en IntelliJ:

1. Selector de ramas.
2. `Checkout` sobre `develop`.
3. `Git` > `Pull`.

## 2. Revisar las ramas del equipo

El jefe de equipo debe comprobar:

- Que cada alumno ha subido su rama.
- Que el codigo no tiene errores graves.
- Que los nombres de commit son claros.
- Que no se han modificado partes que no correspondian.

## 3. Hacer merge de una rama de alumno en `develop`

Pasos:

1. Estando en `develop`, abrir el selector de ramas.
2. Buscar la rama del alumno.
3. Pulsar `Merge into Current`.
4. Resolver conflictos si aparecen.
5. Probar el proyecto.

Ejemplo:

```text
Estando en develop:
merge de feature/G2-GabrielCruz-sprint1
```

## 4. Resolver conflictos de merge

Si IntelliJ muestra un conflicto:

1. Abrir la herramienta de merge.
2. Comparar izquierda, centro y derecha.
3. Conservar el codigo correcto.
4. No aceptar cambios sin leerlos.
5. Marcar el conflicto como resuelto.
6. Hacer commit del merge si IntelliJ lo solicita.

Consejo:

- Si el conflicto afecta a una parte funcional importante, consultar con el compañero antes de cerrar el merge.

## 5. Subir `develop`

Cuando el jefe de equipo ya ha integrado y probado los cambios:

1. Hacer `Push` de `develop`.
2. Verificar en GitHub que la rama remota se ha actualizado.

Resumen del flujo del jefe de equipo:

```text
Checkout develop
Pull origin/develop
Merge ramas feature del equipo
Probar proyecto
Commit de merge si hace falta
Push origin/develop
```

## Parte 5. Paso final de `develop` a `main`

Este paso solo debe hacerse cuando la version este revisada y estable.

### 1. Preparar la integracion final

Antes de pasar a `main`:

1. Comprobar que `develop` funciona.
2. Revisar que no hay conflictos pendientes.
3. Confirmar que todos los cambios del sprint estan dentro de `develop`.

### 2. Hacer merge de `develop` a `main` en IntelliJ

Pasos:

1. Cambiar a la rama `main`.
2. Hacer `Pull` de `origin/main`.
3. Abrir el selector de ramas.
4. Seleccionar `develop`.
5. Pulsar `Merge into Current`.
6. Resolver conflictos si los hubiera.
7. Probar de nuevo el proyecto.

### 3. Subir `main`

Cuando ya este todo correcto:

1. Hacer `Push` de `main`.
2. Comprobar en GitHub que `main` contiene la version final.

## Parte 6. Flujo completo resumido

### Flujo de cada estudiante

```text
Clonar repositorio
Checkout a tu rama feature
Pull
Trabajar
Commit
Push
Avisar al jefe de equipo
```

### Flujo del jefe de equipo

```text
Checkout develop
Pull
Merge ramas feature del equipo
Resolver conflictos
Probar
Push a develop
```

### Flujo final del proyecto

```text
Checkout main
Pull
Merge develop en main
Probar
Push a main
```

## Parte 7. Recomendaciones finales

- Hacer `commit` pequenos y frecuentes.
- Usar mensajes de commit que expliquen lo que se ha hecho.
- No esperar al ultimo dia para subir cambios.
- Si hay conflictos, resolverlos con calma.
- Si no sabes en que rama estas, miralo antes de hacer `commit`.
- Si no sabes si hacer merge, pregunta antes de mezclar trabajo.

## Parte 8. Errores frecuentes

### Error 1. He trabajado en `develop` sin querer

Solucion:

1. No hagas `push` todavia.
2. Consulta con el jefe de equipo o mueve los cambios a tu rama personal.

### Error 2. IntelliJ no encuentra mi rama

Solucion:

1. Hacer `Fetch`.
2. Buscar la rama remota en `origin`.
3. Crear la rama local desde la remota.

### Error 3. No me deja hacer push

Posibles causas:

- No has hecho login en GitHub.
- Tu rama local va desactualizada.
- Estas intentando subir a una rama protegida.

### Error 4. Hay conflictos

Solucion:

1. Leer el conflicto.
2. No aceptar todo automaticamente.
3. Revisar con el compañero si hace falta.

## Parte 9. Frases que debe recordar el alumnado

- "Yo trabajo en mi rama, no en `main`".
- "El jefe de equipo integra en `develop`".
- "`main` solo se actualiza cuando todo funciona".

22