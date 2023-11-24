package com.wxy.mall.frame.table;

import com.wxy.mall.dao.impl.*;
import com.wxy.mall.domain.*;
import com.wxy.mall.enums.UserTypeEnum;
import com.wxy.mall.jdbc.connection.MyConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 表格类
 *
 * @author 魏心怡
 * @version 1.0.0
 * @date 2023/6/5
 */
public class MyJTable {


    private static final Connection CONN = MyConnection.getConnection(6);

    private CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
    private ProductInventoryDaoImpl productInventoryDaoImpl = new ProductInventoryDaoImpl();
    private ProductDaoImpl productDaoImpl = new ProductDaoImpl();
    private UsersDaoImpl usersDaoImpl = new UsersDaoImpl();
    private StockGoodsDaoImpl stockGoodsDaoImpl = new StockGoodsDaoImpl();
    private ProductVoDaoImpl productVoDaoImpl = new ProductVoDaoImpl();


    /**
     * 查询所有已上架的库存内容
     *
     * @param productListingPanel
     */
    public void initProductListing(JPanel productListingPanel) {
        // 先查询所有库存中已上架的商品信息
        ArrayList<ProductInventory> productInventoryList = productInventoryDaoImpl.getForList(
                CONN,
                "select * from tb_product_inventory where isShelf = 1"
        );

        // 查询商品表中的商品信息
        Map<Integer, Product> productMap = productDaoImpl.getForList(
                        CONN,
                        "select * from tb_product where productInventoryId in ("
                                + productInventoryList.stream()
                                .map(productInventory -> String.valueOf(productInventory.getId()))
                                .collect(Collectors.joining(","))
                                + ")"
                )
                // 将商品信息转成map
                .stream()
                .collect(Collectors.toMap(
                                Product::getProductInventoryId,
                                Function.identity()
                        )
                );

        // 组装数据
        Object[][] a = new Object[productInventoryList.size()][10];
        // 进行初始化的操作
        for (int i = 0; i < productInventoryList.size(); i++) {
            Product product = productMap.get(productInventoryList.get(i).getId());
            a[i][0] = productInventoryList.get(i).getId();
            a[i][1] = productInventoryList.get(i).getProductCategory();
            a[i][2] = productInventoryList.get(i).getProductName();
            a[i][3] = productInventoryList.get(i).getManufacturer();
            a[i][4] = productInventoryList.get(i).getProductNum();
            a[i][5] = productInventoryList.get(i).getProductBuyer();
            a[i][6] = productInventoryList.get(i).getIsShelf() == 0
                    ? "待上架" : productInventoryList.get(i).getIsShelf() == 1
                    ? "已上架" : "已上架";
            a[i][7] = product.getPrice();
            a[i][8] = product.getDiscount();
            a[i][9] = product.getProductStatus();
        }

        String[] names = {"序号", "分类名称", "商品名称", "进货厂家", "商品数量",
                "商品进价", "是否上架", "销售价格", "折扣", "商品状态"};
        productListingPanel.removeAll();

        JTable table = new JTable(a, names);
        productListingPanel.add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );
    }

    /**
     * 库存的表格
     *
     * @param inventoryTablePanel 库存分类的面板
     * @param productCategoryName 商品分类名
     * @param productName         商品名
     */
    public void inventoryTable(JPanel inventoryTablePanel,
                               String productCategoryName,
                               String productName) {
        // 初始化数据
        List<ProductInventory> productInventoryList = Collections.emptyList();
        // 判断条件是否满足
        if ("".equals(productCategoryName) && "".equals(productName)) {
            // 查询全部数据
            productInventoryList = productInventoryDaoImpl.getForList(
                    CONN,
                    "select * from tb_product_inventory"
            );
        } else {
            // 根据条件进行模糊查询
            productInventoryList = productInventoryDaoImpl.getForList(
                    CONN,
                    "select * from tb_product_inventory where productCategory = '" + productCategoryName + "' " +
                            "and productName like '%" + productName + "%'"
            );
        }

        // 处理数据
        Object[][] a = new Object[productInventoryList.size()][7];
        if (productInventoryList.size() > 0) {
            for (int i = 0; i < productInventoryList.size(); i++) {
                a[i][0] = productInventoryList.get(i).getId();
                a[i][1] = productInventoryList.get(i).getProductCategory();
                a[i][2] = productInventoryList.get(i).getProductName();
                a[i][3] = productInventoryList.get(i).getManufacturer();
                a[i][4] = productInventoryList.get(i).getProductNum();
                a[i][5] = productInventoryList.get(i).getProductBuyer();
                a[i][6] = productInventoryList.get(i).getIsShelf() == 0
                        ? "待上架" : productInventoryList.get(i).getIsShelf() == 1
                        ? "已上架" : "已上架";
            }
        }
        String[] names = {"序号", "分类名称", "商品名称", "进货厂家", "商品数量", "商品进价", "是否上架"};
        inventoryTablePanel.removeAll();

        JTable table = new JTable(a, names);
        inventoryTablePanel.add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

    }

    /**
     * 创建商品分类的表格
     */
    public void initCategoryTable(JPanel categoryTablePanel,
                                  String categoryName) {
        // Collections.emptyList() 利用集合的工具类声明一个空的list集合
        List<Category> categoryList = Collections.emptyList();
        // 判断是否有查询条件
        if ("".equals(categoryName)) {
            // 没有查询条件 直接查询所有
            categoryList = categoryDaoImpl.getForList(
                    CONN,
                    "select * from tb_category"
            );
        } else {
            categoryList = categoryDaoImpl.getForList(
                    CONN,
                    "select * from tb_category where categoryName like '%" + categoryName + "%'"
            );
        }

        Object[][] a = new Object[categoryList.size()][2];
        if (categoryList.size() > 0) {
            for (int i = 0; i < categoryList.size(); i++) {
                a[i][0] = categoryList.get(i).getId();
                a[i][1] = categoryList.get(i).getCategoryName();
            }
        }
        String[] names = {"序号", "分类名称"};
        categoryTablePanel.removeAll();

        JTable table = new JTable(a, names);
        table.setBounds(0, 0, 446, 282);
        categoryTablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * 初始化用户列表
     *
     * @param userTablePanel
     * @param userName
     */
    public void initUserTablePanel(JPanel userTablePanel, String userName) {
        ArrayList<Users> usersList;
        if ("".equals(userName)) {
            // 查询所有用户
            usersList = usersDaoImpl.getForList(
                    CONN,
                    "select * from tb_users where userType = ?",
                    UserTypeEnum.ORDINARY.name()
            );
        } else {
            // 根据用户名模糊匹配
            usersList = usersDaoImpl.getForList(
                    CONN,
                    "select * from tb_users where userType = ? and userName like '%" + userName + "%'",
                    UserTypeEnum.ORDINARY.name()
            );
        }

        Object[][] a = new Object[usersList.size()][6];

        for (int i = 0; i < usersList.size(); i++) {
            a[i][0] = usersList.get(i).getId();
            a[i][1] = usersList.get(i).getUserName();
            a[i][2] = usersList.get(i).getPhone();
            a[i][3] = usersList.get(i).getNickName();
            a[i][4] = usersList.get(i).getName();
            a[i][5] = usersList.get(i).getCreateTime();
        }

        String[] names = {"序号", "账号", "电话", "昵称", "姓名", "创建时间"};
        userTablePanel.removeAll();

        JTable table = new JTable(a, names);
        userTablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * 初始化进货记录表
     *
     * @param purchaseRecordsTablePanel
     */
    public void initPurchaseRecordsTabel(JPanel purchaseRecordsTablePanel) {
        // 直接查询所有的进货记录
        ArrayList<StockGoods> stockGoodsList = stockGoodsDaoImpl.getForList(
                CONN,
                "select * from tb_stock_goods"
        );
        Object[][] a = new Object[stockGoodsList.size()][12];
        for (int i = 0; i < stockGoodsList.size(); i++) {
            a[i][0] = stockGoodsList.get(i).getId();
            a[i][1] = stockGoodsList.get(i).getCategoryName();
            a[i][2] = stockGoodsList.get(i).getProductName();
            a[i][3] = stockGoodsList.get(i).getBuyer();
            a[i][4] = stockGoodsList.get(i).getBuyerPhone();
            a[i][5] = stockGoodsList.get(i).getProductNum();
            a[i][6] = stockGoodsList.get(i).getProductPrice();
            a[i][7] = stockGoodsList.get(i).getTotalPurchasePrice();
            a[i][8] = stockGoodsList.get(i).getPurchasingManufacturer();
            a[i][9] = stockGoodsList.get(i).getManufacturerPhone();
            a[i][10] = stockGoodsList.get(i).getManufacturerAddress();
            a[i][11] = stockGoodsList.get(i).getPurchaseInstructions();
        }

        String[] names = {"序号", "分类名称", "商品名", "进货人", "进货人联系方式", "商品数量", "商品价格",
                "商品总价", "进货厂商", "厂商电话", "厂商地址", "进货说明"};
        purchaseRecordsTablePanel.removeAll();
        JTable table = new JTable(a, names);
        purchaseRecordsTablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * 初始化商品管理面板
     *
     * @param productTablePanel
     * @param categoryName
     * @param productName
     */
    public JTable initProductTablePanel(JPanel productTablePanel, String categoryName, String productName, JTable table) {
        ArrayList<ProductVo> productVoList;
        if ("".equals(categoryName)) {
            // 查询所有
            productVoList = productVoDaoImpl.getForList(
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
                            "\ttpi.isShelf = 1"
            );
        } else {
            // 条件查询
            productVoList = productVoDaoImpl.getForList(
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
                            "\ttpi.isShelf = 1 and tpi.productCategory = ? and tpi.productName like '%" + productName + "%'",
                    categoryName
            );
        }

        Object[][] a = new Object[productVoList.size()][10];
        for (int i = 0; i < productVoList.size(); i++) {
            a[i][0] = productVoList.get(i).getId();
            a[i][1] = productVoList.get(i).getProductCategory();
            a[i][2] = productVoList.get(i).getProductName();
            a[i][3] = productVoList.get(i).getManufacturer();
            a[i][4] = productVoList.get(i).getProductNum();
            a[i][5] = productVoList.get(i).getProductBuyer();
            a[i][6] = productVoList.get(i).getIsShelf() == 1 ? "上架" : "下架";
            a[i][7] = productVoList.get(i).getPrice();
            a[i][8] = productVoList.get(i).getDiscount();
            a[i][9] = productVoList.get(i).getProductStatus();
        }

        String[] names = {"序号", "分类名称", "商品名", "厂商",  "商品数量", "商品进价",
                "是否上架", "销售价格", "销售折扣", "商品状态"};
        productTablePanel.removeAll();
        table = new JTable(a, names);
        productTablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        return table;
    }
}
