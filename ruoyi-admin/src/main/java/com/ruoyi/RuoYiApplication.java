package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);

        System.out.println(
                "================================================亿诺凌科运营后台，启动成功  \n" +
                " __     __  _   _   _        _  __                _             __      __           \n" +
                " \\ \\   / / | \\ | | | |      | |/ /               | |     /\\     \\ \\    / /     /\\    \n" +
                "  \\ \\_/ /  |  \\| | | |      | ' /   ______       | |    /  \\     \\ \\  / /     /  \\   \n" +
                "   \\   /   | . ` | | |      |  <   |______|  _   | |   / /\\ \\     \\ \\/ /     / /\\ \\  \n" +
                "    | |    | |\\  | | |____  | . \\           | |__| |  / ____ \\     \\  /     / ____ \\ \n" +
                "    |_|    |_| \\_| |______| |_|\\_\\           \\____/  /_/    \\_\\     \\/     /_/    \\_\\");
    }
}
