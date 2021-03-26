package com.halo.config.config;

import com.halo.config.config.properties.HaloConfigProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author shoufeng
 */

@Configuration
@EnableConfigurationProperties(value = {
		HaloConfigProperties.class
})
@ConditionalOnProperty(prefix = HaloConfigProperties.PREFIX, value = "enable")
@Slf4j
@Data
public class HaloConfigConfig {
}
