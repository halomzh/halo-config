package com.config.example;

import com.halo.config.constant.HaloConfigConstant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shoufeng
 */

@SpringBootApplication
@RestController
@RequestMapping("/example")
@Slf4j
public class ExampleApp {

	@Autowired
	private RedissonClient redissonClient;

	@Value("${halo.example.name}")
	private String name;

	public static void main(String[] args) {
		SpringApplication.run(ExampleApp.class, args);
	}

	//	@PostConstruct
	public void test001() {
		String propertiesValue = "halo.example.name=\"zhangsan\"\n" +
				"halo.example.age=11\n" +
				"halo.example.type=\"user\"";
		redissonClient.getBucket(HaloConfigConstant.CONFIG_KEY_PREFIX + "example" + ":" + HaloConfigConstant.PROPERTIES + ":application-example.properties").set(propertiesValue);
	}

	@GetMapping("/get")
	public String get() {
		return name;
	}
}
