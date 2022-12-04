
FROM amazoncorretto:17 AS build
COPY ./ /Users/hiepdeptrai0908/Document/Java/333-staff-api
RUN cd /Users/hiepdeptrai0908/Document/Java/333-staff-api && ./mvnw spring-boot:run

FROM amazoncorretto:17-alpine
COPY --from=build /Users/hiepdeptrai0908/Document/Java/333-staff-api/0.0.1-SNAPSHOT.jar /usr/local/lib/0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dfile.encoding=UTF-8","/usr/local/lib/0.0.1-SNAPSHOT.jar"]