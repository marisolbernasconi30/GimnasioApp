import { INSCRIPCIONES_URL, apiFetch, protegerPagina } from './config.js';

protegerPagina();

let paginaActual = 0;
const cantidadPorPagina = 10;
let totalPaginas = 0;

let estadoActual = "activas";
let modoListado = "paginado";

async function cargarInscripciones() {

    let url;

    if (estadoActual === "inactivas") {
        url = `${INSCRIPCIONES_URL}/inactivas?page=${paginaActual}&size=${cantidadPorPagina}`;
    } else {
        url = `${INSCRIPCIONES_URL}?page=${paginaActual}&size=${cantidadPorPagina}`;
    }

    try {

        const respuesta = await apiFetch(url);

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

// BOTÓN ACTIVAS
document.getElementById("btnActivas").addEventListener("click", () => {

    estadoActual = "activas";
    paginaActual = 0;
    cargarInscripciones();

});

// BOTÓN INACTIVAS

document.getElementById("btnInactivas").addEventListener("click", () => {
    estadoActual = "inactivas";
    paginaActual = 0;
    cargarInscripciones();
});

// SIGUIENTE

document.getElementById("siguiente").addEventListener("click", () => {

    if (paginaActual < totalPaginas - 1) {
        paginaActual++;
        cargarInscripciones();
    }
});

// ANTERIOR

document.getElementById("anterior").addEventListener("click", () => {

    if (paginaActual > 0) {
        paginaActual--;
        cargarInscripciones();
    }
});

// MOSTRAR INSCRIPCIONES

function mostrarInscripciones(inscripciones) {

    const cuerpo = document.getElementById("cuerpoTablaInscripciones");
    cuerpo.innerHTML = "";
    if (inscripciones.length === 0) {
        cuerpo.innerHTML = `
            <tr>
                <td colspan="8">
                    No hay inscripciones para mostrar.
                </td>
            </tr>
        `;
        return;
    }

    inscripciones.forEach(inscripcion => {

        const fila = document.createElement("tr");
        const estado = inscripcion.activa
            ? "Activa"
            : "Inactiva";

        const etiquetasPago = {
            AL_DIA: "Pagada",
            POR_VENCER: "Por vencer",
            VENCIDA: "Vencida"
        };

        const clasesBadge = {
            AL_DIA: "badge-al-dia",
            POR_VENCER: "badge-por-vencer",
            VENCIDA: "badge-vencida"
        };

        const pago = `
            <span class="badge-estado ${clasesBadge[inscripcion.estadoPago]}">
                ${etiquetasPago[inscripcion.estadoPago]}
            </span>
        `;

        fila.innerHTML = `
            <td>${inscripcion.nombreCliente}</td>
            <td>${inscripcion.apellidoCliente}</td>
            <td>${inscripcion.dni}</td>
            <td>${inscripcion.tipoEntrenamiento}</td>
            <td>${inscripcion.fechaInicio}</td>
            <td>${inscripcion.fechaVencimiento}</td>
            <td>${estado}</td>
            <td>${pago}</td>
        `;
        cuerpo.appendChild(fila);
    });
}

// ACTUALIZAR PAGINACIÓN

function actualizarBotones() {

    const botonAnterior = document.getElementById("anterior");
    const botonSiguiente = document.getElementById("siguiente");
    botonAnterior.style.display = "inline-block";
    botonSiguiente.style.display = "inline-block";
    botonAnterior.disabled = paginaActual === 0;
    botonSiguiente.disabled = paginaActual >= totalPaginas - 1;
    const textoPagina = document.getElementById("paginaActual");
    textoPagina.style.display = "inline";
    textoPagina.textContent = `Página ${paginaActual + 1} de ${totalPaginas}`;
}

const btnVencidas = document.getElementById("btnVencidas");
const btnPorVencer = document.getElementById("btnPorVencer");
const btnAlDia = document.getElementById("btnAlDia");

async function cargarVencidas() {
    try {
        const respuesta = await apiFetch(`${INSCRIPCIONES_URL}/vencidas`);
        if (!respuesta.ok) {
            throw new Error("Error al obtener inscripciones vencidas");
        }
        const inscripciones = await respuesta.json();
        mostrarInscripciones(inscripciones);
        ocultarPaginacion();
    } catch (error) {
        console.error(error);
    }
}

async function cargarPorVencer() {

    try {
        const respuesta = await apiFetch(`${INSCRIPCIONES_URL}/por-vencer`);
        if (!respuesta.ok) {
            throw new Error("Error al obtener inscripciones por vencer");
        }
        const inscripciones = await respuesta.json();
        mostrarInscripciones(inscripciones);
        ocultarPaginacion();
    } catch (error) {
        console.error(error);
    }
}

async function cargarAlDia() {

    try {
        const respuesta = await apiFetch(`${INSCRIPCIONES_URL}/al-dia`);
        if (!respuesta.ok) {
            throw new Error("Error al obtener inscripciones al día");
        }
        const inscripciones = await respuesta.json();
        mostrarInscripciones(inscripciones);
        ocultarPaginacion();
    } catch (error) {
        console.error(error);
    }
}

btnVencidas.addEventListener("click", () => { cargarVencidas(); });
btnPorVencer.addEventListener("click", () => { cargarPorVencer(); });
btnAlDia.addEventListener("click", () => { cargarAlDia(); });

function ocultarPaginacion() {

    document.getElementById("anterior").style.display = "none";
    document.getElementById("siguiente").style.display = "none";
    document.getElementById("paginaActual").style.display = "none";
}

// CARGAR PRIMERA PÁGINA
cargarInscripciones();