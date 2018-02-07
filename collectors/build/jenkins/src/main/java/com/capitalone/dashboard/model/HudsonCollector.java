package com.capitalone.dashboard.model;

import com.capitalone.dashboard.collector.HudsonSettings;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Extension of Collector that stores current build server configuration.
 */
public class HudsonCollector extends Collector {
    public static HudsonCollector prototype(HudsonSettings hudsonSettings) {
        HudsonCollector protoType = new HudsonCollector();
        protoType.setName("Hudson");
        protoType.setCollectorType(CollectorType.Build);
        protoType.setOnline(true);
        protoType.setEnabled(true);
        Map<String, Object> options = new HashMap<>();
        options.put(HudsonJob.INSTANCE_URL,"");
        options.put(HudsonJob.JOB_URL,"");
        options.put(HudsonJob.JOB_NAME,"");
        protoType.setAllFields(options);
        protoType.setUniqueFields(options);

        ObjectMapper m = new ObjectMapper();
        Map properties = m.convertValue(hudsonSettings, Map.class);
        protoType.setProperties(properties);

        return protoType;
    }
}
