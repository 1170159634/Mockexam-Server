FROM java:8
EXPOSE 8000
VOLUME /tmp
ADD *.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT  java  -Dspring.profiles.active=prod -jar /app.jar

