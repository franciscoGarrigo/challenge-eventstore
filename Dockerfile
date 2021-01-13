FROM maven:3.6.3-openjdk-11

COPY . /
RUN mvn clean install
ADD target/challenge-eventstore.jar challenge-eventstore.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /challenge-eventstore.jar"]
