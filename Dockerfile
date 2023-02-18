ARG BUILD_HOME=/app

FROM gradle:jdk18 as build

ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME

WORKDIR $APP_HOME

COPY --chown=gradle:gradle build.gradle settings.gradle $APP_HOME/
COPY --chown=gradle:gradle src $APP_HOME/src
#COPY --chown=gradle:gradle config $APP_HOME/config

RUN gradle --no-daemon bootJar

FROM amazoncorretto:18

ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME

COPY --from=build $APP_HOME/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT java -jar app.jar
