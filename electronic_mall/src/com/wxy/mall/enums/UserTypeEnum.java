package com.wxy.mall.enums;

import lombok.Getter;

/**
 * @author 魏心怡
 * @version 1.0.0
 * @date 2023/5/30
 */
@Getter
public enum UserTypeEnum {

    ADMIN("管理员"),
    ORDINARY("普通用户");

    private String msg;

    UserTypeEnum() {
    }

    UserTypeEnum(String msg) {
        this.msg = msg;
    }

    /**
     * 重写toString方法，返回我们想要的数据(名字)。
     * @return
     */
    @Override
    public String toString() {
        return this.msg;
    }
}
