# API de Registro de Usuarios

Este proyecto implementa una API RESTful para la gestión de usuarios, desarrollada con Spring Boot. La API facilita el registro de nuevos usuarios y el almacenamiento de su información, incluyendo detalles de contacto.

## Tecnologías Utilizadas

- **Spring Boot 3:** Framework para el desarrollo de aplicaciones Spring, facilitando la configuración y despliegue.
- **JDK 17:** Versión del Kit de Desarrollo de Java utilizada, asegurando modernidad y eficiencia en el código.

## Configuración del Proyecto

Para poner en marcha este proyecto en tu entorno local, sigue los siguientes pasos:

1. **Clonación del Repositorio:** Clona el repositorio en tu máquina local usando `git clone [URL del repositorio]`.
2. **Preparación del Entorno:**
   - Asegúrate de tener instalado el JDK 17.
   - Instala Maven para gestionar las dependencias y la construcción del proyecto.
3. **Configuración del IDE:**
   - Abre el proyecto en tu IDE preferido.
   - Configura el IDE para reconocer Maven como el sistema de construcción.
4. **Acceso a Swagger:**
   - Una vez el proyecto esté en ejecución, puedes probar los endpoints a través de Swagger en esta URL: http://localhost:8080/swagger-ui/index.html.

## Autenticación y Uso de la API

Para utilizar los servicios protegidos de esta API, debes seguir estos pasos:

1. **Generación de Token JWT:**
   - Utiliza el servicio `/auth/login` para generar un token. Ejemplo de solicitud:
     ```json
     {
       "username": "ccanoles@bci.cl",
       "password": "somosSocius123"
     }
     ```
   - Este servicio te proporcionará un token de acceso JWT que deberás usar en las siguientes solicitudes.

2. **Uso del Token:**
   - Añade el token JWT en la cabecera `Authorization` como `Bearer Token` al realizar llamadas a los siguientes endpoints:
     - `/usuario/agregar`
     - `/usuario/buscar`

## Ejecución del Proyecto

Para ejecutar el proyecto utilizando Maven, ejecuta el siguiente comando en la terminal:

```bash
mvn spring-boot:run
