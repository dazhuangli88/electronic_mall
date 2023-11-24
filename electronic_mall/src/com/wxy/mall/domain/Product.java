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
public class Product {

    /**
     * 主键Id
     */
    private Integer id;
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
