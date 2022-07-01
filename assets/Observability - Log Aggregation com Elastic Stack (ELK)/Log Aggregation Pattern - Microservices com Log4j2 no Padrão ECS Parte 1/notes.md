### Links 
[Log4j2 Vulnerability](https://spring.io/blog/2021/12/10/log4j2-vulnerability-and-spring-boot)  
[CVE-2021-44228](https://nvd.nist.gov/vuln/detail/CVE-2021-44228)  
[Spring Boot Logging Doc](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.logging.file-rotation)  
[ECS Java Logging Reference](https://www.elastic.co/guide/en/ecs-logging/java/current/index.html)  
[ECS Logging Introduction](https://www.elastic.co/guide/en/ecs-logging/overview/master/intro.html)  
[ECS Overview](https://www.elastic.co/guide/en/ecs/1.12/ecs-reference.html)  
[ECS Logging Java Get Start](https://www.elastic.co/guide/en/ecs-logging/java/1.x/setup.html)  

### Comandos

```cmd
Command Start Filebeat: .\filebeat.exe -c filebeat.yml -e
```

### Criar Index Kibana

1. No menu lateral clique em Analytics => Discover
2. Crie um index com o 'Name' ```filebeat-*``` e 'Timestamp field' ```@timestamp```
