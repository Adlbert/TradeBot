FROM openjdk:11-jre
MAINTAINER Adrian Hofer <adrian.hofer@hotmail.com>

ENTRYPOINT ["java","-jar", "/usr/share/adrobin/tradebot.jar"]

# Add Maven dependencies (not shaded into the artifact; Docker-cached)
ADD target/libs           /usr/share/adrobin/libs
# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/adrobin/tradebot.jar

