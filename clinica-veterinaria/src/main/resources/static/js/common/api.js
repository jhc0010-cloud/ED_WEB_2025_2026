// Responsable: Juan Hakram Huertas Chergui - G1 Cliente API comun.
async function apiRequest(url, options = {}) {
    const response = await fetch(url, {
        headers: {
            "Content-Type": "application/json",
            ...(options.headers || {})
        },
        ...options
    });

    if (response.status === 204) {
        return null;
    }

    const contentType = response.headers.get("content-type") || "";
    const payload = contentType.includes("application/json") ? await response.json() : await response.text();

    if (!response.ok) {
        let message = typeof payload === "object" && payload.error ? payload.error : `Error HTTP ${response.status}`;
        if (typeof payload === "object" && payload.fields) {
            const fieldMessages = Object.values(payload.fields).filter(Boolean);
            if (fieldMessages.length) message = fieldMessages.join(". ");
        }
        throw new Error(message);
    }

    return payload;
}

function apiGet(url) {
    return apiRequest(url);
}

function apiPost(url, body) {
    return apiRequest(url, {
        method: "POST",
        body: JSON.stringify(body)
    });
}

function apiPut(url, body) {
    return apiRequest(url, {
        method: "PUT",
        body: JSON.stringify(body)
    });
}

function apiDelete(url) {
    return apiRequest(url, { method: "DELETE" });
}

function showStatus(element, message, type = "success") {
    if (!element) return;
    element.innerHTML = `<div class="alert alert-${type} py-2 mb-0">${escapeHtml(message)}</div>`;
}

function escapeHtml(value) {
    return String(value ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function formatDate(value) {
    return value ? new Date(value).toLocaleString("es-ES") : "";
}

function estadoBadge(estado) {
    const classes = {
        PENDIENTE: "text-bg-warning",
        CONFIRMADA: "text-bg-primary",
        CANCELADA: "text-bg-secondary",
        COMPLETADA: "text-bg-success",
        ABIERTA: "text-bg-info",
        EN_PROCESO: "text-bg-primary",
        CERRADA: "text-bg-success",
        PAGADO: "text-bg-success",
        RECHAZADO: "text-bg-danger"
    };
    return `<span class="badge ${classes[estado] || "text-bg-info"}">${escapeHtml(estado || "")}</span>`;
}
