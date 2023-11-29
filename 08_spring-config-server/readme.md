mvn spring-boot:build-image
docker run -p 8888:8888 docker.io/library/spring-config-server:0.0.1-SNAPSHOT