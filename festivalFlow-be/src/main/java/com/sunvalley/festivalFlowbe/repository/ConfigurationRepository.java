package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.utility.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, Integer> {

    ConfigurationEntity getByName(String name);

    @Modifying
    @Query("update ConfigurationEntity c set c.value = ?2 where c.name = ?1")
    void updateByName(String name, boolean value);
}