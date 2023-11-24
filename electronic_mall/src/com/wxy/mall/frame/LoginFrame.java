package com.wxy.mall.frame;


import com.wxy.mall.dao.impl.UsersDaoImpl;
import com.wxy.mall.domain.Users;
import com.wxy.mall.enums.UserTypeEnum;
import com.wxy.mall.frame.admin.IndexAdminFrame;
import com.wxy.mall.jdbc.connection.MyConnection;
import com.wxy.mall.util.MD5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Objects;

/**
 * 登录界面
 *
 * @author 魏心怡
 */
public class LoginFrame extends JFrame {

    private static final Connection CONN = MyConnection.getConnection(6);

    /**
     * 定义文本输入框
     */
    private JTextField userTextField;
    private JTextField passWordField;

    /**
     * 定义一个容器盒子
     */
    private JComboBox userBox;

    private UsersDaoImpl usersDaoImpl;

    /**
     * 在构造方法里面去初始化容器组件
     */
    public LoginFrame() {
        // 初始化用户业务层对象
        usersDaoImpl = new UsersDaoImpl();
        init();
    }

    private void init() {
        //要设置的背景图片
        ImageIcon img = new ImageIcon("src\\image\\背景图.jpg");
        JLabel imgLabel = new JLabel(img);
        //将背景图放在标签里
        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
        //将背景标签添加到JFrame的LayeredPane面板里。
        imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        // 设置背景标签的位置
        Container contain = this.getContentPane();
        getContentPane().setLayout(null);

        JLabel mangerLabel = new JLabel("电子商城系统");
        mangerLabel.setIcon(new ImageIcon(LoginFrame.class.getResource("/image/企业管理.png")));
        mangerLabel.setForeground(Color.BLACK);
        mangerLabel.setBackground(Color.WHITE);
        mangerLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
        mangerLabel.setBounds(275, 33, 482, 52);
        getContentPane().add(mangerLabel);

        JLabel userLabel = new JLabel("用户账号:");
        userLabel.setIcon(new ImageIcon(LoginFrame.class.getResource("/image/账号.png")));
        userLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        userLabel.setBounds(218, 138, 136, 28);
        getContentPane().add(userLabel);

        userTextField = new JTextField("系统管理员");
        // 设置账号的点击事件 ——》 点击的时候将文本框中的内容清空
        userTextField.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // 清空内容
                userTextField.setText("");
            }
        });
        userTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userTextField.setBounds(364, 138, 275, 34);
        getContentPane().add(userTextField);
        userTextField.setColumns(10);

        JLabel passWordLJLabel = new JLabel("用户密码:");
        passWordLJLabel.setIcon(new ImageIcon(LoginFrame.class.getResource("/image/密   码.png")));
        passWordLJLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        passWordLJLabel.setBounds(218, 210, 136, 34);
        getContentPane().add(passWordLJLabel);

        passWordField = new JTextField("123456");
        // 给密码框添加鼠标点击事件  点击密码框的时候，清空密码框中的内容
        passWordField.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                passWordField.setText("");
            }
        });
        passWordField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        passWordField.setBounds(364, 213, 275, 34);
        getContentPane().add(passWordField);
        passWordField.setColumns(10);

        JLabel user = new JLabel("角色选择: ");
        user.setIcon(new ImageIcon(LoginFrame.class.getResource("/image/管理员.png")));
        user.setFont(new Font("微软雅黑", Font.BOLD, 20));
        user.setBounds(218, 294, 136, 34);
        getContentPane().add(user);

        // 创建下拉列表框
        userBox = new JComboBox();
        userBox.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userBox.setModel(new DefaultComboBoxModel(new UserTypeEnum[]{UserTypeEnum.ADMIN}));
        userBox.setBounds(364, 297, 275, 34);
        getContentPane().add(userBox);
        ((JPanel) contain).setOpaque(false);

        JButton registerBtn = new JButton("登录");
        // 使用Lambda表达式
        registerBtn.addActionListener(e -> register(e));
        registerBtn.setIcon(new ImageIcon(LoginFrame.class.getResource("/image/登录.png")));
        registerBtn.setForeground(Color.BLACK);
        registerBtn.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        registerBtn.setBounds(175, 389, 119, 40);
        getContentPane().add(registerBtn);

        JButton resetBtn = new JButton("重置");
        resetBtn.addActionListener(e -> {
            userTextField.setText("由不少于6位的数组和字母组成！");
            passWordField.setText("由不少于6位的数组和字母组成！");
            userBox.setSelectedIndex(0);
        });
        resetBtn.setIcon(new ImageIcon(LoginFrame.class.getResource("/image/重置.png")));
        resetBtn.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        resetBtn.setBounds(538, 389, 119, 41);
        getContentPane().add(resetBtn);

        this.setTitle("电子商城");
        //设置窗体大小
        this.setSize(828, 525);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * 登录事件
     *
     * @param a
     */
    protected void register(ActionEvent a) {
        // 获取用户账号中的内容
        String userName = userTextField.getText();
        System.out.println(userName);
        // 获取密码
        String userPassWord = passWordField.getText();
        // 首先判断用户名和密码是否为空
        if ("".equals(userName) || "".equals(userPassWord)) {
            JOptionPane.showMessageDialog(this, "用户名和密码不能为空！！");
        }

        // 判断是否是管理员身份
        if (UserTypeEnum.ADMIN.equals(userBox.getSelectedItem())) {
            // 先根据用户名查询用户信息
            Users users = usersDaoImpl.getForObject(
                    CONN,
                    "select * from tb_users where userName = ?",
                    userName
            );

            // 判断是否可以查询到用户信息
            if (Objects.isNull(users)) {
                JOptionPane.showMessageDialog(this, "用户不存在！！");
            } else {
                // 校验密码
                if (users.getPassWord().equals(MD5.encrypt(userPassWord))) {
                    // 说明校验通过 直接登录到后台管理界面
                    JOptionPane.showMessageDialog(this, "登录成功！！");

                    //回到初始值
                    userTextField.setText("由不少于6位的数组和字母组成！");
                    passWordField.setText("由不少于6位的数组和字母组成！");
                    userBox.setSelectedIndex(0);

                    // 关闭当前窗口
                    this.dispose();
                    // 打开后台首页窗口 TODO 可能需要传递参数，还需要主键Id
                    new IndexAdminFrame(userName);
                } else {
                    JOptionPane.showMessageDialog(this, "密码错误！！");
                }
            }


        }
    }

    public static void main(String[] args) {

        new LoginFrame();
    }

}

