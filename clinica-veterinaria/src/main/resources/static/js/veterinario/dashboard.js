// Responsable: Francisco Fernandez - G5 Dashboard veterinario.
document.addEventListener("DOMContentLoaded", async () => {
    const strip = document.querySelector(".metric-strip");
    if (!strip) return;
    try {
        const [espera, consultas, recetas] = await Promise.all([
            apiGet("/api/citas/espera/me"),
            apiGet("/api/consultas"),
            apiGet("/api/recetas")
        ]);
        renderMetrics(strip, [
            [espera.length, "Pacientes en espera"],
            [consultas.length, "Consultas registradas"],
            [recetas.length, "Recetas emitidas"]
        ]);
    } catch (error) {
        renderMetrics(strip, [["Error", error.message]]);
    }
});

function renderMetrics(container, metrics) {
    container.innerHTML = metrics.map(([value, label]) => `<div class="metric"><strong>${escapeHtml(value)}</strong><span>${escapeHtml(label)}</span></div>`).join("");
}
