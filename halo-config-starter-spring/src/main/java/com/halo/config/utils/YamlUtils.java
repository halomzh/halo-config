package com.halo.config.utils;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * yaml工具
 *
 * @author shoufeng
 */

@Slf4j
public class YamlUtils {

	public static <T> T generateObject(String source, Class<T> tClass) {
		Yaml yaml = new Yaml(new Constructor(tClass));

		return yaml.load(source);
	}

	public static Object generateObject(String source) {
		Yaml yaml = new Yaml();

		return yaml.load(source);
	}

	public static String parseObjectToYaml(Object obj){
		Yaml yaml = new Yaml();
		return yaml.dumpAsMap(obj);
	}

}
