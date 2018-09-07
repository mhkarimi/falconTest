Hi! This is the README file as it was supposed to be written the task description. I used **Spring boot**, **Spring Data JPA , Spring Data Redis, Redis Pub-Sub IntelliJ IDEA, Redis, MySQL, maven, swagger, webSocket, docker** and **docker-compose**.
  
## What was my strategies to solve the problem?
  
When a user post a dummyJson object , I will convert the object to Json and publish it to a channel on redis pub-sub.  Then when a subscriber receive the message , it will convert it to DummyJson object and will persist it on database.
When you send a GET request to retrieve the DummyJson from the database, the sendJsonObjectToWebSocketInGETMethod method of **SocketHandler** will be called and send the result to the websocket . 

  
## Building Instructions: 
``` 
 *  to build the source codes on project directory:
 - # mvn clean install    
 * to create a report on tests:
 - # mvn site:site 
 * to see tye report go to the $PROJECT_DIRECTORY/target/site and open index.html file
 * to build the docker image:
 - # mvn clean package docker:build -Dmaven.test.skip=true
 * to start the application 
 - # docker-compose up
 * to see the logs from docker-compose:
 - # docker-compose -f docker-compose.yml logs -f
 
 ```
## Test the websocket   
  
first open the link below from the browser:
```
http://localhost:8080/
```

  
## Add a DummyJson Object  
  
    
POST /api/name
Request:  
  
 ```
 curl -X POST "http://localhost:8080/api/name" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"id\": 0, \"name\": \"yourGivenName\"}"
  ```  
  
  ## GET DummyJson Objects
```
 curl   -GET "http://localhost:8080/api/name"
  ```  
  and you will see the result in the web-page you had already open.
  