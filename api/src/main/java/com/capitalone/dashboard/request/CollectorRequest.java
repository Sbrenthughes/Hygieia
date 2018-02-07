package com.capitalone.dashboard.request;

import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorType;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaf107 on 1/11/16.
 */
public class CollectorRequest {
    @NotNull
    private String name;
    @NotNull
    private CollectorType collectorType;

    private String propertyKey;

    private String propertyValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CollectorType getCollectorType() {
        return collectorType;
    }

    public void setCollectorType(CollectorType collectorType) {
        this.collectorType = collectorType;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public Collector toCollector() {
        Collector collector = new Collector();
        collector.setCollectorType(collectorType);
        collector.setName(name);

        if(!"".equals(propertyKey)) {
            Map properties = new HashMap();
            properties.put(propertyKey, propertyValue);
            collector.setProperties(properties);
        }

        return collector;
    }
}
