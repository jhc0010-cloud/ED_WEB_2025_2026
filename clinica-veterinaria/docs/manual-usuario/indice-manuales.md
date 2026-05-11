# Manuales de usuario por rol

Responsable documentacion: Giulshen Hueso - G8.

Esta carpeta contiene los manuales de uso de la aplicacion Clinica Veterinaria, separados por perfil de acceso.

## Manuales disponibles

| Rol | Documento | Pantalla inicial |
| --- | --- | --- |
| Administrador | `manual-admin.md` | `/admin/dashboard.html` |
| Auxiliar | `manual-auxiliar.md` | `/auxiliar/dashboard.html` |
| Veterinario | `manual-veterinario.md` | `/veterinario/dashboard.html` |
| Cliente | `manual-cliente.md` | `/cliente/dashboard.html` |

## Acceso general

1. Abrir `http://localhost:8080/login.html`.
2. Escribir usuario y contrasena.
3. Pulsar `Entrar`.
4. El sistema redirige automaticamente al panel correspondiente segun el rol.

## Usuarios demo

| Rol | Usuario | Contrasena |
| --- | --- | --- |
| Administrador | `admin` | `admin123` |
| Auxiliar | `auxiliar` | `auxiliar123` |
| Veterinario | `vet` | `vet123` |
| Cliente | `cliente` | `cliente123` |

## Recomendacion para la demo

El flujo completo recomendado es:

```text
Admin revisa usuarios
Auxiliar crea cliente/mascota y confirma cita
Veterinario registra consulta y recetas
Auxiliar confirma pago o imprime documentos
Cliente consulta citas, historial, recetas y pagos
```
