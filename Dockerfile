FROM openjdk:17 AS build
WORKDIR /app
COPY target/SpaceLab_3_5-1.0-SNAPSHOT.jar RetailApp_I_Dima.jar

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/RetailApp_I_Dima.jar .

ENTRYPOINT ["java", "-jar", "RetailApp_I_Dima.jar"]
