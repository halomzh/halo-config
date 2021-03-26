package com.halo.config.annotation;

import java.lang.annotation.*;

/**
 * TODO 用于自动刷新配置（还未实现）
 * @author shoufeng
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RefreshValue {

	String nameSpace() default "";

}
