FROM java:8
EXPOSE 8000
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV LANG C.UTF-8
VOLUME /tmp
ADD ../../../../*.jar /app.jar
RUN bash -c 'touch /app.jar'
#ENV JVM_OPTIONS="  -Xms900m -Xmx900m -Xmn400m -server -XX:+UseParNewGC  -XX:ParallelGCThreads=4 -XX:MaxTenuringThreshold=9 -XX:+DisableExplicitGC -XX:+ScavengeBeforeFullGC -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+ExplicitGCInvokesConcurrent -XX:+HeapDumpOnOutOfMemoryError -XX:-OmitStackTraceInFastThrow "
#ENTRYPOINT  java ${JVM_OPTIONS} -Dspring.profiles.active=prod -jar /app.jar
ENTRYPOINT  java  -Dspring.profiles.active=prod -jar /app.jar
