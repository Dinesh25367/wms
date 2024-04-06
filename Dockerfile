FROM eclipse-temurin:17-jre-alpine
ARG PROFILE dev
ENV SPRING_PROFILES_ACTIVE $PROFILE
ADD ./application/target/classes/application-$PROFILE.yml /usr/app/fxservice/wms/config/application-$PROFILE.yml
ADD ./application/target/application-0.0.1-SNAPSHOT.jar /usr/app/fxservice/wms/jar/wms.jar
ENTRYPOINT ["java", "-Xms128M", "-Xmx256M", "-jar", "/usr/app/fxservice/wms/jar/wms.jar"]
