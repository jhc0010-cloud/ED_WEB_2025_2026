// Responsable: Adrian Barbos - G6 Gestion de mascotas.
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("mascotaForm");
    const body = document.getElementById("mascotasBody");
    const status = document.getElementById("mascotasStatus");
    const clienteSelect = document.getElementById("clienteId");
    const veterinarioSelect = document.getElementById("veterinarioId");

    init();

    async function init() {
        try {
            const [clientes, veterinarios] = await Promise.all([
                apiGet("/api/clientes"),
                apiGet("/api/veterinarios")
            ]);
            clienteSelect.innerHTML = clientes.map((cliente) => `<option value="${cliente.id}">${escapeHtml(cliente.nombre)} ${escapeHtml(cliente.apellidos)}</option>`).join("");
            veterinarioSelect.innerHTML = `<option value="">Sin asignar</option>` + veterinarios.map((veterinario) => `<option value="${veterinario.id}">${escapeHtml(veterinario.nombre)} ${escapeHtml(veterinario.apellidos)}</option>`).join("");
            await loadMascotas();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    }

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        const id = document.getElementById("mascotaId").value;
        const payload = getPayload();
        try {
            if (id) {
                await apiPut(`/api/mascotas/${id}`, payload);
                showStatus(status, "Mascota actualizada.");
            } else {
                await apiPost("/api/mascotas", payload);
                showStatus(status, "Mascota creada.");
            }
            form.reset();
            document.getElementById("mascotaId").value = "";
            await init();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    });

    body.addEventListener("click", async (event) => {
        const button = event.target.closest("button[data-action]");
        if (!button) return;
        const id = button.dataset.id;
        const mascotas = await apiGet("/api/mascotas");
        const mascota = mascotas.find((item) => String(item.id) === id);
        if (button.dataset.action === "edit" && mascota) {
            document.getElementById("mascotaId").value = mascota.id;
            document.getElementById("nombreMascota").value = mascota.nombre;
            document.getElementById("especie").value = mascota.especie;
            document.getElementById("raza").value = mascota.raza || "";
            document.getElementById("sexo").value = mascota.sexo || "";
            document.getElementById("chip").value = mascota.chip || "";
            document.getElementById("fechaNacimiento").value = mascota.fechaNacimiento || "";
            clienteSelect.value = mascota.cliente?.id || "";
            veterinarioSelect.value = mascota.veterinario?.id || "";
        }
        if (button.dataset.action === "delete") {
            await apiDelete(`/api/mascotas/${id}`);
            showStatus(status, "Mascota dada de baja.");
            await loadMascotas();
        }
    });

    function getPayload() {
        return {
            nombre: document.getElementById("nombreMascota").value.trim(),
            especie: document.getElementById("especie").value.trim(),
            raza: document.getElementById("raza").value.trim(),
            fechaNacimiento: document.getElementById("fechaNacimiento").value || null,
            sexo: document.getElementById("sexo").value.trim(),
            chip: document.getElementById("chip").value.trim(),
            clienteId: Number(clienteSelect.value),
            veterinarioId: veterinarioSelect.value ? Number(veterinarioSelect.value) : null
        };
    }

    async function loadMascotas() {
        const mascotas = await apiGet("/api/mascotas");
        body.innerHTML = mascotas.map((mascota) => `
            <tr>
                <td>${escapeHtml(mascota.nombre)}<div class="small-muted">${escapeHtml(mascota.especie)} ${escapeHtml(mascota.raza || "")}</div></td>
                <td>${escapeHtml(mascota.cliente?.nombre || "")} ${escapeHtml(mascota.cliente?.apellidos || "")}</td>
                <td>${escapeHtml(mascota.veterinario?.nombre || "Sin asignar")}</td>
                <td><span class="badge ${mascota.reiacVerificado ? "text-bg-success" : "text-bg-warning"}">${mascota.reiacVerificado ? "Verificado" : "Pendiente"}</span></td>
                <td>${mascota.activa ? "Activa" : "Baja"}</td>
                <td class="text-end">
                    <button class="btn btn-outline-success btn-sm" data-action="edit" data-id="${mascota.id}">Editar</button>
                    <button class="btn btn-outline-danger btn-sm" data-action="delete" data-id="${mascota.id}">Baja</button>
                </td>
            </tr>
        `).join("");
    }
});
