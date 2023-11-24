package com.wxy.mall.frame;

import com.wxy.mall.dao.impl.CartDaoImpl;
import com.wxy.mall.dao.impl.CategoryDaoImpl;
import com.wxy.mall.dao.impl.CollectDaoImpl;
import com.wxy.mall.dao.impl.OrderDaoImpl;
import com.wxy.mall.dao.impl.ProductInventoryDaoImpl;
import com.wxy.mall.dao.impl.ProductVoDaoImpl;
import com.wxy.mall.dao.impl.UsersDaoImpl;
import com.wxy.mall.domain.Cart;
import com.wxy.mall.domain.Category;
import com.wxy.mall.domain.Collect;
import com.wxy.mall.domain.Order;
import com.wxy.mall.domain.ProductInventory;
import com.wxy.mall.domain.ProductVo;
import com.wxy.mall.domain.Users;
import com.wxy.mall.enums.UserTypeEnum;
import com.wxy.mall.frame.front.FrontLoginFrame;
import com.wxy.mall.jdbc.connection.MyConnection;
import com.wxy.mall.util.MD5;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 前台管理系统
 *
 * @author 魏心怡
 */
public class FrontIndexFrame extends JFrame {

    private static final Connection CONN = MyConnection.getConnection(6);

    private JPanel contentPane;

    private JLabel loginAndRegisterLabel;
    private JTabbedPane tabbedPane;
    private JPanel productPanel;
    private JPanel orderPanel;
    private JPanel cartPanel;
    private JPanel infoPanel;
    private JPanel retrievePasswordPanel;
    private JLabel productCategoryLabel;
    private JButton productSearchButton;
    private JButton enterShoppingButton;
    private JComboBox productComboBox;
    private JPanel productTablePanel;

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
    private JButton saveOrUpdateButton;

    private JLabel retrieveUserNameLabel;
    private JTextField retrieveUserNameField;
    private JLabel newPasswordLabel;
    private JPasswordField newPasswordField;
    private JLabel confirmPasswordLabel;
    private JPasswordField confirmPasswordField;
    private JButton retrievePasswordButton;
    private JButton restButton;

    private JTextField productCategoryField;
    private JTextField producNameField;
    private JTextField factoryField;
    private JTextField priceSaleField;
    private JTextField discountField;
    private JLabel lblNewLabel_5;
    private JLabel buyNumLabel;
    private JTextField buyNumField;
    private JLabel lblNewLabel_6;
    private JLabel lblNewLabel_7;
    private JLabel totalPriceLabel;
    private JTextField addressField;
    private JLabel addressLabel;
    private JTextField totalField;
    private JLabel lblNewLabel_10;
    private JLabel productCateLabel;
    private JLabel productNameLabel;
    private JLabel factoryLabel;
    private JLabel priceSaleLabel;
    private JLabel discountLabel;
    private JButton collectButton;
    private JButton cartButton;
    private JButton oneClickPurchaseButton;
    private JLabel orderProductCategoryLabel;
    private JComboBox orderProductCategoryComboBox;
    private JLabel orderProductNameLabel;
    private JTextField orderProductNameField;
    private JButton orderSearchButton;
    private JPanel orderInfoPanel;
    private JPanel collectPanel;

    private JTextField cartProductNameField;
    private JTextField cartCategoryField;
    private JTextField cartProductField;
    private JTextField cartFactoryField;
    private JTextField cartPriceField;
    private JTextField cartNumField;
    private JTextField cartDiscountField;
    private JTextField cartTotalPriceField;
    private JTextField cartPhoneField;
    private JComboBox cartProductCategoryComboBox;
    private JButton cartSearchButton;
    private JPanel cartTablePanel;
    private JTextArea cartAddressTextArea;
    private JButton emptyCartButton;
    private JButton deleteCartButton;
    private JButton cartPurchaseButton;

    private JTextField collectProductNameField;
    private JComboBox collectProductCategoryComboBox;
    private JButton collectCancleButton;
    private JButton collectSearchButton;
    private JPanel collectTablePanel;
    private JTable collectTable;
    private JTextField phoneCartField;

    private Users users;

    private UsersDaoImpl usersDaoImpl = new UsersDaoImpl();
    private CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
    private ProductVoDaoImpl productVoDaoImpl = new ProductVoDaoImpl();
    private OrderDaoImpl orderDaoImpl = new OrderDaoImpl();
    private ProductInventoryDaoImpl productInventoryDaoImpl = new ProductInventoryDaoImpl();
    private CartDaoImpl cartDaoImpl = new CartDaoImpl();
    private CollectDaoImpl collectDaoImpl = new CollectDaoImpl();

    public FrontIndexFrame(Users users) {
        this.users = users;
/**
 * 登陆页面
 */
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 753, 541);
        setVisible(true);
        setTitle("电子商城前台");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        loginAndRegisterLabel = new JLabel(
                Objects.isNull(this.users)
                        ? "登录/注册" : Objects.isNull(this.users.getNickName())
                        ? Objects.isNull(this.users.getName())
                        ? this.users.getUserName() : this.users.getName()
                        : this.users.getNickName()
        );

        //如果this.users为null，则返回"登录/注册"；
        //否则，如果users的NickName为null，则检查它的Name属性是否为null；
        //如果NickName和Name属性都为null，则返回users的UserName；
        //否则，返回NickName属性的值。

