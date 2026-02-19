package com.banking.masking;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MaskingService {

    private final MaskingProperties properties;
    private final ObjectMapper objectMapper;

    public MaskingService(MaskingProperties properties) {
        this.properties = properties;
        // Logic MUST be inside the constructor
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public <T> T mask(T object) {
        if (object == null || !properties.isEnabled()) return object;

        try {
            // Convert object to JSON tree (creates a copy, satisfying the "don't modify original" rule)
            ObjectNode node = objectMapper.valueToTree(object);

            // Mask configured fields
            properties.getFields().forEach(field -> {
                if (node.has(field)) {
                    String original = node.get(field).asText("");
                    node.put(field, maskString(original));
                }
            });

            // Convert back to a new instance of the original object type
            return objectMapper.treeToValue(node, (Class<T>) object.getClass());

        } catch (Exception e) {
            // Fail-safe: if masking fails, return the original object
            return object;
        }
    }

    private String maskString(String original) {
        if (original == null || original.isEmpty()) return "";
        
        switch (properties.getMaskStyle()) {
            case FULL:
                return "*".repeat(original.length());
            case LAST4:
                int len = original.length();
                return "*".repeat(Math.max(0, len - 4)) + original.substring(Math.max(0, len - 4));
            case PARTIAL:
            default:
                if (original.length() <= 3) return "*".repeat(original.length());
                return original.substring(0, 2) + "*".repeat(original.length() - 2);
        }
    }
}