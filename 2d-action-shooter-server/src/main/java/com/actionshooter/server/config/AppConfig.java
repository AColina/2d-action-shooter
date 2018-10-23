package com.actionshooter.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author Angel Colina
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = "com.actionshooter.server", excludeFilters = {
        @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
        @ComponentScan.Filter(value = Configuration.class, type = FilterType.ANNOTATION)
})
public class AppConfig {

}
