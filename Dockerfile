# 基础镜像
FROM openjdk:17-jdk-alpine

# 设置时区
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

# 创建工作目录
WORKDIR /app

# 复制 JAR 包
COPY target/student-management-web-0.0.1-SNAPSHOT.jar /app/app.jar

# 暴露端口
EXPOSE 8080

# 启动命令（使用 prod 环境）
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.profiles.active=prod"]