package org.muzi.open.helper.config;

import java.lang.annotation.*;

/**
 * @author: muzi
 * @time: 2018-08-11 14:10
 * @description:
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface PersistableConfig {
    String key() default "";

    boolean skip() default false;
}
