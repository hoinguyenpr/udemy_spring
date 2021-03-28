From openjdk:8
RUN apt-get update
RUN apt-get install nano 
ADD ./target/device-module-0.0.1-SNAPSHOT.jar /app.jar
RUN mkdir -p /config
ADD ./src/main/resources/application_docker.properties /config/application.properties
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Xmx256M","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.config.location=classpath:/,file:/config/application.properties"]