package com.mervyn.enums;

/**
 * @author: mervynlam
 * @Title: StatusEnum
 * @Description:
 * @date: 2022/4/17 13:22
 */
public enum EventTypeEnum {
    MOUSE(1, "mouse"),
    BOARD(2, "board"),
    END(3, "end"),
    ;

    private Integer code;
    private String label;

    public Integer getCode() {
        return code;
    }

    EventTypeEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    public static EventTypeEnum getByCode(Integer code) {
        for (EventTypeEnum e : EventTypeEnum.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }

    public static String getLabelByCode(Integer code) {
        EventTypeEnum e = getByCode(code);
        if (e != null) {
            return e.label;
        }
        return "";
    }
}
