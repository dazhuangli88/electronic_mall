package com.wxy.mall.jdbc.entity;

/**
 * Jdbc实体类
 *
 * @author 魏心怡
 * @version 1.0.0
 * @date 2022/1/23
 */
public class JdbcEntity {

    private String username;
    private String password;
    private String url;
    private String driverClassName;

    public JdbcEntity() {
    }

    public JdbcEntity(String username, String password, String url, String driverClassName) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.driverClassName = driverClassName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}
