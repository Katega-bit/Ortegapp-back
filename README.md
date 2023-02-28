# Ortegapp
## API REST con SPRING - Proyecto de AD-PSP de 2ºDAM.

<img src="https://img.shields.io/badge/Spring--Framework-5.7-green"/>  <img src="https://img.shields.io/badge/Java-17.0-brightgreen"/>

 <img src="https://niixer.com/wp-content/uploads/2020/11/spring-boot.png" width="500" alt="Spring Logo"/>

___


## **Documentación**

:point_right: [Dirección SWAGGER-IO](http://localhost:8080/swagger-ui/index.html#/)

:point_right: Se incluye también una colección de Postman para probar datos.


## **Introducción** :speech_balloon:

Este es un proyecto práctico para el desarrollo de una API REST en lenguaje Java y manejando diferentes tecnologías en la que destaca **Spring**.

También se ha prácticado el manejo de **PostMan**, **Swagger** y la metodología ágil **SCRUM** para el reparto de tareas a través la ramificación de estas con **GitHub**.

El proyecto trata de realizar una API REST para la gestión de una operación de recaudación de donaciones de alimentos de un colegio, el control y gestión de esos alimentos, el reparto y gestión de los destinatarios. Además podrá incorporar un ranking para conocer cuáles han sido las clases más solidarias del colegio.



Se pueden realizar las siguientes funcionalidades: 	:point_right:
* Listado de PRODUCTO
* Busqueda de un PRODUCTO buscada por su id
* Creación de un nueva PRODUCTO
* Edición de un PRODUCTO
* Borrado de un PRODUCTO


* Listado de los USER
* Búsqueda de un USER por id
* Creación de un nuevo TUSER
* Edición de un USER
* Borrado de un USER

* Creacion de un COMENTARIO
* Borrado de un COMENTARIO
* Busqueda de un COMENTARIOS en un PRODUCTO por id





---

## **Tecnologías utilizadas**

Para realizar este proyecto hemos utilizado:

1. [Spring Boot 2.7.5](https://spring.io/) - Dependencias: **Spring-Web**, **JPA-HIBERNATE**, **H2 Database**, **Sprin-doc Open-api**, **Lombok**
2. [Apache Maven 3.8.6](https://maven.apache.org/)
3. [Postman](https://www.postman.com/)
4. [GitHub](https://github.com/)
5. [springdoc 1.6.13](https://springdoc.org/)
6. [Swagger](https://swagger.io/)



### Ejemplos del Código Usado:

**JAVA**:
```Java
@PostMapping
public ResponseEntity<ProductoResponse> newProducto( @RequestPart("producto") @Valid CreateProduct createProduct,
@RequestPart("file") MultipartFile file){
        ProductoResponse nuevo = productoService.save(createProduct, file);

        URI createdURI = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(nuevo.getId()).toUri();


        return ResponseEntity
        .created(createdURI)
        .body(nuevo);

        }

```

**Documentación**

```Java

@Operation(summary = "Crea un nuevo producto")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201",
                description = "Se ha creado un nuevo producto",
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = ProductoResponse.class),
                        examples = @ExampleObject(value = """
                                                {
                                                    "id": 2,
                                                    "nombre": "Fresitas",
                                                    "foto": "https://classroom.google.com/u/0/h",
                                                    "tipo": "Gomita",
                                                    "descripcion": "Lorem Ipsum Dolor Sit Amet....",
                                                    "precio": 5.0,
                                                    "likes": [],
                                                    "comentarios": []
                                                }
                                """))}),
        @ApiResponse(responseCode = "400",
                description = "No se han introducido los datos correctamente",
                content = @Content),
})
```


---
## **Arranque**

Realiza un *git clone* de la siguiente dirección:
*https://github.com/Katega-bit/Ortegapp-back*

```console
git clone https://github.com/Katega-bit/Ortegapp-back
```

Dirígete hasta la carpeta:

> cd ./ortegapp/


**Primero** tienes que tener instalado Apache Maven y sería recomendable tener alguna IDE, como **Intellij IDEA** o **VisualStudio Code**

Ejecuta el siguiente comando:

    mvn complile


Ejecuta el siguiente comando:

    mvn clean


Ejecuta el comando:

    mvn spring-boot:run


Si diese algún error, realiza el siguiente comando:

    mvn dependencies:resolve
    ---> 100% 

Credenciales Basicas de logeo:
* ADMIN: 
username: admin
password: Aa1234578*

* USER: 
username: user
password: Aa1234578*    

___
## **Autores**

Este proyecto ha sido realizado por:


* [Carlos Ortega Reina - GITHUB](https://github.com/CarlitrosPicaTecla)

Estudiante de 2º Desarrollo de Aplicaciones Multiplataforma, grado
superior de formación profesional en la ciudad de Sevilla, España.

### **Thump up :+1: And if it was useful for you, star it! :star: :smiley:**

___
## **TODO**

Tareas realizadas y cosas por hacer.

[ ] Fix possible future errors
___
