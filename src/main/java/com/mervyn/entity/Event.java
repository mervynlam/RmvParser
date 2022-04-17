package com.mervyn.entity;

import com.mervyn.enums.EventEnum;
import com.mervyn.enums.EventTypeEnum;
import com.mervyn.enums.StatusEnum;
import com.mervyn.utils.RmvUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: mervynlam
 * @Title: Event
 * @Description:
 * @date: 2022/4/17 13:54
 */
@Data
@AllArgsConstructor
public class Event {
    Long time;
    Integer type;
    Integer event;
    Integer x;
    Integer y;

    @Override
    public String toString() {
        final Integer SQUARE_SIZE = 16;
        StringBuilder stringBuilder = new StringBuilder();
        switch (EventTypeEnum.getByCode(this.type)) {
            case MOUSE:
                stringBuilder.append("\n"+String.format("%d.%03d %s %d %d (%d %d)", time/1000, time%1000, EventEnum.getLabelByCode(event), x/SQUARE_SIZE, y/SQUARE_SIZE, x, y));
                break;
            case BOARD:
                stringBuilder.append("\n"+EventEnum.getLabelByCode(event)+" " + x +" "+ y);
                break;
            case END:
                stringBuilder.append("\n"+StatusEnum.getLabelByCode(event));
                break;
        }
        return stringBuilder.toString();
    }
}
