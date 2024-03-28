package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.utility.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, Integer> {

    ConfigurationEntity getByName(String name);

    @Modifying
    @Query("update ConfigurationEntity c set c.value = :value where c.name = :name")
    void updateByName(String name, boolean value);
}