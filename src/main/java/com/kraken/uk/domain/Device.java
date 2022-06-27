package com.kraken.uk.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "of")
public class Device {
    private String id;
    private String name;
}
