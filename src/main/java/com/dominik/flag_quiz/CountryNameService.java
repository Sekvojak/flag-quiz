package com.dominik.flag_quiz;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class CountryNameService {

    private final Map<String, String> namesByCode;

    public CountryNameService() {
        this.namesByCode = load();
    }

    private Map<String, String> load() {
        try (InputStream is = new ClassPathResource("countries-sk.json").getInputStream()) {
            ObjectMapper om = new ObjectMapper();
            Map<String, String> map = om.readValue(is, new TypeReference<>() {});
            // normalizuj kľúče na lowercase (pre istotu)
            return map.entrySet().stream()
                    .collect(java.util.stream.Collectors.toMap(
                            e -> e.getKey().toLowerCase(),
                            Map.Entry::getValue
                    ));
        } catch (Exception e) {
            throw new RuntimeException("Neviem načítať countries-sk.json", e);
        }
    }

    public String getNameOrFallback(String code) {
        if (code == null) return null;
        return namesByCode.getOrDefault(code.toLowerCase(), code.toLowerCase());
    }

    public boolean hasName(String code) {
        if (code == null) return false;
        return namesByCode.containsKey(code.toLowerCase());
    }

}