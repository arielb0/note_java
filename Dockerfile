FROM azul/zulu-openjdk:17-latest
VOLUME /tmp
# COPY ./build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","build/libs/demo-0.0.1-SNAPSHOT.jar"]