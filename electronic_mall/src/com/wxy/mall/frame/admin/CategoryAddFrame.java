package com.wxy.mall.frame.admin;

import com.wxy.mall.dao.impl.CategoryDaoImpl;
import com.wxy.mall.jdbc.connection.MyConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;

/**
 * @author 魏心怡
 * @version 1.0.0
 * @date 2023/6/5
 */
public class CategoryAddFrame extends JFrame {

    private static final Connection CONN = MyConnection.getConnection(6);

    private JPanel contentPane;

    private JTextField categoryAddField;
    private JLabel categoryAddLabel;
    private JButton confirmButton;

    private CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();

    /**
     * 添加分类面板构造方法
     */
    public CategoryAddFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(600, 200, 493, 276);
        setVisible(true);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        categoryAddLabel = new JLabel("分类名称：");
        categoryAddLabel.setFont(new Font("宋体", Font.BOLD, 18));
        categoryAddLabel.setBounds(52, 68, 104, 30);
        contentPane.add(categoryAddLabel);

        categoryAddField = new JTextField();
        categoryAddField.setFont(new Font("宋体", Font.PLAIN, 18));
        categoryAddField.setBounds(166, 64, 248, 42);
        contentPane.add(categoryAddField);
        categoryAddField.setColumns(10);

        confirmButton = new JButton("确认添加");
        confirmButton.setFont(new Font("宋体", Font.BOLD, 20));
        confirmButton.setBounds(166, 168, 131, 48);
        confirmButton.addActionListener(e -> addCategory(e));
        contentPane.add(confirmButton);
    }

    /**
     * 添加商品分类
     *
     * @param e
     */
    private void addCategory(ActionEvent e) {
        // 获取输入框的值
        String categoryName = categoryAddField.getText();
        if ("".equals(categoryName)) {
            JOptionPane.showMessageDialog(this, "商品分类不能为空！！");
        } else {
            // 有值，直接写入数据库
            int resultData = categoryDaoImpl.update(
                    CONN,
                    "insert into tb_category(categoryName) values(?) ",
                    categoryName
            );

            if (resultData > 0) {
                // 插入成功
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "插入异常！！");
            }

        }

    }
}

