### Observação

#### Como fica a questão do hateoas quando gera a URL utilizando o gateway?
Para ajustar automaticamente os links eu usei a instrução ```server.forward-headers-strategy: FRAMEWORK``` nos *application.yaml* dos serviços authuser e course.

Assim os links do HATEOAS foram reescritos para o host do gateway.
Exemplo:
```
-- Antes --
"links": [
  {
    "rel": "self",
    "href": "http://localhost:8087/ead-authuser/users/38817a48-d1db-4376-b430-c18d803c296b"
  }
]

-- Depois --
"links": [
  {
    "rel": "self",
    "href": "http://localhost:8080/ead-authuser/users/38817a48-d1db-4376-b430-c18d803c296b"
  }
]
```