Observação: No método getAllUsersByCourse(UUID courseId, Pageable pageable) retornar result.getBody() ao invez de new PageImpl(searchResult), como mostrado abaixo:

```java
public Page<UserDto> getAllUsersByCourse(UUID courseId, Pageable pageable){
  List<UserDto> searchResult = null;
  ResponseEntity<ResponsePageDto<UserDto>> result = null;
  String url = REQUEST_URL_AUTHUSER + utilsService.createUrlGetAllUsersByCourse(courseId, pageable);
  log.debug("Request URL: {} ", url);
  log.info("Request URL: {} ", url);
  try{
    ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {};
    result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
    searchResult = result.getBody().getContent();
    log.debug("Response Number of Elements: {} ", searchResult.size());
  } catch (HttpStatusCodeException e){
    log.error("Error request /courses {} ", e);
  }
  log.info("Ending request /users courseId {} ", courseId);
  return result.getBody();
}
```