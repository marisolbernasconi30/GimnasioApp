import { ESTADISTICAS_URL, INSCRIPCIONES_URL, apiFetch, protegerPagina } from './config.js';

protegerPagina();

const etiquetasPago = {
    AL_DIA: "Al día",
    POR_VENCER: "Por vencer",
    VENCIDA: "Vencida"
};

const clasesBadge = {
    AL_DIA: "badge-al-dia",
    POR_VENCER: "badge-por-vencer",
    VENCIDA: "badge-vencida"
};

document.addEventListener("DOMContentLoaded", () => {
    cargarEstadisticas();
});

async function cargarEstadisticas() {

    try {
        const respuesta = await apiFetch(ESTADISTICAS_URL);
        if (!respuesta.ok) throw new Error("Error al obtener estadísticas");

        const datos = await respuesta.json();

        document.getElementById("porcentajeNuevos").textContent = datos.cantidadClienteNuevo;
        document.getElementById("porcentajeBaja").textContent = datos.cantidadClientesBaja;

        document.getElementById("totalRecaudado").textContent =
            datos.totalRecaudado.toLocaleString("es-AR");

        mostrarTabalTipos(datos.porcentajeEntrenamiento);

    } catch (error) {
        console.error(error);
        alert("No se pudieron obtener las estadísticas");
    }
}

function mostrarTabalTipos(porcentajes) {

    const contenedor = document.getElementById("listaTipos");
    contenedor.innerHTML = "";

    Object.entries(porcentajes).forEach(([tipo, porcentaje]) => {

        const boton = document.createElement("button");
        boton.type = "button";
        boton.className = "btn-tipo-entrenamiento";
        boton.dataset.tipo = tipo;
        boton.style.marginBottom = "8px";

        boton.innerHTML = `
            <span>${tipo}</span>
            <span class="porcentaje">${porcentaje.toFixed(1)}%</span>
        `;

        contenedor.appendChild(boton);
    });
}

document.getElementById("listaTipos").addEventListener("click", async (event) => {

    const boton = event.target.closest(".btn-tipo-entrenamiento");
    if (!boton) return;

    const tipo = boton.dataset.tipo;

    try {
        const respuesta = await apiFetch(`${INSCRIPCIONES_URL}/tipo/${tipo}/activas`);
        if (!respuesta.ok) throw new Error("Error al obtener inscriptos");

        const inscripciones = await respuesta.json();
        mostrarDetalleTipo(tipo, inscripciones);

    } catch (error) {
        console.error(error);
        alert("No se pudo obtener el detalle de este entrenamiento");
    }
});

function mostrarDetalleTipo(tipo, inscripciones) {

    document.getElementById("nombreTipoDetalle").textContent = tipo;

    const cuerpo = document.getElementById("tablaDetalleTipo");
    cuerpo.innerHTML = "";

    if (inscripciones.length === 0) {
        cuerpo.innerHTML = `<tr><td colspan="4">No hay clientes inscriptos actualmente.</td></tr>`;
    } else {
        inscripciones.forEach(inscripcion => {

            const badge = `
                <span class="badge-estado ${clasesBadge[inscripcion.estadoPago]}">
                    ${etiquetasPago[inscripcion.estadoPago]}
                </span>
            `;

            const fila = document.createElement("tr");
            fila.innerHTML = `
                <td>${inscripcion.nombreCliente}</td>
                <td>${inscripcion.apellidoCliente}</td>
                <td>${inscripcion.dni}</td>
                <td>${badge}</td>
            `;
            cuerpo.appendChild(fila);
        });
    }

    document.getElementById("detalleTipo").style.display = "block";
    document.getElementById("detalleTipo").scrollIntoView({ behavior: "smooth" });
}