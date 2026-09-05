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
                                <td>${usuario.edad}</td>
                                <td>${usuario.celular}</td>
                                <td>${usuario.domicilio}</td>
                                <td>${usuario.lesion}</td>
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
                                <td>${usuario.apellido}</td>
                                <td>${usuario.dni}</td>
                                <td>${usuario.edad}</td>
                                <td>${usuario.celular}</td>
                                <td>${usuario.domicilio}</td>
                                <td>${usuario.lesion}</td>
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
    const fechaNacimiento = document.getElementById("fechaNacimiento").value;
    const dni = document.getElementById("dni").value;
    const celular = document.getElementById("celular").value;
    const direccion = document.getElementById("direccion").value;
    const lesion = document.getElementById("lesion").value;

    const cliente = {
        nombre,
        apellido,
        fechaNacimiento,
        dni,
        celular,
        direccion,
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
    
