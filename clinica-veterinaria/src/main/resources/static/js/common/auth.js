// Responsable: Jesus Enrique de la Torre Jimenez - G3 Registro y autenticacion frontend.
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("registerForm");
    if (!form) return;

    const status = document.getElementById("mensajeRegistro");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        const body = {
            nombre: document.getElementById("nombre").value.trim(),
            apellidos: document.getElementById("apellidos").value.trim(),
            dni: document.getElementById("dni").value.trim(),
            telefono: document.getElementById("telefono").value.trim(),
            direccion: document.getElementById("direccion").value.trim(),
            email: document.getElementById("email").value.trim(),
            username: document.getElementById("usuario").value.trim(),
            password: document.getElementById("clave").value
        };

        try {
            await apiPost("/api/auth/register", body);
            showStatus(status, "Registro correcto. Ya puedes iniciar sesion.", "success");
            form.reset();
        } catch (error) {
            showStatus(status, error.message, "danger");
        }
    });
});
