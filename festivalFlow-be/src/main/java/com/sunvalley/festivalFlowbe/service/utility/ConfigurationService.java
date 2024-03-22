package com.sunvalley.festivalFlowbe.service.utility;

import com.sunvalley.festivalFlowbe.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;


    @Autowired
    public ConfigurationService(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    public boolean getByName(String name) {
        return configurationRepository.getByName(name).isValue();
    }

    public void updateByName(String name, boolean value) {
        configurationRepository.updateByName(name, value);
    }
}
