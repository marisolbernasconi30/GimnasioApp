import { obtenerRole, obtenerNombre, cerrarSesion } from './config.js';

fetch("navbar.html")
    .then(res => res.text())
    .then(data => {
        document.getElementById("navbar").innerHTML = data;
    })
    .then(() => {

        const role = obtenerRole();

        const menuEstadisticas = document.getElementById("menuEstadisticas");
        const menuUsuarios = document.getElementById("menuUsuarios");

        if (role !== "ADMIN") {
            if (menuEstadisticas) menuEstadisticas.style.display = "none";
            if (menuUsuarios) menuUsuarios.style.display = "none";
        }

        const btnLogout = document.getElementById("btnCerrarSesion");
        if (btnLogout) {
            btnLogout.addEventListener("click", (e) => {
                e.preventDefault();
                cerrarSesion();
            });
        }
         //  mensaje de bienvenida (solo existe en panel1.html)
        const mensajeBienvenida = document.getElementById("mensajeBienvenida");
        if (mensajeBienvenida) {
            const etiquetaRol = { ADMIN: "administrador", USER: "recepcionista" };
            const nombre = obtenerNombre();
            mensajeBienvenida.textContent =
                `¡Bienvenido/a ${etiquetaRol[role] ?? ""} ${nombre}! 🌟`;
        }
    })
    .catch(err => console.error("Error cargando navbar:", err));