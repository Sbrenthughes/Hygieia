package com.capitalone.dashboard.request;

import com.capitalone.dashboard.model.PropertyManager;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Properties;

public class PropertyManagerRequest {
    @NotNull
    private String name;

    @NotNull
    @Pattern(message = "Period(s) found.", regexp = "^[^.]*$")
    private String propertiesKey;

    private String propertiesValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropertiesKey() {
        return propertiesKey;
    }

    public void setPropertiesKey(String propertiesKey) {
        this.propertiesKey = propertiesKey;
    }

    public String getPropertiesValue() {
        return propertiesValue;
    }

    public void setPropertiesValue(String propertiesValue) {
        this.propertiesValue = propertiesValue;
    }

    public PropertyManager toCollectorProperties() {
        Properties properties = new Properties();
        properties.put(this.propertiesKey, this.propertiesValue);
        return new PropertyManager(this.name, properties);
    }
}