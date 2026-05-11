// Responsable: Blas Vilar Martin - G4 Gestion de clientes.
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("clienteForm");
    const body = document.getElementById("clientesBody");
    const status = document.getElementById("clientesStatus");
    const buscar = document.getElementById("buscarCliente");

    loadClientes();

    document.getElementById("btnBuscar").addEventListener("click", () => loadClientes(buscar.value));
    document.getElementById("btnLimpiar").addEventListener("click", () => {
        buscar.value = "";
        loadClientes();
    });

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        const id = document.getElementById("clienteId").value;
        const payload = getPayload();

        try {
            if (id) {
                await apiPut(`/api/clientes/${id}`, payload);
                showStatus(status, "Cliente actualizado.");
            } else {
                await apiPost("/api/clientes", payload);
                showStatus(status, "Cliente creado.");
            }
            form.reset();
            document.getElementById("clienteId").value = "";
            await loadClientes();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    });

    body.addEventListener("click", async (event) => {
        const button = event.target.closest("button[data-action]");
        if (!button) return;
        const id = button.dataset.id;
        if (button.dataset.action === "edit") {
            const cliente = await apiGet(`/api/clientes/${id}`);
            document.getElementById("clienteId").value = cliente.id;
            document.getElementById("nombre").value = cliente.nombre;
            document.getElementById("apellidos").value = cliente.apellidos;
            document.getElementById("dni").value = cliente.dni;
            document.getElementById("telefono").value = cliente.telefono;
            document.getElementById("email").value = cliente.email || "";
            document.getElementById("direccion").value = cliente.direccion || "";
        }
        if (button.dataset.action === "delete") {
            await apiDelete(`/api/clientes/${id}`);
            showStatus(status, "Cliente eliminado.");
            await loadClientes();
        }
    });

    function getPayload() {
        return {
            nombre: document.getElementById("nombre").value.trim(),
            apellidos: document.getElementById("apellidos").value.trim(),
            dni: document.getElementById("dni").value.trim(),
            telefono: document.getElementById("telefono").value.trim(),
            email: document.getElementById("email").value.trim(),
            direccion: document.getElementById("direccion").value.trim(),
            usuarioId: null
        };
    }

    async function loadClientes(query = "") {
        try {
            const url = query ? `/api/clientes?q=${encodeURIComponent(query)}` : "/api/clientes";
            const clientes = await apiGet(url);
            body.innerHTML = clientes.map((cliente) => `
                <tr>
                    <td>${escapeHtml(cliente.nombre)} ${escapeHtml(cliente.apellidos)}</td>
                    <td>${escapeHtml(cliente.dni)}</td>
                    <td>${escapeHtml(cliente.telefono)}</td>
                    <td>${escapeHtml(cliente.email || "")}</td>
                    <td class="text-end">
                        <button class="btn btn-outline-success btn-sm" data-action="edit" data-id="${cliente.id}">Editar</button>
                        <button class="btn btn-outline-danger btn-sm" data-action="delete" data-id="${cliente.id}">Baja</button>
                    </td>
                </tr>
            `).join("");
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    }
});
