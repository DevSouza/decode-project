### Comando terminal

Command line: ```mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8085```

### Gerar as portas de forma automática pra cada microserviço quando subir no service registry

```yml 
server:
  port: ${PORT:0}


eureka:
  instance:
    instance-id: ${spring.application.name}:${random.value}
```