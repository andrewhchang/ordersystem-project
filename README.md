# Ordering System Project

This project is a microservice-based system utilising several different technologies, with Spring Boot and Maven.

##Technologies
1. Rest APIs are used for each of the microservices, providing a single endpoint that is exposed to the user. The CustomerOrder microservice takes a single JSON POST request. All validation of products and the splitting of orders and sending to suppliers is handled by the Product microservice and the Purchase Order microservice respectively. Single click and forget.

2. Each supplier is set up differently using the following three technologies - REST, SOAP Webservice, and JMS(RabbitMQ).

3. Embedded H2 database is used to store entries in memory, JPA repository and Hibernate are used to retrieve and save entries.

4. A Eureka server is used for service discovery.

5. JUnit, Mockito and MockMVC are used for unit tests.
