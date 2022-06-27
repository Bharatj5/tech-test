package com.kraken.uk.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(builderMethodName = "of")
public class SiteOutagePostResponseDto {
    private final int noOfOutageReported;
    private final LocalDateTime reportedAt;
}
