package com.kraken.uk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Outage {
    private String id;
    private String begin;
    private String end;
}
