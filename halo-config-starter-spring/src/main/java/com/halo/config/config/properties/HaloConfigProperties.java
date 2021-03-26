package com.halo.config.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author shoufeng
 */

@Data
@ConfigurationProperties(prefix = HaloConfigProperties.PREFIX)
@NoArgsConstructor
@AllArgsConstructor
public class HaloConfigProperties {

	public static final String PREFIX = "halo.config";

	/**
	 * 是否开启
	 */
	private boolean enable = false;

	/**
	 * 默认名称空间
	 */
	private String defaultNameSpace;
	
}
