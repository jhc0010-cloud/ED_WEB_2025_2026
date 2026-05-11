// Responsable: Francisco Fernandez - G5 Recetas y medicamentos.
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("recetaForm");
    const status = document.getElementById("recetaStatus");
    const body = document.getElementById("recetasBody");
    const consultaSelect = document.getElementById("consultaId");
    const pendingBody = document.getElementById("medicamentosPendientesBody");
    const addButton = document.getElementById("btnAgregarMedicamento");
    const medicamentoInput = document.getElementById("medicamento");
    const dosisInput = document.getElementById("dosis");
    const indicacionesInput = document.getElementById("indicaciones");
    let medicamentosPendientes = [];

    init();

    addButton.addEventListener("click", () => {
        addMedicamentoFromInputs();
    });

    pendingBody.addEventListener("click", (event) => {
        const button = event.target.closest("button[data-remove-index]");
        if (!button) return;
        medicamentosPendientes.splice(Number(button.dataset.removeIndex), 1);
        renderPendientes();
    });

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        if (!medicamentosPendientes.length && medicamentoInput.value.trim()) {
            addMedicamentoFromInputs();
        }
        if (!medicamentosPendientes.length) {
            showStatus(status, "Anade al menos un medicamento.", "warning");
            return;
        }
        try {
            const consultaId = Number(consultaSelect.value);
            await Promise.all(medicamentosPendientes.map((medicamento) => apiPost("/api/recetas", {
                consultaId,
                medicamento: medicamento.medicamento,
                dosis: medicamento.dosis,
                indicaciones: medicamento.indicaciones
            })));
            showStatus(status, "Receta guardada con todos los medicamentos.");
            medicamentosPendientes = [];
            form.reset();
            renderPendientes();
            await init();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    });

    async function init() {
        const consultas = await apiGet("/api/consultas");
        consultaSelect.innerHTML = consultas.map((consulta) => `<option value="${consulta.id}">#${consulta.id} - ${escapeHtml(consulta.cita?.mascota?.nombre || "")} - ${escapeHtml(formatDate(consulta.cita?.fechaHora))}</option>`).join("");
        consultaSelect.disabled = consultas.length === 0;
        form.querySelector("button[type=submit]").disabled = consultas.length === 0;
        addButton.disabled = consultas.length === 0;
        renderPendientes();
        await loadRecetas();
    }

    function addMedicamentoFromInputs() {
        const medicamento = medicamentoInput.value.trim();
        const dosis = dosisInput.value.trim();
        const indicaciones = indicacionesInput.value.trim();
        if (!medicamento || !dosis) {
            showStatus(status, "Medicamento y dosis son obligatorios.", "warning");
            return;
        }
        medicamentosPendientes.push({ medicamento, dosis, indicaciones });
        medicamentoInput.value = "";
        dosisInput.value = "";
        indicacionesInput.value = "";
        medicamentoInput.focus();
        renderPendientes();
    }

    function renderPendientes() {
        pendingBody.innerHTML = medicamentosPendientes.map((item, index) => `
            <tr>
                <td>${escapeHtml(item.medicamento)}</td>
                <td>${escapeHtml(item.dosis)}</td>
                <td>${escapeHtml(item.indicaciones || "")}</td>
                <td class="text-end"><button class="btn btn-outline-secondary btn-sm" data-remove-index="${index}">Quitar</button></td>
            </tr>
        `).join("") || `<tr><td colspan="4" class="text-center small-muted py-4">Todavia no hay medicamentos en esta receta.</td></tr>`;
    }

    async function loadRecetas() {
        try {
            const recetas = await apiGet("/api/recetas");
            body.innerHTML = recetas.map((receta) => `
                <tr>
                    <td>${receta.id}</td>
                    <td>#${receta.consulta?.id || ""} - ${escapeHtml(receta.consulta?.cita?.mascota?.nombre || "")}</td>
                    <td>${escapeHtml(receta.medicamento)}</td>
                    <td>${escapeHtml(receta.dosis || "")}</td>
                    <td>${escapeHtml(receta.indicaciones || "")}</td>
                </tr>
            `).join("") || `<tr><td colspan="5" class="text-center small-muted py-4">No hay recetas emitidas.</td></tr>`;
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    }
});
