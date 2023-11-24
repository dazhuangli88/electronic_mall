package com.wxy.mall.frame.admin;

import com.wxy.mall.dao.impl.CategoryDaoImpl;
import com.wxy.mall.dao.impl.ProductInventoryDaoImpl;
import com.wxy.mall.dao.impl.StockGoodsDaoImpl;
import com.wxy.mall.dao.impl.UsersDaoImpl;
import com.wxy.mall.domain.Category;
import com.wxy.mall.domain.ProductInventory;
import com.wxy.mall.domain.Users;
import com.wxy.mall.enums.UserTypeEnum;
import com.wxy.mall.frame.LoginFrame;
import com.wxy.mall.frame.table.MyJTable;
import com.wxy.mall.jdbc.connection.MyConnection;
import com.wxy.mall.util.MD5;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 后台管理首页
 *
 * @author 魏心怡
 */
public class IndexAdminFrame extends JFrame {

    private static final Connection CONN = MyConnection.getConnection(6);

    /**
     * 定义用户账号变量
     */
    private String userAccount;

    private JPanel contentPane;

    private JLabel userLabel;
    private JPanel categoryPanel;
    private JPanel productPanel;
    private JPanel userPanel;
    private JPanel accountPanel;
    private JPanel restPanel;

    private JLabel originalPasswordLabel;
    private JPasswordField originalPasswordField;

    private JLabel newPasswordLabel;
    private JPasswordField newPasswordField;

    private JLabel confirmPasswordLabel;
    private JPasswordField confirmPasswordField;

    private JButton confirmButton;

    private JLabel accountLabel;
    private JTextField accountField;
    private JLabel phoneLabel;
    private JTextField phoneField;
    private JLabel nickNameLabel;
    private JTextField nickNameField;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel userTypeLabel;
    private JTextField userTypeField;

    private JLabel categoryLabel;
    private JTextField categoryField;

    private JButton saveOrUpdateButton;
    private JButton categorySearchButton;
    private JButton categoryAddButton;

    private JPanel categoryTablePanel;
    private JButton categoryRefreshButton;


    private JPanel stockGoodsPanel;
    private JLabel productCategoryLabel;
    private JComboBox productCategoryComboBox;
    private JLabel productNameLabel;
    private JTextField productNameField;
    private JTextField buyerField;
    private JTextField contactInfoField;
    private JTextField productNumField;
    private JTextField productPriceField;
    private JTextField purchasingManufacturerField;
    private JTextField manufacturerPhoneNumberField;
    private JTextField manufacturerAddressField;
    private JLabel totalPriceGoodsLabel;
    private JTextField totalPriceGoodsField;
    private JLabel firstLabel;
    private JLabel buyerLabel;
    private JLabel contactInfoLabel;
    private JLabel productNumLabel;
    private JTextArea purchaseInstructionsTextArea;
    private JButton confirmReceiptButton;
    private JLabel productPriceLabel;
    private JLabel purchasingManufacturerLabel;

    private JPanel inventoryPanel;
    private JLabel inventoryProductCategoryLabel;
    private JTextField inventoryProductNameField;
    private JTable inventoryTable;
    private JComboBox inventoryProductCategoryComboBox;
    private JPanel inventoryTablePanel;

    private JPanel purchaseRecordsPanel;

    private JLabel userNameLabel;
    private JTextField userNameField;
    private JButton userSearchButton;
    private JPanel userTablePanel;

    private JPanel purchaseRecordsTabelPanel;

    private JLabel categoryProductLabel;
    private JComboBox categoryProductComboBox;
    private JLabel tradeNameLabel;
    private JTextField textField;
    private JButton searchButton;
    private JButton productRemovalButton;
    private JPanel productTablePanel;
    private JTable table;


    /**
     * 声明用户持久层对象
     */
    private UsersDaoImpl usersDaoImpl = new UsersDaoImpl();
    private CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
    private StockGoodsDaoImpl stockGoodsDaoImpl = new StockGoodsDaoImpl();
    private ProductInventoryDaoImpl productInventoryDaoImpl = new ProductInventoryDaoImpl();

