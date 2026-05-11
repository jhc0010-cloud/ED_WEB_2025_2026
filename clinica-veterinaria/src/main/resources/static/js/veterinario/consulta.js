// Responsable: Jonathan Carvajal Reynaga - G5 Registro de consulta.
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("consultaForm");
    const citaSelect = document.getElementById("citaId");
    const status = document.getElementById("consultaStatus");

    loadCitas();

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        const payload = {
            citaId: Number(citaSelect.value),
            sintomas: document.getElementById("sintomas").value.trim(),
            diagnostico: document.getElementById("diagnostico").value.trim(),
            tratamiento: document.getElementById("tratamiento").value.trim()
        };
        try {
            const consulta = await apiPost("/api/consultas", payload);
            showStatus(status, `Consulta guardada con ID ${consulta.id}. Se ha generado un pago pendiente.`);
            form.reset();
            await loadCitas();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    });

    async function loadCitas() {
        try {
            const citas = await apiGet("/api/citas/espera/me");
            const selected = new URLSearchParams(location.search).get("cita");
            citaSelect.innerHTML = citas.map((cita) => `<option value="${cita.id}">${formatDate(cita.fechaHora)} - ${escapeHtml(cita.mascota?.nombre || "")} (${escapeHtml(cita.motivo || "")})</option>`).join("");
            if (selected) citaSelect.value = selected;
            citaSelect.disabled = citas.length === 0;
            form.querySelector("button[type=submit]").disabled = citas.length === 0;
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    }
});
