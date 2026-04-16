package com.dominik.flag_quiz;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryNameService {

    private final Map<String, List<String>> namesByCode;

    public CountryNameService() {
        this.namesByCode = load();
    }

    private Map<String, List<String>> load() {
        try (InputStream is = new ClassPathResource("countries-sk.json").getInputStream()) {
            ObjectMapper om = new ObjectMapper();

            Map<String, Object> raw = om.readValue(is, new TypeReference<>() {});

            Map<String, List<String>> result = new HashMap<>();

            for (Map.Entry<String, Object> entry : raw.entrySet()) {
                String code = entry.getKey().toLowerCase();
                Object value = entry.getValue();

                if (value instanceof String s) {
                    result.put(code, List.of(s));
                } else if (value instanceof List<?> list) {
                    List<String> names = list.stream()
                            .filter(Objects::nonNull)
                            .map(Object::toString)
                            .collect(Collectors.toList());
                    result.put(code, names);
                }
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Neviem načítať countries-sk.json", e);
        }
    }

    public String getPrimaryName(String code) {
        if (code == null) return null;
        List<String> names = namesByCode.get(code.toLowerCase());
        if (names == null || names.isEmpty()) return code.toLowerCase();
        return names.get(0);
    }

    public List<String> getAllNames(String code) {
        if (code == null) return List.of();
        return namesByCode.getOrDefault(code.toLowerCase(), List.of(code.toLowerCase()));
    }

    public boolean hasName(String code) {
        return namesByCode.containsKey(code.toLowerCase());
    }

    public Set<String> codes() {
        return namesByCode.keySet();
    }
}