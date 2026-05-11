// Responsable: David Iglesias Borland - G7 Estado de citas y consultas.
document.addEventListener("DOMContentLoaded", loadEstado);

async function loadEstado() {
    const body = document.getElementById("estadoBody");
    const status = document.getElementById("estadoStatus");
    try {
        const resumen = await apiGet("/api/portal/resumen");
        body.innerHTML = resumen.citas.map((cita) => {
            const consulta = resumen.consultas.find((item) => item.cita?.id === cita.id);
            return `
                <tr>
                    <td>${escapeHtml(formatDate(cita.fechaHora))}</td>
                    <td>${escapeHtml(cita.mascota?.nombre || "")}</td>
                    <td>${escapeHtml(cita.veterinario?.nombre || "")} ${escapeHtml(cita.veterinario?.apellidos || "")}</td>
                    <td>${estadoBadge(cita.estado)}</td>
                    <td>${consulta ? escapeHtml(consulta.diagnostico || consulta.estado) : "Pendiente de consulta"}</td>
                </tr>
            `;
        }).join("");
    } catch (error) {
        showStatus(status, error.message, "danger");
    }
}
