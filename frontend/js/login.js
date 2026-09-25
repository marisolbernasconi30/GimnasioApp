import { AUTH_URL, guardarSesion } from './config.js';

const btnLogin = document.getElementById("btnLogin");
const mensajeError = document.getElementById("mensajeError");

async function login() {

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;

    if (username === "" || password === "") {
        mostrarError("Complete usuario y contraseña");
        return;
    }

    try {
        const respuesta = await fetch(`${AUTH_URL}/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (!respuesta.ok) {
            throw new Error("Usuario o contraseña incorrectos");
        }

        const data = await respuesta.json();

        // Decodificamos el rol del propio token (JWT), sin llamar a otro endpoint
        const role = obtenerRoleDelToken(data.token);

        guardarSesion(data.token, role);

        window.location.href = "panel1.html"; 

    } catch (error) {
        console.error(error);
        mostrarError("Usuario o contraseña incorrectos");
    }
}

function mostrarError(mensaje) {
    mensajeError.textContent = mensaje;
    mensajeError.style.display = "block";
}

function obtenerRoleDelToken(token) {
    // Un JWT tiene 3 partes separadas por ".". La del medio (payload) está en base64.
    const payload = JSON.parse(atob(token.split(".")[1]));
    return payload.role || null;
}

btnLogin.addEventListener("click", login);
