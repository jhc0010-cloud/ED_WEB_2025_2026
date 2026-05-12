// Responsable: Jesus Enrique de la Torre Jimenez - G3 Gestion usuarios admin.
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("usuarioForm");
    const body = document.getElementById("usuariosBody");
    const status = document.getElementById("usuariosStatus");
    const rolSelect = document.getElementById("rolId");
    let roles = [];

    init();

    async function init() {
        try {
            roles = await apiGet("/api/admin/roles");
            rolSelect.innerHTML = roles.map((rol) => `<option value="${rol.id}">${escapeHtml(rol.descripcion)}</option>`).join("");
            await loadUsuarios();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    }

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        const id = document.getElementById("usuarioId").value;
        const payload = {
            nombre: document.getElementById("nombre").value.trim(),
            apellidos: document.getElementById("apellidos").value.trim(),
            email: document.getElementById("email").value.trim(),
            username: document.getElementById("username").value.trim(),
            password: document.getElementById("password").value,
            rolId: Number(rolSelect.value)
        };

        try {
            if (id) {
                await apiPut(`/api/admin/usuarios/${id}`, payload);
                showStatus(status, "Usuario actualizado.");
            } else {
                await apiPost("/api/admin/usuarios", payload);
                showStatus(status, "Usuario creado.");
            }
            form.reset();
            document.getElementById("usuarioId").value = "";
            await loadUsuarios();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    });

    body.addEventListener("click", async (event) => {
        const button = event.target.closest("button[data-action]");
        if (!button) return;
        const id = button.dataset.id;
        if (button.dataset.action === "edit") {
            const usuarios = await apiGet("/api/admin/usuarios");
            const usuario = usuarios.find((item) => String(item.id) === id);
            if (!usuario) return;
            document.getElementById("usuarioId").value = usuario.id;
            document.getElementById("nombre").value = usuario.nombre;
            document.getElementById("apellidos").value = usuario.apellidos;
            document.getElementById("email").value = usuario.email;
            document.getElementById("username").value = usuario.username;
            rolSelect.value = usuario.rol?.id || "";
            document.getElementById("password").value = "";
        }
        if (button.dataset.action === "delete") {
            await apiDelete(`/api/admin/usuarios/${id}`);
            showStatus(status, "Usuario eliminado.");
            await loadUsuarios();
        }
    });

    async function loadUsuarios() {
        const usuarios = await apiGet("/api/admin/usuarios");
        body.innerHTML = usuarios.map((usuario) => `
            <tr>
                <td>${escapeHtml(usuario.username)}</td>
                <td>${escapeHtml(usuario.nombre)} ${escapeHtml(usuario.apellidos)}</td>
                <td>${escapeHtml(usuario.email)}</td>
                <td>${escapeHtml(usuario.rol?.descripcion || usuario.rol?.nombre || "")}</td>
                <td class="text-end">
                    <button class="btn btn-outline-success btn-sm" data-action="edit" data-id="${usuario.id}">Editar</button>
                    <button class="btn btn-outline-danger btn-sm" data-action="delete" data-id="${usuario.id}">Eliminar</button>
                </td>
            </tr>
        `).join("");
    }
});
