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

        const role = obtenerRoleDelToken(data.token);
        const nombre = obtenerNombreDelToken(data.token);
        guardarSesion(data.token, role, nombre);

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
    const payload = JSON.parse(atob(token.split(".")[1]));
    return payload.role || null;
}

function obtenerNombreDelToken(token) {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return payload.firstname || "";
}

document.getElementById("togglePassword").addEventListener("click", () => {
    const input = document.getElementById("password");
    input.type = input.type === "password" ? "text" : "password";
});

btnLogin.addEventListener("click", login);
