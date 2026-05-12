// Responsable: David Martin Marti - G3 Navegacion comun por rol.
const ROLE_NAV = {
    admin: [
        ["/admin/dashboard.html", "Dashboard", "IN"],
        ["/admin/usuarios.html", "Usuarios", "US"],
        ["/admin/perfiles.html", "Perfiles", "RO"]
    ],
    auxiliar: [
        ["/auxiliar/dashboard.html", "Dashboard", "IN"],
        ["/auxiliar/clientes.html", "Clientes", "CL"],
        ["/auxiliar/mascotas.html", "Mascotas", "MA"],
        ["/auxiliar/citas.html", "Citas", "CI"],
        ["/auxiliar/pagos.html", "Pagos", "PA"],
        ["/auxiliar/reiac.html", "REIAC", "RE"]
    ],
    veterinario: [
        ["/veterinario/dashboard.html", "Dashboard", "IN"],
        ["/veterinario/lista-espera.html", "Lista de espera", "LE"],
        ["/veterinario/consulta-form.html", "Consulta", "CO"],
        ["/veterinario/recetas.html", "Recetas", "RX"]
    ],
    cliente: [
        ["/cliente/dashboard.html", "Dashboard", "IN"],
        ["/cliente/citas.html", "Citas", "CI"],
        ["/cliente/estado.html", "Estado", "ES"],
        ["/cliente/historial.html", "Historial", "HI"],
        ["/cliente/pagos.html", "Pagos", "PA"]
    ]
};

document.addEventListener("DOMContentLoaded", async () => {
    const role = document.body.dataset.role || getRoleFromPath();
    if (!role || !ROLE_NAV[role]) return;

    document.body.classList.add("app-shell");
    document.querySelectorAll(".app-nav").forEach((nav) => nav.remove());

    const main = document.querySelector("main");
    const sidebar = document.createElement("aside");
    sidebar.className = "app-sidebar";
    sidebar.innerHTML = `
        <a class="brand-lockup" href="${ROLE_NAV[role][0][0]}">
            <span class="brand-mark">CV</span>
            <span><strong>Clinica Vet</strong><small>${roleLabel(role)}</small></span>
        </a>
        <nav class="side-nav">
            ${ROLE_NAV[role].map(([href, label, icon]) => `
                <a class="${location.pathname === href ? "active" : ""}" href="${href}">
                    <span class="nav-icon">${icon}</span>
                    <span>${label}</span>
                </a>
            `).join("")}
        </nav>
        <a class="side-logout" href="/logout">Cerrar sesion</a>
    `;
    document.body.insertBefore(sidebar, document.body.firstChild);

    if (main) {
        main.classList.add("app-main");
        const topbar = document.createElement("div");
        topbar.className = "app-topbar";
        topbar.innerHTML = `
            <div>
                <span class="eyebrow">${roleLabel(role)}</span>
                <strong>${document.title || "Panel"}</strong>
            </div>
            <div class="user-chip" id="currentUserChip">Sesion activa</div>
        `;
        main.insertBefore(topbar, main.firstChild);
    }

    try {
        const response = await fetch("/api/auth/me");
        if (!response.ok) return;
        const me = await response.json();
        const chip = document.getElementById("currentUserChip");
        if (chip) chip.textContent = `${me.nombre || me.username} ${me.apellidos || ""}`.trim();
        window.currentUser = me;
        document.dispatchEvent(new CustomEvent("current-user-ready", { detail: me }));
    } catch {
        // La pagina sigue siendo usable aunque no se pueda pintar el usuario.
    }
});

function getRoleFromPath() {
    const first = location.pathname.split("/").filter(Boolean)[0];
    return ROLE_NAV[first] ? first : null;
}

function roleLabel(role) {
    return {
        admin: "Administracion",
        auxiliar: "Recepcion y admision",
        veterinario: "Area veterinaria",
        cliente: "Portal cliente"
    }[role] || "Clinica";
}
