async function apiGet(url) {
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error(`Error HTTP ${response.status}`);
    }
    return response.json();
}

