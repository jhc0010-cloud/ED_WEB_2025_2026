# Manual de usuario - Administrador

Responsable funcional: Jesus Enrique de la Torre Jimenez - G3.

## Objetivo del rol

El administrador gestiona los usuarios del sistema y revisa los perfiles disponibles. Este rol se utiliza para controlar el acceso de auxiliares, veterinarios, clientes y otros administradores.

## Acceso

1. Abrir `http://localhost:8080/login.html`.
2. Usuario demo: `admin`.
3. Contrasena demo: `admin123`.
4. Al iniciar sesion se abre `/admin/dashboard.html`.

## Pantallas disponibles

| Pantalla | Uso |
| --- | --- |
| Dashboard | Resumen del area de administracion. |
| Usuarios | Alta, edicion y baja de usuarios. |
| Perfiles | Consulta de roles disponibles. |

## Gestion de usuarios

Ruta: `/admin/usuarios.html`.

### Crear usuario

1. Abrir la pantalla `Usuarios`.
2. Rellenar nombre, apellidos, email, usuario, contrasena y rol.
3. Pulsar `Guardar`.
4. Verificar que el usuario aparece en la tabla.

### Editar usuario

1. Localizar el usuario en la tabla.
2. Pulsar la accion de edicion.
3. Modificar los datos necesarios.
4. Guardar cambios.

### Eliminar usuario

1. Localizar el usuario.
2. Pulsar la accion de eliminacion.
3. Confirmar la operacion si el navegador lo solicita.

## Consulta de perfiles

Ruta: `/admin/perfiles.html`.

Permite revisar los roles cargados en la base de datos:

- `ROLE_ADMIN`.
- `ROLE_AUXILIAR`.
- `ROLE_VETERINARIO`.
- `ROLE_CLIENTE`.

## Errores habituales

| Situacion | Solucion |
| --- | --- |
| No puedo entrar al panel admin | Comprobar que el usuario tiene rol `ADMIN`. |
| Aparece error 403 | La sesion no tiene permisos suficientes. |
| No se guarda un usuario | Revisar campos obligatorios, email y username duplicados. |

## Cierre de sesion

Pulsar `Cerrar sesion` en el menu lateral o abrir `/logout`.
