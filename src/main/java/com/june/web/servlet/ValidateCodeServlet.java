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

import org.springframework.http.HttpHeaders;
import org.springframework.util.MimeTypeUtils;

import com.june.util.Constants;

@WebServlet(urlPatterns = "/vCode.jpg")
public class ValidateCodeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final char[] CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int width = 70, height = 30;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(20);
			g.drawLine(x, y, x + xl, y + yl);
		}
		StringBuilder validateCode = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(CHARS[random.nextInt(CHARS.length)]);
			validateCode.append(rand);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 15 * i + 6, 22);
		}
		g.dispose();
		
		// 将验证码存入Session中
		request.getSession(false).setAttribute(Constants.VALIDATE_CODE, validateCode.toString());
		// 禁止图像缓存
		response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store, no-cache");
		response.setContentType(MimeTypeUtils.IMAGE_JPEG_VALUE);
		try (OutputStream os = response.getOutputStream()) {			
			// 以流的形式输出到页面
			ImageIO.write(image, "JPEG", os);
		}
	}
	
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) fc = 255;
		if (bc > 255) bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
