import {
    INSCRIPCIONES_URL,
    PAGOS_URL
} from './config.js';


async function cargarPagadas() {

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
        const pagadas = [];


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

            if (pagos.length > 0) {

                pagadas.push({
                    inscripcion: inscripcion,
                    pago: pagos[0]
                });

            }

        }

        mostrarPagadas(pagadas);

    } catch (error) {

        console.error(error);
        alert(
            "No se pudieron cargar las inscripciones pagadas"
        );

    }

}


function mostrarPagadas(pagadas) {

    const tbody =
        document.getElementById("tablaPagadas");

    tbody.innerHTML = "";


    if (pagadas.length === 0) {

        tbody.innerHTML = `
            <tr>
                <td colspan="8">
                    No hay inscripciones pagadas.
                </td>
            </tr>
        `;

        return;
    }


    pagadas.forEach(item => {

        const inscripcion =
            item.inscripcion;

        const pago =
            item.pago;

        const estado =
            inscripcion.activa
                ? "ACTIVA"
                : "VENCIDA";

        const fila =
            document.createElement("tr");

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
            <td>
                ${pago.fechaPago}
            </td>
            <td>
                $${pago.monto}
            </td>
            <td>
                ${pago.metodoPago}
            </td>

        `;


        tbody.appendChild(fila);

    });

}


document.addEventListener(
    "DOMContentLoaded",
    cargarPagadas
);