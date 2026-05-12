// Responsable: Theo - G7 Pagos cliente.
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("pagoForm");
    const pagoSelect = document.getElementById("pagoId");
    const metodoSelect = document.getElementById("metodoPago");
    const body = document.getElementById("pagosBody");
    const status = document.getElementById("pagosStatus");
    let pagos = [];

    loadPagos();

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        await pagar(Number(pagoSelect.value));
    });

    body.addEventListener("click", async (event) => {
        const button = event.target.closest("button[data-pago]");
        if (!button) return;
        await pagar(Number(button.dataset.pago));
    });

    async function pagar(id) {
        try {
            await apiPut(`/api/portal/pagos/${id}/pagar?metodoPago=${encodeURIComponent(metodoSelect.value)}`, {});
            showStatus(status, "Pago confirmado correctamente.");
            await loadPagos();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    }

    async function loadPagos() {
        try {
            pagos = await apiGet("/api/portal/pagos");
            const pendientes = pagos.filter((pago) => pago.estado === "PENDIENTE");
            pagoSelect.innerHTML = pendientes.map((pago) => `<option value="${pago.id}">#${pago.id} - ${Number(pago.importe).toFixed(2)} EUR</option>`).join("");
            pagoSelect.disabled = pendientes.length === 0;
            form.querySelector("button[type=submit]").disabled = pendientes.length === 0;
            body.innerHTML = pagos.map((pago) => `
                <tr>
                    <td>${pago.id}</td>
                    <td>${escapeHtml(pago.consulta?.cita?.mascota?.nombre || "Servicio clinico")}</td>
                    <td>${Number(pago.importe).toFixed(2)} EUR</td>
                    <td>${escapeHtml(pago.metodoPago)}</td>
                    <td>${estadoBadge(pago.estado)}</td>
                    <td>${pago.fechaPago ? escapeHtml(formatDate(pago.fechaPago)) : "Pendiente"}</td>
                    <td class="text-end">${pago.estado === "PENDIENTE" ? `<button class="btn btn-success btn-sm" data-pago="${pago.id}">Pagar</button>` : ""}</td>
                </tr>
            `).join("");
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    }
});
