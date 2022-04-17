package com.mervyn.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.System.exit;

/**
 * @author: mervynlam
 * @Title: CommonUtil
 * @Description:
 * @date: 2022/4/16 22:17
 */
public class CommonUtil {
    public static byte[] readFile(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            long len = file.length();
            byte[] ret = new byte[(int) len];
            fis.read(ret);
            return ret;
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] readFile(String filePath) {
        return CommonUtil.readFile(new File(filePath));
    }

    public static void error(String msg, Boolean isExit){
        error(msg);
        if (isExit) exit(1);
    }

    public static void error(String msg){
        System.out.println(msg);
    }

    public static String formatTime(Long timestamp) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.setTimeInMillis(timestamp*1000);
        return sdf.format(calendar.getTime());
    }
}
