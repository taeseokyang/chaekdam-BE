package com.example.subsub.dto.response;

import com.example.subsub.domain.Property;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PropertyResponse {

    private final LocalDate expiredAt;
    private final String content;

    public PropertyResponse(Property property){
        this.expiredAt = property.getExpiredAt();
        this.content = property.getContent();
    }

    public static List<PropertyResponse> toList(List<Property> property){
        return property.stream()
                .map(PropertyResponse::new)
                .toList();
    }
}
