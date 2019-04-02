package com.june.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MimeTypeUtils;

import com.june.util.Constants;

@WebServlet("/vCode.jpg")
public class ValidateCodeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_WIDTH = 70;

    private static final int DEFAULT_HEIGHT = 30;

    private final Random random = new Random();

    private static final char[] CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                   IOException {
        String widthParam = request.getParameter("w");
        String heightParam = request.getParameter("h");
        final int width = StringUtils.isBlank(widthParam) ? ValidateCodeServlet.DEFAULT_WIDTH
                                                          : Integer.parseInt(widthParam);
        final int height = StringUtils.isBlank(heightParam) ? ValidateCodeServlet.DEFAULT_HEIGHT
                                                            : Integer.parseInt(heightParam);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(getRandomColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        g.setColor(getRandomColor(160, 200));
        // 随机产生干扰点
        createNoisePoint(g, width, height, 50);
        // 随机产生干扰线
        createNoiseLine(g, width, height, 10);
        StringBuilder validateCode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String code = String.valueOf(ValidateCodeServlet.CHARS[random.nextInt(ValidateCodeServlet.CHARS.length)]);
            validateCode.append(code);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(code, 15 * i + 6, 22);
        }
        g.dispose();

        // 将验证码存入Session中
        request.getSession().setAttribute(Constants.VALIDATE_CODE_KEY, validateCode.toString());
        // 禁止图像缓存
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store, no-cache");
        response.setContentType(MimeTypeUtils.IMAGE_JPEG_VALUE);
        try (OutputStream os = response.getOutputStream()) {
            // 以流的形式输出到页面
            ImageIO.write(image, "JPEG", os);
        }
    }

    /**
     * 随机产生干扰点
     */
    private void createNoisePoint(Graphics g, int width, int height, int count) {
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.setColor(getRandomColor());
            g.drawOval(x, y, 1, 1);
        }
    }

    /**
     * 随机产生干扰线
     */
    private void createNoiseLine(Graphics g, int width, int height, int count) {
        for (int i = 0; i < random.nextInt(count) + 6; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(20);
            g.setColor(getRandomColor());
            g.drawLine(x, y, x + xl, y + yl);
        }
    }

    /**
     * 随机获取颜色
     */
    private Color getRandomColor() {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r, g, b);
    }

    private Color getRandomColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
