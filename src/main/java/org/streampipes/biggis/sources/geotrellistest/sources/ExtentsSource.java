package org.streampipes.biggis.sources.geotrellistest.sources;

import org.streampipes.biggis.sources.geotrellistest.streams.ExtentLayernameStream;
import org.streampipes.container.declarer.EventStreamDeclarer;
import org.streampipes.container.declarer.SemanticEventProducerDeclarer;
import org.streampipes.model.graph.DataSourceDescription;
import org.streampipes.sdk.builder.DataSourceBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jochen Lutz on 2018-02-25.
 */
public class ExtentsSource implements SemanticEventProducerDeclarer {
    public DataSourceDescription declareModel() {
        return DataSourceBuilder.create("geotrellis-test1", "Geotrellis Test Sources", "A data source that generates Geotrellis test streams")
                .build();
    }

    public List<EventStreamDeclarer> getEventStreams() {
        return Arrays.asList(
                new ExtentLayernameStream()
        );
    }
}
