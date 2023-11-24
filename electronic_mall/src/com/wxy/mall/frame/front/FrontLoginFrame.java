package com.wxy.mall.frame.front;

import com.wxy.mall.dao.impl.UsersDaoImpl;
import com.wxy.mall.domain.Users;
import com.wxy.mall.enums.UserTypeEnum;
import com.wxy.mall.frame.FrontIndexFrame;
import com.wxy.mall.jdbc.connection.MyConnection;
import com.wxy.mall.util.MD5;
import com.wxy.mall.util.RandomUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * 前台用户登录界面
 *
 * @author 魏心怡
 */
public class FrontLoginFrame extends JFrame {

    private static final Connection CONN = MyConnection.getConnection(6);

    private JPanel contentPane;
    private JTextField userNameField;
    private JTextField passwordField;
    private JTextField codeField;

    private JLabel logoLabel;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JLabel roleLabel;
    private JComboBox roleComboBox;
    private JLabel codeLabel;
    private JLabel showCodeLabel;
    private JButton refreshButton;
    private JButton loginButton;
    private JButton registButton;
    private JButton resetButton;

    /**
     * 全局的验证码code
     */
    private String code;

    private UsersDaoImpl usersDaoImpl = new UsersDaoImpl();

    public FrontLoginFrame() {
        setTitle("电子商城前台");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 617, 472);
        setVisible(true);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        logoLabel = new JLabel("用户登录/注册");
        logoLabel.setFont(new Font("华文新魏", Font.BOLD, 42));
        logoLabel.setBounds(154, 25, 311, 58);
        contentPane.add(logoLabel);

        userNameLabel = new JLabel("用户名：");
        userNameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        userNameLabel.setBounds(93, 121, 89, 32);
        contentPane.add(userNameLabel);

        userNameField = new JTextField();
        userNameField.setFont(new Font("宋体", Font.PLAIN, 18));
        userNameField.setBounds(196, 121, 288, 32);
        contentPane.add(userNameField);
        userNameField.setColumns(10);

        passwordLabel = new JLabel("密  码：");
        passwordLabel.setFont(new Font("宋体", Font.BOLD, 18));
        passwordLabel.setBounds(93, 187, 89, 32);
        contentPane.add(passwordLabel);

        passwordField = new JTextField();
        passwordField.setFont(new Font("宋体", Font.PLAIN, 18));
        passwordField.setColumns(10);
        passwordField.setBounds(196, 187, 288, 32);
        contentPane.add(passwordField);

        roleLabel = new JLabel("角  色：");
        roleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        roleLabel.setBounds(93, 250, 89, 32);
        contentPane.add(roleLabel);

        roleComboBox = new JComboBox();
        roleComboBox.setFont(new Font("宋体", Font.PLAIN, 18));
        roleComboBox.setBounds(196, 250, 288, 32);
        roleComboBox.setModel(new DefaultComboBoxModel(new UserTypeEnum[]{UserTypeEnum.ORDINARY}));
        contentPane.add(roleComboBox);

        codeLabel = new JLabel("验证码：");
        codeLabel.setFont(new Font("宋体", Font.BOLD, 18));
        codeLabel.setBounds(93, 309, 89, 32);
        contentPane.add(codeLabel);

        codeField = new JTextField();
        codeField.setFont(new Font("宋体", Font.PLAIN, 18));
        codeField.setColumns(10);
        codeField.setBounds(196, 310, 106, 32);
        contentPane.add(codeField);

        code = RandomUtil.getSixBitRandom();

        showCodeLabel = new JLabel(code);
        showCodeLabel.setFont(new Font("华文新魏", Font.BOLD, 18));
        showCodeLabel.setBounds(316, 311, 89, 32);
        contentPane.add(showCodeLabel);

        refreshButton = new JButton("刷新");
        refreshButton.setBounds(412, 311, 72, 32);
        refreshButton.addActionListener(e -> {
            code = RandomUtil.getSixBitRandom();
            showCodeLabel.setText(code);
        });
        contentPane.add(refreshButton);

