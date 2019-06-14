FROM openjdk:12-oracle

RUN useradd --system --user-group --create-home svc_user

USER svc_user

WORKDIR /home/svc_user

ARG JAR_FILE

ADD target/${JAR_FILE} app.jar

EXPOSE 8080/tcp

ENTRYPOINT ["/usr/bin/java", "-jar", "app.jar"]