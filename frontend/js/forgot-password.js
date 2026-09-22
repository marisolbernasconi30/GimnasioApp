import { AUTH_URL } from './config.js';

document.getElementById("btnEnviar").addEventListener("click", async () => {

    const email = document.getElementById("email").value.trim();
    const mensaje = document.getElementById("mensaje");

    if (!email) {
        mensaje.textContent = "Ingresá tu email";
        return;
    }

    try {
        const respuesta = await fetch(`${AUTH_URL}/forgot-password`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email })
        });

        const data = await respuesta.json();
        mensaje.textContent = data.mensaje;

    } catch (error) {
        mensaje.textContent = "Ocurrió un error. Intentá de nuevo.";
    }
});