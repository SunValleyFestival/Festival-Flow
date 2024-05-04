package com.sunvalley.festivalFlowbe.service.utility;

import com.sunvalley.festivalFlowbe.repository.ConfigurationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    public String getByName(String name) {
        return configurationRepository.getByName(name).getValue();
    }

    @Transactional
    public void updateByName(String name, boolean value) {
        configurationRepository.updateByName(name, value);
    }
}
