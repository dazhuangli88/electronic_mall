package com.wxy.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品分类
 *
 * @author 魏心怡
 * @version 1.0.0
 * @date 2023/6/4
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {

    /**
     * 主键Id
     */
    private Integer id;
    /**
     * 商品分类的名字
     */
    private String categoryName;
}
