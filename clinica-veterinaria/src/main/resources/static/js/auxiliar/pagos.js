// Responsable: Achraf Akhmoul - G4 Pagos y documentos auxiliar.
document.addEventListener("DOMContentLoaded", () => {
    const pagosBody = document.getElementById("pagosAuxBody");
    const recetasBody = document.getElementById("recetasAuxBody");
    const metodoSelect = document.getElementById("metodoPagoAux");
    const status = document.getElementById("pagosAuxStatus");
    let pagos = [];
    let recetas = [];

    loadAll();

    pagosBody.addEventListener("click", async (event) => {
        const confirmar = event.target.closest("button[data-confirmar-pago]");
        const imprimir = event.target.closest("button[data-imprimir-pago]");
        if (confirmar) {
            await confirmarPago(Number(confirmar.dataset.confirmarPago));
            return;
        }
        if (imprimir) {
            const pago = pagos.find((item) => item.id === Number(imprimir.dataset.imprimirPago));
            if (pago) imprimirRecibo(pago);
        }
    });

    recetasBody.addEventListener("click", (event) => {
        const button = event.target.closest("button[data-imprimir-receta]");
        if (!button) return;
        const receta = recetas.find((item) => item.id === Number(button.dataset.imprimirReceta));
        if (receta) imprimirReceta(receta);
    });

    async function loadAll() {
        try {
            [pagos, recetas] = await Promise.all([
                apiGet("/api/pagos"),
                apiGet("/api/recetas")
            ]);
            renderPagos();
            renderRecetas();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    }

    async function confirmarPago(id) {
        try {
            await apiPut(`/api/pagos/${id}/confirmar?metodoPago=${encodeURIComponent(metodoSelect.value)}`, {});
            showStatus(status, "Pago confirmado correctamente.");
            await loadAll();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    }

    function renderPagos() {
        pagosBody.innerHTML = pagos.map((pago) => `
            <tr>
                <td>${pago.id}</td>
                <td>${clienteNombre(pago.cliente)}</td>
                <td>${conceptoPago(pago)}</td>
                <td>${Number(pago.importe).toFixed(2)} EUR</td>
                <td>${escapeHtml(pago.metodoPago || "Pendiente")}</td>
                <td>${estadoBadge(pago.estado)}</td>
                <td class="text-end">
                    ${pago.estado === "PENDIENTE" ? `<button class="btn btn-success btn-sm me-1" data-confirmar-pago="${pago.id}">Confirmar</button>` : ""}
                    <button class="btn btn-outline-success btn-sm" data-imprimir-pago="${pago.id}">Recibo</button>
                </td>
            </tr>
        `).join("") || `<tr><td colspan="7" class="text-center small-muted py-4">No hay pagos registrados.</td></tr>`;
    }

    function renderRecetas() {
        recetasBody.innerHTML = recetas.map((receta) => `
            <tr>
                <td>${receta.id}</td>
                <td>${escapeHtml(receta.consulta?.cita?.mascota?.nombre || "")}</td>
                <td>${clienteNombre(receta.consulta?.cita?.mascota?.cliente)}</td>
                <td>${escapeHtml(receta.medicamento)}</td>
                <td>${escapeHtml(receta.dosis || "")}</td>
                <td class="text-end"><button class="btn btn-outline-success btn-sm" data-imprimir-receta="${receta.id}">Imprimir</button></td>
            </tr>
        `).join("") || `<tr><td colspan="6" class="text-center small-muted py-4">No hay recetas emitidas.</td></tr>`;
    }

    function imprimirRecibo(pago) {
        const consulta = pago.consulta;
        printDocument("Recibo de pago", `
            <h1>Recibo de pago #${escapeHtml(pago.id)}</h1>
            <p><strong>Cliente:</strong> ${clienteNombre(pago.cliente)}</p>
            <p><strong>Concepto:</strong> ${conceptoPago(pago)}</p>
            <p><strong>Importe:</strong> ${Number(pago.importe).toFixed(2)} EUR</p>
            <p><strong>Metodo:</strong> ${escapeHtml(pago.metodoPago || "Pendiente")}</p>
            <p><strong>Estado:</strong> ${escapeHtml(pago.estado)}</p>
            <p><strong>Fecha:</strong> ${escapeHtml(pago.fechaPago ? formatDate(pago.fechaPago) : "Pendiente")}</p>
            <p><strong>Consulta:</strong> ${escapeHtml(consulta?.id || "")}</p>
        `);
    }

    function imprimirReceta(receta) {
        const cita = receta.consulta?.cita;
        printDocument("Receta veterinaria", `
            <h1>Receta veterinaria #${escapeHtml(receta.id)}</h1>
            <p><strong>Mascota:</strong> ${escapeHtml(cita?.mascota?.nombre || "")}</p>
            <p><strong>Cliente:</strong> ${clienteNombre(cita?.mascota?.cliente)}</p>
            <p><strong>Veterinario:</strong> ${escapeHtml(cita?.veterinario?.nombre || "")} ${escapeHtml(cita?.veterinario?.apellidos || "")}</p>
            <p><strong>Fecha consulta:</strong> ${escapeHtml(formatDate(cita?.fechaHora))}</p>
            <hr>
            <p><strong>Medicamento:</strong> ${escapeHtml(receta.medicamento)}</p>
            <p><strong>Dosis:</strong> ${escapeHtml(receta.dosis || "")}</p>
            <p><strong>Indicaciones:</strong> ${escapeHtml(receta.indicaciones || "")}</p>
        `);
    }
});

function clienteNombre(cliente) {
    return `${escapeHtml(cliente?.nombre || "")} ${escapeHtml(cliente?.apellidos || "")}`.trim() || "Cliente";
}

function conceptoPago(pago) {
    const mascota = pago.consulta?.cita?.mascota?.nombre;
    return escapeHtml(mascota ? `Consulta de ${mascota}` : "Servicio clinico");
}

function printDocument(title, content) {
    const win = window.open("", "_blank", "width=800,height=900");
    if (!win) return;
    win.document.write(`
        <!doctype html>
        <html lang="es">
        <head>
            <meta charset="utf-8">
            <title>${escapeHtml(title)}</title>
            <style>
                body { font-family: Arial, sans-serif; color: #1f2933; padding: 32px; line-height: 1.45; }
                h1 { color: #176a4a; font-size: 28px; margin-bottom: 24px; }
                p { margin: 8px 0; }
                hr { border: 0; border-top: 1px solid #d9e2de; margin: 24px 0; }
            </style>
        </head>
        <body>${content}</body>
        </html>
    `);
    win.document.close();
    win.focus();
    win.print();
}
