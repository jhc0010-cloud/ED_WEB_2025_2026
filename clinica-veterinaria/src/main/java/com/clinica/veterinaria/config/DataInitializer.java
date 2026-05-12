package com.clinica.veterinaria.config;

// Responsable backend: Juan Hakram Huertas Chergui - G1, configuracion backend.
import com.clinica.veterinaria.entity.Cita;
import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.Consulta;
import com.clinica.veterinaria.entity.EstadoCita;
import com.clinica.veterinaria.entity.EstadoConsulta;
import com.clinica.veterinaria.entity.EstadoPago;
import com.clinica.veterinaria.entity.Mascota;
import com.clinica.veterinaria.entity.Pago;
import com.clinica.veterinaria.entity.Rol;
import com.clinica.veterinaria.entity.Usuario;
import com.clinica.veterinaria.entity.Veterinario;
import com.clinica.veterinaria.repository.CitaRepository;
import com.clinica.veterinaria.repository.ClienteRepository;
import com.clinica.veterinaria.repository.ConsultaRepository;
import com.clinica.veterinaria.repository.MascotaRepository;
import com.clinica.veterinaria.repository.PagoRepository;
import com.clinica.veterinaria.repository.RolRepository;
import com.clinica.veterinaria.repository.UsuarioRepository;
import com.clinica.veterinaria.repository.VeterinarioRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer
implements CommandLineRunner {
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final MascotaRepository mascotaRepository;
    private final CitaRepository citaRepository;
    private final ConsultaRepository consultaRepository;
    private final PagoRepository pagoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(RolRepository rolRepository, UsuarioRepository usuarioRepository, ClienteRepository clienteRepository, VeterinarioRepository veterinarioRepository, MascotaRepository mascotaRepository, CitaRepository citaRepository, ConsultaRepository consultaRepository, PagoRepository pagoRepository, PasswordEncoder passwordEncoder, JdbcTemplate jdbcTemplate) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.veterinarioRepository = veterinarioRepository;
        this.mascotaRepository = mascotaRepository;
        this.citaRepository = citaRepository;
        this.consultaRepository = consultaRepository;
        this.pagoRepository = pagoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void run(String ... args) {
        this.allowMultipleRecetasPerConsulta();
        Rol adminRol = this.ensureRole("ROLE_ADMIN", "Administrador");
        Rol auxiliarRol = this.ensureRole("ROLE_AUXILIAR", "Auxiliar");
        Rol veterinarioRol = this.ensureRole("ROLE_VETERINARIO", "Veterinario");
        Rol clienteRol = this.ensureRole("ROLE_CLIENTE", "Cliente");
        this.ensureUser("admin", "Administrador", "Sistema", "admin@clinica.local", "admin123", adminRol);
        this.ensureUser("auxiliar", "Laura", "Martin", "auxiliar@clinica.local", "auxiliar123", auxiliarRol);
        Usuario vetUser = this.ensureUser("vet", "Ana", "Martinez", "vet@clinica.local", "vet123", veterinarioRol);
        Usuario clienteUser = this.ensureUser("cliente", "Marta", "Perez", "cliente@clinica.local", "cliente123", clienteRol);
        Cliente cliente = this.ensureCliente(clienteUser);
        Veterinario veterinario = this.ensureVeterinario(vetUser);
        Mascota mascota = this.ensureMascota(cliente, veterinario);
        Cita citaCompletada = this.ensureCitas(mascota, veterinario);
        this.ensureConsultaYPago(cliente, citaCompletada);
    }

    private void allowMultipleRecetasPerConsulta() {
        try {
            for (Map<String, Object> index : this.jdbcTemplate.queryForList("SHOW INDEX FROM recetas WHERE Column_name = 'consulta_id' AND Non_unique = 0")) {
                String keyName = String.valueOf(index.get("Key_name"));
                if (!"PRIMARY".equalsIgnoreCase(keyName) && keyName.matches("[A-Za-z0-9_]+")) {
                    this.jdbcTemplate.execute("ALTER TABLE recetas DROP INDEX " + keyName);
                }
            }
        } catch (Exception ignored) {
        }
    }

    private Rol ensureRole(String nombre, String descripcion) {
        return this.rolRepository.findByNombre(nombre).orElseGet(() -> (Rol)this.rolRepository.save(new Rol(nombre, descripcion)));
    }

    private Usuario ensureUser(String username, String nombre, String apellidos, String email, String password, Rol rol) {
        return this.usuarioRepository.findByUsername(username).map(usuario -> {
            if (usuario.getRol() == null || !usuario.getRol().getNombre().equals(rol.getNombre())) {
                usuario.setRol(rol);
                return (Usuario)this.usuarioRepository.save(usuario);
            }
            return usuario;
        }).orElseGet(() -> {
            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setEmail(email);
            usuario.setPassword(this.passwordEncoder.encode((CharSequence)password));
            usuario.setActivo(true);
            usuario.setRol(rol);
            return (Usuario)this.usuarioRepository.save(usuario);
        });
    }

    private Cliente ensureCliente(Usuario usuario) {
        if (this.clienteRepository.count() > 0L) {
            return (Cliente)this.clienteRepository.findAll().get(0);
        }
        Cliente cliente = new Cliente();
        cliente.setNombre("Marta");
        cliente.setApellidos("Perez Ruiz");
        cliente.setDni("12345678A");
        cliente.setTelefono("612345678");
        cliente.setEmail("marta.perez@example.com");
        cliente.setDireccion("Calle Mayor 12, Madrid");
        cliente.setUsuario(usuario);
        return (Cliente)this.clienteRepository.save(cliente);
    }

    private Veterinario ensureVeterinario(Usuario usuario) {
        if (this.veterinarioRepository.count() > 0L) {
            return (Veterinario)this.veterinarioRepository.findAll().get(0);
        }
        Veterinario veterinario = new Veterinario();
        veterinario.setNombre("Ana");
        veterinario.setApellidos("Martinez");
        veterinario.setDni("45678901D");
        veterinario.setNumeroColegiado("COL12345");
        veterinario.setEspecialidad("Medicina general");
        veterinario.setUsuario(usuario);
        return (Veterinario)this.veterinarioRepository.save(veterinario);
    }

    private Mascota ensureMascota(Cliente cliente, Veterinario veterinario) {
        if (this.mascotaRepository.count() > 0L) {
            return (Mascota)this.mascotaRepository.findAll().get(0);
        }
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");
        mascota.setEspecie("Perro");
        mascota.setRaza("Golden Retriever");
        mascota.setFechaNacimiento(LocalDate.of(2020, 6, 10));
        mascota.setSexo("Hembra");
        mascota.setChip("CHIP001");
        mascota.setActiva(true);
        mascota.setReiacVerificado(true);
        mascota.setCliente(cliente);
        mascota.setVeterinario(veterinario);
        return (Mascota)this.mascotaRepository.save(mascota);
    }

    private Cita ensureCitas(Mascota mascota, Veterinario veterinario) {
        if (this.citaRepository.count() > 0L) {
            return (Cita)this.citaRepository.findAll().get(0);
        }
        Cita citaCompletada = new Cita();
        citaCompletada.setMascota(mascota);
        citaCompletada.setVeterinario(veterinario);
        citaCompletada.setFechaHora(LocalDateTime.now().minusDays(2L));
        citaCompletada.setMotivo("Revision anual");
        citaCompletada.setEstado(EstadoCita.COMPLETADA);
        citaCompletada = (Cita)this.citaRepository.save(citaCompletada);
        Cita citaPendiente = new Cita();
        citaPendiente.setMascota(mascota);
        citaPendiente.setVeterinario(veterinario);
        citaPendiente.setFechaHora(LocalDateTime.now().plusDays(1L));
        citaPendiente.setMotivo("Vacunacion y control");
        citaPendiente.setEstado(EstadoCita.CONFIRMADA);
        this.citaRepository.save(citaPendiente);
        return citaCompletada;
    }

    private void ensureConsultaYPago(Cliente cliente, Cita cita) {
        if (this.consultaRepository.count() == 0L) {
            Consulta consulta = new Consulta();
            consulta.setCita(cita);
            consulta.setSintomas("Cansancio leve y revision preventiva");
            consulta.setDiagnostico("Estado general correcto");
            consulta.setTratamiento("Vacuna anual y desparasitacion");
            consulta.setEstado(EstadoConsulta.CERRADA);
            consulta = (Consulta)this.consultaRepository.save(consulta);
            if (this.pagoRepository.count() == 0L) {
                Pago pago = new Pago();
                pago.setCliente(cliente);
                pago.setConsulta(consulta);
                pago.setImporte(BigDecimal.valueOf(45.0));
                pago.setMetodoPago("TARJETA");
                pago.setEstado(EstadoPago.PENDIENTE);
                this.pagoRepository.save(pago);
            }
        }
    }
}
