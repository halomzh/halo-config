package com.halo.config.processor;

import com.halo.config.constant.HaloConfigConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author shoufeng
 */

@Slf4j
public class ConfigEnvironmentPostProcessor implements EnvironmentPostProcessor {

	@SneakyThrows
	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		Boolean enable = environment.getProperty("halo.config.enable", Boolean.class);
		if (ObjectUtils.isEmpty(enable) || !enable) {
			return;
		}
		String defaultNameSpace = environment.getProperty("halo.config.default-name-space");
		if (StringUtils.isBlank(defaultNameSpace)) {
			throw new RuntimeException("启动halo-config配置中心失败: 默认名称空间不能为空");
		}
		HashMap<String, String> propertiesNameValueMap = new HashMap<>();
		Config config = Config.fromYAML(this.getClass().getClassLoader().getResourceAsStream("halo-conf-redisson.yml"));
		RedissonClient redissonClient = Redisson.create(config);
		//目前就支持properties，其他格式先不管了
		for (String key : redissonClient.getKeys().getKeysByPattern(HaloConfigConstant.CONFIG_KEY_PREFIX + defaultNameSpace + ":" + HaloConfigConstant.PROPERTIES + ":*." + HaloConfigConstant.PROPERTIES)) {
			String[] keySplits = key.split(":");
			String propertiesName = keySplits[keySplits.length - 1];
			String activeProfile = propertiesName.replace("." + HaloConfigConstant.PROPERTIES, "").replace("application", "").replace("-", "");
			environment.addActiveProfile(activeProfile);
			propertiesNameValueMap.put(propertiesName, (String) redissonClient.getBucket(key).get());
		}
		propertiesNameValueMap.forEach((propertiesName, propertiesValue) -> {
			MutablePropertySources propertySources = environment.getPropertySources();
			Properties properties = new Properties();
			try {
				properties.load(new StringReader(propertiesValue));
			} catch (IOException e) {
				e.printStackTrace();
			}
			propertySources.addLast(new PropertiesPropertySource("applicationConfig: [classpath:/" + propertiesName + "]", properties));
		});
	}

}
