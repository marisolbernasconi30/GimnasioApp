import { CLIENTES_URL } from './config.js';

const parametros = new URLSearchParams(window.location.search);
const idCliente = parametros.get("id");

// ---------------------------------------------
// TOAST (mismo que en crearCliente)
// ---------------------------------------------
function mostrarToast(mensaje, tipo) {
    const toast = document.getElementById("toast");
    toast.textContent = mensaje;
    toast.className = "toast mostrar " + tipo;

    setTimeout(() => {
        toast.className = "toast " + tipo;
    }, 3000);
}

// ---------------------------------------------
// CARGAR DATOS DEL CLIENTE
// ---------------------------------------------
async function cargarCliente() {

    try {
        const respuesta = await fetch(`${CLIENTES_URL}/${idCliente}`);

        if (!respuesta.ok) {
            throw new Error("Error al obtener el cliente");
        }

        const cliente = await respuesta.json();

        document.getElementById("nombre").value = cliente.nombre;
        document.getElementById("apellido").value = cliente.apellido;
        document.getElementById("fechaNacimiento").value = cliente.fechaNacimiento;
        document.getElementById("dni").value = cliente.dni;
        document.getElementById("celular").value = cliente.celular;
        document.getElementById("domicilio").value = cliente.domicilio;
        document.getElementById("lesion").value = cliente.lesion;

    } catch (error) {
        console.error(error);
        mostrarToast("Error al cargar los datos del cliente", "error");
    }
}

// ---------------------------------------------
// GUARDAR CAMBIOS (PUT)
// ---------------------------------------------
function editarCliente() {

    const cliente = {
        nombre: document.getElementById("nombre").value,
        apellido: document.getElementById("apellido").value,
        fechaNacimiento: document.getElementById("fechaNacimiento").value,
        dni: document.getElementById("dni").value,
        celular: document.getElementById("celular").value,
        domicilio: document.getElementById("domicilio").value,
        lesion: document.getElementById("lesion").value
    };

    fetch(`${CLIENTES_URL}/${idCliente}`, {
        method: "PUT",
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
            console.log("Cliente actualizado:", data);
            mostrarToast("Cliente actualizado con éxito", "exito");
        })
        .catch(error => {
            console.error("Error:", error);
            mostrarToast("Error al actualizar cliente", "error");
        });
}

document.getElementById("btnGuardar").addEventListener("click", editarCliente);

cargarCliente();

