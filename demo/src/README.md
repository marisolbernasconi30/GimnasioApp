# 🏋️ Sistema de Gestión de Gimnasio

Aplicación full-stack para la administración integral de un gimnasio: gestión de socios, inscripciones a distintas disciplinas y control de pagos mensuales, con autenticación por roles.

## 📋 Descripción

El sistema permite llevar el control diario de un gimnasio: alta de clientes, inscripción a distintos tipos de entrenamiento, y registro de pagos. Cada inscripción calcula automáticamente su estado según la fecha del último pago:

- 🟢 **Al día** — el pago está vigente.
- 🟡 **Por vencer** — quedan 7 días o menos para el vencimiento.
- 🔴 **Vencida** — pasó la fecha de vencimiento sin un nuevo pago.

Este cálculo se hace siempre en el momento de la consulta (nunca se guarda como un valor fijo), por lo que el estado nunca queda desactualizado respecto a los pagos reales.

## ✨ Funcionalidades principales

- **Gestión de clientes**: alta, edición, búsqueda por DNI, baja lógica (con baja en cascada de sus inscripciones).
- **Gestión de inscripciones**: alta por tipo de entrenamiento, listado paginado de activas/inactivas, filtrado por estado de pago (al día / por vencer / vencidas), cancelación individual.
- **Gestión de pagos**: registro de pagos por inscripción, historial de pagos, validación para no permitir pagar una inscripción que ya está al día.
- **Panel de estadísticas** (solo administradores): distribución de socios por tipo de entrenamiento, altas y bajas del mes, recaudación mensual, con detalle de inscriptos al hacer clic en cada disciplina.
- **Autenticación y autorización**: login con JWT y dos roles diferenciados:
  - **ADMIN** (dueño/gerente): acceso total, incluyendo estadísticas y gestión de usuarios del sistema.
  - **USER** (recepcionista): operación diaria — clientes, inscripciones y pagos — sin acceso a estadísticas ni a la gestión de usuarios.

## 🛠️ Tecnologías

**Backend**
- Java 21
- Spring Boot (Web, Data JPA, Security, Validation)
- Spring Security + JWT (JJWT)
- MySQL
- Maven

**Frontend**
- HTML5 / CSS3
- JavaScript (ES Modules), sin frameworks

## 🔐 Seguridad

- Autenticación stateless con JSON Web Tokens.
- Contraseñas hasheadas con BCrypt.
- Autorización por rol a nivel de método (`@PreAuthorize`) en los endpoints sensibles.
- Un usuario administrador inicial se crea automáticamente al arrancar la aplicación por primera vez (ver sección de instalación).

## 📁 Estructura del proyecto

```
src/main/java/com/example/demo/
├── controller/       # Endpoints REST
│   └── login/        # Endpoints de autenticación y usuarios
├── service/          # Lógica de negocio
│   └── login/
├── repository/        # Acceso a datos (Spring Data JPA)
│   └── login/
├── entity/            # Entidades JPA
│   ├── enums/
│   └── login/
├── dto/               # Objetos de transferencia (request/response)
│   └── login/
├── jwt/               # Filtro y servicio de JWT
└── config/            # Configuración de Spring Security
    └── login/
```

## 🚀 Cómo correrlo localmente

### Requisitos previos
- JDK 21
- MySQL
- Maven

### Backend

1. Cloná el repositorio.
2. Creá una base de datos en MySQL (por ejemplo `gym_management_system`).
3. Configurá las variables de entorno con tus credenciales de MySQL:
   ```
   DB_HOST=jdbc:mysql://localhost:3306/
   DB_USERNAME=tu_usuario
   DB_PASSWORD=tu_contraseña
   ```
4. Ejecutá la aplicación (por ejemplo, desde tu IDE o con `mvn spring-boot:run`).
5. Al arrancar por primera vez, el sistema crea automáticamente un usuario administrador:
   ```
   username: admin
   password: cambiar123
   ```
   Se recomienda cambiar esta contraseña o crear un nuevo administrador y dar de baja este usuario inicial una vez en producción.

### Frontend

1. Abrí la carpeta del frontend con una extensión de servidor local (por ejemplo, Live Server de VS Code).
2. Abrí `Login.html` para iniciar sesión.
3. Verificá que la URL del backend en `js/config.js` coincida con donde esté corriendo tu servidor (por defecto `http://localhost:8080`).

## 📌 Endpoints principales

| Método | Endpoint | Descripción | Acceso |
|---|---|---|---|
| POST | `/auth/login` | Inicio de sesión | Público |
| GET | `/clientes` | Listar clientes activos | Autenticado |
| GET | `/clientes/dni/{dni}` | Buscar cliente por DNI | Autenticado |
| POST | `/clientes` | Crear cliente | Autenticado |
| PUT | `/clientes/{id}/baja` | Dar de baja un cliente | Autenticado |
| GET | `/inscripciones/cliente/{id}` | Inscripciones de un cliente | Autenticado |
| POST | `/inscripciones` | Crear inscripción | Autenticado |
| GET | `/inscripciones/vencidas` \| `/por-vencer` \| `/al-dia` | Filtrar por estado de pago | Autenticado |
| POST | `/pagos` | Registrar un pago | Autenticado |
| GET | `/pagos/inscripcion/{id}` | Historial de pagos de una inscripción | Autenticado |
| GET | `/estadisticas` | Panel de estadísticas | **Solo ADMIN** |
| GET / POST | `/usuarios` | Listar / crear usuarios del sistema | **Solo ADMIN** |

## 🧠 Decisiones de diseño destacadas

- **Separación DTO / Entity**: las entidades JPA nunca se exponen directamente en la API; cada endpoint tiene su propio DTO de entrada y salida, evitando filtrar datos sensibles (por ejemplo, contraseñas) o acoplar la API a la estructura de la base de datos.
- **Estado calculado, no persistido**: el estado de pago de una inscripción se calcula en el momento a partir de la fecha de vencimiento, en un único punto del código (`PagoEstadoCalculator`), evitando inconsistencias entre distintas partes del sistema.
- **Roles a nivel de método**: el control de acceso por rol se aplica con `@PreAuthorize` directamente sobre los métodos de los controllers, manteniendo la configuración de seguridad centralizada y explícita.

## 🔜 Posibles mejoras futuras

- Mover la clave secreta de JWT a una variable de entorno en lugar de tenerla en el código.
- Soporte para pagos de meses acumulados (hoy el sistema asume que se paga mes a mes, sin deuda acumulada).
- Tests automatizados (unitarios y de integración).
- Despliegue en un entorno productivo (Render/Railway + Vercel/Netlify, por ejemplo).

## 👤 Autor

Proyecto personal desarrollado como parte de mi portfolio, en el marco de mis estudios de Ingeniería en Sistemas.