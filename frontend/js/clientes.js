const API_URL = "http://localhost:8080/clientes"; 
 
 // Obtener clientes activos
        function listarClientes() {

            fetch("http://localhost:8080/clientes") 
                .then(response => response.json())
                .then(usuarios => {

                    console.log(usuarios);

                    const tabla = document.getElementById("usuarios");

                    tabla.innerHTML = "";

                    usuarios.forEach(usuario => {

                        tabla.innerHTML += `
                            <tr>
                                <td>${usuario.nombre}</td>
                                <td>${usuario.apellido}</td>
                                <td>${usuario.dni}</td>
                                <td>${usuario.fecha_nacimiento}</td>
                                <td>${usuario.celular}</td>
                                <td>${usuario.domicilio}</td>
                                <td>${usuario.lesion}</td>
                                <td>
                                    <button class="btn-editar" data-id="${usuario.id}">
                                         Editar
                                    </button>
                                </td>

                            </tr>
                        `;

                    });

                })
                .catch(error => {
                    console.error("Error obteniendo usuarios:", error);
                });
            }
        // Obtener clientes inactivos   
 function listarClientesInactivos() {

            fetch("http://localhost:8080/clientes/baja") 
                .then(response => response.json())
                .then(usuarios => {

                    console.log(usuarios);

                    const tabla = document.getElementById("usuarios");

                    tabla.innerHTML = "";

                    usuarios.forEach(usuario => {

                        tabla.innerHTML += `
                            <tr>
                                <td>${usuario.nombre}</td>
                                <td>${usuario.apido}</td>
                                <td>${usuario.dni}</td>
                                <td>${usuario.fecha_nacimiento}</td>
                                <td>${usuario.celular}</td>
                                <td>${usuario.domicilio}</td>
                                <td>${usuario.lesion}</td>
                                <td>
                                    <button class="btn-editar" data-id="${usuario.id}">
                                        Editar
                                    </button>
                                </td>   
                            </tr>
                        `;

                    });

                })
                .catch(error => {
                    console.error("Error obteniendo usuarios:", error);
                });
            }

//POST

function crearCliente() {

    const nombre = document.getElementById("nombre").value;
    const apellido = document.getElementById("apellido").value;
    const fecha_nacimiento = document.getElementById("fecha_nacimiento").value;
    const dni = document.getElementById("dni").value;
    const celular = document.getElementById("celular").value;
    const domicilio = document.getElementById("domicilio").value;
    const lesion = document.getElementById("lesion").value;

    const cliente = {
        nombre,
        apellido,
        fecha_nacimiento,
        dni,
        celular,
        domicilio,
        lesion
    };

    console.log("Voy a enviar:", cliente);

    fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(cliente)
    })
    .then(response => response.json())
    .then(data => {
        console.log("Cliente creado:", data);
    })
    .catch(error => {
        console.error("Error:", error);
    });
}

//PUT EDITAR CLIENTE
function editarCliente() {


}
