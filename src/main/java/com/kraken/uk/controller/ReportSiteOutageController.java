package com.kraken.uk.controller;

import com.kraken.uk.dto.SiteOutagePostResponseDto;
import com.kraken.uk.service.SiteOutageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report-outages")
@RequiredArgsConstructor
public class ReportSiteOutageController {

    private final SiteOutageService siteOutageService;

    @PostMapping("/{siteId}")
    public SiteOutagePostResponseDto reportSiteOutage(@PathVariable final String siteId) {
        return siteOutageService.reportSiteOutage(siteId);
    }
}
