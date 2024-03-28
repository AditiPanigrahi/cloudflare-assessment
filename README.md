# cloudflare-assessment
Project to demonstrate cloudflare assessment for url shortener

Please follow instructions to create and run the springboot service locally. After this you can use the curl command to call the API's provided as part of the Url shortener service.

Prequisites:
1. Mvn
2. IDE- Sugggest Intellij community version
3. Springboot 3.x/JDK 7
4. Docker Desktop


Url Shortener Design 

High Level Design


# API design

Assumptions
1. Url shortener host is also localhost

# Scalability

2. Docker containers can be scaled using a load balancer and kubernetes to manage the multiple instances of the containers.

# Availabilty

2. Database container survives system restart

# Build and Run

From inside the "cloudflare-assessment/CloudflareAssessment" directory, run the following commands:
1 Build the springboot service
  ./mvnw clean install -DskipTests./

2. Build the docker image for url shortener service 
  docker buildx build -f Dockerfile . -t urlshortener:latest

3.Run all the docker containers required to run the APIs
  docker-compose -f docker-compose.yml up    
  
# Testing from COMMAND LINE/TERMINAL

 curl -X POST -H "Content-Type: application/json" -d 'http://short-url-example.com' http://localhost:8080/api/shortenUrl  

 curl -L http://localhost:8080/api/{shortenedId} OR curl -L {response-of-above-curl}

 curl -X DELETE -H "Content-Type: application/json" -d 'http://short-url-example.com' http://localhost:8080/api/removeShortUrl


# Demo

Please find it inside the zip along with the code repository.

