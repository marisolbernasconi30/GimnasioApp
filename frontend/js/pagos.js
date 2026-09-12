import { PAGOS_URL, CLIENTES_URL, INSCRIPCIONES_URL } from './config.js';

// VARIABLES

let clienteActual = null;
let inscripcionSeleccionada = null;

// ELEMENTOS
const dniInput = document.getElementById("dni");
const btnBuscar = document.getElementById("btnBuscar");
const tablaInscripciones = document.getElementById("tablaInscripciones");
const contenedorInscripciones = document.getElementById("contenedorInscripciones");
const datosCliente = document.getElementById("datosCliente");
const datosPago = document.getElementById("datosPago");
const btnRegistrarPago = document.getElementById("btnRegistrarPago");

// BUSCAR CLIENTE POR DNI

async function buscarCliente() {

    const dni = dniInput.value.trim();

    if (dni === "") {
        alert("Ingrese un DNI");
        return;
    }

    try {
        console.log("Buscando cliente con DNI:", dni);
        const response = await fetch(
            `${CLIENTES_URL}/dni/${dni}`
        );
        if (!response.ok) {
            throw new Error("Cliente no encontrado");
        }
        const cliente = await response.json();
        console.log("Cliente encontrado:", cliente);
        // Guardamos el cliente
        clienteActual = cliente;
        // Mostramos sus datos
        document.getElementById("nombreCliente").textContent = cliente.nombre;
        document.getElementById("apellidoCliente").textContent = cliente.apellido;
        document.getElementById("dniCliente").textContent = cliente.dni;
        datosCliente.style.display = "block";
        // Buscamos sus inscripciones
        await cargarInscripciones(cliente.id); //aca hay un problema 
    } catch (error) {
        console.error(error);
        alert("No se encontró ningún usuario con ese DNI");
    }

}

// CARGAR INSCRIPCIONES

async function cargarInscripciones(clienteId) {
    try {
        const response = await fetch(`${INSCRIPCIONES_URL}/cliente/${clienteId}`);
        if (!response.ok) throw new Error("Error al obtener las inscripciones");
        const inscripciones = await response.json();
        mostrarInscripciones(inscripciones); // ya no hace falta "inscripcionesConPago"
    } catch (error) {
        console.error(error);
        alert("No se pudieron obtener las inscripciones");
    }
}

// MOSTRAR INSCRIPCIONES

function mostrarInscripciones(inscripciones) {
    tablaInscripciones.innerHTML = "";

    if (inscripciones.length === 0) {
        tablaInscripciones.innerHTML = `<tr><td colspan="5">El usuario no tiene inscripciones.</td></tr>`;
        contenedorInscripciones.style.display = "block";
        return;
    }

    inscripciones.forEach(inscripcion => {
        const fila = document.createElement("tr");

        const etiquetas = {
            AL_DIA: "ACTIVA",
            POR_VENCER: "POR VENCER",
            VENCIDA: "VENCIDA"
        };
        const estado = etiquetas[inscripcion.estadoPago];

        const boton = inscripcion.estadoPago === "AL_DIA"
            ? `<span>YA PAGADA</span>`
            : `<button type="button" class="btn-seleccionar" data-id="${inscripcion.id}">Seleccionar</button>`;

        fila.innerHTML = `
            <td>${inscripcion.tipoEntrenamiento}</td>
            <td>${inscripcion.fechaInicio}</td>
            <td>${inscripcion.fechaBaja ?? "-"}</td>
            <td>${estado}</td>
            <td>${boton}</td>
        `;
        tablaInscripciones.appendChild(fila);
    });

    contenedorInscripciones.style.display = "block";
}

function fechaLocal(fechaString) {

    const [año, mes, dia] = fechaString.split("-");

    return new Date(
        Number(año),
        Number(mes) - 1,
        Number(dia)
    );
}

// SELECCIONAR INSCRIPCIÓN

tablaInscripciones.addEventListener(
    "click",
    function (event) {

        if (
            !event.target.classList.contains("btn-seleccionar")
        ) {
            return;
        }

        const inscripcionId = event.target.dataset.id;

        console.log("Inscripción seleccionada:", inscripcionId);

        // Guardamos solamente el ID

        inscripcionSeleccionada = Number(inscripcionId);
        console.log("ID guardado:", inscripcionSeleccionada);
        // Mostrar datos del pago
        datosPago.style.display = "block";
        // Ponemos la fecha actual
        ponerFechaActual();
        // Marcamos visualmente la fila

        document
            .querySelectorAll(".btn-seleccionar")
            .forEach(boton => {
                boton.style.fontWeight =
                    "normal";
            });

        event.target.style.fontWeight = "bold";
    }
);

// PONER FECHA ACTUAL

function ponerFechaActual() {

    const hoy = new Date();
    const año = hoy.getFullYear();
    const mes = String(hoy.getMonth() + 1).padStart(2, "0");
    const dia = String(hoy.getDate()).padStart(2, "0");
    const fecha = `${año}-${mes}-${dia}`;
    document.getElementById("fechaPago").value = fecha;
}

// REGISTRAR PAGO

async function registrarPago() {

    // COMPROBAR INSCRIPCIÓN

    if (
        inscripcionSeleccionada === null
    ) {
        alert("Seleccione una inscripción");
        return;
    }

    // FECHA

    const fechaPago =
        document.getElementById(
            "fechaPago"
        ).value;

    if (fechaPago === "") {
        alert("Ingrese la fecha del pago");
        return;
    }

    // MONTO

    const monto =
        document.getElementById("monto").value;

    if (monto === "" || Number(monto) <= 0
    ) {
        alert("Ingrese un monto válido");
        return;
    }

    // MÉTODO DE PAGO

    const metodoSeleccionado =
        document.querySelector('input[name="metodoPago"]:checked');

    if (!metodoSeleccionado) {
        alert("Seleccione un método de pago");
        return;
    }

    const metodoPago = metodoSeleccionado.value;

    // OBJETO QUE MANDAREMOS AL BACKEND
    const pago = {

        inscripcionId: inscripcionSeleccionada,
        fechaPago: fechaPago,
        monto: Number(monto),
        metodoPago: metodoPago

    };

    console.log("Pago que voy a enviar:", pago);

    // POST

    try {
        const response = await fetch(PAGOS_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(pago)
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.mensaje || "Error al registrar el pago");
        }

        console.log("Pago registrado:", data);
        alert("Pago registrado correctamente");
        limpiarPago();

    } catch (error) {
        console.error("Error:", error);
        alert(error.message);
    }
}

// LIMPIAR DATOS DEL PAGO

function limpiarPago() {

    inscripcionSeleccionada = null;

    document.getElementById(
        "monto"
    ).value = "";

    document.querySelectorAll(
        'input[name="metodoPago"]'
    ).forEach(radio => {
        radio.checked = false;
    });
    datosPago.style.display =
        "none";
}

// EVENTOS

btnBuscar.addEventListener("click", buscarCliente);

btnRegistrarPago.addEventListener("click", registrarPago);