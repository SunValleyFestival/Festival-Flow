package com.sunvalley.festivalFlowbe.service.utility;

import com.sunvalley.festivalFlowbe.repository.ConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;


    @Query("select c.value from ConfigurationEntity c where c.name = ?1")
    public boolean getByName(String name) {
        return configurationRepository.getByName(name).isValue();
    }

    @Query("update ConfigurationEntity c set c.value = ?2 where c.name = ?1")
    public void updateByName(String name, boolean value) {
        configurationRepository.updateByName(name, value);
    }
}
