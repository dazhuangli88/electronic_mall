package com.wxy.mall.jdbc.utils;
import com.wxy.mall.jdbc.constant.ConnectionConstant;
import com.wxy.mall.jdbc.entity.JdbcEntity;

import java.sql.*;

/**
 * @author 魏心怡
 * @version 1.0.0
 * @date 2022/1/23
 */
public class DataSource {

    /**
     * 数据库配置信息实体类
     */
    private JdbcEntity entity;
    /**
     * 创建连接的类型，默认是单例
     */
    private String type;
    /**
     * 创建连接的大小，默认等于1
     */
    private static Integer size;
    /**
     * 使用线程池存储连接
     */
    private static ThreadLocal<Connection> local = new ThreadLocal<>();
    /**
     * 定义连接对象
     */
    private Connection conn;

    public DataSource(JdbcEntity entity) {
        this.entity = entity;
    }

    /**
     * 创建数据库连接：
     *
     * 直接使用默认的创建：
     *      默认单例模式，1个连接
     * @return
     */
    public Connection createConnection(){
        synchronized (this){
            size = ConnectionConstant.SIZE;
            if (local.get() == null){
                try {
                    //加载Driver驱动
                    Class.forName(entity.getDriverClassName());
                    //获取连接
                    conn = DriverManager.getConnection(entity.getUrl(),entity.getUsername(),entity.getPassword());
                    local.set(conn);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return local.get();
        }
    }

    /**
     * 指定线程池中创建连接的个数
     * @param size
     * @return
     */
    public Connection createConnection(Integer size){
        synchronized (this){
            if (local.get() == null){
                if (size >= 1){
                    for (int i = 0; i < size; i++) {
                        //不指定类型默认创建单例
                        if (conn == null){
                            try {
                                //加载Driver驱动
                                Class.forName(entity.getDriverClassName());
                                //获取连接
                                conn = DriverManager.getConnection(entity.getUrl(),entity.getUsername(),entity.getPassword());
                                local.set(conn);
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            local.set(conn);
                        }
                    }
                }else{
                    throw new RuntimeException("size小于1异常");
                }
            }
            DataSource.size = size;
            return local.get();
        }
    }

    /**
     * 指定以什么类型创建连接
     * @param size
     * @param type
     * @return
     */
    public Connection createConnection(Integer size, String type){
        synchronized (this){
            if (local.get() == null){
                if (size >= 1){
                    if (ConnectionConstant.MULTITON.equals(type)){
                        //多列方式创建
                        for (int i = 0; i < size; i++) {
                            try {
                                //加载Driver驱动
                                Class.forName(entity.getDriverClassName());
                                //获取连接
                                conn = DriverManager.getConnection(entity.getUrl(),entity.getUsername(),entity.getPassword());
                                local.set(conn);
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        //单例的方式创建
                        createConnection(size);
                    }

                }else{
                    throw new RuntimeException("size小于1异常");
                }
            }
            DataSource.size = size;
            this.type = type;
            return local.get();
        }
    }

    /**
     * 获取数据库的连接
     * @return
     */
    public Connection getConnection() {
        return getConnection(0);
    }

    /**
     * 指定池中创建连接的个数
     * @param size
     * @return
     */
    public Connection getConnection(Integer size) {
        return getConnection(size, ConnectionConstant.SINGLETON);

    }

    /**
     * 指定创建连接的类型
     * @param size
     * @param type
     * @return
     */
    public Connection getConnection(Integer size,String type) {
        if (local.get() == null){
            this.createConnection(size,type);
        }
        return local.get();

    }

    /**
     * 关闭连接
     * @param conn
     * @param ps
     * @param rs
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
        closeResource(ps,rs);
        closeResource(conn);
    }

    /**
     * 关闭连接 方法重载
     * @param conn
     * @param ps
     */
    public static void closeResource(Connection conn, Statement ps) {
        closeResource(ps,null);
        closeResource(conn);
    }

    /**
     * 关闭连接 方法重载
     * @param ps
     * @param rs
     */
    public static void closeResource(Statement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     * @param conn
     */
    public static void closeResource(Connection conn) {
        try {
            if (conn != null) {
                local.set(conn);
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
