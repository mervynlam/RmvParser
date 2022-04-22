package com.mervyn.utils;

import com.mervyn.enums.CellTypeEnum;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author: mervynlam
 * @Title: BoardUtil
 * @Description:
 * @date: 2022/4/22 21:55
 */
public class BoardUtil {

    public static void printBoard(int[] board, int width) {
        for (int i = 0; i < board.length; ++i) {
            if (i % width == 0) System.out.println();
            System.out.print(board[i]+" ");
        }
        System.out.println();
    }

    //return number of bbbv on this time, 0 or 1
    public static int openCell(int[] board, int[] flag, int x, int y, int width) {
        int index = y*width + x;
        // has already flagged
        if (flag[index] == 1) return 0;
        if (board[index] == 0) {
            //is open
            flagOpen(board, flag, x, y, width);
            return 1;
        } else if (board[index] == 9) {
            //is mine
            flag[index] = 1;
            return 0;
        } else {
            //is number
            int open = getArroundNums(board, width, x, y, CellTypeEnum.OPEN.getCode());
            flag[index] = 1;
            if (open > 0) return 0;     //edge of open
            else return 1;              //1 bbbv
        }
    }

    //bfs
    public static void flagOpen(int[] board, int[] flag, int x, int y, int width) {
        Queue<Integer> queue = new ArrayDeque<>();
        int index = y*width + x;
        //8 directions
        int[] arround = new int[]{(width*-1)-1, width*-1,(width*-1)+1,
                -1, 1,
                width-1, width, width+1};
        //flag current index
        flag[index] = 1;
        queue.add(index);
        while(!queue.isEmpty()) {
            int curIndex = queue.poll();
            int curX = curIndex % width;
            int curY = curIndex / width;
            for (int i = 0; i < arround.length; ++i) {
                int dirIndex = curIndex + arround[i];
                int xDiff = Math.abs(dirIndex%width-curX);
                int yDiff = Math.abs(dirIndex/width-curY);
                if (0 <= dirIndex && dirIndex < board.length && xDiff <= 1 && yDiff <= 1 && flag[dirIndex] == 0) {
                    // has not flagged yet and within the scope and neighbor to current index
                    if (board[dirIndex] == 0) {
                        //is open
                        flag[dirIndex] = 1;
                        queue.add(dirIndex);
                    } else {
                        //is number
                        flag[dirIndex] = 1;
                    }
                }
            }
        }
    }

    public static void getBoardMap(int[] board, int width) {
        for (int i = 0; i < board.length; ++i) {
            if (board[i] == 9) {
                continue;
            }
            int x = i % width;
            int y = i / width;
            int mines = getArroundNums(board, width, x, y, CellTypeEnum.MINE.getCode());
            board[i] = mines;
        }
    }

    public static int getArroundNums(int[] board, int width, int x, int y, int target) {
        int index = y*width + x;
        int count = 0;
        //8 directions
        int[] arround = new int[]{(width*-1)-1, width*-1,(width*-1)+1,
                -1, 1,
                width-1, width, width+1};
        for (int i = 0; i < arround.length; ++i) {
            int dirIndex = index + arround[i];
            int xDiff = Math.abs(dirIndex%width-x);
            int yDiff = Math.abs(dirIndex/width-y);
            if (0 <= dirIndex && dirIndex < board.length && xDiff <= 1 && yDiff <= 1) {
                count += board[dirIndex] == target?1:0;
            }
        }
        return count;
    }
}
