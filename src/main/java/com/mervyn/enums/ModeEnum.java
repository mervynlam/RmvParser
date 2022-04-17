package com.mervyn.enums;

/**
 * @author: mervynlam
 * @Title: ModeEnum
 * @Description:
 * @date: 2022/4/17 13:22
 */
public enum ModeEnum {
    NORMAL(0, "normal"),
    UPK(1, "UPK"),
    CHEAT(2, "cheat"),
    DENSITY(3, "density"),
    ;

    private Integer code;
    private String label;

    public Integer getCode() {
        return code;
    }

    ModeEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    public static ModeEnum getByCode(Integer code) {
        for (ModeEnum e : ModeEnum.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }

    public static String getLabelByCode(Integer code) {
        ModeEnum e = getByCode(code);
        if (e != null) {
            return e.label;
        }
        return "";
    }
}
