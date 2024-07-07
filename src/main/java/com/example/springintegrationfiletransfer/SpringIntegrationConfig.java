package com.example.springintegrationfiletransfer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

import java.io.File;

@Configuration
@EnableIntegration
public class SpringIntegrationConfig {

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel")
    public FileReadingMessageSource fileReadingMessageSource(){
        CompositeFileListFilter fileListFilter = new CompositeFileListFilter();
        fileListFilter.addFilter(new SimplePatternFileListFilter("*.txt"));
        FileReadingMessageSource reader = new FileReadingMessageSource();
        reader.setDirectory(new File("C:\\Users\\tOxicKisses\\Desktop\\source"));
        reader.setFilter(fileListFilter);
        return reader;
    }

    @Bean
    @ServiceActivator(inputChannel = "fileInputChannel")
    public FileWritingMessageHandler fileWritingMessageHandler(){
        FileWritingMessageHandler writer = new FileWritingMessageHandler(new File("C:\\Users\\tOxicKisses\\Desktop\\Destination"));
        writer.setAutoCreateDirectory(true);
        writer.setExpectReply(false);
        return writer;
    }
}
