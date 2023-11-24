package com.wxy.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 魏心怡
 * @version 1.0.0
 * @date 2023/6/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductVo {


    /**
     * 主键id
     */
    private Integer id;
    /**
     * 商品分类
     */
    private String productCategory;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 厂商
     */
    private String manufacturer;
    /**
     * 商品数量
     */
    private Integer productNum;
    /**
     * 商品进价
     */
    private Double productBuyer;
    /**
     * 是否上架(0：待上架    1：已上架    2：已下架)
     */
    private Integer isShelf;

    /**
     * 库存表Id
     */
    private Integer productInventoryId;
    /**
     * 销售价格
     */
    private Double price;
    /**
     * 商品折扣
     */
    private Double discount;
    /**
     * 商品状态   正常/降价
     */
    private String productStatus;
}
