# Use a imagem base do OpenJDK
FROM openjdk:11-jre-slim

# Copie o arquivo JAR da compilação do Maven para o contêiner
COPY target/app-0.0.1-SNAPSHOT.jar /app/app.jar

# Defina o diretório de trabalho
WORKDIR /app

# Exponha a porta 8080 para fora do contêiner
EXPOSE 8080

# Comando para iniciar a aplicação Spring Boot quando o contêiner for iniciado
CMD ["java", "-jar", "app.jar"]