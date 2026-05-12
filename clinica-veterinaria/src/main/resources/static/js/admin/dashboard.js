// Responsable: Jesus Enrique de la Torre Jimenez - G3 Dashboard admin.
document.addEventListener("DOMContentLoaded", async () => {
    const strip = document.querySelector(".metric-strip");
    if (!strip) return;
    try {
        const [usuarios, roles] = await Promise.all([
            apiGet("/api/admin/usuarios"),
            apiGet("/api/admin/roles")
        ]);
        renderMetrics(strip, [
            [usuarios.length, "Usuarios registrados"],
            [roles.length, "Roles configurados"],
            [usuarios.filter((u) => u.activo !== false).length, "Cuentas activas"]
        ]);
    } catch (error) {
        renderMetrics(strip, [["Error", error.message]]);
    }
});

function renderMetrics(container, metrics) {
    container.innerHTML = metrics.map(([value, label]) => `<div class="metric"><strong>${escapeHtml(value)}</strong><span>${escapeHtml(label)}</span></div>`).join("");
}
