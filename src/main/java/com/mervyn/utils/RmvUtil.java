package com.mervyn.utils;

import com.mervyn.entity.Event;
import com.mervyn.entity.Game;
import com.mervyn.enums.EventEnum;
import com.mervyn.enums.EventTypeEnum;

import java.util.ArrayList;
import java.util.List;

import static com.mervyn.utils.CommonUtil.error;

/**
 * @author: mervynlam
 * @Title: RmvUtil
 * @Description:
 * @date: 2022/4/16 22:24
 */
public class RmvUtil {

    private static final String HEADER = "*rmv";
    private static final Integer BUFF = 0xff;
    private static final Integer SQUARE_SIZE = 16;
    private static int offset = 0x00;

    public static Game analyzeVideo(byte[] buffer) {
        //Check first 4 bytes of header is *rmv
        for (int i = 0; i < 4; ++i) {
            if (buffer[offset++] != HEADER.charAt(i)) {
                error("No RMV header", true);
            }
        }

        //The getint2 function reads 2 bytes at a time
        //In legitimate videos byte 4=0 and byte 5=1, getint2 sum is thus 1
        if(getInt(2,buffer)!=1) error("Invalid video type", true);

        String version;
        Long gameTime;
        Integer width;
        Integer height;
        Integer mines;
        Integer questionMark;
        Integer level;
        Integer mode;
        Integer bbbv = 0;
        Integer score = 0;
        Double bbbvs = 0.0;
        Integer noFlagging;
        Integer status;
        String playerName;
        int[] board;
        int[] preFlag;
        List<Event> eventList = new ArrayList<>();
        boolean isFirstEvent = true;

        //The getint functions reads 4 bytes at a time
        int filesize=getInt(4,buffer); 					//Gets byte 6-9
        int result_str_size=getInt(2,buffer);       	//Gets bytes 10-11
        int version_info_size=getInt(2,buffer);     	//Gets bytes 12-13
        int player_info_size=getInt(2,buffer); 	    	//Gets bytes 14-15
        int board_size=getInt(2,buffer);       	    	//Gets bytes 16-17
        int preflagged_size=getInt(2,buffer);   		//Gets bytes 18-19
        int properties_size=getInt(2,buffer);  	    	//Gets bytes 20-21
        int vid_size=getInt(4,buffer);                  	//Gets bytes 22-25
        int checksum_size=getInt(2,buffer);             //Gets bytes 26-27
        getInt(1,buffer);						            //Gets byte 28 which is a newline


        //Length of result_string_size starts 3 bytes before 'LEVEL' and ends on the '#' before Version
        //Version 2.2 was first to have a full length header
        //Earlier versions could have maximum header length of 35 bytes if Intermediate and 9999.99
        //This means it is Version 2.2 or later so we want to parse more of the header
        String resultString = new String(buffer, offset, result_str_size-1);
        //get bbbv
        try {
            String[] baseInfo = resultString.split("#");
            String bbbvStr = baseInfo[4].split(":")[1];
            bbbv = Integer.valueOf(bbbvStr);
        } catch (Exception e) {
            error("get bbbv error", true);
        }

        offset += result_str_size-1;
        version = new String(buffer, offset, version_info_size);
        offset += version_info_size;

        //Check next two bytes to see if player entered Name
        int num_player_info=getInt(2,buffer);

        //Fetch Player fields (name, nick, country, token) if they exist
        //These last 3 fields were defined in Viennasweeper 3.1 RC1
        int nameLength = getInt(1,buffer);
        playerName = new String(buffer, offset, nameLength);
        offset+=nameLength;

        if(num_player_info>1)
        {
            int length = getInt(1,buffer);
            String nick = new String(buffer, offset, length);
            offset+=length;
        }
        if(num_player_info>2)
        {
            int length = getInt(1,buffer);
            String country = new String(buffer, offset, length);
            offset+=length;
        }
        if(num_player_info>3)
        {
            int length = getInt(1,buffer);
            String token = new String(buffer, offset, length);
            offset+=length;
        }

        //Throw away next 4 bytes
        gameTime = Long.valueOf(getInt(4,buffer));

        //Get board size and Mine details
        width=getInt(1,buffer); 		//Next byte is w so 8, 9 or 1E
        height=getInt(1,buffer); 		//Next byte is h so 8, 9 or 10
        mines=getInt(2,buffer); 	//Next two bytes are number of mines
        /*
        *
        *
        for _ in range(self.num_mines):
            col = ord(data.read(1))
            row = ord(data.read(1))
            self.mines.append((row, col))
        */
        board = new int[width*height];
        for (int i = 0; i < mines; ++i) {
            int col = getInt(1, buffer);
            int row = getInt(1, buffer);
            board[row*width+col] = 1;
        }

        /*
        *
        # preflagged
        self.preflags = []
        if preflagged_size:
            num_preflags = self.read_int(data.read(2))
            for _ in range(num_preflags):
                col = ord(data.read(1))
                row = ord(data.read(1))
                self.preflags.append((row, col))
        * */

        //Check number of flags placed before game started
        if (preflagged_size > 0) {
            int preFlagNum = getInt(2,buffer);
            for (int i = 0; i < preFlagNum; ++i) {
                int col = getInt(1, buffer);
                int row = getInt(1, buffer);

                //preFlag Event
                Event rc = new Event(0L, EventTypeEnum.MOUSE.getCode(), EventEnum.RMB_DOWN.getCode(), col * SQUARE_SIZE + SQUARE_SIZE / 2, row * SQUARE_SIZE + SQUARE_SIZE / 2);
                Event rr = new Event(0L, EventTypeEnum.MOUSE.getCode(), EventEnum.RMB_UP.getCode(), col * SQUARE_SIZE + SQUARE_SIZE / 2, row * SQUARE_SIZE + SQUARE_SIZE / 2);
                eventList.add(rr);
                eventList.add(rc);
            }
        }

        questionMark = getInt(1, buffer);           //Value 1 if Questionmarks used, otherwise 0
        noFlagging = getInt(1, buffer);         //Value 1 if no Flags were used, otherwise 0
        mode = getInt(1, buffer);           //Value 0 for Classic, 1 UPK, 2 Cheat, 3 Density
        level = getInt(1, buffer);          //Value 0 for Beg, 1 Int, 2 Exp, 3 Custom
//        Throw away rest of properties
        for(int i=4;i<properties_size;++i) {
            getInt(1, buffer);
        }

        while (true) {
            int eventCode = getInt(1, buffer);
            if (eventCode == 0) {
                //Get next 4 bytes containing time of event
                getInt(4, buffer);
            } else if(1 <= eventCode && eventCode <= 7) {
                //Get mouse event (3 bytes time, 1 wasted, 2 width, 2 height)
                int time = getInt(3,buffer);
                getInt(1,buffer);
                int x = getInt(2,buffer)-12;
                int y = getInt(2,buffer)-56;
                Event event = new Event(Long.valueOf(time), EventTypeEnum.MOUSE.getCode(), eventCode, x, y);

                //Viennasweeper does not record clicks before timer starts
                //LR starts timer so the first LC is missed in the video file
                //This code generates the missing LC in that case
                //In other cases it generates a ghost event thus event[0] is empty
                if (isFirstEvent) {
                    isFirstEvent = false;
                    event.setEvent(EventEnum.LMB_DOWN.getCode());
                    Event eventCopy = new Event(Long.valueOf(time), EventTypeEnum.MOUSE.getCode(), eventCode, x, y);
                    eventList.add(event);
                    eventList.add(eventCopy);
                } else {
                    eventList.add(event);
                }
            } else if (eventCode == 8) {
                error("Invalid event");
            } else if (eventCode<=14 || (eventCode>=18 && eventCode<=27)){
                //Get board event (ie, 'pressed' or 'number 3')
                int x = getInt(1,buffer);
                int y = getInt(1,buffer);
                Event event = new Event(null, EventTypeEnum.BOARD.getCode(), eventCode, x, y);
                eventList.add(event);
            } else if (eventCode<=17) {
                //Get game status (ie, 'won')
                status = eventCode;
                Event event = new Event(null, EventTypeEnum.END.getCode(), eventCode, null, null);
                eventList.add(event);
                break;
            } else {
                error("Invalid event");
            }
        }
        score = getInt(3, buffer);
        bbbvs = (((bbbv*1000000)/(score))*1.0)/1000;
        return new Game(version, gameTime, width, height, mines, questionMark, level, mode, bbbv, score, bbbvs, noFlagging, status, playerName, board, eventList);
    }

