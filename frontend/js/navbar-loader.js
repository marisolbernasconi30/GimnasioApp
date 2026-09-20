import { obtenerRole, cerrarSesion } from './config.js';

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
    })
    .catch(err => console.error("Error cargando navbar:", err));