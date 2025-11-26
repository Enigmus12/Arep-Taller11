Además de los documentos entregados, también se incluye el código fuente del proyecto y un video demostrativo donde se evidencia el funcionamiento del sistema. En el video se muestra el proceso de autenticación, el registro del usuario y la interacción entre el frontend y el backend, garantizando así la verificación práctica del desarrollo realizado.

## 1. High-Level Architecture 

### Arquitectura lógica 

la solucion implementa una plataforma web con autenticación gestionada mediante Amazon Cognito e integración con proveedores externos (Google, Facebook y Amazon). La arquitectura se organiza en tres capas principales: Frontend, Backend y Base de Datos.

### Componentes

**Frontend (React + TypeScript)**

- Desarrollado con React TSX.
- Maneja el flujo de autenticación.
- Se conecta con Amazon Cognito mediante OIDC para:
  - Login manual (usuario y contraseña)
  - Login con Google
  - Login con Facebook
  - Login con Amazon
- Tras autenticación, el usuario es redirigido a una vista interna donde se valida si ya existe en el backend.
- Si es nuevo, envía los datos al backend para registrarlo.
**Backend (Spring Boot – Java)**

- Expone un endpoint principal:
  - POST /users → registra un usuario autenticado en la base de datos.
- Maneja la lógica asociada al almacenamiento de datos.
- Recibe la información del usuario proveniente del frontend después del login.
- Valida y persiste la información en la base de datos.
- Arquitectura modular simple pero extensible a microservicios si el sistema creciera.

**Base de Datos (MongoDB)**

- Se almacena cada usuario al primer registro.
- Estructura NoSQL flexible para crecimiento futuro.
- Modelo de datos simple: id, nombre, email, proveedorAuth, fechaRegistro.

**Servicios externos**

- Amazon Cognito para autenticación.
- Proveedores de identidad (IdP):
  - Google
  - Facebook
  - Amazon

### Flujo principal

1. El usuario abre el frontend React.
2. Escoge iniciar sesión manual o mediante Google/Facebook/Amazon.
3. Cognito autentica y devuelve el token.
4. El frontend verifica si el usuario existe → si no existe, llama al backend.
5. Spring Boot guarda el usuario en MongoDB.
6. El usuario es redirigido a una página interna que confirma el acceso.

## 2. Product Backlog 

### Épica 1: Autenticación de Usuarios 

- US-001 (5 pts): Como usuario, quiero registrarme manualmente para acceder al sistema
- US-002 (3 pts): Como usuario, quiero iniciar sesión con mi email/contraseña
- US-003 (8 pts): Como usuario, quiero autenticarme con Google/Facebook/Amazon para acceso rápido
- US-004 (2 pts): Como usuario registrado, quiero ver una página de bienvenida

**Subtotal implementado:** ~18 story points

---

## Lo que FALTA para el sistema municipal completo

### Épica 2: Gestión de Solicitudes Ciudadanas

- US-005 (8 pts): Como ciudadano, quiero reportar un problema (bache, alumbrado, basura) con descripción y ubicación
- US-006 (5 pts): Como ciudadano, quiero adjuntar fotos a mi reporte
- US-007 (3 pts): Como ciudadano, quiero ver el listado de mis solicitudes
- US-008 (5 pts): Como ciudadano, quiero ver el estado de cada solicitud (pendiente, en proceso, resuelta)
- US-009 (3 pts): Como ciudadano, quiero recibir notificaciones de cambios de estado

### Épica 3: Panel Administrativo

- US-010 (5 pts): Como administrador, quiero ver todas las solicitudes pendientes
- US-011 (8 pts): Como administrador, quiero asignar solicitudes a departamentos
- US-012 (5 pts): Como administrador, quiero cambiar el estado de una solicitud
- US-013 (3 pts): Como administrador, quiero filtrar solicitudes por tipo/fecha/estado
- US-014 (8 pts): Como administrador, quiero ver reportes de tiempos de resolución

### Épica 4: Comunicación

- US-015 (5 pts): Como ciudadano, quiero comentar en mi solicitud
- US-016 (5 pts): Como administrador, quiero responder comentarios

### Épica 5: Geolocalización

- US-017 (8 pts): Como ciudadano, quiero marcar la ubicación en un mapa
- US-018 (5 pts): Como administrador, quiero ver solicitudes en un mapa

**Total backlog pendiente:** ~68 story points  
**Total proyecto completo:** ~86 story points

---

## 3. Presupuesto de Desarrollo

### Supuestos

- Velocidad del equipo: 20 story points por sprint (2 semanas)
- Duración sprint: 2 semanas
- Trabajo completado: 18 story points (1 sprint aprox.)
- Trabajo pendiente: 68 story points

### Composición del Equipo

| Rol                     | Cantidad | Tarifa/semana | Costo por sprint (2 sem) |
| ----------------------- | -------- | ------------- | ------------------------ |
| Frontend Dev (React)    | 1        | $800          | $1,600                   |
| Backend Dev (Java)      | 1        | $900          | $1,800                   |
| Full Stack Dev          | 1        | $850          | $1,700                   |
| QA Engineer             | 1        | $600          | $1,200                   |
| Product Manager         | 0.5      | $1,000        | $1,000                   |
| DevOps                  | 0.5      | $900          | $900                     |
| **TOTAL por sprint**    |          |               | **$8,200**               |

### Cálculo de Sprints

**Sprints necesarios = Story points pendientes / Velocidad**

Sprints necesarios = 68 / 20 = 3.4 sprints ≈ **4 sprints**

### Costo total del proyecto

4 sprints × $8,200 = **$32,800**

### Con contingencia del 15%

→ $32,800 × 1.15 = **$37,720**