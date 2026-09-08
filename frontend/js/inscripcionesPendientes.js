import {
    INSCRIPCIONES_URL,
    PAGOS_URL
} from './config.js';


async function cargarPendientes() {

    try {

        const response = await fetch(
            `${INSCRIPCIONES_URL}?page=0&size=1000`
        );

        if (!response.ok) {
            throw new Error(
                "Error al obtener las inscripciones"
            );
        }

        const pagina = await response.json();
        const inscripciones = pagina.content;
        const pendientes = [];


        for (const inscripcion of inscripciones) {

            const responsePago = await fetch(
                `${PAGOS_URL}/inscripcion/${inscripcion.id}`
            );

            if (!responsePago.ok) {
                throw new Error(
                    "Error al consultar los pagos"
                );
            }
            const pagos = await responsePago.json();

            // Si no tiene pagos, está pendiente
            if (pagos.length === 0) {
                pendientes.push(inscripcion);
            }
        }


        mostrarPendientes(pendientes);


    } catch (error) {

        console.error(error);

        alert(
            "No se pudieron cargar las inscripciones pendientes"
        );
    }
}


function mostrarPendientes(inscripciones) {

    const tbody =
        document.getElementById("tablaPendientes");
    tbody.innerHTML = "";


    if (inscripciones.length === 0) {

        tbody.innerHTML = `
            <tr>
                <td colspan="5">
                    No hay inscripciones pendientes.
                </td>
            </tr>
        `;

        return;
    }


    inscripciones.forEach(inscripcion => {

        const fila =
            document.createElement("tr");

        const estado =
            inscripcion.activa
                ? "ACTIVA"
                : "VENCIDA";


        fila.innerHTML = `

            <td>
                ${inscripcion.id}
            </td>
            <td>
                ${inscripcion.tipoEntrenamiento}
            </td>
            <td>
                ${inscripcion.fechaInicio}
            </td>
            <td>
                ${inscripcion.fechaBaja ?? "-"}
            </td>
            <td>
                ${estado}
            </td>

        `;

        tbody.appendChild(fila);

    });

}


document.addEventListener(
    "DOMContentLoaded",
    cargarPendientes
);