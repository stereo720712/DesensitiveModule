package com.common.desensitive.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sean
 */

@Getter
@AllArgsConstructor
public enum SensitiveType {

    DEFAULT("不处里"),
    /**
     * 身份证编号
     */
    ID_CARD("身份证"),

    /**
     * 地址/住址
     */
    ADDRESS("地址"),

    /**
     * 姓名
     */
    NAME("姓名"),

    /**
     * 手机号
     */
    PHONE("手机号"),

    /**
     * 手机号
     */
    EMAIL("邮箱"),

    /**
     * 银行卡号
     */
    BANK_CARD_NO("银行卡号"),

    /**
     * 存币地址
     */
    RECHARGE_ADDRESS("存币地址");


    private String desc;
}
