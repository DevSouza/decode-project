Observação: No minuto 00:58, a indentação da propriedade 'cloud' deve ficar da seguinte maneira (mesmo nível que application e config):

```
spring:
  application:
    name: ead-notification-service
  config:
    import: 'configserver:'
  cloud:
    config:
      discovery:
        serviceId: ead-config-server
```