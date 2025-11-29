# **Catálogo de Eventos - Tiquetera Online (v1.0)**  
*Primera versión funcional con API REST, arquitectura por capas, almacenamiento en memoria y documentación OpenAPI*

---

## Objetivo de la Historia de Usuario (HU)

Construir la **primera versión del catálogo de la tiquetera online**, permitiendo **gestionar Eventos y Lugares (Venues)** desde una **API REST**, aplicando:

- Arquitectura por capas  
- Almacenamiento en memoria temporal  
- Documentación OpenAPI (Swagger UI)  
- Validaciones y manejo de códigos HTTP

---

## Estructura del Proyecto (Arquitectura por Capas)

```bash
src/main/java/com/codeup/catalogoDeEventos/
├── controller/
│   ├── EventController.java
│   └── LugarController.java
├── domain/
│   ├── Evento.java
│   └── Lugar.java
├── dto/
│   ├── EventoRequest.java
│   ├── EventoDetalleResponse.java
│   └── LugarRequest.java
├── service/
│   ├── EventoService.java
│   └── LugarService.java
├── advice/
│   ├── ResourceNotFoundException.java
│   └── ErrorResponse.java
└── CatalogoDeEventosApplication.java
```

> **Nota**: No se usa `repository` aún → se simula con `List<T>` en los servicios.

---

## Endpoints Disponibles

### Eventos (`/api/evento`)

| Método | Endpoint             | Descripción                     |
|--------|----------------------|---------------------------------|
| `POST`   | `/api/evento`          | Crear evento                    |
| `GET`    | `/api/evento`          | Listar todos los eventos        |
| `GET`    | `/api/evento/{id}`     | Obtener evento por ID           |
| `GET`    | `/api/evento/{id}/detalle` | Detalle con info del venue |
| `PUT`    | `/api/evento/{id}`     | Actualizar evento               |
| `DELETE` | `/api/evento/{id}`     | Eliminar evento                 |

---

### Lugares / Venues (`/api/venue`)

| Método | Endpoint             | Descripción                     |
|--------|----------------------|---------------------------------|
| `POST`   | `/api/venue`           | Crear venue                     |
| `GET`    | `/api/venue`           | Listar todos los lugares        |
| `GET`    | `/api/venue/{id}`      | Obtener venue por ID            |
| `PUT`    | `/api/venue/{id}`      | Actualizar venue                |
| `DELETE` | `/api/venue/{id}`      | Eliminar venue                  |

---

## DTOs (Data Transfer Objects)

### `EventoRequest.java`
```json
{
  "nombre": "Concierto de Rock",
  "descripcion": "Evento musical",
  "fecha": "2025-12-25T20:00:00",
  "capacidad": 200,
  "idLugar": 1,
  "precio": 1500.0
}
```

### `LugarRequest.java`
```json
{
  "nombre": "Teatro Municipal",
  "direccion": "Calle Falsa 123",
  "ciudad": "Bogotá",
  "pais": "Colombia",
  "capacidad": 500
}
```

---

## Validaciones Implementadas

| Campo         | Validación                             |
|---------------|----------------------------------------|
| `nombre`      | `@NotBlank` → obligatorio y no vacío   |
| `direccion`   | `@NotBlank` → obligatorio              |
| `ciudad`      | `@NotBlank` → obligatorio              |
| `pais`        | `@NotBlank` → obligatorio              |
| `capacidad`   | `@NotNull` + `@Positive`               |
| `fecha`       | `@NotNull` + `@FutureOrPresent`        |
| `idLugar`     | `@NotNull`                             |

> **Errores 400** devuelven JSON claro con mensajes en español.

---

## Códigos HTTP Adecuados

| Acción       | Código HTTP | Body de Respuesta                     |
|--------------|-------------|---------------------------------------|
| Crear        | `201 Created` | Objeto creado                         |
| Listar       | `200 OK`      | Lista de objetos                      |
| Obtener      | `200 OK`      | Objeto encontrado                     |
| Actualizar   | `200 OK`      | Objeto actualizado                    |
| Eliminar     | `204 No Content` | Sin cuerpo                         |
| No encontrado| `404 Not Found` | `ErrorResponse` con mensaje         |
| Datos inválidos | `400 Bad Request` | Mapa de errores por campo        |

---

## Documentación OpenAPI (Swagger UI)

**Acceso**:  
`http://localhost:8080/swagger-ui.html`

**Características**:
- Descripciones claras por endpoint
- Ejemplos de `request` y `response`
- Modelos (`Evento`, `Lugar`, `ErrorResponse`)
- Parámetros y códigos de estado documentados
- Soporte para pruebas interactivas

---

## Almacenamiento en Memoria

- Datos persisten **solo durante la ejecución**
- `List<Evento>` y `List<Lugar>` en servicios
- IDs generados automáticamente (secuencial)
- Ideal para pruebas y desarrollo inicial

---



## Demostración (Cierre de Actividad)

| Acción | Herramienta | Evidencia |
|-------|-------------|---------|
| Ejecutar API | `mvn spring-boot:run` | Servidor en `localhost:8080` |
| Acceder a Swagger | Navegador | `swagger-ui.html` |
| Probar CRUD | Swagger UI | Capturas de POST, GET, PUT, DELETE |
| Ver errores | Swagger / Postman | 400 y 404 con mensajes claros |

---

## Criterios de Aceptación (Cumplidos)

| Criterio | Estado |
|--------|--------|
| CRUD completo sin persistencia | Completed |
| API cumple reglas REST | Completed |
| Swagger UI navegable y documentado | Completed |
| Arquitectura por capas | Completed |
| Registro en memoria durante ejecución | Completed |

---

## Próximos Pasos (Siguientes Iteraciones)

1. **Persistencia con JPA + H2/MySQL**
2. **Relaciones bidireccionales Evento ↔ Lugar**
3. **Búsquedas avanzadas (por fecha, ciudad, precio)**
4. **Paginación y ordenamiento**
5. **Seguridad (JWT, roles)**
6. **Pruebas unitarias e integradas**

---

## Tecnologías Usadas

| Tecnología        | Versión |
|-------------------|--------|
| Java              | 17+    |
| Spring Boot       | 3.2+   |
| Springdoc OpenAPI | 2.0+   |
| Lombok            | 1.18+  |
| Jakarta Validation| 3.0+   |

---

## Autor

**Estudiante**:  Rogelis Garcia  
**Fecha**: 03 de noviembre de 2025  

---

> **¡API lista para demo y evolución!**  
> Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

--- 
