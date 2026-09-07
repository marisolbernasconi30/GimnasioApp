import { INSCRIPCIONES_URL } from './config.js';

let paginaActual = 0;
const cantidadPorPagina = 10;
let totalPaginas = 0;

// Reemplaza a "estado" leído de la URL
let estadoActual = "activas"; // valor por defecto al cargar la página

async function cargarInscripciones() {

    let url;

    if (estadoActual === "inactivas") {
        url = `${INSCRIPCIONES_URL}/inactivas?page=${paginaActual}&size=${cantidadPorPagina}`;
    } else {
        url = `${INSCRIPCIONES_URL}?page=${paginaActual}&size=${cantidadPorPagina}`;
    }

    try {
        const respuesta = await fetch(url);

        if (!respuesta.ok) {
            throw new Error("Error al obtener las inscripciones");
        }

        const datos = await respuesta.json();

        totalPaginas = datos.totalPages;
        mostrarInscripciones(datos.content);
        actualizarBotones();

    } catch (error) {
        console.error(error);
    }
}

document.getElementById("btnActivas").addEventListener("click", () => {
    estadoActual = "activas";
    paginaActual = 0;
    cargarInscripciones();
});

document.getElementById("btnInactivas").addEventListener("click", () => {
    estadoActual = "inactivas";
    paginaActual = 0;
    cargarInscripciones();
});


// ---------------------------------------------
// BOTÓN SIGUIENTE
// ---------------------------------------------

document.getElementById("siguiente").addEventListener("click", () => {

    if (paginaActual < totalPaginas - 1) {
        paginaActual++;
        cargarInscripciones();
    }

});


// ---------------------------------------------
// BOTÓN ANTERIOR
// ---------------------------------------------

document.getElementById("anterior").addEventListener("click", () => {

    if (paginaActual > 0) {
        paginaActual--;
        cargarInscripciones();
    }

});


// ---------------------------------------------
// MOSTRAR INSCRIPCIONES
// ---------------------------------------------

function mostrarInscripciones(inscripciones) {

    const cuerpo = document.getElementById("cuerpoTablaInscripciones");

    cuerpo.innerHTML = "";

    inscripciones.forEach(inscripcion => {

        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>${inscripcion.activa ? "Activa" : "Inactiva"}</td>
            <td>${inscripcion.tipoEntrenamiento}</td>
            <td>${inscripcion.id}</td>
            <td>${inscripcion.fechaInicio}</td>
            <td>${inscripcion.fechaBaja ?? "-"}</td>
        `;

        cuerpo.appendChild(fila);
    });
}


// ---------------------------------------------
// ACTUALIZAR BOTONES
// ---------------------------------------------

function actualizarBotones() {

    const botonAnterior = document.getElementById("anterior");
    const botonSiguiente = document.getElementById("siguiente");

    botonAnterior.disabled = paginaActual === 0;

    botonSiguiente.disabled =
        paginaActual >= totalPaginas - 1;

    const textoPagina = document.getElementById("paginaActual");

    textoPagina.textContent = `Página ${paginaActual + 1}`;    
}


// ---------------------------------------------
// CARGAR PRIMERA PÁGINA
// ---------------------------------------------

cargarInscripciones();