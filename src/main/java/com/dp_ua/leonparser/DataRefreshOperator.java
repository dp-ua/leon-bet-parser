package com.dp_ua.leonparser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@AllArgsConstructor
@Slf4j
public class DataRefreshOperator {
    private final ParserFacade parserFacade;
    private final ResultProcessor resultProcessor;
    private final ConfigurableApplicationContext applicationContext;

    @PostConstruct
    public void initialize() {
        refreshAndHandleData();
    }

    public void refreshAndHandleData() {
        parserFacade.parseData()
                .flatMap(resultProcessor::handleParsedEvents)
                .subscribe(
                        result -> log.info("Processing complete"),
                        error -> log.error("Error processing data", error),
                        () -> {
                            log.info("Processing finished");
                            applicationContext.close();
                        }
                );
    }
}
