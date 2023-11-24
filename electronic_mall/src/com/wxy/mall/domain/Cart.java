package com.wxy.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 魏心怡
 * @version 1.0.0
 * @date 2023/6/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private Integer id;
    private Integer userId;
    private String productCategory;
    private String productName;
    private String factory;
    private Double priceSale;
    private Double discount;
    private Integer buyNum;
    private Double totalPrice;
    private String phone;
    private String address;
}
