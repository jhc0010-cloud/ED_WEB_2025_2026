// Responsable: David Martin Marti - G3 Perfiles y roles admin.
document.addEventListener("DOMContentLoaded", async () => {
    const body = document.getElementById("rolesBody");
    if (!body) return;
    try {
        const roles = await apiGet("/api/admin/roles");
        body.innerHTML = roles.map((rol) => `
            <tr>
                <td>${escapeHtml(rol.nombre)}</td>
                <td>${escapeHtml(rol.descripcion)}</td>
            </tr>
        `).join("");
    } catch (error) {
        body.innerHTML = `<tr><td colspan="2" class="text-danger">${escapeHtml(error.message)}</td></tr>`;
    }
});