    public static int getIntBase(byte[] buffer) {
        if (offset< buffer.length) {
            byte b = buffer[offset++];
            return b & BUFF;
        } else {
            error("Error 4: Unexpected end of file", true);
            return -1;
        }
    }
    public static int getInt(int length, byte[] buffer) {
        int res = 0;
        for (int i = 0; i < length; ++i) {
            res <<= 8;
            res += getIntBase(buffer);
        }
        return res;
    }

    public static void main(String[] args) {
        byte[] bytes = CommonUtil.readFile("D:\\github\\RmvParser\\src\\main\\resources\\exp_369_NF_1650126255.rmv");
//        byte[] bytes = CommonUtil.readFile("D:\\github\\RmvParser\\src\\main\\resources\\beg_4911_FL_1650118200.rmv");
//        byte[] bytes = CommonUtil.readFile("D:\\github\\RmvParser\\src\\main\\resources\\beg_1227_NF_1650166366.rmv");
//        byte[] bytes = CommonUtil.readFile("D:\\github\\RmvParser\\src\\main\\resources\\exp_99201_FL_1650122593.rmv");
//        byte[] bytes = CommonUtil.readFile("D:\\github\\RmvParser\\src\\main\\resources\\beg_7991_NF_1650167676.rmv");
        Game game = RmvUtil.analyzeVideo(bytes);
        System.out.println(game.toString());
    }
}
