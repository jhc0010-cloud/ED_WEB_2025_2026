// Responsable: German Galvez Aranda - G4 Agenda y confirmacion de citas.
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("citaForm");
    const body = document.getElementById("citasBody");
    const status = document.getElementById("citasStatus");
    const mascotaSelect = document.getElementById("mascotaId");
    const veterinarioSelect = document.getElementById("veterinarioId");
    let citasActuales = [];

    init();

    async function init() {
        try {
            const [mascotas, veterinarios] = await Promise.all([
                apiGet("/api/mascotas"),
                apiGet("/api/veterinarios")
            ]);
            mascotaSelect.innerHTML = mascotas
                .filter((mascota) => mascota.activa)
                .map((mascota) => `<option value="${mascota.id}">${escapeHtml(mascota.nombre)} (${escapeHtml(mascota.cliente?.nombre || "")})</option>`)
                .join("");
            veterinarioSelect.innerHTML = veterinarios.map((veterinario) => `<option value="${veterinario.id}">${escapeHtml(veterinario.nombre)} ${escapeHtml(veterinario.apellidos)}</option>`).join("");
            await loadCitas();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    }

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        const payload = {
            mascotaId: Number(mascotaSelect.value),
            veterinarioId: Number(veterinarioSelect.value),
            fechaHora: document.getElementById("fechaHora").value,
            motivo: document.getElementById("motivo").value.trim()
        };
        try {
            await apiPost("/api/citas", payload);
            showStatus(status, "Cita creada.");
            form.reset();
            await loadCitas();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    });

    body.addEventListener("click", async (event) => {
        const button = event.target.closest("button[data-estado]");
        const printButton = event.target.closest("button[data-imprimir-cita]");
        if (printButton) {
            const cita = citasActuales.find((item) => item.id === Number(printButton.dataset.imprimirCita));
            if (cita) imprimirCita(cita);
            return;
        }
        if (!button) return;
        try {
            await apiPut(`/api/citas/${button.dataset.id}/estado?estado=${button.dataset.estado}`, {});
            showStatus(status, `Cita marcada como ${button.dataset.estado}.`);
            await loadCitas();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    });

    async function loadCitas() {
        citasActuales = await apiGet("/api/citas");
        body.innerHTML = citasActuales.map((cita) => `
            <tr>
                <td>${escapeHtml(formatDate(cita.fechaHora))}</td>
                <td>${escapeHtml(cita.mascota?.nombre || "")}</td>
                <td>${escapeHtml(cita.veterinario?.nombre || "")} ${escapeHtml(cita.veterinario?.apellidos || "")}</td>
                <td>${escapeHtml(cita.motivo || "")}</td>
                <td>${estadoBadge(cita.estado)}</td>
                <td class="text-end">
                    ${cita.estado === "CONFIRMADA" ? `<button class="btn btn-outline-secondary btn-sm" data-id="${cita.id}" data-estado="CANCELADA">Cancelar</button>` : ""}
                    ${cita.estado === "PENDIENTE" ? `<button class="btn btn-success btn-sm" data-id="${cita.id}" data-estado="CONFIRMADA">Confirmar</button>` : ""}
                    <button class="btn btn-outline-success btn-sm" data-imprimir-cita="${cita.id}">Imprimir</button>
                </td>
            </tr>
        `).join("");
    }

    function imprimirCita(cita) {
        const win = window.open("", "_blank", "width=800,height=900");
        if (!win) return;
        win.document.write(`
            <!doctype html>
            <html lang="es">
            <head>
                <meta charset="utf-8">
                <title>Cita ${escapeHtml(cita.id)}</title>
                <style>
                    body { font-family: Arial, sans-serif; color: #1f2933; padding: 32px; line-height: 1.45; }
                    h1 { color: #176a4a; font-size: 28px; margin-bottom: 24px; }
                    p { margin: 8px 0; }
                </style>
            </head>
            <body>
                <h1>Justificante de cita #${escapeHtml(cita.id)}</h1>
                <p><strong>Fecha:</strong> ${escapeHtml(formatDate(cita.fechaHora))}</p>
                <p><strong>Mascota:</strong> ${escapeHtml(cita.mascota?.nombre || "")}</p>
                <p><strong>Cliente:</strong> ${escapeHtml(cita.mascota?.cliente?.nombre || "")} ${escapeHtml(cita.mascota?.cliente?.apellidos || "")}</p>
                <p><strong>Veterinario:</strong> ${escapeHtml(cita.veterinario?.nombre || "")} ${escapeHtml(cita.veterinario?.apellidos || "")}</p>
                <p><strong>Motivo:</strong> ${escapeHtml(cita.motivo || "")}</p>
                <p><strong>Estado:</strong> ${escapeHtml(cita.estado || "")}</p>
            </body>
            </html>
        `);
        win.document.close();
        win.focus();
        win.print();
    }
});
