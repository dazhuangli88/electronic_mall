package com.wxy.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 魏心怡
 * @version 1.0.0
 * @date 2023/6/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockGoods {

    /**
     * 主键Id
     */
    private Integer id;
    /**
     * 商品分类的名字
     */
    private String categoryName;
    /**
     * 商品名
     */
    private String productName;
    /**
     * 进货人
     */
    private String buyer;
    /**
     * 进货人联系方式
     */
    private String buyerPhone;
    /**
     * 商品数量
     */
    private Integer productNum;
    /**
     * 商品价格
     */
    private Double productPrice;
    /**
     * 商品总结
     */
    private Double totalPurchasePrice;
    /**
     * 进货厂商
     */
    private String purchasingManufacturer;
    /**
     * 厂商电话
     */
    private String manufacturerPhone;
    /**
     * 厂商地址
     */
    private String manufacturerAddress;
    /**
     * 进货说明
     */
    private String purchaseInstructions;
}
