// Responsable: Jorge Garcia Moreno - G5 Lista de espera veterinaria.
document.addEventListener("DOMContentLoaded", loadLista);

async function loadLista() {
    const body = document.getElementById("listaEsperaBody");
    const status = document.getElementById("listaStatus");
    try {
        const citas = await apiGet("/api/citas/espera/me");
        body.innerHTML = citas.map((cita) => `
            <tr>
                <td>${escapeHtml(formatDate(cita.fechaHora))}</td>
                <td>${escapeHtml(cita.mascota?.nombre || "")}</td>
                <td>${escapeHtml(cita.mascota?.cliente?.nombre || "")} ${escapeHtml(cita.mascota?.cliente?.apellidos || "")}</td>
                <td>${escapeHtml(cita.motivo || "")}</td>
                <td>${estadoBadge(cita.estado)}</td>
                <td class="text-end"><a class="btn btn-success btn-sm" href="/veterinario/consulta-form.html?cita=${cita.id}">Atender</a></td>
            </tr>
        `).join("") || `<tr><td colspan="6" class="text-center small-muted py-4">No hay citas confirmadas en espera.</td></tr>`;
    } catch (error) {
        showStatus(status, error.message, "danger");
    }
}
