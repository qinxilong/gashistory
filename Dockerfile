FROM openjdk:8-jdk
# 设置工作目录，进入到容器中的初始目录,不存在会自动创建
ENV MYPATH /root

ADD gasdemo.jar  gasdemo.jar
ADD application.properties /root/config/application.properties

EXPOSE 8080
ENTRYPOINT ["java","-Dfile.encoding=UTF8","-Duser.timezone=GMT+08","-jar","gasdemo.jar", "--spring.config.location=file:/root/config/application.properties"]

