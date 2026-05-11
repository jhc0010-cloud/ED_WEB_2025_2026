// Responsable: David Iglesias Borland - G7 Dashboard cliente.
document.addEventListener("DOMContentLoaded", async () => {
    const strip = document.querySelector(".metric-strip");
    if (!strip) return;
    try {
        const resumen = await apiGet("/api/portal/resumen");
        renderMetrics(strip, [
            [resumen.citas.filter((c) => c.estado !== "CANCELADA").length, "Citas e historial"],
            [resumen.consultas.length, "Consultas cerradas"],
            [resumen.pagos.filter((p) => p.estado === "PENDIENTE").length, "Pagos pendientes"]
        ]);
    } catch (error) {
        renderMetrics(strip, [["Error", error.message]]);
    }
});

function renderMetrics(container, metrics) {
    container.innerHTML = metrics.map(([value, label]) => `<div class="metric"><strong>${escapeHtml(value)}</strong><span>${escapeHtml(label)}</span></div>`).join("");
}
