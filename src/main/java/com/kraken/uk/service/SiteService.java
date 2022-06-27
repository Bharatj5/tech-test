package com.kraken.uk.service;

import com.kraken.uk.domain.SiteInfo;
import com.kraken.uk.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository siteRepository;

    public SiteInfo getSiteInfo(final String siteId) {
        return siteRepository.getSiteInfo(siteId);
    }

}
