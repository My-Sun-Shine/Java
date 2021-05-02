package com.springboot.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @Classname MyAppHealthIndicator
 * @Date 2021/5/2 16:40
 * @Created by FallingStars
 * @Description
 */
@Component
public class MyAppHealthIndicator implements HealthIndicator {
    @Override
    public Health getHealth(boolean includeDetails) {
        return health();
    }

    @Override
    public Health health() {
        //自定义的检查方法
        //Health.up().build()代表健康
        return Health.down().withDetail("msg", "服务异常").build();
    }
}
