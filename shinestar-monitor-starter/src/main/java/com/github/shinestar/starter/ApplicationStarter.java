package com.github.shinestar.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 *
 * 仪表盘主启动
 *
 * @author yangcj
 */
@SpringBootApplication
public class ApplicationStarter {


    /**
     * 参数设置，初始化配置文件
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }
}
