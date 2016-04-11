# FROM java:openjdk-8-jdk-alpine

# add directly the war
# ADD *.war /app.war

# RUN sh -c 'touch /app.war'
# VOLUME /tmp
# CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.war"]

# EXPOSE 8080

FROM java:8
ARG BUILD_VERSION=0.0.1
ADD product-master-app-${BUILD_VERSION}.war app.war
RUN bash -c 'touch app.war'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.war"]