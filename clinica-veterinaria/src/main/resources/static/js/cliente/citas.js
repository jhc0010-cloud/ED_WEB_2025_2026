// Responsable: Theo - G7 Solicitud de citas del cliente.
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("citaClienteForm");
    const mascotaSelect = document.getElementById("mascotaIdCliente");
    const veterinarioSelect = document.getElementById("veterinarioIdCliente");
    const slotSelect = document.getElementById("slotCliente");
    const submitButton = document.getElementById("btnSolicitarCita");
    const body = document.getElementById("citasClienteBody");
    const status = document.getElementById("citasClienteStatus");

    init();

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        const payload = {
            mascotaId: Number(mascotaSelect.value),
            veterinarioId: Number(veterinarioSelect.value),
            fechaHora: slotSelect.value,
            motivo: document.getElementById("motivoCliente").value.trim()
        };
        try {
            await apiPost("/api/portal/citas", payload);
            showStatus(status, "Solicitud enviada. Un auxiliar confirmara la cita.");
            form.reset();
            await init();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    });

    veterinarioSelect.addEventListener("change", loadDisponibles);

    async function init() {
        try {
            const [mascotas, veterinarios] = await Promise.all([
                apiGet("/api/portal/mascotas"),
                apiGet("/api/veterinarios")
            ]);
            if (!mascotas.length) {
                mascotaSelect.innerHTML = `<option value="">Sin mascotas registradas</option>`;
                veterinarioSelect.innerHTML = veterinarios.map((veterinario) => `<option value="${veterinario.id}">${escapeHtml(veterinario.nombre)} ${escapeHtml(veterinario.apellidos)} - ${escapeHtml(veterinario.especialidad)}</option>`).join("");
                mascotaSelect.disabled = true;
                renderNoSlots("Necesitas una mascota registrada");
                showStatus(status, "Todavia no tienes mascotas registradas. Contacta con la clinica para dar de alta una mascota.", "warning");
                await loadCitas();
                return;
            }
            mascotaSelect.disabled = false;
            mascotaSelect.innerHTML = mascotas.map((mascota) => `<option value="${mascota.id}">${escapeHtml(mascota.nombre)} (${escapeHtml(mascota.especie)})</option>`).join("");
            veterinarioSelect.innerHTML = veterinarios.map((veterinario) => `<option value="${veterinario.id}">${escapeHtml(veterinario.nombre)} ${escapeHtml(veterinario.apellidos)} - ${escapeHtml(veterinario.especialidad)}</option>`).join("");
            await loadDisponibles();
            await loadCitas();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    }

    async function loadDisponibles() {
        if (!veterinarioSelect.value) {
            renderNoSlots("Selecciona un veterinario");
            return;
        }
        try {
            const slots = await apiGet(`/api/portal/citas/disponibles?veterinarioId=${encodeURIComponent(veterinarioSelect.value)}`);
            if (!slots.length) {
                renderNoSlots("No hay huecos disponibles");
                return;
            }
            slotSelect.disabled = false;
            submitButton.disabled = false;
            slotSelect.innerHTML = slots.map((slot) => `<option value="${escapeHtml(slot.fechaHora)}">${escapeHtml(formatDate(slot.fechaHora))}</option>`).join("");
        } catch (error) {
            renderNoSlots("No se pudieron cargar huecos");
            showStatus(status, error.message, "danger");
        }
    }

    function renderNoSlots(message) {
        slotSelect.disabled = true;
        submitButton.disabled = true;
        slotSelect.innerHTML = `<option value="">${escapeHtml(message)}</option>`;
    }

    async function loadCitas() {
        const citas = await apiGet("/api/portal/citas");
        body.innerHTML = citas.map((cita) => `
            <tr>
                <td>${escapeHtml(formatDate(cita.fechaHora))}</td>
                <td>${escapeHtml(cita.mascota?.nombre || "")}</td>
                <td>${escapeHtml(cita.veterinario?.nombre || "")} ${escapeHtml(cita.veterinario?.apellidos || "")}</td>
                <td>${escapeHtml(cita.motivo || "")}</td>
                <td>${estadoBadge(cita.estado)}</td>
            </tr>
        `).join("");
    }
});
