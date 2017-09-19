package com.capitalone.dashboard.service;


        import com.capitalone.dashboard.misc.HygieiaException;
        import com.capitalone.dashboard.model.PropertyManager;
        import com.capitalone.dashboard.request.PropertyManagerRequest;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;

public interface PropertyManagerService {

    PropertyManager create(PropertyManager collectorProperties) throws HygieiaException;

    PropertyManager getByName(String name) throws HygieiaException;

    Page<PropertyManager> collectorPropertiesWithFilter(String filter, Pageable pageable);

    PropertyManager update(PropertyManager collectorProperties) throws HygieiaException;

    PropertyManager remove(PropertyManagerRequest collectorPropertiesRequest) throws HygieiaException;
}