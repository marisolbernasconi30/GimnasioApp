import {  CLIENTES_URL, INSCRIPCIONES_URL} from './config.js';

let clienteActual = null;

// ELEMENTOS
const dniInput = document.getElementById("dni");
const btnBuscar = document.getElementById("btnBuscar");
const datosCliente = document.getElementById("datosCliente");
const formularioInscripcion = document.getElementById(  "formularioInscripcion");
const btnCrearInscripcion = document.getElementById( "btnCrearInscripcion" );

// BUSCAR CLIENTE

async function buscarCliente() {

    const dni = dniInput.value.trim();
    if (dni === "") {
        alert("Ingrese un DNI");
        return;
    }
    try {
        const response = await fetch(
            `${CLIENTES_URL}/dni/${dni}`
        );

        if (!response.ok) {
            throw new Error(
                "Cliente no encontrado"
            );
        }

        const cliente = await response.json();
        clienteActual = cliente;

        document.getElementById(   "nombreCliente" ).textContent = cliente.nombre;
        document.getElementById( "apellidoCliente" ).textContent = cliente.apellido;
        document.getElementById(  "dniCliente" ).textContent = cliente.dni;
        datosCliente.style.display =  "block";
        formularioInscripcion.style.display = "block";

        ponerFechaActual();


    } catch (error) {
        console.error(error);
        alert( "No se encontró ningún cliente con ese DNI" );
    }
}

// FECHA ACTUAL

function ponerFechaActual() {
    const hoy = new Date();
    const año = hoy.getFullYear();
    const mes = String(  hoy.getMonth() + 1 ).padStart(2, "0");
    const dia = String(  hoy.getDate() ).padStart(2, "0");
    document.getElementById(  "fechaInicio").value =  `${año}-${mes}-${dia}`;
}

// CREAR INSCRIPCIÓN

async function crearInscripcion() {

    if (!clienteActual) {
        alert("Primero busque un cliente");
        return;
    }

    const tipoEntrenamiento =  document.getElementById(  "tipoEntrenamiento" ).value;

    const fechaInicio = document.getElementById( "fechaInicio"  ).value;
    if (tipoEntrenamiento === "") {
        alert(  "Seleccione un tipo de entrenamiento" );
        return;
    }

    if (fechaInicio === "") {

        alert( "Seleccione una fecha de inicio");
        return;
    }

    const inscripcion = {
        clienteId: clienteActual.id,
        tipoEntrenamiento:  tipoEntrenamiento,
        fechaInicio:  fechaInicio
    };

    console.log( "Inscripción que voy a enviar:", inscripcion );

    try {

        const response =
            await fetch(
                INSCRIPCIONES_URL,
                {
                    method: "POST",
                    headers: {
                        "Content-Type":
                            "application/json"
                    },
                    body:
                        JSON.stringify(
                            inscripcion
                        )
                }
            );

        if (!response.ok) {
            throw new Error(  "Error al crear inscripción"  );
        }

        const nuevaInscripcion = await response.json();
        console.log( "Inscripción creada:", nuevaInscripcion );

        alert(
            "Inscripción creada correctamente"
        );

        // PREGUNTAR SI QUIERE OTRA

        const otra =
            confirm( "¿Desea crear otra inscripción para este cliente?" );

        if (otra) {

            // Limpiamos solamente el formulario
            document.getElementById(  "tipoEntrenamiento"  ).value = "";
            ponerFechaActual();

        } else {

            // Volvemos a limpiar todo
            limpiarFormulario();

        }

    } catch (error) {
        console.error(error);
        alert(  "No se pudo crear la inscripción" );
    }
}

// LIMPIAR

function limpiarFormulario() {

    clienteActual = null;
    dniInput.value = "";
    document.getElementById(  "tipoEntrenamiento" ).value = "";
    document.getElementById( "fechaInicio" ).value = "";
    datosCliente.style.display = "none";
    formularioInscripcion.style.display = "none";
}

// EVENTOS

btnBuscar.addEventListener( "click", buscarCliente);
btnCrearInscripcion.addEventListener( "click", crearInscripcion);