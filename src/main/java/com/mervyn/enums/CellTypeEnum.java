package com.mervyn.enums;

/**
 * @author: mervynlam
 * @Title: CellTypeEnum
 * @Description:
 * @date: 2022/4/17 13:22
 */
public enum CellTypeEnum {
    OPEN(0, "open"),
    NUMBER_1(1, "number_1"),
    NUMBER_2(2, "number_2"),
    NUMBER_3(3, "number_3"),
    NUMBER_4(4, "number_4"),
    NUMBER_5(5, "number_5"),
    NUMBER_6(6, "number_6"),
    NUMBER_7(7, "number_7"),
    NUMBER_8(8, "number_8"),
    MINE(9, "mine"),
    ;

    private Integer code;
    private String label;

    public Integer getCode() {
        return code;
    }

    CellTypeEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    public static CellTypeEnum getByCode(Integer code) {
        for (CellTypeEnum e : CellTypeEnum.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }

    public static String getLabelByCode(Integer code) {
        CellTypeEnum e = getByCode(code);
        if (e != null) {
            return e.label;
        }
        return "";
    }
}
