Observação: No método getAllCoursesByUser(UUID userId, Pageable pageable) retornar result.getBody() ao invés de new PageImpl(searchResult), como mostrado abaixo:

```java
public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable){
  List<CourseDto> searchResult = null;
  ResponseEntity<ResponsePageDto<CourseDto>> result = null;
  String url = REQUEST_URL_COURSE + utilsService.createUrlGetAllCoursesByUser(userId, pageable);
  log.debug("Request URL: {} ", url);
  log.info("Request URL: {} ", url);
  try{
    ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};
    result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
    searchResult = result.getBody().getContent();
    log.debug("Response Number of Elements: {} ", searchResult.size());
  } catch (HttpStatusCodeException e){
    log.error("Error request /courses {} ", e);
  }
  log.info("Ending request /courses userId {} ", userId);
  return result.getBody();
}
```