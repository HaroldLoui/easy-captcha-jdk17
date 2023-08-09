package com.wf.captcha.base;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 算术验证码抽象类
 * Created by 王帆 on 2019-08-23 上午 10:08.
 */
public abstract class ArithmeticCaptchaAbstract extends Captcha {

    /**
     * 计算公式
     */
    private String arithmeticString;

    /**
     * 构造函数
     */
    public ArithmeticCaptchaAbstract() {
        setLen(2);
    }

    /**
     * 生成随机验证码
     *
     * @return 验证码字符数组
     */
    @Override
    protected char[] alphas() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(num(10));
            if (i < len - 1) {
                int type = num(1, 4);
                if (type == 1) {
                    sb.append("+");
                } else if (type == 2) {
                    sb.append("-");
                } else if (type == 3) {
                    sb.append("x");
                }
            }
        }
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            chars = String.valueOf(engine.eval(sb.toString().replaceAll("x", "*")));
        } catch (ScriptException e) {
            e.fillInStackTrace();
        }
        sb.append("=?");
        arithmeticString = sb.toString();
        return chars.toCharArray();
    }

    /**
     * 获取算数字符串
     * @return 算数字符串
     */
    public String getArithmeticString() {
        checkAlpha();
        return arithmeticString;
    }

    /**
     * 设置算数字符串
     *
     * @param arithmeticString 算数字符串
     */
    public void setArithmeticString(String arithmeticString) {
        this.arithmeticString = arithmeticString;
    }
}