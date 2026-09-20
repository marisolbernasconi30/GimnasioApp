import { USUARIOS_URL, apiFetch, protegerPagina, obtenerRole } from './config.js';

protegerPagina();

// Si alguien sin ser ADMIN entra directo a esta URL, lo mandamos afuera
if (obtenerRole() !== "ADMIN") {
    alert("No tenés permiso para acceder a esta sección");
    window.location.href = "panel1.html";
}

document.addEventListener("DOMContentLoaded", () => {
    cargarUsuarios();
});

async function cargarUsuarios() {

    try {
        const respuesta = await apiFetch(USUARIOS_URL);
        if (!respuesta.ok) throw new Error("Error al obtener usuarios");

        const usuarios = await respuesta.json();
        mostrarUsuarios(usuarios);

    } catch (error) {
        console.error(error);
        alert("No se pudieron obtener los usuarios");
    }
}

function mostrarUsuarios(usuarios) {

    const cuerpo = document.getElementById("tablaUsuarios");
    cuerpo.innerHTML = "";

    usuarios.forEach(usuario => {

        const fila = document.createElement("tr");

        const estado = usuario.activo ? "Activo" : "Dado de baja";

        const boton = usuario.activo
            ? `<button type="button" class="btn-baja-usuario" data-id="${usuario.id}">Dar de baja</button>`
            : `<span>—</span>`;

        fila.innerHTML = `
            <td>${usuario.username}</td>
            <td>${usuario.firstname}</td>
            <td>${usuario.lastname}</td>
            <td>${usuario.role}</td>
            <td>${estado}</td>
            <td>${boton}</td>
        `;
        cuerpo.appendChild(fila);
    });
}

document.getElementById("btnCrearUsuario").addEventListener("click", async () => {

    const usuario = {
        username: document.getElementById("username").value.trim(),
        password: document.getElementById("password").value,
        firstname: document.getElementById("firstname").value.trim(),
        lastname: document.getElementById("lastname").value.trim(),
        role: document.getElementById("role").value
    };

    if (!usuario.username || !usuario.password || !usuario.firstname || !usuario.lastname) {
        alert("Complete todos los campos");
        return;
    }

    try {
        const respuesta = await apiFetch(USUARIOS_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(usuario)
        });

        const data = await respuesta.json();

        if (!respuesta.ok) {
            throw new Error(data.mensaje || "Error al crear el usuario");
        }

        alert("Usuario creado correctamente");

        document.getElementById("username").value = "";
        document.getElementById("password").value = "";
        document.getElementById("firstname").value = "";
        document.getElementById("lastname").value = "";

        cargarUsuarios();

    } catch (error) {
        console.error(error);
        alert(error.message);
    }
});

document.getElementById("tablaUsuarios").addEventListener("click", async (event) => {

    if (!event.target.classList.contains("btn-baja-usuario")) return;

    const id = event.target.dataset.id;

    if (!confirm("¿Dar de baja este usuario?")) return;

    try {
        const respuesta = await apiFetch(`${USUARIOS_URL}/${id}/baja`, {
            method: "PUT"
        });

        if (!respuesta.ok) throw new Error("Error al dar de baja al usuario");

        cargarUsuarios();

    } catch (error) {
        console.error(error);
        alert(error.message);
    }
});