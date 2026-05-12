// Responsable: Carlos Posadas - G6 Verificacion REIAC.
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("reiacForm");
    const body = document.getElementById("reiacBody");
    const status = document.getElementById("reiacStatus");

    loadHistorico();

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        const chip = document.getElementById("chip").value.trim();
        try {
            const result = await apiRequest(`/api/reiac/verificar?chip=${encodeURIComponent(chip)}`, { method: "POST" });
            showStatus(status, `Resultado REIAC: ${result.resultado}`);
            form.reset();
            await loadHistorico();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    });

    async function loadHistorico() {
        const items = await apiGet("/api/reiac");
        body.innerHTML = items.map((item) => `
            <tr>
                <td>${escapeHtml(item.codigoMicrochip)}</td>
                <td><span class="badge ${item.resultado === "VALIDO" ? "text-bg-success" : "text-bg-warning"}">${escapeHtml(item.resultado)}</span></td>
                <td>${escapeHtml(new Date(item.fechaConsulta).toLocaleString("es-ES"))}</td>
            </tr>
        `).join("");
    }
});
