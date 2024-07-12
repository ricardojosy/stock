# stock
Registration of products, users and Orders (under construction).

The purpose of this project is to showcase my abilities and demonstrate the technologies I am proficient in.

To this project were used Java 17, Spring Boot, Spring Data, Spring Security 6, Postgres, Flyway, RabbitMQ, Docker, Rest, JUnit and Mockito.

This project is the backend of the stock-webapp angular application with location here https://github.com/ricardojosy/stock-webapp

# Begin
Access folder named local and run docker compose to up 3 containers with DB Postgres, RabbitMQ and pgAdmin.

# Postgres
The Postgres user db is 'postgres', the password is 123 and the database name is 'stock'

# Initialization
At the first time you run the application the user 'admin' with password '123' will be create with the ADMIN role.
All anothers users create by the application will have BASIC role.

