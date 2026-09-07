import { CLIENTES_URL } from './config.js';

const url = CLIENTES_URL;

// Obtener clientes activos
function listarClientes() {

    console.log("1. Ejecutando listarClientes()");
    console.log("2. URL:", CLIENTES_URL);

    fetch(CLIENTES_URL)
        .then(response => {

            console.log("3. Respuesta recibida:", response);
            console.log("4. Status:", response.status);

            if (!response.ok) {
                throw new Error("HTTP error: " + response.status);
            }

            return response.json();
        })
        .then(usuarios => {

            console.log("5. Usuarios recibidos:", usuarios);
            console.log("6. Cantidad:", usuarios.length);

            const tabla = document.getElementById("usuarios");

            console.log("7. Tabla encontrada:", tabla);

            tabla.innerHTML = "";

            usuarios.forEach(usuario => {

                tabla.innerHTML += `
                    <tr>
                        <td>${usuario.nombre}</td>
                        <td>${usuario.apellido}</td>
                        <td>${usuario.dni}</td>
                        <td>${usuario.fechaNacimiento}</td>
                        <td>${usuario.celular}</td>
                        <td>${usuario.domicilio}</td>
                        <td>${usuario.lesion}</td>
                        <td>
                            <button class="btn-editar" data-id="${usuario.id}">
                                Editar
                            </button>
                        </td>
                    </tr>
                `;
            });

        })
        .catch(error => {
            console.error("ERROR:", error);
        });
}

// Obtener clientes inactivos   
function listarClientesInactivos() {

    fetch(url+"/baja")
        .then(response => response.json())
        .then(usuarios => {

            console.log(usuarios);

            const tabla = document.getElementById("usuarios");

            tabla.innerHTML = "";

            usuarios.forEach(usuario => {

                tabla.innerHTML += `
                            <tr>
                                <td>${usuario.nombre}</td>
                                <td>${usuario.apellido}</td>
                                <td>${usuario.dni}</td>
                                <td>${usuario.fechaNacimiento}</td>
                                <td>${usuario.celular}</td>
                                <td>${usuario.domicilio}</td>
                                <td>${usuario.lesion}</td>
                                <td>
                                    <button class="btn-editar" data-id="${usuario.id}">
                                        Editar
                                    </button>
                                </td>   
                            </tr>
                        `;

            });

        })
        .catch(error => {
            console.error("Error obteniendo usuarios:", error);
        });
}

//POST

function crearCliente() {

    const nombre = document.getElementById("nombre").value;
    const apellido = document.getElementById("apellido").value;
    const fechaNacimiento = document.getElementById("fechaNacimiento").value;
    const dni = document.getElementById("dni").value;
    const celular = document.getElementById("celular").value;
    const domicilio = document.getElementById("domicilio").value;
    const lesion = document.getElementById("lesion").value;

    const cliente = {
        nombre,
        apellido,
        fechaNacimiento,
        dni,
        celular,
        domicilio,
        lesion
    };

    console.log("Voy a enviar:", cliente);

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(cliente)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("HTTP error: " + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log("Cliente creado:", data);
            mostrarToast("Cliente registrado con éxito", "exito");
            document.querySelector("form").reset();
        })
        .catch(error => {
            console.error("Error:", error);
            mostrarToast("Error al crear cliente", "error");
        });
}

//PUT EDITAR CLIENTE


document.addEventListener("DOMContentLoaded", () => {

    const btnActivos = document.getElementById("btnActivos");
    const btnInactivos = document.getElementById("btnInactivos");
    const btnMatricular = document.getElementById("btnMatricular");
    const tablaUsuarios = document.getElementById("usuarios");

    if (btnActivos) {
        btnActivos.addEventListener("click", listarClientes);
    }

    if (btnInactivos) {
        btnInactivos.addEventListener("click", listarClientesInactivos);
    }

    if (btnMatricular) {
        btnMatricular.addEventListener("click", crearCliente);
    }

    // BOTÓN EDITAR
    if (tablaUsuarios) {

        tablaUsuarios.addEventListener("click", (event) => {

            if (event.target.classList.contains("btn-editar")) {

                const id = event.target.dataset.id;

                console.log("ID a editar:", id);

                window.location.href = `EditarUsuario.html?id=${id}`;
            }

        });

    }

});


function mostrarToast(mensaje, tipo) {
    const toast = document.getElementById("toast");

    toast.textContent = mensaje;
    toast.className = "toast mostrar " + tipo;

    setTimeout(() => {
        toast.className = "toast " + tipo;
    }, 3000);
}