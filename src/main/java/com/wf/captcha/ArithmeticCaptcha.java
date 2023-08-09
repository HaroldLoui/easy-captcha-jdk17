package com.wf.captcha;

import com.wf.captcha.base.ArithmeticCaptchaAbstract;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 算数类型png格式验证码
 * Created by 王帆 on 2018-07-27 上午 10:08.
 */
public class ArithmeticCaptcha extends ArithmeticCaptchaAbstract {

    /**
     * 构造函数
     */
    public ArithmeticCaptcha() {
    }

    /**
     * 构造函数
     *
     * @param width  验证码宽度
     * @param height 验证码高度
     */
    public ArithmeticCaptcha(int width, int height) {
        this();
        setWidth(width);
        setHeight(height);
    }

    /**
     * 构造函数
     *
     * @param width  验证码宽度
     * @param height 验证码高度
     * @param len    验证字符长度
     */
    public ArithmeticCaptcha(int width, int height, int len) {
        this(width, height);
        setLen(len);
    }

    /**
     * 构造函数
     *
     * @param width  验证码宽度
     * @param height 验证码高度
     * @param len    验证字符长度
     * @param font   验证码字体类型
     */
    public ArithmeticCaptcha(int width, int height, int len, Font font) {
        this(width, height, len);
        setFont(font);
    }

    /**
     * 生成验证码
     *
     * @param out 输出流
     * @return 是否成功
     */
    @Override
    public boolean out(OutputStream out) {
        checkAlpha();
        return graphicsImage(getArithmeticString().toCharArray(), out);
    }

    /**
     * 输出base64编码，默认类型：data:image/png;base64,
     *
     * @return base64编码字符串
     */
    @Override
    public String toBase64() {
        return toBase64("data:image/png;base64,");
    }

    /**
     * 生成验证码图形
     *
     * @param strs 验证码
     * @param out  输出流
     * @return boolean
     */
    private boolean graphicsImage(char[] strs, OutputStream out) {
        try {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bi.getGraphics();
            // 填充背景
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            // 抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 画干扰圆
            drawOval(2, g2d);
            // 画字符串
            g2d.setFont(getFont());
            FontMetrics fontMetrics = g2d.getFontMetrics();
            // 每一个字符所占的宽度
            int fW = width / strs.length;
            // 字符的左右边距
            int fSp = (fW - (int) fontMetrics.getStringBounds("8", g2d).getWidth()) / 2;
            for (int i = 0; i < strs.length; i++) {
                g2d.setColor(color());
                // 文字的纵坐标
                int fY = height - ((height - (int) fontMetrics.getStringBounds(String.valueOf(strs[i]), g2d).getHeight()) >> 1);
                g2d.drawString(String.valueOf(strs[i]), i * fW + fSp + 3, fY - 3);
            }
            g2d.dispose();
            ImageIO.write(bi, "png", out);
            out.flush();
            return true;
        } catch (IOException e) {
            e.fillInStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        }
        return false;
    }
}