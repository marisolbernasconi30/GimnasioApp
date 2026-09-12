import { CLIENTES_URL, INSCRIPCIONES_URL } from "./config.js";

const formBuscar = document.getElementById("formBuscar");
const dniInput = document.getElementById("dni");

const datosUsuario = document.getElementById("datosUsuario");
const contenedorInscripciones = document.getElementById("contenedorInscripciones");

const nombreUsuario = document.getElementById("nombreUsuario");
const apellidoUsuario = document.getElementById("apellidoUsuario");
const dniUsuario = document.getElementById("dniUsuario");

const tablaInscripciones = document.getElementById("tablaInscripciones");

let clienteActual = null;


// BUSCAR USUARIO
formBuscar.addEventListener("submit", async function (event) {

    event.preventDefault();

    const dni = dniInput.value.trim();

    if (dni === "") {
        alert("Ingrese un DNI.");
        return;
    }

    try {

        // Buscar cliente por DNI
        const respuestaCliente = await fetch(
            `${CLIENTES_URL}/dni/${dni}`
        );

        if (!respuestaCliente.ok) {
            throw new Error("Cliente no encontrado");
        }

        const cliente = await respuestaCliente.json();

        clienteActual = cliente;

        // Mostrar datos
        nombreUsuario.textContent = cliente.nombre;
        apellidoUsuario.textContent = cliente.apellido;
        dniUsuario.textContent = cliente.dni;

        datosUsuario.style.display = "block";

        // Buscar inscripciones
        await cargarInscripciones(cliente.id);

    } catch (error) {

        console.error(error);

        datosUsuario.style.display = "none";
        contenedorInscripciones.style.display = "none";

        alert("No se encontró ningún usuario con ese DNI.");
    }
});


// CARGAR INSCRIPCIONES DEL USUARIO
async function cargarInscripciones(clienteId) {

    try {

        const respuesta = await fetch(
            `${INSCRIPCIONES_URL}/cliente/${clienteId}`
        );

        if (!respuesta.ok) {
            throw new Error("Error al obtener las inscripciones");
        }

        const inscripciones = await respuesta.json();

        mostrarInscripciones(inscripciones);

    } catch (error) {

        console.error(error);
        alert("No se pudieron obtener las inscripciones.");
    }
}


// MOSTRAR INSCRIPCIONES
function mostrarInscripciones(inscripciones) {

    tablaInscripciones.innerHTML = "";

    if (inscripciones.length === 0) {

        tablaInscripciones.innerHTML = `
            <tr>
                <td colspan="5">
                    El usuario no tiene inscripciones.
                </td>
            </tr>
        `;

        contenedorInscripciones.style.display = "block";

        return;
    }

    inscripciones.forEach(inscripcion => {

        const fila = document.createElement("tr");

        const estado = inscripcion.activa
            ? "Activa"
            : "Inactiva";

        fila.innerHTML = `
            <td>${inscripcion.tipoEntrenamiento}</td>
            <td>${inscripcion.fechaInicio}</td>
            <td>${inscripcion.fechaVencimiento ?? "-"}</td>
            <td>${estado}</td>
            <td>
                ${
                    inscripcion.activa
                        ? `
                            <button
                                class="btn-dar-de-baja"
                                data-id="${inscripcion.id}">
                                Dar de baja
                            </button>
                          `
                        : "-"
                }
            </td>
        `;

        tablaInscripciones.appendChild(fila);
    });
    contenedorInscripciones.style.display = "block";
}


// DAR DE BAJA UNA INSCRIPCIÓN
async function darDeBajaInscripcion(id) {

    const confirmar = confirm(
        "¿Está seguro de que desea dar de baja esta inscripción?"
    );

    if (!confirmar) {
        return;
    }

    try {

        const respuesta = await fetch(
            `${INSCRIPCIONES_URL}/${id}/cancelar`,
            {
                method: "PUT"
            }
        );

        if (!respuesta.ok) {
            throw new Error(
                "No se pudo dar de baja la inscripción"
            );
        }

        alert("Inscripción dada de baja correctamente.");
        // Volver a cargar las inscripciones
        await cargarInscripciones(clienteActual.id);

    } catch (error) {

        console.error(error);
        alert("No se pudo dar de baja la inscripción.");
    }
}


// BOTONES GENERADOS DINÁMICAMENTE
document.addEventListener("click", function (event) {

    if (
        event.target.classList.contains("btn-dar-de-baja")
    ) {

        const id = event.target.dataset.id;
        darDeBajaInscripcion(id);
    }

});