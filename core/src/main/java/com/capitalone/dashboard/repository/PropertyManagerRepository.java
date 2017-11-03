package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.PropertyManager;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;



/**
 * {@link PropertyManager} repository.
 */
public interface PropertyManagerRepository extends CrudRepository<PropertyManager, ObjectId> {

    PropertyManager findByName(String name);

    Page<PropertyManager> findAll(Pageable pageable);

}