import { AUTH_URL } from './config.js';

const token = new URLSearchParams(window.location.search).get("token");

document.getElementById("btnConfirmar").addEventListener("click", async () => {

    const newPassword = document.getElementById("newPassword").value;
    const mensaje = document.getElementById("mensaje");

    if (!token) {
        mensaje.textContent = "Link inválido.";
        return;
    }

    if (!newPassword || newPassword.length < 4) {
        mensaje.textContent = "Ingresá una contraseña válida";
        return;
    }

    try {
        const respuesta = await fetch(`${AUTH_URL}/reset-password`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ token, newPassword })
        });

        const data = await respuesta.json();
        mensaje.textContent = data.mensaje;

        if (respuesta.ok) {
            setTimeout(() => window.location.href = "index.html", 2000);
        }

    } catch (error) {
        mensaje.textContent = "Ocurrió un error. Intentá de nuevo.";
    }
});