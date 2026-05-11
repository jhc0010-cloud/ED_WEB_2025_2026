// Responsable: Gonzalo Viedma - G4 Dashboard auxiliar.
document.addEventListener("DOMContentLoaded", async () => {
    const strip = document.querySelector(".metric-strip");
    if (!strip) return;
    try {
        const [clientes, mascotas, citas, pagos] = await Promise.all([
            apiGet("/api/clientes"),
            apiGet("/api/mascotas"),
            apiGet("/api/citas"),
            apiGet("/api/pagos")
        ]);
        renderMetrics(strip, [
            [clientes.length, "Clientes activos"],
            [mascotas.filter((m) => m.activa !== false).length, "Mascotas en seguimiento"],
            [citas.filter((c) => c.estado === "CONFIRMADA").length, "Citas confirmadas"],
            [pagos.filter((p) => p.estado === "PENDIENTE").length, "Pagos pendientes"]
        ]);
    } catch (error) {
        renderMetrics(strip, [["Error", error.message]]);
    }
});

function renderMetrics(container, metrics) {
    container.innerHTML = metrics.map(([value, label]) => `<div class="metric"><strong>${escapeHtml(value)}</strong><span>${escapeHtml(label)}</span></div>`).join("");
}