        loginAndRegisterLabel.setFont(new Font("宋体", Font.BOLD, 18));
        loginAndRegisterLabel.setBounds(612, 0, 125, 35);
        loginAndRegisterLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                gotoFrontLogin();
            }
        });
        getContentPane().add(loginAndRegisterLabel);

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setBounds(0, 38, 737, 456);
        contentPane.add(tabbedPane);

        productPanel = new JPanel();
        tabbedPane.addTab("商品列表", null, productPanel, null);
        productPanel.setLayout(null);

        productCategoryLabel = new JLabel("商品种类：");
        productCategoryLabel.setFont(new Font("宋体", Font.BOLD, 18));
        productCategoryLabel.setBounds(14, 13, 108, 36);
        productPanel.add(productCategoryLabel);

        productComboBox = new JComboBox();
        productComboBox.setFont(new Font("宋体", Font.PLAIN, 18));
        productComboBox.setBounds(136, 13, 197, 36);
        productComboBox.setModel(
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
                //1.查询分类列表，得到分类集合，2.将分类集合转换成stream流，
                //3将流映射成map，获取分类名称，4.收集分类名称组成list集合
                //5.将集合转换成数组
        );
        productPanel.add(productComboBox);

        productSearchButton = new JButton("查询");
        productSearchButton.setFont(new Font("宋体", Font.BOLD, 18));
        productSearchButton.setBounds(391, 13, 82, 36);
        productSearchButton.addActionListener(e -> productSearch(e));
        productPanel.add(productSearchButton);

        productTablePanel = new JPanel();
        productTablePanel.setBounds(14, 68, 613, 100);
        productPanel.add(productTablePanel);
        productTablePanel.setLayout(new BorderLayout(0, 0));

        this.initProductFront(productTablePanel, "");


        productCateLabel = new JLabel("商品种类：");
        productCateLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        productCateLabel.setBounds(14, 181, 96, 29);
        productPanel.add(productCateLabel);

        productCategoryField = new JTextField();
        productCategoryField.setEnabled(false);
        productCategoryField.setBounds(121, 181, 174, 30);
        productPanel.add(productCategoryField);
        productCategoryField.setColumns(10);

        productNameLabel = new JLabel("商品名称：");
        productNameLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        productNameLabel.setBounds(348, 181, 96, 29);
        productPanel.add(productNameLabel);

        producNameField = new JTextField();
        producNameField.setEnabled(false);
        producNameField.setColumns(10);
        producNameField.setBounds(458, 181, 169, 30);
        productPanel.add(producNameField);

        factoryLabel = new JLabel("厂家：");
        factoryLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        factoryLabel.setBounds(14, 223, 96, 29);
        productPanel.add(factoryLabel);

        factoryField = new JTextField();
        factoryField.setEnabled(false);
        factoryField.setColumns(10);
        factoryField.setBounds(121, 224, 174, 30);
        productPanel.add(factoryField);

        priceSaleLabel = new JLabel("销售单价：");
        priceSaleLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        priceSaleLabel.setBounds(348, 223, 96, 29);
        productPanel.add(priceSaleLabel);

        priceSaleField = new JTextField();
        priceSaleField.setEnabled(false);
        priceSaleField.setColumns(10);
        priceSaleField.setBounds(458, 224, 137, 30);
        productPanel.add(priceSaleField);

        lblNewLabel_6 = new JLabel("元");
        lblNewLabel_6.setFont(new Font("宋体", Font.PLAIN, 18));
        lblNewLabel_6.setBounds(604, 224, 23, 29);
        productPanel.add(lblNewLabel_6);

        discountLabel = new JLabel("折扣：");
        discountLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        discountLabel.setBounds(14, 265, 96, 29);
        productPanel.add(discountLabel);

        discountField = new JTextField();
        discountField.setEnabled(false);
        discountField.setColumns(10);
        discountField.setBounds(121, 267, 137, 30);
        productPanel.add(discountField);

        lblNewLabel_5 = new JLabel("折");
        lblNewLabel_5.setFont(new Font("宋体", Font.PLAIN, 18));
        lblNewLabel_5.setBounds(272, 267, 23, 29);
        productPanel.add(lblNewLabel_5);

        buyNumLabel = new JLabel("购买数量：");
        buyNumLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        buyNumLabel.setBounds(348, 265, 96, 29);
        productPanel.add(buyNumLabel);

        buyNumField = new JTextField();
        buyNumField.setColumns(10);
        buyNumField.setBounds(458, 267, 137, 30);
        // 添加DocumentListener监听器
        buyNumField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 当文本内容发生改变时被调用
                String priceSale = priceSaleField.getText();
                String discount = discountField.getText();
                String buyNum = buyNumField.getText();
                if ("".equals(priceSale) || "".equals(discount) || "".equals(buyNum) || Double.valueOf(buyNum) <= 0) {
                    totalField.setText("");
                } else {
                    // 改变总价格
                    totalField.setText(Double.valueOf(priceSale) * Double.valueOf(discount) * Double.valueOf(buyNum) + "");
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // 当文本内容发生改变时被调用
                String priceSale = priceSaleField.getText();
                String discount = discountField.getText();
                String buyNum = buyNumField.getText();
                if ("".equals(priceSale) || "".equals(discount) || "".equals(buyNum) || Double.valueOf(buyNum) <= 0) {
                    totalField.setText("");
                } else {
                    // 改变总价格
                    totalField.setText(Double.valueOf(priceSale) * Double.valueOf(discount) * Double.valueOf(buyNum) + "");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // 当文本内容发生改变时被调用
                String priceSale = priceSaleField.getText();
                String discount = discountField.getText();
                String buyNum = buyNumField.getText();
                if ("".equals(priceSale) || "".equals(discount) || "".equals(buyNum) || Double.valueOf(buyNum) <= 0) {
                    totalField.setText("");
                } else {
                    // 改变总价格
                    totalField.setText(Double.valueOf(priceSale) * Double.valueOf(discount) * Double.valueOf(buyNum) + "");
                }
            }
        });
        productPanel.add(buyNumField);

        lblNewLabel_7 = new JLabel("个");
        lblNewLabel_7.setFont(new Font("宋体", Font.PLAIN, 18));
        lblNewLabel_7.setBounds(604, 265, 23, 29);
        productPanel.add(lblNewLabel_7);

        totalPriceLabel = new JLabel("商品总价：");
        totalPriceLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        totalPriceLabel.setBounds(14, 307, 96, 27);
        productPanel.add(totalPriceLabel);

        totalField = new JTextField();
        totalField.setEnabled(false);
        totalField.setColumns(10);
        totalField.setBounds(121, 304, 137, 30);
        productPanel.add(totalField);

        lblNewLabel_10 = new JLabel("元");
        lblNewLabel_10.setFont(new Font("宋体", Font.PLAIN, 18));
        lblNewLabel_10.setBounds(272, 304, 23, 29);
        productPanel.add(lblNewLabel_10);

        phoneLabel = new JLabel("联系电话：");
        phoneLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        phoneLabel.setBounds(348, 305, 96, 29);
        productPanel.add(phoneLabel);

        phoneCartField = new JTextField();
        phoneCartField.setColumns(10);
        phoneCartField.setBounds(458, 307, 169, 30);
        productPanel.add(phoneCartField);

        addressLabel = new JLabel("收货地址：");
        addressLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        addressLabel.setBounds(14, 348, 96, 27);
        productPanel.add(addressLabel);

        addressField = new JTextField();
        addressField.setColumns(10);
        addressField.setBounds(121, 348, 506, 30);
        productPanel.add(addressField);

        collectButton = new JButton("收藏");
        collectButton.setFont(new Font("宋体", Font.BOLD, 20));
        collectButton.setBounds(14, 402, 113, 36);
        collectButton.addActionListener(e -> addCollect(e));
        productPanel.add(collectButton);

        cartButton = new JButton("加入购物车");
        cartButton.setFont(new Font("宋体", Font.BOLD, 20));
        cartButton.setBounds(245, 402, 144, 36);
        cartButton.addActionListener(e -> addCart(e));
        productPanel.add(cartButton);

        oneClickPurchaseButton = new JButton("一键购买");
        oneClickPurchaseButton.setFont(new Font("宋体", Font.BOLD, 20));
        oneClickPurchaseButton.setBounds(491, 402, 136, 36);
        oneClickPurchaseButton.addActionListener(e -> oneClickPurchase(e));
        productPanel.add(oneClickPurchaseButton);

        orderPanel = new JPanel();
        tabbedPane.addTab("订单列表", null, orderPanel, null);
        orderPanel.setLayout(null);

        orderProductCategoryLabel = new JLabel("商品种类：");
        orderProductCategoryLabel.setFont(new Font("宋体", Font.BOLD, 18));
        orderProductCategoryLabel.setBounds(14, 24, 103, 29);
        orderPanel.add(orderProductCategoryLabel);

        orderProductCategoryComboBox = new JComboBox();
        orderProductCategoryComboBox.setFont(new Font("宋体", Font.PLAIN, 18));
        orderProductCategoryComboBox.setBounds(115, 24, 180, 29);
        // 给下拉列表框赋值 一定要在用户登录之后
        if (Objects.nonNull(users)) {
            orderProductCategoryComboBox.setModel(
                    new DefaultComboBoxModel(
                            orderDaoImpl.getForList(
                                            CONN,
                                            "select * from tb_order where userId = ?",
                                            users.getId()
                                    )
                                    .stream()
                                    .map(Order::getProductCategory)
                                    .distinct()
                                    .collect(Collectors.toList())
                                    .toArray()

                    )
            );
        }
        orderPanel.add(orderProductCategoryComboBox);

        orderProductNameLabel = new JLabel("商品名称：");
        orderProductNameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        orderProductNameLabel.setBounds(330, 24, 103, 29);
        orderPanel.add(orderProductNameLabel);

        orderProductNameField = new JTextField();
        orderProductNameField.setFont(new Font("宋体", Font.PLAIN, 18));
        orderProductNameField.setBounds(447, 24, 180, 29);
        orderPanel.add(orderProductNameField);
        orderProductNameField.setColumns(10);

        orderSearchButton = new JButton("搜索");
        orderSearchButton.setFont(new Font("宋体", Font.BOLD, 18));
        orderSearchButton.setBounds(447, 66, 180, 37);
        if (Objects.nonNull(users)) {
            orderSearchButton.addActionListener(e -> initOrderInfoPanel(orderInfoPanel,
                    (String) orderProductCategoryComboBox.getSelectedItem(),
                    orderProductNameField.getText()
            ));
        }

        orderPanel.add(orderSearchButton);

