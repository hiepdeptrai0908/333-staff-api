FROM 333-staff-api AS build
COPY ./ /Users/hiepdeptrai0908/Document/Java/333-staff-api
RUN cd /Users/hiepdeptrai0908/Document/Java/333-staff-api && ./mvnw spring-boot:run

FROM 333-staff-api
COPY --from=build /Users/hiepdeptrai0908/Document/Java/333-staff-api/target/0.0.1-SNAPSHOT.jar /usr/local/lib/0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dfile.encoding=UTF-8","/Users/hiepdeptrai0908/Document/Java/333-staff-api/target/0.0.1-SNAPSHOT.jar"]