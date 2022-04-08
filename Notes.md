# Notas 

### Pattern Erro message
Ler sobre a RFC https://datatracker.ietf.org/doc/html/rfc7807

### Configurando CORS Global 
```java
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**");
  }
}
```


### Sample Listener Pre update/insert
```java
/* Listener */
public class CourseListener {
  @PrePersist
  public void coursePrePersist(CourseModel course) {
    course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
    course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
  }

  @PreUpdate
  public void coursePreUpdate(CourseModel course) {
    course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
  }
}

/* Model */
@EntityListeners(CourseListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_COURSES")
@Data
public class CourseModel implements Serializable{
  ...
}
```

### OpenFeign 
Uma opção para o RestTemplate [Spring Cloud OpenFeign](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/)
