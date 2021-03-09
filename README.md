# Confluent // Colorstack Backend Development Demo

## Set Up Your Environment
Install Maven for your specific operating system using [this link](https://maven.apache.org/install.html). 
Use your terminal to navigate to the location on your local machine where you want to download
this code, and then clone it from Github.

```
$ cd /path/to/your/directory/
$ git clone https://github.com/marcusgreer/colorstack-backend-demo.git
$ cd colorstack-backend-demo
```

## How To Run

### Echo Server Demo
```
$ mvn clean package
$ java -jar echo-server-demo/target/echo-server-demo-1.0-SNAPSHOT.jar
```
Keep this terminal window open, and then run the following command in a separate window using [netcat](https://linuxize.com/post/netcat-nc-command-with-examples/)
```
$ nc localhost 9093
```
Type text into the same terminal where netcat is running, and see it echoed back to you

Kill the Client and Server processes by running `Ctrl+C` in the terminal windows.

### REST API Demo

**Start the server by running:**
```
$ mvn clean package
$ java -jar rest-api-demo/target/rest-api-demo-0.0.1-SNAPSHOT.jar
```

Note you should see an output similar to the following in your terminal to confirm that the
Server is up and running.
```
2021-03-08 15:06:56.087  INFO 91332 --- [           main] c.c.ColorstackBackendDemoApplication     : Started ColorstackBackendDemoApplication in 1.83 seconds (JVM running for 2.321)
```

Make requests to the server via [cURL](https://curl.se/docs/manpage.html)
```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"content":"Do Homework","done":"false"}' \
  http://localhost:3000/tasks
```

## Assignment
Modify the code in `/rest-api-demo/src/main/java/io/demo/restapi/` to create your own API. 

One example would be to create your own version of twitter that can 
- Handle multiple users.
- The ability to subscribe/unsubscribe and
- Get the last N articles that a user is following.

Alternatively, be creative and gain inspiration from the various applications your use every day.


## Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

