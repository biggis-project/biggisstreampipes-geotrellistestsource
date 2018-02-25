package org.streampipes.biggis.sources.geotrellistest;

import org.streampipes.config.SpConfig;
import org.streampipes.container.model.PeConfig;

/**
 * Created by Jochen Lutz on 2018-02-25.
 */
public enum Config implements PeConfig {
    INSTANCE;

    private SpConfig config;
    public static final String JAR_FILE = "/streampipes-sources-geotrellis.jar";

    private final static String HOST = "host";
    private final static String PORT = "port";
    private final static String KAFKA_HOST = "kafka_host";
    private final static String KAFKA_PORT = "kafka_port";

    private final static String SOURCE_LAYERS = "layer names";
    private final static String EXTENT_MIN_X = "Extent min x";
    private final static String EXTENT_MIN_Y = "Extent min y";
    private final static String EXTENT_MAX_X = "Extent max x";
    private final static String EXTENT_MAX_Y = "Extent max y";

    private final static String SERVICE_ID = "pe/org.streampipes.biggis.sources.geotrellis.test1";
    private final static String SERVICE_NAME = "service_name";
    private final static String appName = "Geotrellis-Test-Sources-1";

    Config() {
        config = SpConfig.getSpConfig(SERVICE_ID);

        config.register(HOST, "sources-geotrellis-test", "Hostname for the sources component");
        config.register(PORT, 8090, "Port for the sources component");
        config.register(KAFKA_HOST, "kafka", "Host for kafka of the pe sinks project");
        config.register(KAFKA_PORT, 9092, "Port for kafka of the pe sinks project");
        config.register(SOURCE_LAYERS, "test1,test2,test3", "List of layer names (comma separated)");
        config.register(EXTENT_MIN_X, 8,  "The minimum value for the x coordinate");
        config.register(EXTENT_MIN_Y, 48, "The minimum value for the y coordinate");
        config.register(EXTENT_MAX_X, 9,  "The maximum value for the x coordinate");
        config.register(EXTENT_MAX_Y, 49, "The maximum value for the y coordinate");

        config.register(SERVICE_NAME, appName, "The name of the service");
    }

    public String getHost() {
        return config.getString(HOST);
    }

    public int getPort() {
        return config.getInteger(PORT);
    }

    public String getKafkaHost() {
        return config.getString(KAFKA_HOST);
    }

    public int getKafkaPort() {
        return config.getInteger(KAFKA_PORT);
    }

    public String getSourceLayers() {
        return config.getString(SOURCE_LAYERS);
    }

    public double getExtent_minx() {
        return config.getDouble(EXTENT_MIN_X);
    }

    public double getExtent_miny() {
        return config.getDouble(EXTENT_MIN_Y);
    }

    public double getExtent_maxx() {
        return config.getDouble(EXTENT_MAX_X);
    }

    public double getExtent_maxy() {
        return config.getDouble(EXTENT_MAX_Y);
    }

    public static final String iconBaseUrl = Config.INSTANCE.getHost() + "/img";

    public static final String getIconUrl(String pictureName) {
        return iconBaseUrl + "/" + pictureName + ".png";
    }

    @Override
    public String getId() {
        return SERVICE_ID;
    }

    @Override
    public String getName() {
        return config.getString(SERVICE_NAME);
    }

    public String getAppName() {
        return appName;
    }

    public String getKafkaHostPort() {
        return getKafkaHost() + ":" + getKafkaPort();
    }
}
