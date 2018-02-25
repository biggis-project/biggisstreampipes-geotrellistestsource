FROM anapsix/alpine-java

EXPOSE 8090
ENV CONSUL_LOCATION consul

ADD ./target/geotrellis-test-sources.jar  /geotrellis-test-sources.jar

ENTRYPOINT ["java", "-jar", "/geotrellis-test-sources.jar"]
