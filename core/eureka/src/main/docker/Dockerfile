FROM java:8
MAINTAINER dotff@qq.com
RUN mkdir /eureka
WORKDIR /eureka
ADD eureka-0.0.1-SNAPSHOT.jar /eureka/eureka.jar
EXPOSE 9001
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/eureka/eureka.jar"]
