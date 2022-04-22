package com.mervyn;

import com.mervyn.entity.Game;
import com.mervyn.utils.CommonUtil;
import com.mervyn.utils.RmvUtil;

/**
 * @author: mervynlam
 * @Title: Main
 * @Description:
 * @date: 2022/4/17 20:41
 */
public class Main {

    public static void main(String[] args) {
//        byte[] bytes = CommonUtil.readFile("C:\\Users\\Mervyn\\Downloads\\Huang Yi Chao_Int_13.475(3bv39).rmv");
//        byte[] bytes = CommonUtil.readFile("D:\\github\\RmvParser\\src\\main\\resources\\exp_369_NF_1650126255.rmv");
//        byte[] bytes = CommonUtil.readFile("D:\\github\\RmvParser\\src\\main\\resources\\beg_4911_FL_1650118200.rmv");
//        byte[] bytes = CommonUtil.readFile("D:\\github\\RmvParser\\src\\main\\resources\\beg_6888_FL_1650200391.rmv");
        byte[] bytes = CommonUtil.readFile("D:\\github\\RmvParser\\src\\main\\resources\\beg_3636_NF_1650638692.rmv");
//        byte[] bytes = CommonUtil.readFile("D:\\github\\RmvParser\\src\\main\\resources\\beg_1227_NF_1650166366.rmv");
//        byte[] bytes = CommonUtil.readFile("D:\\github\\RmvParser\\src\\main\\resources\\exp_99201_FL_1650122593.rmv");
//        byte[] bytes = CommonUtil.readFile("D:\\github\\RmvParser\\src\\main\\resources\\beg_7991_NF_1650167676.rmv");
        Game game = RmvUtil.analyzeVideo(bytes);
        System.out.println(game.toString());
    }
}
