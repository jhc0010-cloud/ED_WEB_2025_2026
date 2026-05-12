// Responsable: Theo - G7 Historial y recetas del cliente.
document.addEventListener("DOMContentLoaded", loadHistorial);

async function loadHistorial() {
    const body = document.getElementById("historialBody");
    const status = document.getElementById("historialStatus");
    try {
        const [consultas, recetas] = await Promise.all([
            apiGet("/api/portal/consultas"),
            apiGet("/api/portal/recetas")
        ]);
        body.innerHTML = consultas.map((consulta) => `
            <article class="col-md-6">
                <div class="panel-card h-100">
                    <h2 class="h5">${escapeHtml(consulta.cita?.mascota?.nombre || "Consulta")}</h2>
                    <p class="small-muted mb-1">${escapeHtml(formatDate(consulta.cita?.fechaHora))}</p>
                    <p><strong>Sintomas:</strong> ${escapeHtml(consulta.sintomas || "")}</p>
                    <p><strong>Diagnostico:</strong> ${escapeHtml(consulta.diagnostico || "")}</p>
                    <p><strong>Tratamiento:</strong> ${escapeHtml(consulta.tratamiento || "")}</p>
                    ${renderRecetas(recetas.filter((receta) => receta.consulta?.id === consulta.id))}
                </div>
            </article>
        `).join("") || `<div class="col-12"><div class="panel-card">Todavia no hay consultas cerradas.</div></div>`;
    } catch (error) {
        showStatus(status, error.message, "danger");
    }
}

function renderRecetas(recetas) {
    if (!recetas.length) {
        return `<p class="mb-0"><strong>Recetas:</strong> Sin recetas emitidas.</p>`;
    }
    return `
        <div class="mt-3">
            <strong>Recetas:</strong>
            <div class="table-responsive mt-2">
                <table class="table table-sm mb-0">
                    <thead><tr><th>Medicamento</th><th>Dosis</th><th>Indicaciones</th></tr></thead>
                    <tbody>
                        ${recetas.map((receta) => `
                            <tr>
                                <td>${escapeHtml(receta.medicamento)}</td>
                                <td>${escapeHtml(receta.dosis || "")}</td>
                                <td>${escapeHtml(receta.indicaciones || "")}</td>
                            </tr>
                        `).join("")}
                    </tbody>
                </table>
            </div>
        </div>
    `;
}
