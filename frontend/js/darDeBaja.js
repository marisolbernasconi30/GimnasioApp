import { CLIENTES_URL } from "./config.js";

const form = document.querySelector("form");
const dniInput = document.getElementById("dni");

form.addEventListener("submit", async function (event) {

    event.preventDefault();

    const dni = dniInput.value.trim();

    if (dni === "") {
        alert("Ingrese un DNI.");
        return;
    }

    try {

        // 1. Buscar el cliente por DNI
        const respuestaCliente = await fetch(
            `${CLIENTES_URL}/dni/${dni}`
        );

        if (!respuestaCliente.ok) {
            throw new Error("Cliente no encontrado");
        }

        const cliente = await respuestaCliente.json();

        // 2. Confirmar la baja
        const confirmar = confirm(
            `¿Está seguro de que desea dar de baja a ${cliente.nombre} ${cliente.apellido}?\n\n` +
            `También se darán de baja todas sus inscripciones.`
        );

        if (!confirmar) {
            return;
        }

        // 3. Dar de baja al cliente
        const respuestaBaja = await fetch(
            `${CLIENTES_URL}/${cliente.id}/baja`,
            {
                method: "PUT"
            }
        );

        if (!respuestaBaja.ok) {
            throw new Error("No se pudo dar de baja el usuario");
        }

        alert(
            `El usuario ${cliente.nombre} ${cliente.apellido} ` +
            `fue dado de baja correctamente.`
        );

        // Limpiar formulario
        dniInput.value = "";

    } catch (error) {

        console.error(error);
        alert("No se encontró un usuario con ese DNI.");
    }
});