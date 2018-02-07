package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorType;

import java.util.Collection;

/**
 * A {@link Collector} repository
 */
public interface CollectorRepository extends BaseCollectorRepository<Collector> {
    Iterable<Collector> findAllByCollectorTypeNotInAndNameNotIn(Collection<String> typeExcludeCollection, Collection<String> nameExcludeCollection);
    Collector findByNameAndCollectorType(String name, CollectorType collectorType);
}
