package com.wxy.mall.frame.admin;


import com.wxy.mall.dao.impl.CategoryDaoImpl;
import com.wxy.mall.dao.impl.ProductDaoImpl;
import com.wxy.mall.dao.impl.ProductInventoryDaoImpl;
import com.wxy.mall.domain.Category;
import com.wxy.mall.domain.ProductInventory;
import com.wxy.mall.frame.table.MyJTable;
import com.wxy.mall.jdbc.connection.MyConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 上架商品窗口
 *
 * @author 魏心怡
 */
public class ProductListingFrame extends JFrame {

    private static final Connection CONN = MyConnection.getConnection(6);

    private JPanel contentPane;
    private JTextField salePriceField;
    private JTextField discountField;
    private JTable table;

    private JLabel categoryNameLabel;
    private JComboBox categoryNameComboBox;
    private JComboBox productNamecomboBox;
    private JLabel salePriceLabel;
    private JLabel discountLabel;
    private JLabel productNameLabel;
    private JLabel listedLabel;
    private JButton clickListingButton;
    private JPanel productListingPanel;

    private CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
    private ProductInventoryDaoImpl productInventoryDaoImpl = new ProductInventoryDaoImpl();
    private ProductDaoImpl productDaoImpl = new ProductDaoImpl();

    public ProductListingFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 682, 571);
        setVisible(true);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        categoryNameLabel = new JLabel("商品分类：");
        categoryNameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        categoryNameLabel.setBounds(14, 29, 105, 38);
        contentPane.add(categoryNameLabel);

        categoryNameComboBox = new JComboBox();
        categoryNameComboBox.setFont(new Font("宋体", Font.PLAIN, 18));
        categoryNameComboBox.setBounds(120, 29, 182, 38);
        categoryNameComboBox.setModel(
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
        // 给点击事件做联动处理，处理的就是商品名字
        categoryNameComboBox.addActionListener(e -> setProductName(e));
        contentPane.add(categoryNameComboBox);

        productNameLabel = new JLabel("商品名称：");
        productNameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        productNameLabel.setBounds(316, 29, 105, 38);
        contentPane.add(productNameLabel);

        productNamecomboBox = new JComboBox();
        productNamecomboBox.setFont(new Font("宋体", Font.PLAIN, 18));
        productNamecomboBox.setBounds(425, 29, 228, 38);
        initProductNameComboBox();
        contentPane.add(productNamecomboBox);

        salePriceLabel = new JLabel("销售价格：");
        salePriceLabel.setFont(new Font("宋体", Font.BOLD, 18));
        salePriceLabel.setBounds(14, 81, 105, 38);
        contentPane.add(salePriceLabel);

        salePriceField = new JTextField();
        salePriceField.setFont(new Font("宋体", Font.PLAIN, 18));
        salePriceField.setBounds(120, 80, 182, 39);
        contentPane.add(salePriceField);
        salePriceField.setColumns(10);

        discountLabel = new JLabel("商品折扣：");
        discountLabel.setFont(new Font("宋体", Font.BOLD, 18));
        discountLabel.setBounds(316, 80, 105, 38);
        contentPane.add(discountLabel);

        discountField = new JTextField();
        discountField.setFont(new Font("宋体", Font.PLAIN, 18));
        discountField.setColumns(10);
        discountField.setBounds(425, 80, 228, 39);
        contentPane.add(discountField);

        listedLabel = new JLabel("已上架商品：");
        listedLabel.setFont(new Font("宋体", Font.BOLD, 18));
        listedLabel.setBounds(14, 133, 127, 38);
        contentPane.add(listedLabel);

        clickListingButton = new JButton("一键上架");
        clickListingButton.setFont(new Font("宋体", Font.BOLD, 18));
        clickListingButton.setBounds(526, 133, 127, 38);
        clickListingButton.addActionListener(e -> productClickListing(e));
        contentPane.add(clickListingButton);

        productListingPanel = new JPanel();
        productListingPanel.setBounds(14, 184, 636, 327);
        contentPane.add(productListingPanel);
        productListingPanel.setLayout(new BorderLayout(0, 0));

        // 设置表格
        new MyJTable().initProductListing(productListingPanel);
    }

    /**
     * 商品一键上架的方法
     *
     * @param e
     */
    private void productClickListing(ActionEvent e) {
        // 获取到选择的商品分类
        String categoryName = (String) categoryNameComboBox.getSelectedItem();
        // 获取商品名称
        String productName = (String) productNamecomboBox.getSelectedItem();
        // 获取销售价格
        String salePrice = salePriceField.getText();
        // 获取折扣
        String discount = discountField.getText();

        // 根据商品分类和商品名称查询进货记录id
        ProductInventory productInventory = productInventoryDaoImpl.getForObject(
                CONN,
                "select * from tb_product_inventory where productCategory = ? and productName = ?",
                categoryName,
                productName
        );

        // 将上架的商品存入商品表
        productDaoImpl.update(
                CONN,
                "insert into tb_product(productInventoryId,price,discount,productStatus) values(?,?,?,?)",
                productInventory.getId(),
                Double.valueOf(salePrice),
                Double.valueOf(discount),
                Double.valueOf(discount) < 1 ? "降价" : "正常"
        );

        // 修改商品库存表里面的商品状态
        productInventoryDaoImpl.update(
                CONN,
                "update tb_product_inventory set isShelf = ? where productCategory = ? and productName = ?",
                1,
                categoryName,
                productName
        );

        // 置空
        salePriceField.setText("");
        discountField.setText("");

        // 设置表格
        new MyJTable().initProductListing(productListingPanel);

        // 给出提示框，商品上架成功
        JOptionPane.showMessageDialog(
                null,
                "商品上架成成功！",
                "提示",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void initProductNameComboBox() {
        // 获取到选择的商品分类
        String categoryName = (String) categoryNameComboBox.getSelectedItem();
        // 查询库存表，匹配所有未上架的商品分类下的商品
        List<String> productNameList = productInventoryDaoImpl.getForList(
                        CONN,
                        "select * from tb_product_inventory where productCategory = ? and isShelf != 1",
                        categoryName
                )
                .stream()
                .map(ProductInventory::getProductName)
                .collect(Collectors.toList());

        // 给商品名称下拉列表赋值
        productNamecomboBox.setModel(
                new DefaultComboBoxModel(productNameList.toArray())
        );
    }

    /**
     * 处理商品名联动
     *
     * @param e
     */
    private void setProductName(ActionEvent e) {
        // 获取到选择的商品分类
        initProductNameComboBox();
    }
}