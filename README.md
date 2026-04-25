# E-Commerce API — Spring Boot

API REST de comercio electrónico con autenticación JWT, control de roles, gestión de inventario y auditoría de acciones.

---

## Tecnologías utilizadas

| Capa | Tecnología |
|------|------------|
| Lenguaje | Java 25 |
| Framework | Spring Boot 3.x |
| Seguridad | Spring Security + JWT (jjwt) |
| Persistencia | Spring Data JPA + Hibernate |
| Base de datos | PostgreSQL 18 (Docker) |
| Encriptación | BCrypt |
| Utilidades | Lombok |

---

## Estructura del proyecto

```
com.taller.ecommerce
├── controller
│   ├── OrdenController
│   ├── ProductoController
│   └── UsuarioController
├── dto
│   ├── AuthResponseDTO
│   ├── ErrorResponse
│   ├── ItemOrdenRequestDTO / ItemOrdenResponseDTO
│   ├── LoginDTO
│   ├── OrdenRequestDTO / OrdenResponseDTO
│   ├── ProductoRequestDTO / ProductoResponseDTO
│   └── UsuarioRegistroDTO / UsuarioRequestDTO
├── exception
│   ├── CredencialesInvalidasException
│   ├── GlobalExceptionHandler
│   └── StockInsuficienteException
├── model
│   ├── Auditoria
│   ├── ItemOrden
│   ├── Orden
│   ├── Producto
│   ├── Proveedor
│   ├── Rol
│   └── Usuario
├── repository
│   ├── AuditoriaRepository
│   ├── ItemOrdenRepository
│   ├── OrdenRepository
│   ├── ProductoRepository
│   ├── ProveedorRepository
│   ├── RolRepository
│   └── UsuarioRepository
├── security
│   ├── CustomAccessDeniedHandler
│   ├── JwtFilter
│   ├── JwtService
│   └── SecurityConfig
└── service
    ├── AuditoriaService
    ├── OrdenService
    ├── ProductoService
    └── UsuarioService
```

---

## Requisitos previos

- Java 25+
- Docker y Docker Compose
- Maven 3.8+

---

## Base de datos con Docker

La base de datos corre en PostgreSQL 18 dentro de Docker. Levántala con:

```bash
docker compose up -d
```

El archivo `dump.sql` incluido contiene toda la estructura y datos de prueba. Se carga automáticamente al iniciar el contenedor por primera vez.

> ⚠️ Si el volumen ya existe en tu máquina, corre `docker compose down -v` primero para partir desde cero.

Para detenerla:
```bash
docker compose down
```

---

## Configuración de la aplicación

Edita `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---


La API queda disponible en: `http://localhost:8080`

---

## Credenciales de prueba

### Login ADMIN
```
POST /api/usuarios/login
```
```json
{
  "correo": "admin@test.com",
  "contraseña": "1234"
}
```

### Login CLIENTE
```
POST /api/usuarios/login
```
```json
{
  "correo": "luis@test.com",
  "contraseña": "1234"
}
```

El token retornado se usa en todas las peticiones protegidas:
```
Authorization: Bearer <token>
```

---

## Endpoints

### Usuarios — público
| Método | Endpoint |
|--------|----------|
| POST | `/api/usuarios/register` |
| POST | `/api/usuarios/login` |

### Productos — solo ADMIN
| Método | Endpoint |
|--------|----------|
| GET | `/api/productos` |
| GET | `/api/productos/{id}` |
| POST | `/api/productos` |
| PUT | `/api/productos/{id}` |
| DELETE | `/api/productos/{id}` |

### Órdenes — solo CLIENTE
| Método | Endpoint |
|--------|----------|
| POST | `/api/ordenes` |
| GET | `/api/ordenes/usuario/{id}` |

---

## Decisiones técnicas

- **Arquitectura en capas**: Controller → Service → Repository, con separación clara de responsabilidades.
- **JWT stateless**: no se usan sesiones HTTP, cada petición se autentica con el token.
- **BCrypt**: las contraseñas nunca se almacenan en texto plano.
- **Optimistic Locking (`@Version`)**: previene condiciones de carrera al actualizar el stock de productos.
- **Soft Delete**: los productos se marcan como `activo = false` en vez de eliminarse físicamente.
- **Auditoría centralizada**: login exitoso/fallido, creación/actualización de productos, creación de órdenes y accesos denegados quedan registrados en la tabla `auditoria`.
- **Roles con Spring Security**: `ADMIN` gestiona productos, `CLIENTE` gestiona órdenes.
