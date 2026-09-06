let paginaActual = 0;
const cantidadPorPagina = 10;

const parametros = new URLSearchParams(window.location.search);
const estado = parametros.get("estado");

async function cargarInscripciones() {

    const url = `${INSCRIPCIONES_URL}?estado=${estado}&page=${paginaActual}&size=${cantidadPorPagina}`;

    try {

        const respuesta = await fetch(url);

        if (!respuesta.ok) {
            throw new Error("Error al obtener las inscripciones");
        }

        const datos = await respuesta.json();

        mostrarInscripciones(datos);

    } catch (error) {
        console.error(error);
    }
}

document.getElementById("siguiente").addEventListener("click", () => {
    paginaActual++;
    cargarInscripciones();
});

document.getElementById("anterior").addEventListener("click", () => {

    if (paginaActual > 0) {
        paginaActual--;
        cargarInscripciones();
    }

});

function mostrarInscripciones(inscripciones) {

    const cuerpo = document.getElementById("cuerpoTabla");

    cuerpo.innerHTML = "";

    inscripciones.forEach(inscripcion => {

        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>${inscripcion.activa===1 ? "Activa" : "Inactiva"}</td>
            <td>${inscripcion.tipo_entrenamiento}</td>
            <td>${inscripcion.cliente_id}</td>
            <td>${inscripcion.fecha_inicio}</td>
            <td>${inscripcion.fecha_baja ?? "-"}</td>
        `;

        cuerpo.appendChild(fila);
    });
}

cargarInscripciones();