        loginButton = new JButton("登录");
        loginButton.setFont(new Font("宋体", Font.BOLD, 18));
        loginButton.setBounds(93, 366, 99, 32);
        loginButton.addActionListener(e -> userLogin(e));
        contentPane.add(loginButton);

        registButton = new JButton("注册");
        registButton.setFont(new Font("宋体", Font.BOLD, 18));
        registButton.setBounds(249, 366, 99, 32);
        registButton.addActionListener(e -> registerUser(e));
        contentPane.add(registButton);

        resetButton = new JButton("重置");
        resetButton.setFont(new Font("宋体", Font.BOLD, 18));
        resetButton.setBounds(412, 366, 99, 32);
        resetButton.addActionListener(e -> resetUser(e));
        contentPane.add(resetButton);
    }

    /**
     * 用户登录
     * @param e
     */
    private void userLogin(ActionEvent e) {
        // 判断验证码是否输入正确
        String inputCode = codeField.getText();
        if (code.equals(inputCode)) {
            // 判断用户名和密码是否为空
            String username = userNameField.getText();
            String password = passwordField.getText();
            if ("".equals(username) || "".equals(password)) {
                JOptionPane.showMessageDialog(this, "用户名或密码不能为空！");
            } else {
                // 判断用户名是否存在
                Users users = usersDaoImpl.getForObject(
                        CONN,
                        "select * from tb_users where userName = ? and passWord = ?",
                        username,
                        MD5.encrypt(password)
                );

                if (Objects.isNull(users)) {
                    JOptionPane.showMessageDialog(this, "用户不存在！");
                } else {
                    // 登录成功，打开前台首页
                    JOptionPane.showMessageDialog(this, "登录成功！");
                    this.dispose();
                    new FrontIndexFrame(users);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "验证码输入错误！");
        }

        // 清空数据
        userNameField.setText("");
        passwordField.setText("");
        codeField.setText("");
        code = RandomUtil.getSixBitRandom();
        showCodeLabel.setText(code);
    }

    /**
     * 重置时间
     * @param e
     */
    private void resetUser(ActionEvent e) {
        // 清空数据
        userNameField.setText("");
        passwordField.setText("");
        codeField.setText("");
        code = RandomUtil.getSixBitRandom();
        showCodeLabel.setText(code);
    }

    /**
     * 用户注册
     *
     * @param e
     */
    private void registerUser(ActionEvent e) {
        // 判断验证码是否输入正确
        String inputCode = codeField.getText();
        if (code.equals(inputCode)) {
            // 判断用户名和密码是否为空
            String username = userNameField.getText();
            String password = passwordField.getText();
            if ("".equals(username) || "".equals(password)) {
                JOptionPane.showMessageDialog(this, "用户名或密码不能为空！");
            } else {
                // 判断用户名是否存在
                ArrayList<Users> usersList = usersDaoImpl.getForList(
                        CONN,
                        "select * from tb_users where userName = ?",
                        username
                );

                if (usersList.size() > 0) {
                    // 用户名已存在
                    JOptionPane.showMessageDialog(this, "用户名已存在！");
                } else {
                    // 向数据库中插入用户信息
                    usersDaoImpl.update(
                            CONN,
                            "insert into tb_users(userName,passWord,userType,createTime,updateTime) values(?,?,?,?,?)",
                            username,
                            MD5.encrypt(password),
                            UserTypeEnum.ORDINARY.name(),
                            new Date(),
                            new Date()
                    );
                    JOptionPane.showMessageDialog(this, "注册成功，快去登录吧！");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "验证码输入错误！");
        }

        // 清空数据
        userNameField.setText("");
        passwordField.setText("");
        codeField.setText("");
        code = RandomUtil.getSixBitRandom();
        showCodeLabel.setText(code);
    }
}
