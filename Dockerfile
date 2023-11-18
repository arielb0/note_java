FROM azul/zulu-openjdk:17-latest
VOLUME /tmp
RUN ./gradlew build
COPY ./build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]