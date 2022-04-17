package com.mervyn.entity;

import com.mervyn.enums.EventTypeEnum;
import com.mervyn.enums.LevelEnum;
import com.mervyn.enums.ModeEnum;
import com.mervyn.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import com.mervyn.utils.CommonUtil;

/**
 * @author: mervynlam
 * @Title: Game
 * @Description:
 * @date: 2022/4/17 13:05
 */
@Data
@AllArgsConstructor
public class Game {
    private String version;
    private Long gameTime;

    private Integer width;
    private Integer height;
    private Integer mines;

    private Integer questionMark;
    private Integer level;
    private Integer mode;
    private Integer bbbv;
    private Integer score;
    private Double bbbvs;
    private Integer noFlagging;
    private Integer status;

    private String playerName;

    private int[] board;
    private List<Event> eventList;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Version: "+ version);
        stringBuilder.append("\nPlayer: "+ playerName);
        stringBuilder.append("\nLevel: "+ LevelEnum.getLabelByCode(level));
        stringBuilder.append("\nWidth: "+ width);
        stringBuilder.append("\nHeight: "+ height);
        stringBuilder.append("\nMines: "+ mines);
        if (questionMark == 0) stringBuilder.append("\nMarks: Off");
        else stringBuilder.append("\nMarks: On");
        stringBuilder.append("\nTime: "+ String.format("%d.%3d", score / 1000, score % 1000));
        stringBuilder.append("\nGameStatus: "+ StatusEnum.getLabelByCode(status));
        stringBuilder.append("\nBBBV: "+ bbbv);
        stringBuilder.append("\nBBBVS: "+ bbbvs);
        stringBuilder.append("\nTimestamp: "+ gameTime);
        stringBuilder.append("\nFormatTime: "+ CommonUtil.formatTime(gameTime));
        stringBuilder.append("\nMode: "+ ModeEnum.getLabelByCode(mode));
        if (noFlagging == 0) stringBuilder.append("\nStyle: FL");
        else stringBuilder.append("\nStyle: NF");
        stringBuilder.append("\nBoard: ");
        for (int i = 0; i < board.length; ++i) {
            if (i % width == 0) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(board[i]+" ");
        }
        stringBuilder.append("\nEvent: ");
        stringBuilder.append("\n0.000 start");
        for(Event event : eventList) {
            stringBuilder.append(event.toString());
        }
        return stringBuilder.toString();
    }
}
