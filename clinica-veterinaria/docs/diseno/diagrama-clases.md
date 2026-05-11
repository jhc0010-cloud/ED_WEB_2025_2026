# Diagrama de clases

Responsable documentacion de diseno: David Martinez - G8.

Diagrama simplificado de las entidades principales del proyecto.

```mermaid
classDiagram
    class Rol {
        Long id
        String nombre
        String descripcion
    }

    class Usuario {
        Long id
        String nombre
        String apellidos
        String email
        String username
        String password
        boolean activo
    }

    class Cliente {
        Long id
        String nombre
        String apellidos
        String dni
        String telefono
        String email
        String direccion
    }

    class Veterinario {
        Long id
        String nombre
        String apellidos
        String dni
        String numeroColegiado
        String especialidad
    }

    class Mascota {
        Long id
        String nombre
        String especie
        String raza
        LocalDate fechaNacimiento
        String sexo
        String chip
        boolean activa
        boolean reiacVerificado
    }

    class Cita {
        Long id
        LocalDateTime fechaHora
        String motivo
        EstadoCita estado
    }

    class Consulta {
        Long id
        String sintomas
        String diagnostico
        String tratamiento
        EstadoConsulta estado
    }

    class Receta {
        Long id
        String medicamento
        String dosis
        String indicaciones
    }

    class Pago {
        Long id
        BigDecimal importe
        String metodoPago
        EstadoPago estado
        LocalDateTime fechaPago
    }

    class VerificacionReiac {
        Long id
        String codigoMicrochip
        String resultado
        LocalDateTime fechaConsulta
    }

    class Admision {
        Long id
        LocalDateTime fechaIngreso
        String observaciones
    }

    class EstadoCita {
        <<enumeration>>
        PENDIENTE
        CONFIRMADA
        CANCELADA
        COMPLETADA
    }

    class EstadoConsulta {
        <<enumeration>>
        ABIERTA
        EN_PROCESO
        CERRADA
    }

    class EstadoPago {
        <<enumeration>>
        PENDIENTE
        PAGADO
        RECHAZADO
    }

    Rol "1" --> "0..*" Usuario
    Usuario "1" --> "0..1" Cliente
    Usuario "1" --> "0..1" Veterinario
    Cliente "1" --> "0..*" Mascota
    Veterinario "1" --> "0..*" Mascota
    Mascota "1" --> "0..*" Cita
    Veterinario "1" --> "0..*" Cita
    Cita "1" --> "0..1" Consulta
    Consulta "1" --> "0..1" Receta
    Cliente "1" --> "0..*" Pago
    Consulta "1" --> "0..*" Pago
```

## Notas de diseno

- El modelo separa `Usuario` de `Cliente` y `Veterinario` para permitir login solo cuando sea necesario.
- `Mascota` mantiene el propietario (`Cliente`) y el veterinario asignado.
- `Cita` es el punto de union entre cliente, mascota y veterinario.
- `Consulta` solo debe crearse sobre citas confirmadas.
- `Pago` se genera al registrar consulta y queda pendiente hasta que el cliente lo paga.
