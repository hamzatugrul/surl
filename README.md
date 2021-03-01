# SUrl
SURL is a URL Shortener System which scale out unlimited horizontally.

### Technology Stack

* Java 8
* Spring Boot
* Spring Data MongoDB
* JUnit
* Mockito
* Maven
* Docker
* Redis
* MongoDB
* NGINX (Load Balancer)
* Elasticsearch  * *has a issue in feature branch*
* Logstash  * *has a issue in feature branch*
* Kibana  * *has a issue in feature branch*

### Setup and Run

You need to have Java 8 jdk installed on you system.

1. Clone this repository
2. To package jar file and create the app image execute the following command in **surl** directory:
    ```
    mvn clean package docker:build
    ```
3. To run the system:

    ```
    docker-compose up -d 
    ```

4. To shorten a long url:

    ```
    curl -v -H "Content-Type: application/json" -X POST -d '{"longUrl":"www.google.com"}' http://localhost:8080/api/v1/shortener
    ``` 
5. To access your short url: (***SURL*** is the shorted code generated in the 4th step)
    ```
    curl -v -X GET http://localhost:8080/api/v1/{SURL}
    ```
6. To access statistics for a short url with its key:

    ```
    curl -v  -H "Content-Type: application/json" -X GET http://localhost:8080/api/v1/stat/{SURL}
    ```
