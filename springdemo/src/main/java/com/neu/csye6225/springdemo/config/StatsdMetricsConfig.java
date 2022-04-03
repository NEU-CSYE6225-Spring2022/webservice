package com.neu.csye6225.springdemo.config;

import com.timgroup.statsd.NoOpStatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatsdMetricsConfig {

    private final static Logger logger = LogManager.getLogger(StatsdMetricsConfig.class);

    @Value("${publish.metrics}")
    private boolean publishMetrics;

    @Value("${metrics.server.hostname}")
    private String metricsServerHost;

    @Value("${metrics.server.port}")
    private int metricsServerPort;

    @Bean
    public StatsDClient metricsClient() {

        logger.info("Configuring the Statsd metrics with Config PublishMetrics : "+ publishMetrics);
        if(publishMetrics) {
            return new NonBlockingStatsDClient("csye6225", metricsServerHost, metricsServerPort);
        }
        return new NoOpStatsDClient();
    }

}
