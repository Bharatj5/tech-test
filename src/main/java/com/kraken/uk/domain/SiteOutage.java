package com.kraken.uk.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "of")
public class SiteOutage {
    private String id;
    private String name;
    private String begin;
    private String end;
}
