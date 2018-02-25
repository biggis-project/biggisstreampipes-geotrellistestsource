package org.streampipes.biggis.sources.geotrellistest;

import org.streampipes.biggis.sources.geotrellistest.sources.ExtentsSource;
import org.streampipes.container.init.DeclarersSingleton;
import org.streampipes.container.standalone.init.StandaloneModelSubmitter;

/**
 * Created by Jochen Lutz on 2018-02-25.
 */
public class SourcesInit extends StandaloneModelSubmitter {
    public static void main(String[] args) {
        DeclarersSingleton.getInstance()
                .add(new ExtentsSource())
        ;

        new SourcesInit().init(Config.INSTANCE);
    }
}
