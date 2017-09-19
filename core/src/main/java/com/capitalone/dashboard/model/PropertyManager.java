package com.capitalone.dashboard.model;

        import org.bson.types.ObjectId;
        import org.springframework.data.mongodb.core.mapping.Document;

        import java.util.Properties;


@Document(collection="property_manager")
public class PropertyManager extends BaseModel {

    private ObjectId collectorItemId;
    private long timestamp;
    private String name;
    private Properties properties;

    public PropertyManager(){

    }
    public PropertyManager(String name, Properties properties){
        this.name = name;
        this.properties = properties;
    }
    public ObjectId getCollectorItemId() {
        return collectorItemId;
    }

    public void setCollectorItemId(ObjectId collectorItemId) {
        this.collectorItemId = collectorItemId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}