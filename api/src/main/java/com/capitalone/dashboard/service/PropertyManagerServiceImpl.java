
package com.capitalone.dashboard.service;

import com.capitalone.dashboard.misc.HygieiaException;
import com.capitalone.dashboard.model.PropertyManager;
import com.capitalone.dashboard.repository.PropertyManagerRepository;
import com.capitalone.dashboard.request.PropertyManagerRequest;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

@Service
public class PropertyManagerServiceImpl implements PropertyManagerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyManagerServiceImpl.class);

    private final PropertyManagerRepository propertyManagerRepository;

    @Autowired
    public PropertyManagerServiceImpl(PropertyManagerRepository propertyManagerRepository) {
        this.propertyManagerRepository = propertyManagerRepository;
    }
    public PropertyManager create(PropertyManager collectorProperties) throws HygieiaException{
        LOGGER.info("Successfully called service");

        Iterator<Map.Entry<Object,Object>> iter = collectorProperties.getProperties().entrySet().iterator();
        Properties collectorPropertiesForSubmit = new Properties();
        while (iter.hasNext()) {
            Map.Entry<Object,Object> entry = iter.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();

            if(key.indexOf(".") != -1){
                key = key.substring(key.indexOf(".") + 1);
                if(!key.equals("name")){
                    collectorPropertiesForSubmit.put(key,value);
                }else{
                    collectorProperties.setName(value);
                    PropertyManager prop = getByName(value);

                    if(prop != null){
                        collectorProperties.setId(prop.getId());
                    }

                }
            }
        }
        if(collectorProperties.getName() == null || collectorProperties.getName().isEmpty()){
            throw new HygieiaException("Name field not found.", HygieiaException.BAD_DATA);
        }
        collectorProperties.setProperties(collectorPropertiesForSubmit);
        return propertyManagerRepository.save(collectorProperties);

    }
    @Override
    public PropertyManager getByName(String name) throws HygieiaException{
        if(name == null || name.isEmpty()){
            throw new HygieiaException("Name field not found.", HygieiaException.BAD_DATA);
        }
        return propertyManagerRepository.findByName(name);
    }
    public PropertyManager get(ObjectId id) throws HygieiaException{
        if(id == null){
            throw new HygieiaException("No Object ID found.", HygieiaException.BAD_DATA);
        }
        return propertyManagerRepository.findOne(id);
    }
    @Override
    public Iterable<PropertyManager> collectorPropertiesWithFilter(Pageable pageable) {
        Iterable<PropertyManager> propertiesString = propertyManagerRepository.findAll();
        for (PropertyManager propertyManager : propertiesString) {
            LOGGER.info("Successfully called service " + propertyManager.getName());
        }

        return propertiesString;
    }
    @Override
    public PropertyManager update(PropertyManager collectorProperties) throws HygieiaException{
        if(collectorProperties.getName() == null || collectorProperties.getName().isEmpty()){
            throw new HygieiaException("Name field not found.", HygieiaException.BAD_DATA);
        }else{
            PropertyManager properties = getByName(collectorProperties.getName());
            properties.getProperties().putAll(collectorProperties.getProperties());
            return propertyManagerRepository.save(properties);
        }
    }
    @Override
    public PropertyManager remove(PropertyManagerRequest collectorPropertiesRequest) throws HygieiaException{
        if(collectorPropertiesRequest.getName() == null || collectorPropertiesRequest.getName().isEmpty()){
            throw new HygieiaException("Name field not found.", HygieiaException.BAD_DATA);
        }else{
            PropertyManager properties = getByName(collectorPropertiesRequest.getName());
            String key = collectorPropertiesRequest.getPropertiesKey();
            if(key != null && !key.isEmpty()){
                properties.getProperties().remove(key);
                return propertyManagerRepository.save(properties);
            }else{
                throw new HygieiaException("Key field not found.", HygieiaException.BAD_DATA);
            }

        }
    }
}