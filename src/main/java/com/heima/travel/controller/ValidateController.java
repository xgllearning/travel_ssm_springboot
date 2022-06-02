package com.heima.travel.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * 验证类
 * @author laofang
 * @description
 * @date 2021-06-19
 */
@Controller
@RequestMapping("")
public class ValidateController {
    //因为图片是以流的形式返回，因此不需要返回值，只需要用到ImageIO.write
    @GetMapping("/checkCode")
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //服务器通知浏览器不要缓存，因为图片中的字是变化的，如果缓存了响应的数据，则点击不会进行切换
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");

        //在内存中创建一个长80，宽30的图片对象，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //获取画笔对象
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色，然后用画笔颜色去填充图片
        g.setColor(Color.GRAY);
        //填充图片(x轴，y轴，宽，高)填充，0，0说明全部填充
        g.fillRect(0, 0, width, height);
        //生成随机码:生成含有数字和字母的5位长度的随机数
        //RandomStringUtils是org.apache.commons.langs提供，randomAlphanumeric(int)有数字有字母，并可指定长度
        String checkCode = RandomStringUtils.randomAlphanumeric(5);
//将随机的验证码放入HttpSession中，并起个名字，取的时候需要根据name取
        request.getSession().setAttribute("CHECKCODE_SERVER", checkCode);

        //设置画笔颜色为黄色，因为之前图片背景是灰色，所以不能相同，需要给随机码设置个颜色，以便能够显示在图片上
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体", Font.BOLD, 24));
        //通过画笔对象向图片上写入随机的验证码，参数一为随机数对象，参数二三为随机数显示在图片上的坐标
        g.drawString(checkCode, 15, 25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到浏览器，响应输出getOutputStream()..
        ImageIO.write(image, "PNG", response.getOutputStream());
    }
}
