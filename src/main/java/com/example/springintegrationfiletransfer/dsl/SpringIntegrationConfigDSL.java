package com.example.springintegrationfiletransfer.dsl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

import java.io.File;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class SpringIntegrationConfigDSL {
    @Bean
    public IntegrationFlow fileIntegrationFlow() {
        CompositeFileListFilter<File> fileFilter = new CompositeFileListFilter<>();
        fileFilter.addFilter(new SimplePatternFileListFilter("*.txt"));

        return IntegrationFlow.from(
                        Files.inboundAdapter(new File("C:\\Users\\tOxicKisses\\Desktop\\source"))
                                .filter(fileFilter),
                        e -> e.poller(p -> p.fixedDelay(1000)))
                .handle(Files.outboundAdapter(new File("C:\\Users\\tOxicKisses\\Desktop\\Destination"))
                        .autoCreateDirectory(true))
                .get();
    }
}
