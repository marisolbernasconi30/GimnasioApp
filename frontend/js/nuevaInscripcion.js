import { CLIENTES_URL, INSCRIPCIONES_URL, apiFetch, protegerPagina } from './config.js';

protegerPagina();

let clienteActual = null;

const dniInput = document.getElementById("dni");
const btnBuscar = document.getElementById("btnBuscar");
const datosCliente = document.getElementById("datosCliente");
const formularioInscripcion = document.getElementById("formularioInscripcion");
const btnCrearInscripcion = document.getElementById("btnCrearInscripcion");

async function buscarCliente() {

    const dni = dniInput.value.trim();
    if (dni === "") {
        alert("Ingrese un DNI");
        return;
    }
    try {
        const response = await apiFetch(`${CLIENTES_URL}/dni/${dni}`);

        if (!response.ok) {
            throw new Error("Cliente no encontrado");
        }

        const cliente = await response.json();
        clienteActual = cliente;

        document.getElementById("nombreCliente").textContent = cliente.nombre;
        document.getElementById("apellidoCliente").textContent = cliente.apellido;
        document.getElementById("dniCliente").textContent = cliente.dni;
        datosCliente.style.display = "block";
        formularioInscripcion.style.display = "block";

        ponerFechaActual();

    } catch (error) {
        console.error(error);
        alert("No se encontró ningún cliente con ese DNI");
    }
}

function ponerFechaActual() {
    const hoy = new Date();
    const año = hoy.getFullYear();
    const mes = String(hoy.getMonth() + 1).padStart(2, "0");
    const dia = String(hoy.getDate()).padStart(2, "0");
    document.getElementById("fechaInicio").value = `${año}-${mes}-${dia}`;
}

async function crearInscripcion() {

    if (!clienteActual) {
        alert("Primero busque un cliente");
        return;
    }

    const tipoEntrenamiento = document.getElementById("tipoEntrenamiento").value;
    const fechaInicio = document.getElementById("fechaInicio").value;

    if (tipoEntrenamiento === "") {
        alert("Seleccione un tipo de entrenamiento");
        return;
    }

    if (fechaInicio === "") {
        alert("Seleccione una fecha de inicio");
        return;
    }

    const inscripcion = {
        clienteId: clienteActual.id,
        tipoEntrenamiento: tipoEntrenamiento,
        fechaInicio: fechaInicio
    };

    try {
        const response = await apiFetch(INSCRIPCIONES_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(inscripcion)
        });

        if (!response.ok) {
            throw new Error("Error al crear inscripción");
        }

        const nuevaInscripcion = await response.json();

        alert("Inscripción creada correctamente");

        const otra = confirm("¿Desea crear otra inscripción para este cliente?");

        if (otra) {
            document.getElementById("tipoEntrenamiento").value = "";
            ponerFechaActual();
        } else {
            limpiarFormulario();
        }

    } catch (error) {
        console.error(error);
        alert("No se pudo crear la inscripción");
    }
}

function limpiarFormulario() {
    clienteActual = null;
    dniInput.value = "";
    document.getElementById("tipoEntrenamiento").value = "";
    document.getElementById("fechaInicio").value = "";
    datosCliente.style.display = "none";
    formularioInscripcion.style.display = "none";
}

btnBuscar.addEventListener("click", buscarCliente);
btnCrearInscripcion.addEventListener("click", crearInscripcion);