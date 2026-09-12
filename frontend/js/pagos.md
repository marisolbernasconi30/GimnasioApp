El frontend no debería decidir cosas como:

if (pago.monto < 0) ...
if (cliente.estaActivo) ...
if (yaPagoEsteMes) ...

Algunas validaciones simples pueden hacerse en frontend para mejorar la experiencia, pero las reglas importantes deben estar en tu PagoService de Spring Boot.

             FRONTEND
                │
          pagos.js
                │
             fetch()
                │
                ▼
          PagoController
                │
                ▼
           PagoService
                │
                ▼
          PagoRepository
                │
                ▼
              MySQL

Así pagos.js básicamente se encarga de:

"Le pido datos al backend y muestro esos datos en pantalla."

Y Spring Boot se encarga de:

"Determino si ese pago es válido y lo guardo."

Y si vas a tener paginación

Yo no la metería todavía dentro de listarPagos() hasta que tengas definido exactamente cómo quedó tu PagoController.

Cuando tengas algo como:

GET /pagos?page=0&size=10

ahí sí hacemos:


async function listarPagos(pagina = 0, cantidad = 10) {
    ...
}

y agregamos:

← Anterior    Página 1    Siguiente →

Eso te va a quedar mucho más limpio si primero dejamos bien armado Controller → Service → Repository y después conectamos la paginación del frontend.