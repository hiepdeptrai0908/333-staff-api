
FROM amazoncorretto:17 AS build
COPY ./ /Users/hiepdeptrai0908/Document/Java/333-staff-api
RUN cd /Users/hiepdeptrai0908/Document/Java/333-staff-api && ./mvnw spring-boot:run
