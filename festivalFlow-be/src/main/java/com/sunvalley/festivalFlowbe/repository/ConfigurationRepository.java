package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.utility.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, Integer> {
    ConfigurationEntity getByName(String name);

    void updateByName(String name, boolean value);
}