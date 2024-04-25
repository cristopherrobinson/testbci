# Usa la imagen oficial de Java 17 como base
FROM openjdk:17-jdk-slim as build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Instala Maven
RUN apt-get update && \
    apt-get install -y maven

# Copia el archivo pom.xml y descarga las dependencias para aprovechar la caché de Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el resto de tu proyecto
COPY src src

# Construye la aplicación usando Maven
RUN mvn package -DskipTests

# Usa otra vez la imagen base de Java para mantener el contenedor ligero
FROM openjdk:17-jdk-slim

# Copia el JAR desde el primer paso
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto en el que se ejecutará la aplicación
EXPOSE 8084

# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]