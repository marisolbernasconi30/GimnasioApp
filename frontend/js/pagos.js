// ================================
// CONFIGURACIÓN
// ================================

const url = PAGOS_URL;


// ================================
// LISTAR PAGOS
// ================================

async function listarPagos() {

    try {

        const response = await fetch(url);

        if (!response.ok) {
            throw new Error("Error al obtener los pagos");
        }

        const pagos = await response.json();

        mostrarPagos(pagos);

    } catch (error) {

        console.error("Error:", error);
    }
}


// ================================
// MOSTRAR PAGOS EN LA TABLA
// ================================

function mostrarPagos(pagos) {

    const tbody = document.getElementById("tablaPagos");

    tbody.innerHTML = "";

    pagos.forEach(pago => {

        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>${pago.id}</td>
            <td>${pago.usuario}</td>
            <td>${pago.fecha}</td>
            <td>${pago.monto}</td>
            <td>${pago.metodoPago}</td>
        `;

        tbody.appendChild(fila);
    });
}


// ================================
// REGISTRAR PAGO
// ================================

async function registrarPago(pago) {

    try {

        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(pago)
        });

        if (!response.ok) {
            throw new Error("Error al registrar el pago");
        }

        const nuevoPago = await response.json();

        console.log("Pago registrado:", nuevoPago);

        listarPagos();

    } catch (error) {

        console.error("Error:", error);
    }
}


// ================================
// BUSCAR PAGOS DE UN CLIENTE
// ================================

async function listarPagosPorCliente(clienteId) {

    try {

        const response = await fetch(`${PAGOS_URL}/cliente/${clienteId}`);

        if (!response.ok) {
            throw new Error("Error al obtener los pagos del cliente");
        }

        const pagos = await response.json();

        mostrarPagos(pagos);

    } catch (error) {

        console.error("Error:", error);
    }
}

listarPagos();