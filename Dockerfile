FROM java:8
VOLUME /tmp
ADD target/smsapp-0.0.1-SNAPSHOT.jar cpaasapi.jar
RUN bash -c 'touch /cpaasapi.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /cpaasapi.jar"]