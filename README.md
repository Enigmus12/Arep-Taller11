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

## 2. Product Backlog basado en tu proyecto real

### Épica 1 — Autenticación

- US-1: Como usuario quiero iniciar sesión manualmente usando email y contraseña. → 5 SP
- US-2: Como usuario quiero iniciar sesión con Google para no crear nuevas credenciales. → 5 SP
- US-3: Como usuario quiero iniciar sesión con Facebook. → 5 SP
- US-4: Como usuario quiero iniciar sesión con Amazon. → 5 SP
- US-5: Como sistema quiero recibir el token de Cognito y validar acceso del usuario. → 3 SP
### Épica 2 — Registro de Usuarios

- US-6: Como usuario quiero que el sistema me registre automáticamente tras mi primer login. → 3 SP
- US-7: Como backend quiero recibir datos del usuario autenticado y guardarlos en MongoDB. → 5 SP
- US-8: Como sistema quiero comprobar si el usuario ya existe para evitar duplicados. → 3 SP
### Épica 3 — Página interna

- US-9: Como usuario autenticado quiero ver una pantalla de bienvenida con mi estado. → 1 SP
- US-10: Como sistema quiero redirigir al usuario autenticado a la página privada. → 2 SP
### Épica 4 — Infraestructura básica

- US-11: Configurar conexión segura Cognito ↔ Frontend (OAuth). → 5 SP
- US-12: Configurar API Spring Boot → MongoDB. → 3 SP
**Total story points:**

5+5+5+5+3+3+5+3+1+2+5+3 = 45 story points

## 3. Presupuesto Scrum basado en la estimación

### Supuestos

- Sprint: 2 semanas
- Team velocity inicial: 25–30 SP por sprint
- Tu backlog: 45 SP

### Número de sprints necesarios

45 SP / 30 SP por sprint ≈ 1.5 sprints → se redondea a 2 sprints

### Costos de roles por sprint (ejemplo mínimo viable)

| Rol                  | Costo/Hora | Horas/Sprint | Total por sprint |
| -------------------- | ---------- | ------------ | ---------------- |
| Frontend Dev         | $35        | 80 h         | $2,800           |
| Backend Dev          | $40        | 80 h         | $3,200           |
| QA (mínimo)          | $25        | 40 h         | $1,000           |
| PM/PO                | $50        | 20 h         | $1,000           |
| **Total por sprint** |            |              | **$8,000**       |

### Costo total del proyecto

2 sprints × $8,000 = $16,000

### Con contingencia del 15%

→ $16,000 × 1.15 = $18,400