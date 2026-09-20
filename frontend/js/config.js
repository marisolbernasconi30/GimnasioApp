export const API_URL = "http://localhost:8080";

export const CLIENTES_URL = `${API_URL}/clientes`;
export const INSCRIPCIONES_URL = `${API_URL}/inscripciones`;
export const PAGOS_URL = `${API_URL}/pagos`;
export const ESTADISTICAS_URL = `${API_URL}/estadisticas`;
export const USUARIOS_URL = `${API_URL}/usuarios`;
export const AUTH_URL = `${API_URL}/auth`;

// ---------------------------------------------
// SESIÓN
// ---------------------------------------------

export function guardarSesion(token, role) {
    localStorage.setItem("token", token);
    localStorage.setItem("role", role);
}

export function obtenerToken() {
    return localStorage.getItem("token");
}

export function obtenerRole() {
    return localStorage.getItem("role");
}

export function cerrarSesion() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    window.location.href = "Login.html";
}

// Redirige al login si no hay token. Llamar al principio de CADA página protegida.
export function protegerPagina() {
    if (!obtenerToken()) {
        window.location.href = "Login.html";
    }
}

// ---------------------------------------------
// FETCH CON TOKEN (usar esto en vez de fetch() para llamar a la API)
// ---------------------------------------------

export async function apiFetch(url, options = {}) {

    const token = obtenerToken();

    const headers = {
        ...(options.headers || {}),
        "Authorization": `Bearer ${token}`
    };

    const respuesta = await fetch(url, { ...options, headers });

    if (respuesta.status === 401 || respuesta.status === 403) {
        // Token vencido, inválido, o sin permiso -> lo mandamos al login

        if (respuesta.status === 401) {
            cerrarSesion();
        }
    }

    return respuesta;
}