package me.synology.eric88.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class WatermarkUtil {

	public static void main(String[] args) throws IOException {
		String path = System.getProperty("user.home") + "/Downloads/";
		String text = "eric88.synology.me";
		File icon = new File(path + "icon.png");
		File inputImage = new File(path + "OrignalImage.jpg");
		File textOutputImage = new File(path + "TextWatermarkedImage.jpeg");
		File imageOutputImage = new File(path + "ImageWatermarkedImage.jpeg");
		File identifyOutputImage = new File(path + "IdentifyWatermarkedImage.jpeg");

		new Thread() {
			public void run() {
				try {
					ImageIO.write(WatermarkUtil.addTextWatermark(ImageIO.read(inputImage), text), "jpeg",
							textOutputImage);
					ImageIO.write(WatermarkUtil.addImageWatermark(ImageIO.read(inputImage), ImageIO.read(icon)), "jpeg",
							imageOutputImage);
					ImageIO.write(WatermarkUtil.addIdentifyWatermark(ImageIO.read(inputImage), ImageIO.read(icon),
							"eric.chan"), "jpeg", identifyOutputImage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

	}

	/**
	 * Add text to bottom right position
	 * 
	 * @param inputImage
	 * @param text
	 * @return BufferedImage
	 * @throws IOException
	 */
	public static final BufferedImage addTextWatermark(BufferedImage inputImage, String text) throws IOException {
		int padding = 8;

		BufferedImage watermarkedImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
				inputImage.getType());

		Graphics2D graphic = (Graphics2D) watermarkedImage.getGraphics();
		graphic.drawImage(inputImage, 0, 0, null);

		AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
		graphic.setComposite(alphaChannel);
		graphic.setColor(Color.GRAY);
		graphic.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		FontMetrics fontMetrics = graphic.getFontMetrics();
		Rectangle2D textBound = fontMetrics.getStringBounds(text, graphic);

		// add text overlay to the image
		graphic.drawString(text, watermarkedImage.getWidth() - (int) textBound.getWidth() - padding,
				watermarkedImage.getHeight() - (int) textBound.getHeight() - padding);
		graphic.dispose();

		return watermarkedImage;
	}

	/**
	 * Add watermark image to bottom right position
	 * 
	 * @param inputImage     any image size as background
	 * @param watermarkImage it should be Rectangle Image
	 * @return BufferedImage
	 * @throws IOException
	 */
	public static final BufferedImage addImageWatermark(BufferedImage inputImage, BufferedImage watermarkImage)
			throws IOException {
		int padding = 8;

		BufferedImage watermarkedImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
				inputImage.getType());

		Graphics graphic = watermarkedImage.getGraphics();
		graphic.drawImage(inputImage, 0, 0, null);
		graphic.drawImage(watermarkImage, inputImage.getWidth() - watermarkImage.getWidth() - padding,
				inputImage.getHeight() - watermarkImage.getHeight() - padding, inputImage.getWidth() - padding,
				inputImage.getHeight() - padding, 0, 0, watermarkImage.getWidth(), watermarkImage.getHeight(), null);
		graphic.dispose();

		return watermarkedImage;
	}

	/**
	 * Add watermark image and id to bottom right position
	 * 
	 * @param inputImage     any image size as background
	 * @param iconImage it should be Rectangle Image
	 * @param userId         user identify
	 * @return BufferedImage
	 * @throws IOException
	 */
	public static final BufferedImage addIdentifyWatermark(BufferedImage inputImage, BufferedImage iconImage,
			String userId) throws IOException {
		int padding = 8;
		int iconSize = 48;
		int fontSize = 48;

		BufferedImage watermarkedImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
				inputImage.getType());

		Graphics graphic = watermarkedImage.getGraphics();
		graphic.drawImage(inputImage, 0, 0, null);

		graphic.setColor(Color.GRAY);
		graphic.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));
		FontMetrics fontMetrics = graphic.getFontMetrics();
		Rectangle2D textBound = fontMetrics.getStringBounds(userId, graphic);

		graphic.drawImage(iconImage,
				watermarkedImage.getWidth() - iconSize - (int) textBound.getWidth() - padding - padding, watermarkedImage.getHeight() - iconSize - padding, watermarkedImage.getWidth() - (int) textBound.getWidth() - padding - padding, watermarkedImage.getHeight() - padding,
				0, 0, iconImage.getWidth(), iconImage.getHeight(), null);
		graphic.drawString(userId, watermarkedImage.getWidth() - (int) textBound.getWidth() - padding,
				watermarkedImage.getHeight() - iconSize / 4 - padding);
		graphic.dispose();

		return watermarkedImage;
	}
}
