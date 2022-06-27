package com.kraken.uk.service;

import com.kraken.uk.domain.Outage;
import com.kraken.uk.repository.OutageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutageService {

    private final OutageRepository outageRepository;


    public List<Outage> getReportedOutage() {
       return outageRepository.getReportedOutage();
    }

}
