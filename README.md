# Description
This is simple show case where we have exposed simple REST API but at some point Product owner wants a data grid with pagination (displaying  minor subset of properties). GraphQL makes it super easy to implement this requirement with a lot of flexibility so API consumers can pick which fields want to be served as long as they are supported and defined in the GraphQL schema.

# Setup 
- JDK 11
- Maven v3.6.3 
- MongoDB v4.4 (https://www.mongodb.com/try/download/community)
- Postman as nice to have  (https://www.postman.com/)
- Robo 3T (Robomongo) as nice to have (https://robomongo.org/download) or MongoDB Compass (https://www.mongodb.com/products/compass)

# Running Local
````
mvn verify
````

````
mvn spring-boot:run
````
- Navigate to http://localhost:8080/swagger-ui.html to check Swagger UI API definition. 
- Setting up 'demo.initial.data.load' to true in application.properties will load some sample dummy data on startup. 
- Import postman collection in case you want to check up GraphQL exposed end point.

Happy coding!!! 

