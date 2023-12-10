package com.example.subsub.dto.request;

import com.example.subsub.domain.Council;
import com.example.subsub.domain.ItemType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCouncilItemRequest {
    private Integer councilId;
    private String name;
    private ItemType type;
}
