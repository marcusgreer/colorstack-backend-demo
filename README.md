# Confluent // Colorstack Backend Development Demo

## How To Run

Use your terminal to go to the location on your local machine where you want to download
this code, and then clone it from Github.

```
$ cd /path/to/your/directory/
$ git clone https://github.com/marcusgreer/colorstack-backend-demo.git
$ cd colorstack-backend-demo
```

### Echo Server Demo
```
$ mvn clean package
$ java -jar echo-server-demo/target/echo-server-demo-1.0-SNAPSHOT.jar
```
Keep this terminal window open, and then in a separate window run the command via [netcat](https://linuxize.com/post/netcat-nc-command-with-examples/)
```
$ nc localhost 9093
```

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

## Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

