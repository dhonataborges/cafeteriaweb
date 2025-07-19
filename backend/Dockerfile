# Etapa 1: build com Maven
FROM maven:3.9.6-eclipse-temurin-17 as build

# Define o diretório de trabalho no container
WORKDIR /app

# Copia os arquivos do projeto
COPY pom.xml .
COPY src ./src

# Executa o build com Maven (sem rodar testes)
RUN mvn clean package -DskipTests

# Etapa 2: imagem final para rodar a aplicação
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expondo a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]