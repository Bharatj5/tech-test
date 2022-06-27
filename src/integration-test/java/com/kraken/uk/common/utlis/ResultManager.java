package com.kraken.uk.common.utlis;

import com.kraken.uk.domain.Outage;
import com.kraken.uk.domain.SiteInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ResultManager {

    @Getter
    private ResponseEntity<String> responseEntity;
    @Getter
    private List<Outage> outages;
    @Getter
    private SiteInfo siteInfo;
    @Getter@Setter
    private int statusCode;

    public void reset() {
        responseEntity = null;
        statusCode = 0;
        outages = null;
        siteInfo = null;
    }

    public void updateResponseEntity(final ResponseEntity<String> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public void updateOutages(final List<Outage> outageResponse) {
        this.outages = outageResponse;
    }

    public void updateSiteInfo(final SiteInfo siteInfo) {
        this.siteInfo = siteInfo;
    }
}