/**
 *订单面板
 */
        JLabel orderInfoLabel = new JLabel("订单信息");
        orderInfoLabel.setFont(new Font("宋体", Font.BOLD, 18));
        orderInfoLabel.setBounds(14, 66, 103, 37);
        orderPanel.add(orderInfoLabel);

        orderInfoPanel = new JPanel();
        orderInfoPanel.setBounds(14, 116, 613, 311);
        orderPanel.add(orderInfoPanel);
        orderInfoPanel.setLayout(new BorderLayout(0, 0));

        // 初始化订单列表
        if (Objects.nonNull(users)) {
            initOrderInfoPanel(orderInfoPanel,
                    (String) orderProductCategoryComboBox.getSelectedItem(),
                    orderProductNameField.getText()
            );
        } else {
            orderInfoPanel.removeAll();

            JTable table = new JTable();
            orderInfoPanel.add(
                    new JScrollPane(table),
                    BorderLayout.CENTER
            );
        }

        cartPanel = new JPanel();
        tabbedPane.addTab("购物车列表", null, cartPanel, null);
        cartPanel.setLayout(null);

        JLabel cartProductCategoryLabel = new JLabel("商品种类：");
        cartProductCategoryLabel.setFont(new Font("宋体", Font.BOLD, 18));
        cartProductCategoryLabel.setBounds(14, 25, 106, 30);
        cartPanel.add(cartProductCategoryLabel);

        cartProductCategoryComboBox = new JComboBox();
        cartProductCategoryComboBox.setFont(new Font("宋体", Font.PLAIN, 18));
        cartProductCategoryComboBox.setBounds(114, 25, 182, 30);

        // 给下拉列表框赋值 一定要在用户登录之后
        if (Objects.nonNull(users)) {
            cartProductCategoryComboBox.setModel(
                    new DefaultComboBoxModel(
                            orderDaoImpl.getForList(
                                            CONN,
                                            "select * from tb_cart where userId = ?",
                                            users.getId()
                                    )
                                    .stream()
                                    .map(Order::getProductCategory)
                                    .distinct()
                                    .collect(Collectors.toList())
                                    .toArray()

                    )
            );
        }
        cartPanel.add(cartProductCategoryComboBox);

        JLabel cartProductNameLabel = new JLabel("商品名称：");
        cartProductNameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        cartProductNameLabel.setBounds(334, 25, 106, 30);
        cartPanel.add(cartProductNameLabel);

        cartProductNameField = new JTextField();
        cartProductNameField.setFont(new Font("Dialog", Font.PLAIN, 18));
        cartProductNameField.setBounds(454, 26, 173, 30);
        cartPanel.add(cartProductNameField);
        cartProductNameField.setColumns(10);

        cartSearchButton = new JButton("搜索");
        cartSearchButton.setBounds(514, 69, 113, 27);
        if (Objects.nonNull(users)) {
            cartSearchButton.addActionListener(e -> initCartTablePanel(cartTablePanel,
                    (String) cartProductCategoryComboBox.getSelectedItem(),
                    cartProductNameField.getText()
            ));
        }
        cartPanel.add(cartSearchButton);

        JLabel lblNewLabel_8 = new JLabel("购物车列表");
        lblNewLabel_8.setBounds(14, 73, 91, 18);
        cartPanel.add(lblNewLabel_8);

        cartTablePanel = new JPanel();
        cartTablePanel.setBounds(14, 104, 613, 108);
        cartPanel.add(cartTablePanel);
        cartTablePanel.setLayout(new BorderLayout());

        // 初始化订单列表
        if (Objects.nonNull(users)) {
            initCartTablePanel(cartTablePanel,
                    (String) cartProductCategoryComboBox.getSelectedItem(),
                    cartProductNameField.getText()
            );
        } else {
            cartTablePanel.removeAll();

            JTable table = new JTable();
            cartTablePanel.add(
                    new JScrollPane(table),
                    BorderLayout.CENTER
            );
        }

        JLabel cartCategoryLabel = new JLabel("商品种类：");
        cartCategoryLabel.setBounds(14, 237, 84, 18);
        cartPanel.add(cartCategoryLabel);

        cartCategoryField = new JTextField();
        cartCategoryField.setEnabled(false);
        cartCategoryField.setBounds(102, 234, 118, 24);
        cartPanel.add(cartCategoryField);
        cartCategoryField.setColumns(10);

        JLabel cartProductLabel = new JLabel("商品名称：");
        cartProductLabel.setBounds(250, 237, 84, 18);
        cartPanel.add(cartProductLabel);

        cartProductField = new JTextField();
        cartProductField.setEnabled(false);
        cartProductField.setColumns(10);
        cartProductField.setBounds(333, 234, 118, 24);
        cartPanel.add(cartProductField);

        JLabel cartFactoryLabel = new JLabel("厂家：");
        cartFactoryLabel.setBounds(14, 272, 84, 18);
        cartPanel.add(cartFactoryLabel);

        cartFactoryField = new JTextField();
        cartFactoryField.setEnabled(false);
        cartFactoryField.setColumns(10);
        cartFactoryField.setBounds(102, 269, 118, 24);
        cartPanel.add(cartFactoryField);

        JLabel cartPriceLabel = new JLabel("商品价格：");
        cartPriceLabel.setBounds(250, 272, 84, 18);
        cartPanel.add(cartPriceLabel);

        cartPriceField = new JTextField();
        cartPriceField.setEnabled(false);
        cartPriceField.setColumns(10);
        cartPriceField.setBounds(334, 268, 118, 24);
        cartPanel.add(cartPriceField);

        JLabel cartNumLabel = new JLabel("商品数量：");
        cartNumLabel.setBounds(14, 303, 84, 18);
        cartPanel.add(cartNumLabel);

        cartNumField = new JTextField();
        cartNumField.setColumns(10);
        cartNumField.setBounds(102, 300, 118, 24);
        // 绑定值变化事件
        cartNumField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 应该考虑商品原价和原折扣
                String productCategory = cartCategoryField.getText();
                String productName = cartProductField.getText();

                ProductVo productVo = null;
                if ("".equals(productCategory)) {
                    // 查询现有商品价格
                    productVo = productVoDaoImpl.getForObject(
                            CONN,
                            "SELECT\n" +
                                    "\ttpi.id,\n" +
                                    "\ttpi.productCategory,\n" +
                                    "\ttpi.productName,\n" +
                                    "\ttpi.manufacturer,\n" +
                                    "\ttpi.productNum,\n" +
                                    "\ttpi.productBuyer,\n" +
                                    "\ttpi.isShelf,\n" +
                                    "\ttp.productInventoryId,\n" +
                                    "\ttp.price,\n" +
                                    "\ttp.discount,\n" +
                                    "\ttp.productStatus \n" +
                                    "FROM\n" +
                                    "\ttb_product_inventory tpi\n" +
                                    "\tLEFT JOIN tb_product tp ON tpi.id = tp.productInventoryId \n" +
                                    "WHERE\n" +
                                    "\ttpi.isShelf = 1 and tpi.productCategory = ? and tpi.productName = ?",
                            productCategory,
                            productName
                    );
                }

                // 当文本内容发生改变时被调用
                String priceSale = cartPriceField.getText();
                String discount = cartDiscountField.getText();
                String buyNum = cartNumField.getText();
                if ("".equals(priceSale) || "".equals(discount) || "".equals(buyNum) || Double.valueOf(buyNum) <= 0) {
                    cartTotalPriceField.setText("");
                } else {
                    if (Objects.isNull(productVo)) {
                        // 改变总价格
                        cartTotalPriceField.setText(Double.valueOf(priceSale) * Double.valueOf(discount) * Double.valueOf(buyNum) + "");
                    } else {
                        cartTotalPriceField.setText(productVo.getPrice() * productVo.getDiscount() * Double.valueOf(buyNum) + "");
                    }
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                String productCategory = cartCategoryField.getText();
                String productName = cartProductField.getText();

                ProductVo productVo = null;
                if ("".equals(productCategory)) {
                    // 查询现有商品价格
                    productVo = productVoDaoImpl.getForObject(
                            CONN,
                            "SELECT\n" +
                                    "\ttpi.id,\n" +
                                    "\ttpi.productCategory,\n" +
                                    "\ttpi.productName,\n" +
                                    "\ttpi.manufacturer,\n" +
                                    "\ttpi.productNum,\n" +
                                    "\ttpi.productBuyer,\n" +
                                    "\ttpi.isShelf,\n" +
                                    "\ttp.productInventoryId,\n" +
                                    "\ttp.price,\n" +
                                    "\ttp.discount,\n" +
                                    "\ttp.productStatus \n" +
                                    "FROM\n" +
                                    "\ttb_product_inventory tpi\n" +
                                    "\tLEFT JOIN tb_product tp ON tpi.id = tp.productInventoryId \n" +
                                    "WHERE\n" +
                                    "\ttpi.isShelf = 1 and tpi.productCategory = ? and tpi.productName = ?",
                            productCategory,
                            productName
                    );
                }

                // 当文本内容发生改变时被调用
                String priceSale = cartPriceField.getText();
                String discount = cartDiscountField.getText();
                String buyNum = cartNumField.getText();
                if ("".equals(priceSale) || "".equals(discount) || "".equals(buyNum) || Double.valueOf(buyNum) <= 0) {
                    cartTotalPriceField.setText("");
                } else {
                    if (Objects.isNull(productVo)) {
                        // 改变总价格
                        cartTotalPriceField.setText(Double.valueOf(priceSale) * Double.valueOf(discount) * Double.valueOf(buyNum) + "");
                    } else {
                        cartTotalPriceField.setText(productVo.getPrice() * productVo.getDiscount() * Double.valueOf(buyNum) + "");
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String productCategory = cartCategoryField.getText();
                String productName = cartProductField.getText();

                ProductVo productVo = null;
                if ("".equals(productCategory)) {
                    // 查询现有商品价格
                    productVo = productVoDaoImpl.getForObject(
                            CONN,
                            "SELECT\n" +
                                    "\ttpi.id,\n" +
                                    "\ttpi.productCategory,\n" +
                                    "\ttpi.productName,\n" +
                                    "\ttpi.manufacturer,\n" +
                                    "\ttpi.productNum,\n" +
                                    "\ttpi.productBuyer,\n" +
                                    "\ttpi.isShelf,\n" +
                                    "\ttp.productInventoryId,\n" +
                                    "\ttp.price,\n" +
                                    "\ttp.discount,\n" +
                                    "\ttp.productStatus \n" +
                                    "FROM\n" +
                                    "\ttb_product_inventory tpi\n" +
                                    "\tLEFT JOIN tb_product tp ON tpi.id = tp.productInventoryId \n" +
                                    "WHERE\n" +
                                    "\ttpi.isShelf = 1 and tpi.productCategory = ? and tpi.productName = ?",
                            productCategory,
                            productName
                    );
                }

                // 当文本内容发生改变时被调用
                String priceSale = cartPriceField.getText();
                String discount = cartDiscountField.getText();
                String buyNum = cartNumField.getText();
                if ("".equals(priceSale) || "".equals(discount) || "".equals(buyNum) || Double.valueOf(buyNum) <= 0) {
                    cartTotalPriceField.setText("");
                } else {
                    if (Objects.isNull(productVo)) {
                        // 改变总价格
                        cartTotalPriceField.setText(Double.valueOf(priceSale) * Double.valueOf(discount) * Double.valueOf(buyNum) + "");
                    } else {
                        cartTotalPriceField.setText(productVo.getPrice() * productVo.getDiscount() * Double.valueOf(buyNum) + "");
                    }
                }
            }
        });
        cartPanel.add(cartNumField);

        JLabel cartDiscountLabel = new JLabel("折扣：");
        cartDiscountLabel.setBounds(250, 303, 84, 18);
        cartPanel.add(cartDiscountLabel);

        cartDiscountField = new JTextField();
        cartDiscountField.setEnabled(false);
        cartDiscountField.setColumns(10);
        cartDiscountField.setBounds(333, 300, 118, 24);
        cartPanel.add(cartDiscountField);

        JLabel cartTotalPriceLabel = new JLabel("商品总价：");
        cartTotalPriceLabel.setBounds(14, 334, 84, 18);
        cartPanel.add(cartTotalPriceLabel);

        cartTotalPriceField = new JTextField();
        cartTotalPriceField.setEnabled(false);
        cartTotalPriceField.setColumns(10);
        cartTotalPriceField.setBounds(102, 331, 118, 24);
        cartPanel.add(cartTotalPriceField);

        JLabel cartPhoneLabel = new JLabel("联系电话：");
        cartPhoneLabel.setBounds(250, 334, 84, 18);
        cartPanel.add(cartPhoneLabel);

        cartPhoneField = new JTextField();
        cartPhoneField.setColumns(10);
        cartPhoneField.setBounds(334, 331, 118, 24);
        cartPanel.add(cartPhoneField);

        JLabel cartAddressLabel = new JLabel("收货地址");
        cartAddressLabel.setBounds(14, 365, 84, 18);
        cartPanel.add(cartAddressLabel);

        cartAddressTextArea = new JTextArea();
        cartAddressTextArea.setBounds(101, 368, 350, 70);
        cartPanel.add(cartAddressTextArea);

        emptyCartButton = new JButton("清空购物车");
        emptyCartButton.setFont(new Font("宋体", Font.BOLD, 18));
        emptyCartButton.setBounds(483, 236, 144, 41);
        if (Objects.nonNull(users)) {
            emptyCartButton.addActionListener((e) -> emptyCart(e));
        }
        cartPanel.add(emptyCartButton);

        deleteCartButton = new JButton("删除购物车");
        deleteCartButton.setFont(new Font("宋体", Font.BOLD, 18));
        deleteCartButton.setBounds(483, 321, 144, 41);
        if (Objects.nonNull(users)) {
            deleteCartButton.addActionListener((e) -> deleteCart(e));
        }
        cartPanel.add(deleteCartButton);

        cartPurchaseButton = new JButton("一键选购");
        cartPurchaseButton.setFont(new Font("宋体", Font.BOLD, 18));
        cartPurchaseButton.setBounds(483, 397, 144, 41);
        // 绑定点击事件
        cartPurchaseButton.addActionListener((e) -> cartPurchaseCart(e));
        cartPanel.add(cartPurchaseButton);


        /**
         * 收藏列表面板
         */

        collectPanel = new JPanel();
        tabbedPane.addTab("收藏列表", null, collectPanel, null);
        collectPanel.setLayout(null);

        JLabel collectProductCategoryLabel = new JLabel("商品种类：");
        collectProductCategoryLabel.setFont(new Font("宋体", Font.BOLD, 18));
        collectProductCategoryLabel.setBounds(14, 26, 103, 30);
        collectPanel.add(collectProductCategoryLabel);

        collectProductCategoryComboBox = new JComboBox();
        collectProductCategoryComboBox.setFont(new Font("宋体", Font.PLAIN, 18));
        collectProductCategoryComboBox.setBounds(131, 25, 141, 31);
        // 查询收藏列表
        if (Objects.nonNull(users)) {
            collectProductCategoryComboBox.setModel(
                    new DefaultComboBoxModel(
                            collectDaoImpl.getForList(
                                            CONN,
                                            "select * from tb_collect where userId = ?",
                                            users.getId()
                                    )
                                    .stream()
                                    .map(Collect::getProductCategory)
                                    .distinct()
                                    .collect(Collectors.toList())
                                    .toArray()

                    )
            );
        }
        collectPanel.add(collectProductCategoryComboBox);

        JLabel collectProductNameLabel = new JLabel("商品名称：");
        collectProductNameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        collectProductNameLabel.setBounds(308, 26, 103, 30);
        collectPanel.add(collectProductNameLabel);

        collectProductNameField = new JTextField();
        collectProductNameField.setFont(new Font("宋体", Font.PLAIN, 18));
        collectProductNameField.setBounds(424, 26, 203, 30);
        collectPanel.add(collectProductNameField);
        collectProductNameField.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("收藏列表");
        lblNewLabel_4.setFont(new Font("宋体", Font.BOLD, 18));
        lblNewLabel_4.setBounds(14, 94, 103, 30);
        collectPanel.add(lblNewLabel_4);

        collectCancleButton = new JButton("取消收藏");
        collectCancleButton.setFont(new Font("宋体", Font.BOLD, 18));
        collectCancleButton.setBounds(514, 94, 113, 30);
        collectCancleButton.addActionListener(e -> collectCancle(e));
        collectPanel.add(collectCancleButton);

        collectTablePanel = new JPanel();
        collectTablePanel.setBounds(14, 147, 613, 279);
        collectPanel.add(collectTablePanel);
        collectTablePanel.setLayout(new BorderLayout(0, 0));

        // 初始化收藏列表
        if (Objects.isNull(users)) {
            collectTablePanel.removeAll();

            collectTable = new JTable();
            collectTablePanel.add(
                    new JScrollPane(collectTable),
                    BorderLayout.CENTER
            );
        } else {
            initCollectTablePanel(collectTablePanel,
                    "",
                    collectProductNameField.getText()
            );
        }

        collectSearchButton = new JButton("查询");
        collectSearchButton.setFont(new Font("宋体", Font.BOLD, 18));
        collectSearchButton.setBounds(367, 94, 113, 30);
        if (Objects.nonNull(users)) {
            collectSearchButton.addActionListener(e -> initCollectTablePanel(collectTablePanel,
                            (String) collectProductCategoryComboBox.getSelectedItem(),
                            collectProductNameField.getText()
                    )
            );
        }
        collectPanel.add(collectSearchButton);

        infoPanel = new JPanel();
        infoPanel.setLayout(null);
        tabbedPane.addTab("信息管理", null, infoPanel, null);

        /**
         * 账号管理面板
         */
        accountLabel = new JLabel("账  号：");
        accountLabel.setFont(new Font("宋体", Font.BOLD, 18));
        accountLabel.setBounds(79, 40, 92, 36);
        infoPanel.add(accountLabel);

        accountField = new JTextField();
        accountField.setFont(new Font("宋体", Font.PLAIN, 18));
        accountField.setBounds(212, 40, 208, 41);
        accountField.setText(Objects.isNull(users) ? "" : users.getUserName());
        accountField.setEditable(false);
        infoPanel.add(accountField);
        accountField.setColumns(10);

        phoneLabel = new JLabel("电话号码：");
        phoneLabel.setFont(new Font("宋体", Font.BOLD, 18));
        phoneLabel.setBounds(79, 92, 108, 36);
        infoPanel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setFont(new Font("宋体", Font.PLAIN, 18));
        phoneField.setColumns(10);
        phoneField.setBounds(212, 92, 208, 41);
        phoneField.setText(Objects.isNull(users) ? "" : users.getPhone());
        infoPanel.add(phoneField);

        nickNameLabel = new JLabel("昵  称：");
        nickNameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        nickNameLabel.setBounds(79, 144, 92, 36);
        infoPanel.add(nickNameLabel);

        nickNameField = new JTextField();
        nickNameField.setFont(new Font("宋体", Font.PLAIN, 18));
        nickNameField.setColumns(10);
        nickNameField.setBounds(212, 144, 208, 41);
        nickNameField.setText(Objects.isNull(users) ? "" : users.getNickName());
        infoPanel.add(nickNameField);

        nameLabel = new JLabel("姓  名：");
        nameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        nameLabel.setBounds(79, 204, 92, 36);
        infoPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("宋体", Font.PLAIN, 18));
        nameField.setColumns(10);
        nameField.setBounds(212, 198, 208, 41);
        nameField.setText(Objects.isNull(users) ? "" : users.getName());
        infoPanel.add(nameField);

        userTypeLabel = new JLabel("用户类型：");
        userTypeLabel.setFont(new Font("宋体", Font.BOLD, 18));
        userTypeLabel.setBounds(79, 266, 108, 36);
        infoPanel.add(userTypeLabel);

        userTypeField = new JTextField();
        userTypeField.setFont(new Font("宋体", Font.PLAIN, 18));
        userTypeField.setColumns(10);
        userTypeField.setBounds(212, 261, 208, 41);
        // 根据枚举值的名字判断用户类型，展示的是枚举值的具体信息 Msg。  name() 获取的是枚举值的名字 比如ADMIN
        userTypeField.setText(
                Objects.isNull(users) ? "" :
                        users.getUserType().equals(UserTypeEnum.ADMIN.name())
                                ? UserTypeEnum.ADMIN.getMsg() : UserTypeEnum.ORDINARY.getMsg()
        );
        userTypeField.setEditable(false);
        infoPanel.add(userTypeField);

        saveOrUpdateButton = new JButton("保存修改");
        // 处理用户修改信息事件
        saveOrUpdateButton.addActionListener(e -> saveOrUpdateUsersInfo(e));
        saveOrUpdateButton.setFont(new Font("宋体", Font.BOLD, 20));
        saveOrUpdateButton.setBounds(179, 374, 208, 47);
        infoPanel.add(saveOrUpdateButton);


        retrievePasswordPanel = new JPanel();
        retrievePasswordPanel.setLayout(null);
        tabbedPane.addTab("找回密码", null, retrievePasswordPanel, null);

        retrieveUserNameLabel = new JLabel("账  号：");
        retrieveUserNameLabel.setFont(new Font("宋体", Font.BOLD, 20));
        retrieveUserNameLabel.setBounds(79, 61, 99, 35);
        retrievePasswordPanel.add(retrieveUserNameLabel);

        retrieveUserNameField = new JTextField();
        retrieveUserNameField.setBounds(212, 63, 268, 35);
        retrievePasswordPanel.add(retrieveUserNameField);
        retrieveUserNameField.setColumns(10);

        newPasswordLabel = new JLabel("新密码：");
        newPasswordLabel.setFont(new Font("宋体", Font.BOLD, 20));
        newPasswordLabel.setBounds(79, 141, 99, 35);
        retrievePasswordPanel.add(newPasswordLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(212, 141, 268, 35);
        retrievePasswordPanel.add(newPasswordField);

        confirmPasswordLabel = new JLabel("确认密码：");
        confirmPasswordLabel.setFont(new Font("宋体", Font.BOLD, 20));
        confirmPasswordLabel.setBounds(56, 217, 122, 35);
        retrievePasswordPanel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(212, 217, 268, 35);
        retrievePasswordPanel.add(confirmPasswordField);

        retrievePasswordButton = new JButton("找回密码");
        retrievePasswordButton.setFont(new Font("宋体", Font.BOLD, 20));
        retrievePasswordButton.setBounds(110, 336, 144, 42);
        retrievePasswordButton.addActionListener(e -> retrievePassword(e));
        retrievePasswordPanel.add(retrievePasswordButton);

        restButton = new JButton("重  置");
        restButton.setFont(new Font("宋体", Font.BOLD, 20));
        restButton.setBounds(350, 336, 144, 42);
        retrievePasswordPanel.add(restButton);
    }

    /**
     * 添加收藏
     *
     * @param e
     */
    private void addCollect(ActionEvent e) {
        // 判断用户是否登录
        if (Objects.isNull(users)) {
            // 提示还未登录，无法购买
            JOptionPane.showMessageDialog(this, "还未登录，无法收藏商品");
            // return 直接结束当前方法，不再执行下面的方法
            return;
        }

        String productCategory = productCategoryField.getText();
        String productName = producNameField.getText();
        String factory = factoryField.getText();
        String priceSale = priceSaleField.getText();
        String discount = discountField.getText();

        // 存储
        orderDaoImpl.update(
                CONN,
                "insert into tb_collect(userId,productCategory,productName,factory,priceSale," +
                        "discount) values(?,?,?,?,?,?)",
                users.getId(),
                productCategory,
                productName,
                factory,
                priceSale,
                discount
        );

        // 重置数据
        productCategoryField.setText("");
        producNameField.setText("");
        factoryField.setText("");
        priceSaleField.setText("");
        discountField.setText("");
        buyNumField.setText("");
        totalField.setText("");
        phoneCartField.setText("");
        addressField.setText("");

        // 给出提示，购买成功
        JOptionPane.showMessageDialog(this, "商品收藏成功");
    }

    /**
     * 加入购物车
     *
     * @param e
     */
    private void addCart(ActionEvent e) {
        // 判断用户是否登录
        if (Objects.isNull(users)) {
            // 提示还未登录，无法购买
            JOptionPane.showMessageDialog(this, "还未登录，商品无法加入购物车");
            // return 直接结束当前方法，不再执行下面的方法
            return;
        }

        String buyNum = buyNumField.getText();
        String total = totalField.getText();
        String phone = phoneCartField.getText();
        String address = addressField.getText();

        if ("".equals(buyNum) || "".equals(total) || "".equals(phone) || "".equals(address)) {
            JOptionPane.showMessageDialog(this, "数据填写不完整！！！");
        } else {
            String productCategory = productCategoryField.getText();
            String productName = producNameField.getText();

            // 查询商品信息
            ProductInventory productInventory = productInventoryDaoImpl.getForObject(
                    CONN,
                    "select * from tb_product_inventory where productCategory = ? and productName = ?",
                    productCategory,
                    productName
            );

            if (productInventory.getProductNum() >= Integer.valueOf(buyNum)) {
                // 获取所有想要的数据
                String factory = factoryField.getText();
                String priceSale = priceSaleField.getText();
                String discount = discountField.getText();

                // 存储
                orderDaoImpl.update(
                        CONN,
                        "insert into tb_cart(userId,productCategory,productName,factory,priceSale," +
                                "discount,buyNum,totalPrice,phone,address) values(?,?,?,?,?,?,?,?,?,?)",
                        users.getId(),
                        productCategory,
                        productName,
                        factory,
                        priceSale,
                        discount,
                        buyNum,
                        total,
                        phone,
                        address
                );

                // 重置数据
                productCategoryField.setText("");
                producNameField.setText("");
                factoryField.setText("");
                priceSaleField.setText("");
                discountField.setText("");
                buyNumField.setText("");
                totalField.setText("");
                phoneCartField.setText("");
                addressField.setText("");

                // 给出提示，购买成功
                JOptionPane.showMessageDialog(this, "加入购物车成功");
            } else {
                // 提示商品库存不足
                JOptionPane.showMessageDialog(this, "该商品库存不足，无法加入购物车！");
            }
        }
    }

    /**
     * 一键购买
     *
     * @param e
     */
    private void oneClickPurchase(ActionEvent e) {
        // 判断用户是否登录
        if (Objects.isNull(users)) {
            // 提示还未登录，无法购买
            JOptionPane.showMessageDialog(this, "还未登录，无法购买商品");
            // return 直接结束当前方法，不再执行下面的方法
            return;
        }


        String buyNum = buyNumField.getText();
        String total = totalField.getText();
        String phone = phoneCartField.getText();
        String address = addressField.getText();

        if ("".equals(buyNum) || "".equals(total) || "".equals(phone) || "".equals(address)) {
            JOptionPane.showMessageDialog(this, "数据填写不完整！！！");
        } else {
            String productCategory = productCategoryField.getText();
            String productName = producNameField.getText();

            // 查询商品信息
            ProductInventory productInventory = productInventoryDaoImpl.getForObject(
                    CONN,
                    "select * from tb_product_inventory where productCategory = ? and productName = ?",
                    productCategory,
                    productName
            );

            if (productInventory.getProductNum() >= Integer.valueOf(buyNum)) {
                // 获取所有想要的数据
                String factory = factoryField.getText();
                String priceSale = priceSaleField.getText();
                String discount = discountField.getText();

                // 存储
                orderDaoImpl.update(
                        CONN,
                        "insert into tb_order(userId,productCategory,productName,factory,priceSale," +
                                "discount,buyNum,totalPrice,phone,address) values(?,?,?,?,?,?,?,?,?,?)",
                        users.getId(),
                        productCategory,
                        productName,
                        factory,
                        priceSale,
                        discount,
                        buyNum,
                        total,
                        phone,
                        address
                );

                // 更新库存
                productInventoryDaoImpl.update(
                        CONN,
                        "update tb_product_inventory set productNum = ? where productCategory = ? and productName = ?",
                        productInventory.getProductNum() - Integer.valueOf(buyNum),
                        productCategory,
                        productName
                );

                // 重置数据
                productCategoryField.setText("");
                producNameField.setText("");
                factoryField.setText("");
                priceSaleField.setText("");
                discountField.setText("");
                buyNumField.setText("");
                totalField.setText("");
                phoneCartField.setText("");
                addressField.setText("");

                // 刷新面板
                initProductFront(productTablePanel, (String) productComboBox.getSelectedItem());

                // 给出提示，购买成功
                JOptionPane.showMessageDialog(this, "商品购买成功");
            } else {
                // 提示商品库存不足
                JOptionPane.showMessageDialog(this, "该商品库存不足！");
            }
        }
    }

    private void saveOrUpdateUsersInfo(ActionEvent e) {
        // 判断用户是否登录
        if (Objects.isNull(users)) {
            // 说明没有登录，不能操作
            JOptionPane.showMessageDialog(this, "您还没有登录，请登录或注册！");
        } else {
            // 获取输入的信息
            String phone = phoneField.getText();
            String nickName = nickNameField.getText();
            String name = nameField.getText();

            // 写修改代码语句
            usersDaoImpl.update(
                    CONN,
                    "update tb_users set phone = ?, nickName = ?, `name` = ? where userName = '" + users.getUserName() + "'",
                    phone,
                    nickName,
                    name
            );

            JOptionPane.showMessageDialog(this, "信息修改成功！！");
        }
    }

    /**
     * 找回密码的方法
     *
     * @param e
     */
    private void retrievePassword(ActionEvent e) {
        // 判断用户是否登录
        if (Objects.isNull(users)) {
            // 说明没有登录，不能操作
            JOptionPane.showMessageDialog(this, "您还没有登录，请登录或注册！");
        } else {
            // 获取输入框的信息
            String userName = retrieveUserNameField.getText();
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // 先判断是否为空
            if ("".equals(userName) || "".equals(newPassword) || "".equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "输入框内容不能为空！");
            } else {
                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(this, "新密码和确认密码不一致！");
                } else {
                    // 查询用户是否存在
                    Users users = usersDaoImpl.getForObject(
                            CONN,
                            "select * from tb_users where userName = ?",
                            userName
                    );

                    if (Objects.nonNull(users)) {
                        // 进行密码修改
                        usersDaoImpl.update(
                                CONN,
                                "update tb_users set passWord = ? where userName = '" + userName + "'",
                                MD5.encrypt(newPassword)
                        );

                        JOptionPane.showMessageDialog(this, "密码找回成功！");

                        retrieveUserNameField.setText("");
                        newPasswordField.setText("");
                        confirmPasswordField.setText("");

                    } else {
                        JOptionPane.showMessageDialog(this, "该用户不存在，无法找回密码！");
                    }
                }
            }

        }
    }


    /**
     * 查询商品数据
     *
     * @param e
     */
    private void productSearch(ActionEvent e) {
        this.initProductFront(productTablePanel, (String) productComboBox.getSelectedItem());
    }

    private void gotoFrontLogin() {
        // 点击按钮，判断用户是否已经登录，未登录，弹出登录框
        String loginText = loginAndRegisterLabel.getText();
        if (loginText.equals("登录/注册")) {
            // 关闭当前窗口
            this.dispose();
            // 打开登录框
            new FrontLoginFrame();
        }
    }


    /**
     * 初始化前台商品
     *
     * @param productTablePanel
     * @param categoryName
     */
    public void initProductFront(JPanel productTablePanel, String categoryName) {
        ArrayList<ProductVo> productVos;
        if ("".equals(categoryName)) {
            // 查询所有已上架的商品
            productVos = productVoDaoImpl.getForList(
                    CONN,
                    "SELECT\n" +
                            "\ttpi.id,\n" +
                            "\ttpi.productCategory,\n" +
                            "\ttpi.productName,\n" +
                            "\ttpi.manufacturer,\n" +
                            "\ttpi.productNum,\n" +
                            "\ttpi.productBuyer,\n" +
                            "\ttpi.isShelf,\n" +
                            "\ttp.productInventoryId,\n" +
                            "\ttp.price,\n" +
                            "\ttp.discount,\n" +
                            "\ttp.productStatus \n" +
                            "FROM\n" +
                            "\ttb_product_inventory tpi\n" +
                            "\tLEFT JOIN tb_product tp ON tpi.id = tp.productInventoryId \n" +
                            "WHERE\n" +
                            "\ttpi.isShelf = 1 "
            );
        } else {
            // 查询所有已上架的商品
            productVos = productVoDaoImpl.getForList(
                    CONN,
                    "SELECT\n" +
                            "\ttpi.id,\n" +
                            "\ttpi.productCategory,\n" +
                            "\ttpi.productName,\n" +
                            "\ttpi.manufacturer,\n" +
                            "\ttpi.productNum,\n" +
                            "\ttpi.productBuyer,\n" +
                            "\ttpi.isShelf,\n" +
                            "\ttp.productInventoryId,\n" +
                            "\ttp.price,\n" +
                            "\ttp.discount,\n" +
                            "\ttp.productStatus \n" +
                            "FROM\n" +
                            "\ttb_product_inventory tpi\n" +
                            "\tLEFT JOIN tb_product tp ON tpi.id = tp.productInventoryId \n" +
                            "WHERE\n" +
                            "\ttpi.isShelf = 1 and tpi.productCategory = ?",
                    categoryName
            );
        }

        // 组装数据
        Object[][] a = new Object[productVos.size()][7];
        // 进行初始化的操作
        for (int i = 0; i < productVos.size(); i++) {
            a[i][0] = productVos.get(i).getProductCategory();
            a[i][1] = productVos.get(i).getProductName();
            a[i][2] = productVos.get(i).getManufacturer();
            a[i][3] = productVos.get(i).getPrice();
            a[i][4] = productVos.get(i).getDiscount();
            a[i][5] = productVos.get(i).getProductNum();
            a[i][6] = productVos.get(i).getProductStatus();
        }

        String[] names = {"商品种类", "商品名称", "厂家", "销售价格", "折扣", "商品数量", "商品状态"};
        productTablePanel.removeAll();

        JTable table = new JTable(a, names);
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // 获取点击了 第几行
                int row = table.getSelectedRow();
                // 拿到所需的数据
                Object[] objects = a[row];
                // 赋值
                productCategoryField.setText((String) objects[0]);
                producNameField.setText((String) objects[1]);
                factoryField.setText((String) objects[2]);
                priceSaleField.setText(objects[3].toString());
                discountField.setText(objects[4].toString());
                buyNumField.setText("1");
                totalField.setText(((Double) objects[3] * (Double) objects[4]) + "");

            }
        });

        productTablePanel.add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );
    }

    /**
     * 初始化订单列表
     *
     * @param orderInfoPanel
     * @param category
     * @param name
     */
    private void initOrderInfoPanel(JPanel orderInfoPanel, String category, String name) {
        ArrayList<Order> orderList = null;
        // 要查询数据
        if (!"".equals(name)) {
            orderList = orderDaoImpl.getForList(
                    CONN,
                    "select * from tb_order where productCategory = ? and productName like '%" + name + "%' and userId = ?",
                    category,
                    users.getId()
            );
        } else {
            orderList = orderDaoImpl.getForList(
                    CONN,
                    "select * from tb_order where productCategory = ? and userId = ?",
                    category,
                    users.getId()
            );
        }


        // 组装数据
        Object[][] a = new Object[orderList.size()][9];
        // 进行初始化的操作
        for (int i = 0; i < orderList.size(); i++) {
            a[i][0] = orderList.get(i).getProductCategory();
            a[i][1] = orderList.get(i).getProductName();
            a[i][2] = orderList.get(i).getFactory();
            a[i][3] = orderList.get(i).getPriceSale();
            a[i][4] = orderList.get(i).getDiscount();
            a[i][5] = orderList.get(i).getBuyNum();
            a[i][6] = orderList.get(i).getTotalPrice();
            a[i][7] = orderList.get(i).getPhone();
            a[i][8] = orderList.get(i).getAddress();
        }


        String[] names = {"商品种类", "商品名称", "厂家", "商品价格", "折扣", "商品数量",
                "总价", "联系电话", "收货地址"};
        orderInfoPanel.removeAll();

        JTable table = new JTable(a, names);
        orderInfoPanel.add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );
    }

    /**
     * 初始化购物车列表
     *
     * @param cartTablePanel
     * @param category
     * @param name
     */
    private void initCartTablePanel(JPanel cartTablePanel, String category, String name) {
        ArrayList<Cart> cartList = null;
        // 要查询数据
        if (!"".equals(name)) {
            cartList = cartDaoImpl.getForList(
                    CONN,
                    "select * from tb_cart where productCategory = ? and productName like '%" + name + "%' and userId = ?",
                    category,
                    users.getId()
            );
        } else {
            cartList = cartDaoImpl.getForList(
                    CONN,
                    "select * from tb_cart where productCategory = ? and userId = ?",
                    category,
                    users.getId()
            );
        }


        // 组装数据
        Object[][] a = new Object[cartList.size()][10];
        // 进行初始化的操作
        for (int i = 0; i < cartList.size(); i++) {
            // 查询每个商品信息
            ProductVo productVo = productVoDaoImpl.getForObject(
                    CONN,
                    "SELECT\n" +
                            "\ttpi.id,\n" +
                            "\ttpi.productCategory,\n" +
                            "\ttpi.productName,\n" +
                            "\ttpi.manufacturer,\n" +
                            "\ttpi.productNum,\n" +
                            "\ttpi.productBuyer,\n" +
                            "\ttpi.isShelf,\n" +
                            "\ttp.productInventoryId,\n" +
                            "\ttp.price,\n" +
                            "\ttp.discount,\n" +
                            "\ttp.productStatus \n" +
                            "FROM\n" +
                            "\ttb_product_inventory tpi\n" +
                            "\tLEFT JOIN tb_product tp ON tpi.id = tp.productInventoryId \n" +
                            "WHERE\n" +
                            "\ttpi.isShelf = 1 and tpi.productCategory = ? and tpi.productName = ?",
                    cartList.get(i).getProductCategory(),
                    cartList.get(i).getProductName()
            );

            a[i][0] = cartList.get(i).getProductCategory();
            a[i][1] = cartList.get(i).getProductName();
            a[i][2] = cartList.get(i).getFactory();
            a[i][3] = cartList.get(i).getPriceSale();
            a[i][4] = cartList.get(i).getDiscount();
            a[i][5] = cartList.get(i).getBuyNum();
            a[i][6] = cartList.get(i).getTotalPrice();
            a[i][7] = cartList.get(i).getPhone();
            a[i][8] = cartList.get(i).getAddress();
            a[i][9] = Objects.isNull(productVo) ? "已下架" : productVo.getIsShelf() == 1 ? "上架" : "下架";
        }


        String[] names = {"商品种类", "商品名称", "厂家", "商品价格", "折扣", "商品数量",
                "总价", "联系电话", "收货地址", "商品状态"};
        cartTablePanel.removeAll();

        JTable table = new JTable(a, names);
        // 给table绑定鼠标点击事件
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 获取点击了 第几行
                int row = table.getSelectedRow();
                // 拿到所需的数据
                Object[] objects = a[row];

                // 赋值
                String productCategory = (String) objects[0];
                String productName = (String) objects[1];

                // 查询现有商品价格
                ProductVo productVo = productVoDaoImpl.getForObject(
                        CONN,
                        "SELECT\n" +
                                "\ttpi.id,\n" +
                                "\ttpi.productCategory,\n" +
                                "\ttpi.productName,\n" +
                                "\ttpi.manufacturer,\n" +
                                "\ttpi.productNum,\n" +
                                "\ttpi.productBuyer,\n" +
                                "\ttpi.isShelf,\n" +
                                "\ttp.productInventoryId,\n" +
                                "\ttp.price,\n" +
                                "\ttp.discount,\n" +
                                "\ttp.productStatus \n" +
                                "FROM\n" +
                                "\ttb_product_inventory tpi\n" +
                                "\tLEFT JOIN tb_product tp ON tpi.id = tp.productInventoryId \n" +
                                "WHERE\n" +
                                "\ttpi.isShelf = 1 and tpi.productCategory = ? and tpi.productName = ?",
                        productCategory,
                        productName
                );

                cartCategoryField.setText(productCategory);
                cartProductField.setText(productName);
                cartFactoryField.setText((String) objects[2]);
                cartPriceField.setText(Objects.isNull(productVo) ? (String) objects[3] : String.valueOf(productVo.getPrice()));
                cartNumField.setText(String.valueOf(objects[5]));
                cartDiscountField.setText(Objects.isNull(productVo) ? String.valueOf(objects[4]) : String.valueOf(productVo.getDiscount()));
                cartTotalPriceField.setText(
                        String.valueOf(Double.valueOf(cartPriceField.getText()) * (Integer) objects[5] * Double.valueOf(cartDiscountField.getText()))
                );
                cartPhoneField.setText((String) objects[7]);
                cartAddressTextArea.setText((String) objects[8]);
            }
        });

        cartTablePanel.add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

    }

    /**
     * 清空购物车
     *
     * @param e
     */
    private void emptyCart(ActionEvent e) {
        // 弹出确认框
        // 指定用户编号
        int a = JOptionPane.showConfirmDialog(null, "你确定要清空购物车吗？", "确认", JOptionPane.YES_NO_OPTION);
        if (a == JOptionPane.NO_OPTION) {
            return;
        }

        // 直接将当前用户购物车中的数据删除
        cartDaoImpl.update(
                CONN,
                "delete from tb_cart where userId = ?",
                users.getId()
        );

        // 重新初始化面板
        initCartTablePanel(cartTablePanel,
                (String) cartProductCategoryComboBox.getSelectedItem(),
                cartProductNameField.getText()
        );
    }

    /**
     * 删除购物车
     *
     * @param e
     */
    private void deleteCart(ActionEvent e) {
        // 判断用户是否选择商品
        String category = cartCategoryField.getText();
        String productName = cartProductNameField.getText();

        // 如果商品种类为空，直接给出提示，未选择商品
        if ("".equals(category)) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的商品", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            // 直接从数据库删除商品
            cartDaoImpl.update(
                    CONN,
                    "delete from tb_cart where userId = ? and productName = ? and productCategory = ?",
                    users.getId(),
                    productName,
                    category
            );

            // 重新初始化面板
            initCartTablePanel(cartTablePanel,
                    (String) cartProductCategoryComboBox.getSelectedItem(),
                    cartProductNameField.getText()
            );

            // 清空内容
            cartCategoryField.setText("");
            cartProductField.setText("");
            cartFactoryField.setText("");
            cartPriceField.setText("");
            cartNumField.setText("");
            cartDiscountField.setText("");
            cartTotalPriceField.setText("");
            cartPhoneField.setText("");
            cartAddressTextArea.setText("");

            // 给出提示，商品从购物车中移除成功
            JOptionPane.showMessageDialog(this, "商品从购物车中移除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 一键选购商品
     *
     * @param e
     */
    private void cartPurchaseCart(ActionEvent e) {
        String cartCategory = cartCategoryField.getText();
        String productName = cartProductField.getText();
        String factory = cartFactoryField.getText();
        String price = cartPriceField.getText();
        String cartNum = cartNumField.getText();
        String discount = cartDiscountField.getText();
        String totalPrice = cartTotalPriceField.getText();
        String phone = cartPhoneField.getText();
        String addressTextArea = cartAddressTextArea.getText();

        // 判断数据是否填写完整
        if ("".equals(cartCategory) || "".equals(cartNum) || "".equals(phone) || "".equals(addressTextArea)) {
            // 提示信息未填写完整
            JOptionPane.showMessageDialog(this, "购物车数量和联系方式未填写完整", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            // 查询商品是否存在
            ProductVo productVo = productVoDaoImpl.getForObject(
                    CONN,
                    "SELECT\n" +
                            "\ttpi.id,\n" +
                            "\ttpi.productCategory,\n" +
                            "\ttpi.productName,\n" +
                            "\ttpi.manufacturer,\n" +
                            "\ttpi.productNum,\n" +
                            "\ttpi.productBuyer,\n" +
                            "\ttpi.isShelf,\n" +
                            "\ttp.productInventoryId,\n" +
                            "\ttp.price,\n" +
                            "\ttp.discount,\n" +
                            "\ttp.productStatus \n" +
                            "FROM\n" +
                            "\ttb_product_inventory tpi\n" +
                            "\tLEFT JOIN tb_product tp ON tpi.id = tp.productInventoryId \n" +
                            "WHERE\n" +
                            "\ttpi.isShelf = 1 and tpi.productCategory = ? and tpi.productName = ?",
                    cartCategory,
                    productName
            );

            if (Objects.isNull(productVo) || productVo.getIsShelf() == 0) {
                // 提示商品已下架，不可购买
                JOptionPane.showMessageDialog(this, "商品已下架，不可购买", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 判断库存是否充足
            if (Integer.valueOf(cartNum) > productVo.getProductNum()) {
                // 提示库存不足
                JOptionPane.showMessageDialog(this, "商品库存不足", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 直接往订单表里面存储数据
            orderDaoImpl.update(
                    CONN,
                    "insert into tb_order(userId,productCategory,productName,factory,priceSale," +
                            "discount,buyNum,totalPrice,phone,address) values(?,?,?,?,?,?,?,?,?,?)",
                    users.getId(),
                    cartCategory,
                    productName,
                    factory,
                    price,
                    discount,
                    cartNum,
                    totalPrice,
                    phone,
                    addressTextArea
            );

            // 更新库存
            productInventoryDaoImpl.update(
                    CONN,
                    "update tb_product_inventory set productNum = ? where productCategory = ? and productName = ?",
                    productVo.getProductNum() - Integer.valueOf(cartNum),
                    cartCategory,
                    productName
            );

            // 删除购物车中的这条记录
            cartDaoImpl.update(
                    CONN,
                    "delete from tb_cart where userId = ? and productName = ? and productCategory = ?",
                    users.getId(),
                    productName,
                    cartCategory
            );

            // 清空内容
            cartCategoryField.setText("");
            cartProductField.setText("");
            cartFactoryField.setText("");
            cartPriceField.setText("");
            cartNumField.setText("");
            cartDiscountField.setText("");
            cartTotalPriceField.setText("");
            cartPhoneField.setText("");
            cartAddressTextArea.setText("");

            // 重新初始化面板
            initCartTablePanel(cartTablePanel,
                    (String) cartProductCategoryComboBox.getSelectedItem(),
                    cartProductNameField.getText()
            );

            // 提示购买成功
            JOptionPane.showMessageDialog(this, "购买成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 初始化收藏列表
     *
     * @param collectTablePanel
     * @param category
     * @param productName
     */
    private void initCollectTablePanel(JPanel collectTablePanel, String category, String productName) {
        ArrayList<Collect> collectList;
        if ("".equals(category)) {
            // 查询所有
            collectList = collectDaoImpl.getForList(
                    CONN,
                    "select * from tb_collect where userId = ? ",
                    users.getId()
            );
        } else {
            collectList = collectDaoImpl.getForList(
                    CONN,
                    "select * from tb_collect where userId = ? and productCategory = ? and productName like '%" + productName + "%'",
                    users.getId(),
                    category
            );
        }

        // 组装数据
        Object[][] a = new Object[collectList.size()][6];
        // 进行初始化的操作
        for (int i = 0; i < collectList.size(); i++) {
            // 查询每个商品信息
            ProductVo productVo = productVoDaoImpl.getForObject(
                    CONN,
                    "SELECT\n" +
                            "\ttpi.id,\n" +
                            "\ttpi.productCategory,\n" +
                            "\ttpi.productName,\n" +
                            "\ttpi.manufacturer,\n" +
                            "\ttpi.productNum,\n" +
                            "\ttpi.productBuyer,\n" +
                            "\ttpi.isShelf,\n" +
                            "\ttp.productInventoryId,\n" +
                            "\ttp.price,\n" +
                            "\ttp.discount,\n" +
                            "\ttp.productStatus \n" +
                            "FROM\n" +
                            "\ttb_product_inventory tpi\n" +
                            "\tLEFT JOIN tb_product tp ON tpi.id = tp.productInventoryId \n" +
                            "WHERE\n" +
                            "\ttpi.isShelf = 1 and tpi.productCategory = ? and tpi.productName = ?",
                    collectList.get(i).getProductCategory(),
                    collectList.get(i).getProductName()
            );

            a[i][0] = collectList.get(i).getProductCategory();
            a[i][1] = collectList.get(i).getProductName();
            a[i][2] = collectList.get(i).getFactory();
            a[i][3] = collectList.get(i).getPriceSale();
            a[i][4] = collectList.get(i).getDiscount();
            a[i][5] = Objects.isNull(productVo) ? "已下架" : productVo.getIsShelf() == 1 ? "上架" : "下架";
        }


        String[] names = {"商品种类", "商品名称", "厂家", "商品价格", "折扣", "商品状态"};
        collectTablePanel.removeAll();

        collectTable = new JTable(a, names);
        collectTablePanel.add(
                new JScrollPane(collectTable),
                BorderLayout.CENTER
        );
    }

    /**
     * 取消收藏
     *
     * @param e
     */
    private void collectCancle(ActionEvent e) {
        int selectedRow = collectTable.getSelectedRow();
        // 判断table中的行有没有被选中，没有被选中返回-1。选中返回行号
        if (selectedRow >= 0) {
            String productCategory = (String) collectTable.getValueAt(selectedRow, 0);
            String productName = (String) collectTable.getValueAt(selectedRow, 1);

            // 删除表中的收藏记录
            collectDaoImpl.update(
                    CONN,
                    "delete from tb_collect where userId = ? and productCategory = ? and productName = ?",
                    users.getId(),
                    productCategory,
                    productName

            );

            // 重置表格
            initCollectTablePanel(collectTablePanel,
                    (String) collectProductCategoryComboBox.getSelectedItem(),
                    collectProductNameField.getText()
            );

        } else {
            // 提示没有选中数据
            JOptionPane.showMessageDialog(null, "还没有选中任何数据，不能取消收藏", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new FrontIndexFrame(null));
    }
}