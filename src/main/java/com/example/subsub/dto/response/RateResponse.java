package com.example.subsub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RateResponse {
    private Integer loveCount;
    private Integer goodCount;
    private Integer badCount;
}
