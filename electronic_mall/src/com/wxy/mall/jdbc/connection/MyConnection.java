package com.wxy.mall.jdbc.connection;

import com.wxy.mall.jdbc.factory.JdbcFactory;
import com.wxy.mall.jdbc.utils.DataSource;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

/**
 * 自定义活动数据连接类
 *
 * @author 魏心怡
 * @version 1.0.0
 * @date 2023/5/31
 */
@Builder
@Data
public class MyConnection {

    /**
     * 静态获取数据库连接
     * @return
     */
    public static Connection getConnection(Integer size){
        try {
            // 创建配置类
            Properties properties = new Properties();
            // 获取jdbc配置文件流 加载配置流
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("com/wxy/mall/jdbc/jdbc.properties"));
            // 通过自定义jdbc工厂获取连接
            DataSource dataSource = JdbcFactory.createDataSource(properties);
            return dataSource.getConnection(size);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
