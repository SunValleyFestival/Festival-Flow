package com.sunvalley.festivalFlowbe.service.utility;

import com.sunvalley.festivalFlowbe.repository.ConfigurationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;


    public boolean getByName(String name) {
        return configurationRepository.getByName(name).isValue();
    }

    @Transactional
    public void updateByName(String name, boolean value) {
        configurationRepository.updateByName(name, value);
    }
}
