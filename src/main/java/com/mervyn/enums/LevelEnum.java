package com.mervyn.enums;

/**
 * @author: mervynlam
 * @Title: LevelEnum
 * @Description:
 * @date: 2022/4/17 13:22
 */
public enum LevelEnum {
    BEG(0, "beginner"),
    INT(1, "intermediate"),
    EXP(2, "expert"),
    CUS(3, "custom"),
    ;

    private Integer code;
    private String label;

    public Integer getCode() {
        return code;
    }

    LevelEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    public static LevelEnum getByCode(Integer code) {
        for (LevelEnum e : LevelEnum.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }

    public static String getLabelByCode(Integer code) {
        LevelEnum e = getByCode(code);
        if (e != null) {
            return e.label;
        }
        return "";
    }
}
