package com.mervyn.enums;

/**
 * @author: mervynlam
 * @Title: StatusEnum
 * @Description:
 * @date: 2022/4/17 13:22
 */
public enum StatusEnum {
    BLAST(15, "blast"),
    WIN(16, "win"),
    OTHER(17, "other"),
    ;

    private Integer code;
    private String label;

    public Integer getCode() {
        return code;
    }

    StatusEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    public static StatusEnum getByCode(Integer code) {
        for (StatusEnum e : StatusEnum.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }

    public static String getLabelByCode(Integer code) {
        StatusEnum e = getByCode(code);
        if (e != null) {
            return e.label;
        }
        return "";
    }
}
