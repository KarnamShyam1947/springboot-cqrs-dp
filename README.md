# CQRS Design Pattern Implementation

### What is CQRS?

Command Query Responsibility Segregation (CQRS) is a design pattern that separates the responsibilities of reading and writing data into different models. This pattern is especially useful in complex systems where the read and write operations have different performance, scalability, and security requirements.

### How CQRS Works

In a typical CQRS implementation, the system is divided into two distinct parts:

1. **Command Side (Write Model)**:
   - **Commands**: Represent operations that change the state of the system. For example, creating or updating a record.
   - **Command Handler**: Processes these commands and performs necessary state changes. It typically interacts with a command database (e.g., PostgreSQL) where the write operations are stored.
   - **Event Sourcing**: Often used in conjunction with CQRS to capture and store all changes as a sequence of events. This ensures that every change is recorded and can be replayed or audited.

2. **Query Side (Read Model)**:
   - **Queries**: Represent operations that retrieve data without modifying it. For example, fetching a record or performing a search.
   - **Query Handler**: Processes these queries and retrieves data from a read-optimized data store (e.g., MongoDB). This store is updated based on the events published by the command side.
   - **Projections**: These are read models created from the events stored in the event store. They are optimized for specific query patterns and can be updated asynchronously.

### Benefits of CQRS

- **Scalability**: Separating read and write operations allows each side to be scaled independently based on their respective loads. For instance, you can scale the query side more easily if read operations outnumber writes.
  
- **Performance Optimization**: The read model can be optimized specifically for query performance without impacting the write operations. This allows for faster query responses and more efficient data retrieval.

- **Security and Flexibility**: Different security measures can be applied to the command and query sides. Additionally, the separation allows for flexibility in evolving the data models and handling complex business logic.

- **Complex Business Logic**: CQRS can simplify complex business logic by isolating it in the command side, making it easier to manage and maintain.

- **Event Sourcing Compatibility**: CQRS works well with event sourcing, which provides a complete audit trail of changes and enables rebuilding the state by replaying events.

### Use Cases

CQRS is particularly useful in scenarios where:
- The application has complex business logic.
- Read and write operations have different performance and scalability requirements.
- There is a need for a detailed audit trail of changes.

By implementing CQRS, you can achieve a more organized and maintainable architecture that enhances performance and scalability, tailored to the needs of both read and write operations.



## Implementation

### Architecture Overview

1. **Command Service**: Handles write operations and stores data in PostgreSQL. This service processes commands and performs state changes.

2. **Query Service**: Handles read operations and retrieves data from MongoDB. This service provides optimized queries and data retrieval capabilities.

3. **Event Sourcing**: Uses Apache Kafka to manage and propagate events between the command and query services. It ensures that all state changes are captured and can be replayed or processed.

### Technologies Used

- **Spring Boot**: Framework for building the application.
- **PostgreSQL**: Relational database used for command operations.
- **MongoDB**: NoSQL database used for query operations.
- **Apache Kafka**: Messaging platform used for event sourcing.

### Components

- **Command Service**:
  - Manages commands and interacts with PostgreSQL.
  - Responsible for handling data mutations and publishing events to Kafka.

- **Query Service**:
  - Manages queries and interacts with MongoDB.
  - Listens to Kafka topics to stay updated with changes and keep the query data store synchronized.

- **Kafka**:
  - Acts as the event bus to publish and consume events between command and query services.

## How to Set Up the Project

### Prerequisites

- Java 17 or higher
- PostgreSQL
- MongoDB
- Apache Kafka

### Setting Up the Command Service

1. **Clone the Repository**

   ```bash
   https://github.com/KarnamShyam1947/springboot-cqrs-dp.git
   cd springboot-cqrs-dp
    ```

2. **Ensure Services Are Running**


    - Start PostgreSQL
        - Ensure PostgreSQL is installed and running.
        Refer [PostgreSQL Documentation](https://www.postgresql.org/download/) or run docker container 
        ```bash
        docker run 
        --name postgres 
        -p 5432:5432
        -e POSTGRES_PASSWORD=admin 
        -e POSTGRES_USER=admin 
        -d postgres
        ```
        - Update src/main/resources/application.properties in the command-service directory with your PostgreSQL configuration.

    - Start MongoDB
        - Ensure MongoDB is installed and running.Refer [MongoDB Documentation](https://www.mongodb.com/try/download/community) or run docker container 
        ```bash
        docker run 
        --name mongodb 
        -p 27017:27017 
        -d mongo:latest
        ```
        - Update src/main/resources/application.properties in the query-service directory with your MongoDB configuration.

    - Start Apache Kafka
        - Follow the [Kafka Quickstart Guide](https://kafka.apache.org/quickstart)  to install and start Kafka.
        - use the docker compose file provided in this repo
        ```bash
        docker compose -f zookeeper-kafka.yml up -d
        ```
3. **Ensure Ports Are Free**
    - Command Service: Port 8080
    - Query Service: Port 8081

4. **Start Command Service**
    - Navigate to the Command Service Directory
    ```bash
    cd command-api-service
    ```
    - Run command service
    ```bash
    ./mvnw spring-boot:run
    ```

5. **Start Query Service**
    - Navigate to the query Service Directory
    ```bash
    cd query-api-service
    ```
    - Run query service
    ```bash
    ./mvnw spring-boot:run
    ```
