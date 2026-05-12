# MyPilot API

## Requisitos
- Java 17+
- Maven (o usar `mvnw`)

## Configuracion
- JWT secret en `src/main/resources/application.properties`:
  - `jwt.secret=mypilot_secret_key_2026`

## Ejecutar
```powershell
cd C:\Users\madrid\IdeaProjects\MyPilotAPI\api
.\mvnw spring-boot:run
```

## Tests
```powershell
cd C:\Users\madrid\IdeaProjects\MyPilotAPI\api
.\mvnw test
```

## Login (JWT)
`POST /api/auth/login`

Body:
```json
{
  "email": "correo@ejemplo.com",
  "password": "tu_password"
}
```

Respuesta:
```json
{
  "token": "<jwt>",
  "id": 1,
  "nombre": "Nombre",
  "apellido": "Apellido",
  "rol": "VIAJERO"
}
```

> Nota: los passwords en BD deben estar hasheados con BCrypt para que el login funcione.

