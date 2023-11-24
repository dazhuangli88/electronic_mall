package com.wxy.mall.jdbc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;

/**
 * 基本的Jdbc通用方法
 *
 * @author 魏心怡
 * @version 1.0.0
 * @date 2022/1/24
 */
public class JdbcTemplate<T> {

    public Class<T> clazz = null;

    {
        //获取当前BaseDAO的子类继承的父类中的泛型。
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;

        //获取了父类的泛型参数
        Type[] typeArguments = paramType.getActualTypeArguments();
        //泛型的第一个参数
        clazz = (Class<T>) typeArguments[0];
    }

    /**
     * 通用的增删改操作
     *
     * sql中占位符的个数与可变形参的长度相同！
     *
     * sql中占位符的个数与可变形参的长度相同！
     *
     * @param conn 连接对象
     * @param sql 需要处理的sql语句
     * @param agrs 可变参数，参数的顺序应该和sql语句的占位符顺序一样，以保证参数对应。
     */
    public int update(Connection conn,String sql,Object ... agrs) {
        PreparedStatement ps = null;
        try {
            //1. 预编译sql语句，返回PrepareStatement的实例
            ps = conn.prepareStatement(sql);
            //2. 填充占位符
            for (int i = 0; i < agrs.length; i++) {
                //小心参数声明错误！！
                ps.setObject(i+1,agrs[i]);
            }
            //3. 执行
            return ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            //4. 关闭资源
            DataSource.closeResource(null, ps);
        }
        return -1;
    }

    /**
     * 通用的查询结果集
     * @param sql
     * @param args
     * @return
     */
    public ArrayList<T> getForList(Connection conn, String sql, Object ... args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }

            rs = ps.executeQuery();
            //获取结果集的元数据: ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columCount = rsmd.getColumnCount();
            //创建集合对象
            ArrayList<T> list = new ArrayList<T>();
            while(rs.next()) {
                T t = clazz.newInstance();
                //处理结果集一行数据中的每一列   通过循环获取每列的值
                for (int i = 0; i < columCount; i++) {
                    //获取列值
                    Object columnValue = rs.getObject(i+1);

                    //获取每个列的别名
                    String columnLabel = rsmd.getColumnLabel(i+1);

                    //给cust对象指定的columnName属性，赋值为columnValue。通过反射。
                    Field field = clazz.getDeclaredField(columnLabel);
                    //设置可以访问
                    field.setAccessible(true);
                    //设置值
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DataSource.closeResource(null, ps, rs);
        }
        return null;
    }

    /**
     * 针对于不同的表的通用的查询操作，返回表中的一条记录。
     * @param sql
     * @param args
     * @return
     */
    public T getForObject(Connection conn,String sql,Object ... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }

            rs = ps.executeQuery();
            //获取结果集的元数据: ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columCount = rsmd.getColumnCount();
            if (rs.next()) {
                T t = clazz.newInstance();
                //处理结果集一行数据中的每一列     通过循环获取每列的值
                for (int i = 0; i < columCount; i++) {
                    //获取列值
                    Object columnValue = rs.getObject(i+1);

                    //获取每个列的别名
                    String columnLabel = rsmd.getColumnLabel(i+1);

                    //给cust对象指定的columnName属性，赋值为columnValue。通过反射。
                    Field field =  clazz.getDeclaredField(columnLabel);

                    //设置可以访问
                    field.setAccessible(true);
                    //设置值
                    field.set(t, columnValue);
                }
                return t;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DataSource.closeResource(null, ps, rs);
        }
        return null;
    }

    /**
     * 用于查询特殊值的通用的方法
     * @param conn
     * @param sql
     * @param args
     * @param <E>
     * @return
     */
    public <E> E getValue(Connection conn,String sql,Object ... args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return (E) rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DataSource.closeResource(null, ps,rs);
        }
        return null;
    }
}
