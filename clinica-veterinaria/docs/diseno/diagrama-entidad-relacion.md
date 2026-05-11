# Diagrama entidad-relacion

Responsable documentacion de diseno: David Martinez - G8.

Modelo relacional usado por la aplicacion.

```mermaid
erDiagram
    ROLES {
        BIGINT id PK
        VARCHAR nombre
        VARCHAR descripcion
    }

    USUARIOS {
        BIGINT id PK
        VARCHAR nombre
        VARCHAR apellidos
        VARCHAR email
        VARCHAR username
        VARCHAR password
        BOOLEAN activo
        BIGINT rol_id FK
    }

    CLIENTES {
        BIGINT id PK
        VARCHAR nombre
        VARCHAR apellidos
        VARCHAR dni
        VARCHAR telefono
        VARCHAR email
        VARCHAR direccion
        BIGINT usuario_id FK
    }

    VETERINARIOS {
        BIGINT id PK
        VARCHAR nombre
        VARCHAR apellidos
        VARCHAR dni
        VARCHAR numero_colegiado
        VARCHAR especialidad
        BIGINT usuario_id FK
    }

    MASCOTAS {
        BIGINT id PK
        VARCHAR nombre
        VARCHAR especie
        VARCHAR raza
        DATE fecha_nacimiento
        VARCHAR sexo
        VARCHAR chip
        BOOLEAN activa
        BOOLEAN reiac_verificado
        BIGINT cliente_id FK
        BIGINT veterinario_id FK
    }

    CITAS {
        BIGINT id PK
        DATETIME fecha_hora
        VARCHAR motivo
        VARCHAR estado
        BIGINT mascota_id FK
        BIGINT veterinario_id FK
    }

    CONSULTAS {
        BIGINT id PK
        VARCHAR sintomas
        VARCHAR diagnostico
        VARCHAR tratamiento
        VARCHAR estado
        BIGINT cita_id FK
    }

    RECETAS {
        BIGINT id PK
        VARCHAR medicamento
        VARCHAR dosis
        VARCHAR indicaciones
        BIGINT consulta_id FK
    }

    PAGOS {
        BIGINT id PK
        DECIMAL importe
        VARCHAR metodo_pago
        VARCHAR estado
        DATETIME fecha_pago
        BIGINT cliente_id FK
        BIGINT consulta_id FK
    }

    VERIFICACIONES_REIAC {
        BIGINT id PK
        VARCHAR codigo_microchip
        VARCHAR resultado
        DATETIME fecha_consulta
    }

    ADMISIONES {
        BIGINT id PK
        DATETIME fecha_ingreso
        VARCHAR observaciones
    }

    ROLES ||--o{ USUARIOS : asigna
    USUARIOS ||--o| CLIENTES : cuenta_cliente
    USUARIOS ||--o| VETERINARIOS : cuenta_veterinario
    CLIENTES ||--o{ MASCOTAS : propietario
    VETERINARIOS ||--o{ MASCOTAS : asignado
    MASCOTAS ||--o{ CITAS : solicita
    VETERINARIOS ||--o{ CITAS : atiende
    CITAS ||--o| CONSULTAS : genera
    CONSULTAS ||--o| RECETAS : contiene
    CLIENTES ||--o{ PAGOS : realiza
    CONSULTAS ||--o{ PAGOS : factura
```

## Reglas de integridad principales

- `usuarios.username` y `usuarios.email` deben ser unicos.
- `roles.nombre` contiene el nombre tecnico usado por Spring Security.
- Una mascota debe pertenecer a un cliente.
- Una cita debe tener mascota y veterinario.
- Una consulta debe estar asociada a una cita.
- Una receta debe estar asociada a una consulta.
- Un pago debe pertenecer a un cliente.
