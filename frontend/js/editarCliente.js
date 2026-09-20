import { CLIENTES_URL, INSCRIPCIONES_URL, PAGOS_URL, apiFetch, protegerPagina } from './config.js';

protegerPagina();

const parametros = new URLSearchParams(window.location.search);
const idCliente = parametros.get("id");
let inscripcionSeleccionadaFicha = null;

// ---------------------------------------------
// TOAST
// ---------------------------------------------
function mostrarToast(mensaje, tipo) {
    const toast = document.getElementById("toast");
    toast.textContent = mensaje;
    toast.className = "toast mostrar " + tipo;

    setTimeout(() => {
        toast.className = "toast " + tipo;
    }, 3000);
}

// ---------------------------------------------
// CARGAR DATOS DEL CLIENTE
// ---------------------------------------------
async function cargarCliente() {

    try {
        const respuesta = await apiFetch(`${CLIENTES_URL}/${idCliente}`);

        if (!respuesta.ok) {
            throw new Error("Error al obtener el cliente");
        }

        const cliente = await respuesta.json();

        document.getElementById("nombre").value = cliente.nombre;
        document.getElementById("apellido").value = cliente.apellido;
        document.getElementById("fechaNacimiento").value = cliente.fechaNacimiento;
        document.getElementById("dni").value = cliente.dni;
        document.getElementById("celular").value = cliente.celular;
        document.getElementById("domicilio").value = cliente.domicilio;
        document.getElementById("lesion").value = cliente.lesion;

    } catch (error) {
        console.error(error);
        mostrarToast("Error al cargar los datos del cliente", "error");
    }
}

// ---------------------------------------------
// GUARDAR CAMBIOS (PUT)
// ---------------------------------------------
function editarCliente() {

    const cliente = {
        nombre: document.getElementById("nombre").value,
        apellido: document.getElementById("apellido").value,
        fechaNacimiento: document.getElementById("fechaNacimiento").value,
        dni: document.getElementById("dni").value,
        celular: document.getElementById("celular").value,
        domicilio: document.getElementById("domicilio").value,
        lesion: document.getElementById("lesion").value
    };

    apiFetch(`${CLIENTES_URL}/${idCliente}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(cliente)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("HTTP error: " + response.status);
            }
            return response.json();
        })
        .then(data => {
            mostrarToast("Cliente actualizado con éxito", "exito");
        })
        .catch(error => {
            console.error("Error:", error);
            mostrarToast("Error al actualizar cliente", "error");
        });
}

// ---------------------------------------------
// INSCRIPCIONES DEL CLIENTE
// ---------------------------------------------
async function cargarInscripcionesFicha() {

    try {
        const respuesta = await apiFetch(`${INSCRIPCIONES_URL}/cliente/${idCliente}`);
        if (!respuesta.ok) throw new Error("Error al obtener inscripciones");

        const inscripciones = await respuesta.json();
        const cuerpo = document.getElementById("fichaInscripciones");
        cuerpo.innerHTML = "";

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

        inscripciones.forEach(inscripcion => {

            const fila = document.createElement("tr");

            const badge = `
                <span class="badge-estado ${clasesBadge[inscripcion.estadoPago]}">
                    ${etiquetasPago[inscripcion.estadoPago]}
                </span>
            `;
            const botonPago = inscripcion.estadoPago === "AL_DIA"
                ? `<span>—</span>`
                : `<button type="button" class="btn-pagar-ficha" data-id="${inscripcion.id}">Pagar</button>`;

            fila.innerHTML = `
                <td>${inscripcion.tipoEntrenamiento}</td>
                <td>${inscripcion.fechaVencimiento}</td>
                <td>${badge}</td>
                <td><button type="button" class="btn-ver-pagos" data-id="${inscripcion.id}">Ver pagos</button></td>
                <td>${botonPago}</td>
            `;
            cuerpo.appendChild(fila);
        });

    } catch (error) {
        console.error(error);
        mostrarToast("No se pudieron obtener las inscripciones", "error");
    }
}

document.getElementById("fichaInscripciones").addEventListener("click", async (event) => {

    // VER HISTORIAL DE PAGOS
    if (event.target.classList.contains("btn-ver-pagos")) {

        const inscripcionId = event.target.dataset.id;

        try {
            const respuesta = await apiFetch(`${PAGOS_URL}/inscripcion/${inscripcionId}`);
            if (!respuesta.ok) throw new Error("Error al obtener pagos");

            const pagos = await respuesta.json();

            if (pagos.length === 0) {
                alert("Esta inscripción todavía no tiene pagos registrados.");
                return;
            }

            const detalle = pagos
                .map(p => `${p.fechaPago} — $${p.monto} (${p.metodoPago})`)
                .join("\n");

            alert("Historial de pagos:\n\n" + detalle);

        } catch (error) {
            console.error(error);
            mostrarToast("No se pudo obtener el historial de pagos", "error");
        }
    }

    // SELECCIONAR INSCRIPCIÓN PARA PAGAR
    if (event.target.classList.contains("btn-pagar-ficha")) {

        inscripcionSeleccionadaFicha = Number(event.target.dataset.id);
        document.getElementById("fichaFormPago").style.display = "block";

        const hoy = new Date().toISOString().split("T")[0];
        document.getElementById("fichaFechaPago").value = hoy;
    }
});

// ---------------------------------------------
// CONFIRMAR PAGO
// ---------------------------------------------
document.getElementById("btnConfirmarPagoFicha").addEventListener("click", async () => {

    const fechaPago = document.getElementById("fichaFechaPago").value;
    const monto = document.getElementById("fichaMonto").value;
    const metodoSeleccionado = document.querySelector('input[name="fichaMetodoPago"]:checked');

    if (!fechaPago || !monto || Number(monto) <= 0 || !metodoSeleccionado) {
        mostrarToast("Complete todos los campos del pago", "error");
        return;
    }

    const pago = {
        inscripcionId: inscripcionSeleccionadaFicha,
        fechaPago: fechaPago,
        monto: Number(monto),
        metodoPago: metodoSeleccionado.value
    };

    try {
        const respuesta = await apiFetch(PAGOS_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(pago)
        });

        const data = await respuesta.json();

        if (!respuesta.ok) {
            throw new Error(data.mensaje || "Error al registrar el pago");
        }

        mostrarToast("Pago registrado correctamente", "exito");
        document.getElementById("fichaFormPago").style.display = "none";
        cargarInscripcionesFicha();

    } catch (error) {
        console.error(error);
        mostrarToast(error.message, "error");
    }
});

// ---------------------------------------------
// EVENTOS Y CARGA INICIAL
// ---------------------------------------------

document.getElementById("btnGuardar").addEventListener("click", editarCliente);

cargarCliente();
cargarInscripcionesFicha();