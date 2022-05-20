[Spring AMQP](https://docs.spring.io/spring-amqp/docs/current/reference/html/)  
[CloudAMQP](https://www.cloudamqp.com/)  

### Materiais Complementares

[authuser.zip](./authuser.zip)  
[course.zip](./course.zip)

### RabbitMQ Docker

1. Criar um arquivo chamado ```docker-compose.yml```([RabbitMQ.zip](./RabbitMQ.zip)) com o seguinte conteudo.
```yml
version: "3.2"
services:
  rabbitmq:
    image: rabbitmq:3-management-alpine
    container_name: 'rabbitmq'
    environment:
      - RABBITMQ_DEFAULT_USER=admin
      - RABBITMQ_DEFAULT_PASS=admin
    ports:
      - 5672:5672
      - 15672:15672
    volumes:
      - ~/.docker-conf/rabbitmq/data/:/var/lib/rabbitmq/
      - ~/.docker-conf/rabbitmq/log/:/var/log/rabbitmq
    networks:
      - rabbitmq_go_net

networks:
  rabbitmq_go_net:
    driver: bridge
```

2. abrir um terminal na pasta que o arquivo acima foi criado e rodar o comando ```docker-compose up```