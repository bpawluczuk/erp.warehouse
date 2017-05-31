package pl.itse.erp.main.application.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Borys on 2017-04-12.
 */
@Configuration
@ComponentScan("pl.itse.erp")
@EntityScan(basePackages = {"pl.itse.erp.domain", "pl.itse.erp.warehouse.domain"})
public class AppConfig {
}
