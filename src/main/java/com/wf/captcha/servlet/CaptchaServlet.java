package com.wf.captcha.servlet;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.Serial;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;

/**
 * 验证码servlet
 * Created by 王帆 on 2018-07-27 上午 10:08.
 */
public class CaptchaServlet extends HttpServlet {

    // serialVersionUID
    @Serial
    private static final long serialVersionUID = -90304944339413093L;

    /**
     * doGet
     *
     * @param request  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // SpecCaptcha captcha = new SpecCaptcha(130, 48, 6);
        GifCaptcha captcha = new GifCaptcha(130, 48, 6);

        // 设置内置字体
        try {
            captcha.setFont(Captcha.FONT_10);
        } catch (FontFormatException e) {
            e.fillInStackTrace();
        }
        CaptchaUtil.out(captcha, request, response);
    }

    /**
     * doPost
     *
     * @param request  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
