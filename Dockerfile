FROM openjdk:8-jdk-alpine
WORKDIR /app
ADD target/docker-cookit.jar docker-cookit.jar
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
EXPOSE 8080
#CMD ["./mvnw","spring-boot:run"]
ENTRYPOINT ["java","-jar","docker-cookit.jar"]

# VOLUME /tmp
# ARG JAVA_OPTS
# ENV JAVA_OPTS=$JAVA_OPTS
# COPY target/cookit-0.0.1-SNAPSHOT.jar cookit.jar
# EXPOSE 8080
# ENTRYPOINT exec java $JAVA_OPTS -jar cookit.jar
# # For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
# #ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar cookit.jar
