package com.sbapplication.cache;

import com.sbapplication.entity.ConfigJournalAppEntity;
import com.sbapplication.repo.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
@NoArgsConstructor
public class AppCache {

    public enum keys {
        WETHER_API;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    private Map<String,String> appCache = new HashMap<>();

    @PostConstruct
    public void init() {
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all)
        {
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }

    }
}
