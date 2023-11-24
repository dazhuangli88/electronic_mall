package com.wxy.mall.jdbc.factory;

import com.wxy.mall.jdbc.constant.JdbcConstant;
import com.wxy.mall.jdbc.entity.JdbcEntity;
import com.wxy.mall.jdbc.utils.DataSource;

import java.util.Properties;

/**
 * JDBC工厂类
 *
 * @author 魏心怡
 * @version 1.0.0
 * @date 2022/1/23
 */
public class JdbcFactory {

    /**
     * 创建数据源
     *
     * @param properties
     * @return
     */
    public static DataSource createDataSource(Properties properties) {
        //读取配置文件信息
        String username = properties.getProperty(JdbcConstant.JDBC_USERNAME);
        String password = properties.getProperty(JdbcConstant.JDBC_PASSWORD);
        String url = properties.getProperty(JdbcConstant.JDBC_URL);
        String driverClassName = properties.getProperty(JdbcConstant.JDBC_DRIVER_CLASS_NAME);

        return new DataSource(new JdbcEntity(username,password,url,driverClassName));
    }
}
