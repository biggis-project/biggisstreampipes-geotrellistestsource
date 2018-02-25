Purpose
=======
This Streampipes source adds data sources (actually only one) to test the integration
of Geotrellis in Streampipes.
 
Installation
============
 
Add this fragment to your Streampipes docker-compose.yml:
 
    geotrellis-test-sources:
      image: laus.fzi.de:8201/streampipes/geotrellis-test-sources
      depends_on:
        - "kafka"
      environment:
        JAVA_OPTS: "-Dkafka.server=kafka:9092"
      networks:
        spnet:

Data Streams
============
- ExtentLayernameStream

  Produces a series of records containing a layer name and an extent.
  The layer name is chosen randomly from a list, the extent is within a given bounding box.
  Both parameters can be configured with Consul parameters.