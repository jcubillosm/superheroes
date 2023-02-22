# Superheroes API
![](https://img.shields.io/badge/Java11-orange.svg)
![](https://img.shields.io/badge/Spring%20Boot-2.7.8-green.svg)

La API de superheroes es la solución propuesta a la prueba técnica de Spring Boot para W2M.
Se han implementado las interacciones básicas de consulta de la lista completa, consulta por id y consulta por una patrón, modificación por id y eliminación por id.

## Contenido del repositorio
- Uso de `maven` con `SpringBoot`
- Base de datos en `H2` almacenada en memoria y `JPA` para el mantenimiento de la base de datos
- Documentación con `Swagger` e interfaz de usuario con `Swagger-ui`
- Utiliza `Junit` y `MockMVC` para test unitarios
- `HandlerException` para controlar y personalizar excepciones
- `Spring Cache` para el cacheo de peticiones

## Descripción
Se ha llevado a cabo la implementación de la API Superheroes utilizando Spring Boot v2.7.8, Java 11, Maven 3.6.3. Se ha utilizado h2 como base de datos relacional.

La aplicación permite consultar el listado completo de superheroes, mostrando un id y nombre asociado:
> GET http://localhost:8080/superheroes

Permite buscar un superheroe en concreto por el id:
> GET http://localhost:8080/superheroes/1

También permite la búsqueda por un patrón dado, mostrando en este caso un listado de superheroes cuyo nombre contiene la cadena dada:
> GET http://localhost:8080/superheroes?name=man

Además, se puede realizar modificaciones de los nombres a partir del id:
> PUT http://localhost:8080/superheroes

Y se pueden eliminar registros/superheroes por el id
> DELETE http://localhost:8080/superheroes/5

## Instalación
Para su instalación se puede crear una imagen de Docker. Es necesario tener previamente instalado Docker en el host.

Ejecutar:
```sh 
docker build -t superheroes .
docker run -p 8080:8080 superheroes
```

## Documentacion
La api hace uso de un swagger para exponer la documentación en la siguiente url:
[Swagger](http://localhost:8080/swagger-ui/index.html "Swagger")
