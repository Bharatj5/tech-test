package com.kraken.uk.domain;

import lombok.Data;

import java.util.List;

@Data
public class SiteInfo {
    private String id;
    private String name;
    private List<Device> devices;
}
