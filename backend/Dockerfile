# Imagem oficial do OpenJDK 17
FROM openjdk:17-oracle

# Configurações do aplicativo e diretório de trabalho
WORKDIR /app
COPY target/transferencia-service.jar app.jar

# Exponha a porta em que o aplicativo Spring Boot será executado dentro do contêiner
EXPOSE 8082

# Comando para iniciar o aplicativo quando o contêiner for iniciado
CMD ["java", "-jar", "app.jar"]