    /**
     * 构造方法
     */
    public IndexAdminFrame(String account) {
        this.userAccount = account;

        // 根据用户名查询用户信息
        Users users = usersDaoImpl.getForObject(
                CONN,
                "select * from tb_users where userName = ?",
                this.userAccount
        );

        // 设置窗体的标题
        setTitle("电子商城");
        // 设置窗体的关闭按钮是否可用
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗体的大小
        setBounds(100, 100, 870, 576);
        // 设置窗体可见（重要）
        setVisible(true);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 根据查询出来的用户信息，赋值用户账号信息
        userLabel = new JLabel(users.getUserName());
        userLabel.setFont(new Font("华文楷体", Font.BOLD, 18));
        userLabel.setBounds(752, 0, 100, 37);
        contentPane.add(userLabel);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setFont(new Font("宋体", Font.BOLD, 18));
        tabbedPane.setBounds(0, 35, 852, 481);
        contentPane.add(tabbedPane);

        // 商品分类面板
        categoryPanel = new JPanel();
        tabbedPane.addTab("商品分类", null, categoryPanel, null);
        categoryPanel.setLayout(null);

        categoryLabel = new JLabel("分类名称：");
        categoryLabel.setFont(new Font("宋体", Font.BOLD, 18));
        categoryLabel.setBounds(103, 31, 96, 33);
        categoryPanel.add(categoryLabel);

        categoryField = new JTextField();
        categoryField.setFont(new Font("宋体", Font.PLAIN, 18));
        categoryField.setBounds(213, 30, 212, 38);
        categoryPanel.add(categoryField);
        categoryField.setColumns(10);

        categorySearchButton = new JButton("搜索");
        categorySearchButton.setFont(new Font("宋体", Font.BOLD, 20));
        categorySearchButton.setBounds(453, 31, 96, 37);
        categorySearchButton.addActionListener(e -> new MyJTable().initCategoryTable(
                categoryTablePanel,
                categoryField.getText())
        );
        categoryPanel.add(categorySearchButton);

        categoryAddButton = new JButton("添加");
        categoryAddButton.setFont(new Font("宋体", Font.BOLD, 20));
        categoryAddButton.setBounds(453, 81, 96, 37);
        // 点击按钮 ，打开添加框
        categoryAddButton.addActionListener(e -> new CategoryAddFrame());
        categoryPanel.add(categoryAddButton);

        categoryTablePanel = new JPanel();
        categoryTablePanel.setBounds(103, 144, 446, 282);
        categoryPanel.add(categoryTablePanel);
        categoryTablePanel.setLayout(new BorderLayout());

        // 初始化表格
        new MyJTable().initCategoryTable(categoryTablePanel, "");

        categoryRefreshButton = new JButton("刷新");
        categoryRefreshButton.setFont(new Font("宋体", Font.BOLD, 20));
        categoryRefreshButton.setBounds(103, 425, 446, 38);
        // 点击刷新按钮 重新获取数据
        categoryRefreshButton.addActionListener(e -> new MyJTable().initCategoryTable(
                categoryTablePanel,
                categoryField.getText())
        );
        categoryPanel.add(categoryRefreshButton);


        productPanel = new JPanel();
        tabbedPane.addTab("商品管理", null, productPanel, null);
        productPanel.setLayout(null);

        categoryProductLabel = new JLabel("商品分类：");
        categoryProductLabel.setFont(new Font("宋体", Font.BOLD, 18));
        categoryProductLabel.setBounds(33, 28, 101, 29);
        productPanel.add(categoryProductLabel);

        categoryProductComboBox = new JComboBox();
        categoryProductComboBox.setBounds(148, 32, 126, 24);
        categoryProductComboBox.setModel(
                new DefaultComboBoxModel(
                        categoryDaoImpl.getForList(
                                        CONN,
                                        "select * from tb_category"
                                )
                                .stream()
                                .map(Category::getCategoryName)
                                .collect(Collectors.toList())
                                .toArray()
                )
        );
        productPanel.add(categoryProductComboBox);

        tradeNameLabel = new JLabel("商品名称：");
        tradeNameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        tradeNameLabel.setBounds(288, 35, 95, 18);
        productPanel.add(tradeNameLabel);

        textField = new JTextField();
        textField.setBounds(379, 32, 159, 24);
        productPanel.add(textField);
        textField.setColumns(10);

        searchButton = new JButton("查询");
        searchButton.setFont(new Font("宋体", Font.PLAIN, 18));
        searchButton.setBounds(592, 31, 113, 27);
        // 点击按钮 ，查询商品信息
        searchButton.addActionListener(e ->
                table = new MyJTable().initProductTablePanel(
                        productTablePanel,
                        (String) categoryProductComboBox.getSelectedItem(),
                        textField.getText(),
                        table
                )
        );
        productPanel.add(searchButton);

        productRemovalButton = new JButton("商品下架");
        productRemovalButton.setFont(new Font("宋体", Font.PLAIN, 18));
        productRemovalButton.setBounds(592, 71, 113, 27);
        productRemovalButton.addActionListener(e -> productRemoval(e));
        productPanel.add(productRemovalButton);

        productTablePanel = new JPanel();
        productTablePanel.setBounds(33, 110, 672, 353);
        productPanel.add(productTablePanel);
        productTablePanel.setLayout(new BorderLayout());

        // 初始化商品管理面板
        table = new MyJTable().initProductTablePanel(productTablePanel, "", "", table);

        // 进货管理
        stockGoodsPanel = new JPanel();
        tabbedPane.addTab("进货管理", null, stockGoodsPanel, null);
        stockGoodsPanel.setLayout(null);

        productCategoryLabel = new JLabel("商品分类：");
        productCategoryLabel.setFont(new Font("宋体", Font.BOLD, 18));
        productCategoryLabel.setBounds(38, 13, 105, 34);
        stockGoodsPanel.add(productCategoryLabel);

        productCategoryComboBox = new JComboBox();
        productCategoryComboBox.setFont(new Font("宋体", Font.PLAIN, 18));
        productCategoryComboBox.setBounds(155, 13, 186, 34);
        productCategoryComboBox.setModel(
                new DefaultComboBoxModel(
                        categoryDaoImpl.getForList(
                                        CONN,
                                        "select * from tb_category"
                                )
                                .stream()
                                .map(Category::getCategoryName)
                                .collect(Collectors.toList())
                                .toArray()
                )
        );
        stockGoodsPanel.add(productCategoryComboBox);

        productNameLabel = new JLabel("商品名：");
        productNameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        productNameLabel.setBounds(381, 13, 83, 34);
        stockGoodsPanel.add(productNameLabel);

        productNameField = new JTextField();
        productNameField.setFont(new Font("宋体", Font.PLAIN, 18));
        productNameField.setBounds(478, 13, 185, 34);
        productNameField.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String productNum = productNumField.getText();
                String productPrice = productPriceField.getText();

                if (!"".equals(productNum) && !"".equals(productPrice)) {
                    totalPriceGoodsField.setText((Integer.valueOf(productNum) * Double.valueOf(productPrice)) + "");
                } else {
                    totalPriceGoodsField.setText("");
                }
            }
        });
        stockGoodsPanel.add(productNameField);
        productNameField.setColumns(10);

        buyerLabel = new JLabel("进货人：");
        buyerLabel.setFont(new Font("宋体", Font.BOLD, 18));
        buyerLabel.setBounds(38, 81, 105, 34);
        stockGoodsPanel.add(buyerLabel);

        buyerField = new JTextField();
        buyerField.setBounds(156, 81, 186, 34);
        buyerField.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String productNum = productNumField.getText();
                String productPrice = productPriceField.getText();

                if (!"".equals(productNum) && !"".equals(productPrice)) {
                    totalPriceGoodsField.setText((Integer.valueOf(productNum) * Double.valueOf(productPrice)) + "");
                } else {
                    totalPriceGoodsField.setText("");
                }
            }
        });
        stockGoodsPanel.add(buyerField);
        buyerField.setColumns(10);

        contactInfoLabel = new JLabel("联系方式：");
        contactInfoLabel.setFont(new Font("宋体", Font.BOLD, 18));
        contactInfoLabel.setBounds(371, 81, 105, 34);
        stockGoodsPanel.add(contactInfoLabel);

        contactInfoField = new JTextField();
        contactInfoField.setFont(new Font("宋体", Font.PLAIN, 18));
        contactInfoField.setBounds(478, 81, 185, 34);
        contactInfoField.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String productNum = productNumField.getText();
                String productPrice = productPriceField.getText();

                if (!"".equals(productNum) && !"".equals(productPrice)) {
                    totalPriceGoodsField.setText((Integer.valueOf(productNum) * Double.valueOf(productPrice)) + "");
                } else {
                    totalPriceGoodsField.setText("");
                }
            }
        });
        stockGoodsPanel.add(contactInfoField);
        contactInfoField.setColumns(10);

        productNumLabel = new JLabel("商品数量：");
        productNumLabel.setFont(new Font("宋体", Font.BOLD, 18));
        productNumLabel.setBounds(38, 156, 105, 34);
        stockGoodsPanel.add(productNumLabel);

        productNumField = new JTextField();
        productNumField.setFont(new Font("宋体", Font.PLAIN, 18));
        productNumField.setBounds(155, 156, 186, 34);
        stockGoodsPanel.add(productNumField);
        productNumField.setColumns(10);

        productPriceLabel = new JLabel("商品单价：");
        productPriceLabel.setFont(new Font("宋体", Font.BOLD, 18));
        productPriceLabel.setBounds(371, 156, 105, 34);
        stockGoodsPanel.add(productPriceLabel);

        productPriceField = new JTextField();
        productPriceField.setFont(new Font("宋体", Font.PLAIN, 18));
        productPriceField.setColumns(10);
        productPriceField.setBounds(478, 157, 185, 34);
        stockGoodsPanel.add(productPriceField);

        purchasingManufacturerLabel = new JLabel("进货厂商：");
        purchasingManufacturerLabel.setFont(new Font("宋体", Font.BOLD, 18));
        purchasingManufacturerLabel.setBounds(38, 226, 105, 34);
        stockGoodsPanel.add(purchasingManufacturerLabel);

        purchasingManufacturerField = new JTextField();
        purchasingManufacturerField.setFont(new Font("宋体", Font.PLAIN, 18));
        purchasingManufacturerField.setColumns(10);
        purchasingManufacturerField.setBounds(155, 226, 186, 34);
        purchasingManufacturerField.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String productNum = productNumField.getText();
                String productPrice = productPriceField.getText();

                if (!"".equals(productNum) && !"".equals(productPrice)) {
                    totalPriceGoodsField.setText((Integer.valueOf(productNum) * Double.valueOf(productPrice)) + "");
                } else {
                    totalPriceGoodsField.setText("");
                }
            }
        });
        stockGoodsPanel.add(purchasingManufacturerField);

        JLabel manufacturerPhoneNumberLabel = new JLabel("厂商电话：");
        manufacturerPhoneNumberLabel.setFont(new Font("宋体", Font.BOLD, 18));
        manufacturerPhoneNumberLabel.setBounds(371, 226, 105, 34);
        stockGoodsPanel.add(manufacturerPhoneNumberLabel);

        manufacturerPhoneNumberField = new JTextField();
        manufacturerPhoneNumberField.setFont(new Font("宋体", Font.PLAIN, 18));
        manufacturerPhoneNumberField.setColumns(10);
        manufacturerPhoneNumberField.setBounds(478, 226, 185, 34);
        manufacturerPhoneNumberField.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String productNum = productNumField.getText();
                String productPrice = productPriceField.getText();

                if (!"".equals(productNum) && !"".equals(productPrice)) {
                    totalPriceGoodsField.setText((Integer.valueOf(productNum) * Double.valueOf(productPrice)) + "");
                } else {
                    totalPriceGoodsField.setText("");
                }
            }
        });
        stockGoodsPanel.add(manufacturerPhoneNumberField);

        JLabel manufacturerAddressLabel = new JLabel("厂商地址：");
        manufacturerAddressLabel.setFont(new Font("宋体", Font.BOLD, 18));
        manufacturerAddressLabel.setBounds(38, 273, 105, 34);
        stockGoodsPanel.add(manufacturerAddressLabel);

        manufacturerAddressField = new JTextField();
        manufacturerAddressField.setFont(new Font("宋体", Font.PLAIN, 18));
        manufacturerAddressField.setColumns(10);
        manufacturerAddressField.setBounds(155, 273, 505, 34);
        manufacturerAddressField.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String productNum = productNumField.getText();
                String productPrice = productPriceField.getText();

                if (!"".equals(productNum) && !"".equals(productPrice)) {
                    totalPriceGoodsField.setText((Integer.valueOf(productNum) * Double.valueOf(productPrice)) + "");
                } else {
                    totalPriceGoodsField.setText("");
                }
            }
        });
        stockGoodsPanel.add(manufacturerAddressField);

        JLabel purchaseInstructionsLabel = new JLabel("进货说明：");
        purchaseInstructionsLabel.setFont(new Font("宋体", Font.BOLD, 18));
        purchaseInstructionsLabel.setBounds(38, 320, 105, 34);
        stockGoodsPanel.add(purchaseInstructionsLabel);

        purchaseInstructionsTextArea = new JTextArea();
        purchaseInstructionsTextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        purchaseInstructionsTextArea.setBounds(155, 320, 508, 82);
        purchaseInstructionsTextArea.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String productNum = productNumField.getText();
                String productPrice = productPriceField.getText();

                if (!"".equals(productNum) && !"".equals(productPrice)) {
                    totalPriceGoodsField.setText((Integer.valueOf(productNum) * Double.valueOf(productPrice)) + "");
                } else {
                    totalPriceGoodsField.setText("");
                }
            }
        });
        stockGoodsPanel.add(purchaseInstructionsTextArea);

        totalPriceGoodsLabel = new JLabel("商品总价：");
        totalPriceGoodsLabel.setFont(new Font("宋体", Font.BOLD, 18));
        totalPriceGoodsLabel.setBounds(151, 429, 105, 34);
        stockGoodsPanel.add(totalPriceGoodsLabel);

        totalPriceGoodsField = new JTextField();
        totalPriceGoodsField.setBounds(256, 429, 143, 34);
        stockGoodsPanel.add(totalPriceGoodsField);
        totalPriceGoodsField.setColumns(10);

        firstLabel = new JLabel("元");
        firstLabel.setFont(new Font("宋体", Font.BOLD, 18));
        firstLabel.setBounds(413, 429, 72, 34);
        stockGoodsPanel.add(firstLabel);

        confirmReceiptButton = new JButton("确认进货");
        confirmReceiptButton.setFont(new Font("宋体", Font.BOLD, 20));
        confirmReceiptButton.setBounds(508, 429, 155, 34);
        // 处理进货信息
        confirmReceiptButton.addActionListener(e -> confirmReceipt(e));
        stockGoodsPanel.add(confirmReceiptButton);

        purchaseRecordsPanel = new JPanel();
        tabbedPane.addTab("进货记录", null, purchaseRecordsPanel, null);
        purchaseRecordsPanel.setLayout(null);

        purchaseRecordsTabelPanel = new JPanel();
        purchaseRecordsTabelPanel.setBounds(14, 13, 712, 450);
        purchaseRecordsPanel.add(purchaseRecordsTabelPanel);
        purchaseRecordsTabelPanel.setLayout(new BorderLayout(0, 0));
        // 初始化进货表
        new MyJTable().initPurchaseRecordsTabel(purchaseRecordsTabelPanel);

        inventoryPanel = new JPanel();
        tabbedPane.addTab("库存管理", null, inventoryPanel, null);
        inventoryPanel.setLayout(null);

        inventoryProductCategoryLabel = new JLabel("商品种类：");
        inventoryProductCategoryLabel.setFont(new Font("宋体", Font.BOLD, 18));
        inventoryProductCategoryLabel.setBounds(14, 13, 105, 31);
        inventoryPanel.add(inventoryProductCategoryLabel);

        inventoryProductCategoryComboBox = new JComboBox();
        inventoryProductCategoryComboBox.setFont(new Font("宋体", Font.PLAIN, 18));
        inventoryProductCategoryComboBox.setBounds(122, 13, 176, 31);
        inventoryProductCategoryComboBox.setModel(
                new DefaultComboBoxModel(
                        categoryDaoImpl.getForList(
                                        CONN,
                                        "select * from tb_category"
                                )
                                .stream()
                                .map(Category::getCategoryName)
                                .collect(Collectors.toList())
                                .toArray()
                )
        );
        inventoryPanel.add(inventoryProductCategoryComboBox);

        JLabel inventoryProductNameLabel = new JLabel("商品名：");
        inventoryProductNameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        inventoryProductNameLabel.setBounds(329, 13, 84, 31);
        inventoryPanel.add(inventoryProductNameLabel);

        inventoryProductNameField = new JTextField();
        inventoryProductNameField.setFont(new Font("宋体", Font.PLAIN, 18));
        inventoryProductNameField.setBounds(417, 13, 176, 31);
        inventoryPanel.add(inventoryProductNameField);
        inventoryProductNameField.setColumns(10);

        JButton inventorySearchButton = new JButton("搜   索");
        inventorySearchButton.setFont(new Font("宋体", Font.BOLD, 18));
        inventorySearchButton.setBounds(628, 13, 113, 31);
        inventorySearchButton.addActionListener(e -> new MyJTable().inventoryTable(
                        inventoryTablePanel,
                        (String) inventoryProductCategoryComboBox.getSelectedItem(),
                        inventoryProductNameField.getText()
                )
        );
        inventoryPanel.add(inventorySearchButton);

        JButton productListingButton = new JButton("商品上架");
        productListingButton.setFont(new Font("宋体", Font.BOLD, 18));
        productListingButton.setBounds(628, 54, 113, 31);
        // 绑定点击事件
        productListingButton.addActionListener(e -> new ProductListingFrame());
        inventoryPanel.add(productListingButton);

        inventoryTablePanel = new JPanel();
        inventoryTablePanel.setBounds(14, 98, 727, 378);
        inventoryPanel.add(inventoryTablePanel);
        inventoryTablePanel.setLayout(new BorderLayout(0, 0));

        // 初始化表格
        new MyJTable().inventoryTable(inventoryTablePanel, "", "");

        userPanel = new JPanel();
        tabbedPane.addTab("用户管理", null, userPanel, null);
        userPanel.setLayout(null);

        userNameLabel = new JLabel("用户名：");
        userNameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        userNameLabel.setBounds(29, 35, 87, 27);
        userPanel.add(userNameLabel);

        userNameField = new JTextField();
        userNameField.setFont(new Font("宋体", Font.PLAIN, 18));
        userNameField.setBounds(132, 35, 173, 27);
        userPanel.add(userNameField);
        userNameField.setColumns(10);

        userSearchButton = new JButton("搜索");
        userSearchButton.setFont(new Font("宋体", Font.BOLD, 18));
        userSearchButton.setBounds(344, 30, 121, 37);
        userSearchButton.addActionListener(e -> new MyJTable().initUserTablePanel(userTablePanel, userNameField.getText()));
        userPanel.add(userSearchButton);

        userTablePanel = new JPanel();
        userTablePanel.setBounds(29, 107, 712, 356);
        userPanel.add(userTablePanel);
        userTablePanel.setLayout(new BorderLayout(0, 0));

        // 初始化用户列表
        new MyJTable().initUserTablePanel(userTablePanel, userNameField.getText());

        accountPanel = new JPanel();
        tabbedPane.addTab("账号管理", null, accountPanel, null);
        accountPanel.setLayout(null);

        // 账号管理
        accountLabel = new JLabel("账  号：");
        accountLabel.setFont(new Font("宋体", Font.BOLD, 18));
        accountLabel.setBounds(179, 40, 92, 36);
        accountPanel.add(accountLabel);

        accountField = new JTextField();
        accountField.setFont(new Font("宋体", Font.PLAIN, 18));
        accountField.setBounds(296, 40, 208, 41);
        accountField.setText(users.getUserName());
        accountField.setEditable(false);
        accountPanel.add(accountField);
        accountField.setColumns(10);

        phoneLabel = new JLabel("电话号码：");
        phoneLabel.setFont(new Font("宋体", Font.BOLD, 18));
        phoneLabel.setBounds(163, 92, 108, 36);
        accountPanel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setFont(new Font("宋体", Font.PLAIN, 18));
        phoneField.setColumns(10);
        phoneField.setBounds(296, 92, 208, 41);
        phoneField.setText(users.getPhone());
        accountPanel.add(phoneField);

        nickNameLabel = new JLabel("昵  称：");
        nickNameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        nickNameLabel.setBounds(179, 144, 92, 36);
        accountPanel.add(nickNameLabel);

        nickNameField = new JTextField();
        nickNameField.setFont(new Font("宋体", Font.PLAIN, 18));
        nickNameField.setColumns(10);
        nickNameField.setBounds(296, 144, 208, 41);
        nickNameField.setText(users.getNickName());
        accountPanel.add(nickNameField);

        nameLabel = new JLabel("姓  名：");
        nameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        nameLabel.setBounds(179, 204, 92, 36);
        accountPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("宋体", Font.PLAIN, 18));
        nameField.setColumns(10);
        nameField.setBounds(296, 198, 208, 41);
        nameField.setText(users.getName());
        accountPanel.add(nameField);

        userTypeLabel = new JLabel("用户类型：");
        userTypeLabel.setFont(new Font("宋体", Font.BOLD, 18));
        userTypeLabel.setBounds(163, 266, 108, 36);
        accountPanel.add(userTypeLabel);

        userTypeField = new JTextField();
        userTypeField.setFont(new Font("宋体", Font.PLAIN, 18));
        userTypeField.setColumns(10);
        userTypeField.setBounds(296, 261, 208, 41);
        // 根据枚举值的名字判断用户类型，展示的是枚举值的具体信息 Msg。  name() 获取的是枚举值的名字 比如ADMIN
        userTypeField.setText(
                users.getUserType().equals(UserTypeEnum.ADMIN.name())
                        ? UserTypeEnum.ADMIN.getMsg() : UserTypeEnum.ORDINARY.getMsg()
        );
        userTypeField.setEditable(false);
        accountPanel.add(userTypeField);

        saveOrUpdateButton = new JButton("保存修改");
        // 处理用户修改信息事件
        saveOrUpdateButton.addActionListener(e -> saveOrUpdateUsersInfo(e, userAccount));
        saveOrUpdateButton.setFont(new Font("宋体", Font.BOLD, 20));
        saveOrUpdateButton.setBounds(270, 374, 208, 47);
        accountPanel.add(saveOrUpdateButton);

        // 重置密码
        restPanel = new JPanel();
        tabbedPane.addTab("重置密码", null, restPanel, null);
        restPanel.setLayout(null);

        originalPasswordLabel = new JLabel("原密码：");
        originalPasswordLabel.setBounds(157, 99, 94, 37);
        originalPasswordLabel.setFont(new Font("宋体", Font.BOLD, 20));
        restPanel.add(originalPasswordLabel);

        originalPasswordField = new JPasswordField();
        originalPasswordField.setFont(new Font("宋体", Font.PLAIN, 18));
        originalPasswordField.setBounds(265, 97, 266, 44);
        restPanel.add(originalPasswordField);

        newPasswordLabel = new JLabel("新密码：");
        newPasswordLabel.setFont(new Font("宋体", Font.BOLD, 20));
        newPasswordLabel.setBounds(157, 199, 94, 37);
        restPanel.add(newPasswordLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setFont(new Font("宋体", Font.PLAIN, 18));
        newPasswordField.setBounds(265, 197, 266, 44);
        restPanel.add(newPasswordField);

        confirmPasswordLabel = new JLabel("确认密码：");
        confirmPasswordLabel.setFont(new Font("宋体", Font.BOLD, 20));
        confirmPasswordLabel.setBounds(135, 288, 116, 37);
        restPanel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("宋体", Font.PLAIN, 18));
        confirmPasswordField.setBounds(265, 281, 266, 44);
        restPanel.add(confirmPasswordField);

        confirmButton = new JButton("确认修改");
        confirmButton.setFont(new Font("宋体", Font.BOLD, 22));
        confirmButton.setBounds(309, 385, 154, 54);
        // 处理确认修改的方法
        confirmButton.addActionListener(e -> updatePassword(e, userAccount));

        restPanel.add(confirmButton);
    }

    /**
     * 商品下架
     *
     * @param e
     */
    private void productRemoval(ActionEvent e) {
        if (table.getSelectedRow() >= 0) {
            int selectedRow = table.getSelectedRow();
            String category = (String) table.getValueAt(selectedRow, 1);
            String productName = (String) table.getValueAt(selectedRow, 2);

            // 下架商品
            productInventoryDaoImpl.update(
                    CONN,
                    "update tb_product_inventory set isShelf = 0 where productCategory = ? and productName = ?",
                    category,
                    productName
            );

            // 提示 商品下架成功
            JOptionPane.showMessageDialog(this, "商品下架成功！", "提示", JOptionPane.WARNING_MESSAGE);
        } else {
            // 提示没有选择商品，不能下架商品
            JOptionPane.showMessageDialog(this, "请选择商品进行操作！", "未选择商品", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * 确认进货
     *
     * @param e
     */
    private void confirmReceipt(ActionEvent e) {
        // 获取各个字段的值
        String productCategory = (String) productCategoryComboBox.getSelectedItem();
        String productName = productNameField.getText();
        String buyer = buyerField.getText();
        String contactInfo = contactInfoField.getText();
        String productNum = productNumField.getText();
        String productPrice = productPriceField.getText();
        String purchasingManufacturer = purchasingManufacturerField.getText();
        String manufacturerPhoneNumber = manufacturerPhoneNumberField.getText();
        String manufacturerAddress = manufacturerAddressField.getText();
        String purchaseInstructions = purchaseInstructionsTextArea.getText();

        // 判断
        if ("".equals(productCategory) || "".equals(productName) || "".equals(buyer)
                || "".equals(contactInfo) || "".equals(productNum) || "".equals(productPrice)
                || "".equals(productPrice) || "".equals(purchasingManufacturer)
                || "".equals(manufacturerPhoneNumber) || "".equals(manufacturerAddress)
                || "".equals(purchaseInstructions)) {
            // 给出提示
            JOptionPane.showMessageDialog(this, "字段不能为空！！！");
        } else {
            // 添加进货记录
            stockGoodsDaoImpl.update(
                    CONN,
                    "insert into tb_stock_goods(categoryName,productName,buyer,buyerPhone,productNum," +
                            "productPrice,totalPurchasePrice,purchasingManufacturer,manufacturerPhone," +
                            "manufacturerAddress,purchaseInstructions) values(?,?,?,?,?,?,?,?,?,?,?) ",
                    productCategory,
                    productName,
                    buyer,
                    contactInfo,
                    Integer.valueOf(productNum),
                    Double.valueOf(productPrice),
                    Integer.valueOf(productNum) * Double.valueOf(productPrice),
                    purchasingManufacturer,
                    manufacturerPhoneNumber,
                    manufacturerAddress,
                    purchaseInstructions
            );

            // 先查询这个商品是否存在
            ProductInventory productInventory = productInventoryDaoImpl.getForObject(
                    CONN,
                    "select * from tb_product_inventory where productCategory = ? and productName = ?",
                    productCategory,
                    productName
            );
            if (Objects.isNull(productInventory)) {
                // 添加新的
                productInventoryDaoImpl.update(
                        CONN,
                        "insert into tb_product_inventory(productCategory,productName,manufacturer,productNum," +
                                "productBuyer) values(?,?,?,?,?)",
                        productCategory,
                        productName,
                        purchasingManufacturer,
                        Integer.valueOf(productNum),
                        Double.valueOf(productPrice)
                );
            } else {
                // 去做更新
                productInventoryDaoImpl.update(
                        CONN,
                        "update tb_product_inventory set manufacturer = ?, productNum = ?, productBuyer = ? where id = ?",
                        purchasingManufacturer,
                        productInventory.getProductNum() + Integer.valueOf(productNum),
                        productInventory.getProductBuyer() > Double.valueOf(productPrice)
                                ? productInventory.getProductBuyer() : Double.valueOf(productPrice),
                        productInventory.getId()
                );
            }

            JOptionPane.showMessageDialog(this, "进货成功！！！");

            productCategoryComboBox.setSelectedIndex(0);
            productNameField.setText("");
            buyerField.setText("");
            contactInfoField.setText("");
            productNumField.setText("");
            productPriceField.setText("");
            purchasingManufacturerField.setText("");
            manufacturerPhoneNumberField.setText("");
            manufacturerAddressField.setText("");
            purchaseInstructionsTextArea.setText("");
            totalPriceGoodsField.setText("");
        }
    }

    /**
     * 用户修改密码
     *
     * @param e
     * @param userAccount
     */
    private void updatePassword(ActionEvent e, String userAccount) {
        // 获取原始密码
        String oldPassword = new String(originalPasswordField.getPassword());
        // 获取新密码
        String newPassword = new String(newPasswordField.getPassword());
        // 获取确认密码
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // 判断是否有为空的字段
        if ("".equals(oldPassword) || "".equals(newPassword) || "".equals(confirmPassword)) {
            // 给出消息提示
            JOptionPane.showMessageDialog(this, "输入的内容不能为空！！！");
        } else {
            // 判断新密码和旧密码是否相同
            if (oldPassword.equals(newPassword)) {
                JOptionPane.showMessageDialog(this, "旧密码不能与新密码一样！！！");
            } else {
                // 判断新密码和确认密码是否相同
                if (newPassword.equals(confirmPassword)) {
                    // 根据用户账号查询用户信息
                    Users users = usersDaoImpl.getForObject(
                            CONN,
                            "select * from tb_users where userName = ?",
                            userAccount
                    );

                    // 判断输入的密码是否相同
                    if (users.getPassWord().equals(MD5.encrypt(oldPassword))) {
                        // 修改密码
                        int resultData = usersDaoImpl.update(
                                CONN,
                                "update tb_users set passWord = ? where userName = '" + userAccount + "'",
                                MD5.encrypt(newPassword)
                        );

                        if (resultData > 0) {
                            // 给出提示
                            JOptionPane.showMessageDialog(this, "密码修改成功，请重新登录！！！");

                            this.dispose();
                            new LoginFrame();
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "原密码输入不正确！！！");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "确认密码和新密码不相同！！！");
                }
            }
        }

    }

    /**
     * 保存/修改用户信息
     *
     * @param e
     * @param userName
     */
    private void saveOrUpdateUsersInfo(ActionEvent e, String userName) {
        // 获取电话号码的值
        String phone = phoneField.getText();
        // 获取昵称的值
        String nickName = nickNameField.getText();
        // 获取姓名的值
        String name = nameField.getText();

        // 写修改代码语句
        usersDaoImpl.update(
                CONN,
                "update tb_users set phone = ?, nickName = ?, `name` = ? where userName = '" + userName + "'",
                phone,
                nickName,
                name
        );

        JOptionPane.showMessageDialog(this, "信息修改成功！！");
    }
}
