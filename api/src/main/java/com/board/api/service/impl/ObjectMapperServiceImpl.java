package com.board.api.service.impl;

import com.board.api.service.ObjectMapperService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ObjectMapperServiceImpl implements ObjectMapperService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Map<String, Object> convertToMap(Object o) {

        Map<String, Object> result = objectMapper.convertValue(o, new TypeReference<Map<String, Object>>() {});
        result.remove("password");

        return result;
    }
}
