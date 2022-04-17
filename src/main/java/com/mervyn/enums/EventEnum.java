package com.mervyn.enums;

/**
 * @author: mervynlam
 * @Title: StatusEnum
 * @Description:
 * @date: 2022/4/17 13:22
 */
public enum EventEnum {
    MOVE(1, "move"),
    LMB_DOWN(2, "lmb_down"),
    LMB_UP(3, "lmb_up"),
    RMB_DOWN(4, "rmb_down"),
    RMB_UP(5, "rmb_up"),
    MMB_DOWN(6, "mmb_down"),
    MMB_UP(7, "mmb_up"),
    PRESSED(9, "pressed"),
    PRESSED_QM(10, "pressed_qm"),
    CLOSED(11, "closed"),
    QM(12, "qm"),
    FLAG(13, "flag"),
    OPEN(14, "open"),
    OPEN_0(18, "open_0"),
    OPEN_1(19, "open_1"),
    OPEN_2(20, "open_2"),
    OPEN_3(21, "open_3"),
    OPEN_4(22, "open_4"),
    OPEN_5(23, "open_5"),
    OPEN_6(24, "open_6"),
    OPEN_7(25, "open_7"),
    OPEN_8(26, "open_8"),
    OPEN_BLAST(27, "open_blast"),
    ;

    private Integer code;
    private String label;

    public Integer getCode() {
        return code;
    }

    EventEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    public static EventEnum getByCode(Integer code) {
        for (EventEnum e : EventEnum.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }

    public static String getLabelByCode(Integer code) {
        EventEnum e = getByCode(code);
        if (e != null) {
            return e.label;
        }
        return "";
    }
}
