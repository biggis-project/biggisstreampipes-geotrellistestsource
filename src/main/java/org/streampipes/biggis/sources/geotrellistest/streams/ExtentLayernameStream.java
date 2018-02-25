package org.streampipes.biggis.sources.geotrellistest.streams;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.streampipes.biggis.sources.geotrellistest.Config;
import org.streampipes.model.SpDataStream;
import org.streampipes.model.graph.DataSourceDescription;
import org.streampipes.model.schema.PropertyScope;
import org.streampipes.sdk.builder.DataStreamBuilder;
import org.streampipes.sdk.builder.PrimitivePropertyBuilder;
import org.streampipes.sdk.helpers.EpProperties;
import org.streampipes.sdk.helpers.Formats;
import org.streampipes.sdk.helpers.Protocols;
import org.streampipes.sdk.utils.Datatypes;
import org.streampipes.sources.AbstractAdapterIncludedStream;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Jochen Lutz on 2018-02-25.
 */
public class ExtentLayernameStream extends AbstractAdapterIncludedStream implements Runnable {
    private final String topic = "org.streampipes.biggis.geotrellis.extendLayername";

    @Override
    public SpDataStream declareModel(DataSourceDescription dataSourceDescription) {
        return DataStreamBuilder.create("layername-extend", "Layer name + Extend", "")
                .iconUrl(Config.iconBaseUrl + "/layername-extend.png")
                .property(EpProperties.timestampProperty("timestamp"))
                .property(PrimitivePropertyBuilder
                        .create(Datatypes.String, "layername")
                        .label("Layer Name")
                        .description("The name of a layer in a Geotrellis catalog")
                        .domainProperty("http://types.streampipes.org/GeotrellisLayerName")
                        .scope(PropertyScope.HEADER_PROPERTY)
                        .build())
                .property(PrimitivePropertyBuilder
                        .create(Datatypes.String, "extent")
                        .label("Extend")
                        .description("A Geotrellis extent [xmin, ymin, xmax, ymax]")
                        .domainProperty("http://types.streampipes.org/GeotrellisExtent")
                        .scope(PropertyScope.MEASUREMENT_PROPERTY)
                        .build())
                .format(Formats.jsonFormat())
                .protocol(Protocols.kafka(Config.INSTANCE.getKafkaHost(), Config.INSTANCE.getKafkaPort(),
                        topic))
                .build();
    }

    @Override
    public void executeStream() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        Properties kafkaParams = new Properties();
        kafkaParams.put("bootstrap.servers", Config.INSTANCE.getKafkaHostPort());
        kafkaParams.put("key.serializer", StringSerializer.class);
        kafkaParams.put("value.serializer", StringSerializer.class);
        kafkaParams.put("group.id", "use_a_separate_group_id_for_each_stream");//TODO
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaParams);

        String[] layernames = Config.INSTANCE.getSourceLayers().split("\\s*,\\s*");
        double minX = Config.INSTANCE.getExtent_minx();
        double minY = Config.INSTANCE.getExtent_miny();
        double maxX = Config.INSTANCE.getExtent_maxx();
        double maxY = Config.INSTANCE.getExtent_maxy();

        while (true) {//TODO: muss die Quelle stoppbar sein?
            String layer = layernames[ThreadLocalRandom.current().nextInt(layernames.length)];
            double x1 = ThreadLocalRandom.current().nextDouble(minX, maxX);
            double x2 = ThreadLocalRandom.current().nextDouble(x1,   maxX);
            double y1 = ThreadLocalRandom.current().nextDouble(minY, maxY);
            double y2 = ThreadLocalRandom.current().nextDouble(y1,   maxY);

            String json = new StringBuilder()
                    .append("{\n")
                    .append("  \"timestamp\": ")
                    .append(System.currentTimeMillis()/1000)
                    .append(",\n")
                    .append("  \"layername\": ")
                    .append(layer)
                    .append(",\n")
                    .append("  \"extent\": [")
                    .append(x1)
                    .append(",")
                    .append(y1)
                    .append(",")
                    .append(x2)
                    .append(",")
                    .append(y2)
                    .append("]\n")
                    .append("}")
                    .toString();

            producer.send(new ProducerRecord<String, String>(topic, json));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }
}
