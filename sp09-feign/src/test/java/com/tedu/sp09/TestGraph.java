package com.tedu.sp09;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestGraph {

    private static Color BACK_GROUND_COLOR = new Color(238, 238, 238);

    private static int IMG_WIDTH = 240;

    private static int IMG_HEIGHT = 240;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //得到图片缓冲区
        BufferedImage bi = new BufferedImage

                (IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);

        //得到它的绘制环境(这张图片的笔)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();

        g2.setBackground(BACK_GROUND_COLOR);
        g2.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);//填充一个矩形 左上角坐标(0,0),宽70,高150;填充整张图片
        //设置颜色
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 150, 70);//填充整张图片(其实就是设置背景颜色)


        g2.setColor(Color.BLACK);//设置背景颜色

        g2.drawString("HelloWorld", 3, 50); //向图片上写字符串
        ImageIO.write(bi,"JPG", new FileOutputStream("D://"+System.currentTimeMillis()+".jpg"));//保存图片 JPEG表示保存格式

    }

}
