# Use uma imagem base do JDK 17 (ou a versão do JDK que seu app requer)
FROM openjdk:17-jdk-slim

# Cria um diretório para o app
WORKDIR /app

# Copia o arquivo gourmet-inventory-project.jar diretamente para dentro do contêiner
COPY gourmet-inventory-project.jar /app/gourmet-inventory-project.jar

# Defina valores padrão para as variáveis de ambiente AWS
ENV aws_region=us-east-1
ENV aws_s3_bucket=gourmet-inventory-bucket

# Exponha a porta 8080 (ou a porta do seu app)
EXPOSE 8080

# Define o comando para iniciar o app
CMD ["java", "-jar", "/app/gourmet-inventory-project.jar"]

