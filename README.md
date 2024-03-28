# cloudflare-assessment
Project to demonstrate cloudflare assessment for url shortenerA

Please follow instructions to create and run the springboot service locally. After this you can use the curl command to call the API's provided as part of the Url shortener service.

Prequisites:
1. Mvn
2. IDE- Sugggest Intellij community version
3. Springboot 3.x/JDK 7
4. Docker Desktop


# Url Shortener Design 

## API design:
I have provided 3 APIs as part of this assessment:
  1. API to shorten a URL
  2. API to redirect a short URL
  3. API to remove/delete a short URL persisted for a long URL in DB

## Database: 
Since UrlData has a fairly structured data I have used a relational database (MySQL) for persistence of the shortUrl. This can be expanded into a more complex entity with addition of more features.

Assumptions
1. Url shortener server/host is also localhost so the short URL will have the same base URL as the long URL for this assessment.
2. Unique short url generation using Base62 encoding is enough for the scope of this assessment.
   

# Scalability
Docker containers can be scaled using a load balancer and kubernetes to manage the multiple instances of the containers.

# Availability
Database container survives system restart so system is available and consistent.

# Build and Run
Import the project into an IDE of choice with required prereqs supported.

From inside the "cloudflare-assessment/CloudflareAssessment" directory, run the following commands:

Build the springboot service

    ./mvnw clean install -DskipTests
  
Build the docker image for url shortener service 

    docker buildx build -f Dockerfile . -t urlshortener:latest

Run all the docker containers required to run the APIs, might have to repeat this step as DB takes time to come up as a docker container.

    docker-compose -f docker-compose.yml up    
  
# Testing from COMMAND LINE/TERMINAL

 curl -X POST -H "Content-Type: application/json" -d 'http://short-url-example.com' http://localhost:8080/api/shortenUrl  

 curl -L http://localhost:8080/api/{shortenedId} OR curl -L {response-of-above-curl}

 curl -X DELETE -H "Content-Type: application/json" -d 'http://short-url-example.com' http://localhost:8080/api/removeShortUrl


# Demo

Please find it inside the zip along with the code repository.

