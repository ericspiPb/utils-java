package me.synology.eric88.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public final class ImageUtil {

	public static BufferedImage copyImage(BufferedImage source) {
		BufferedImage image = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		Graphics graphics = image.getGraphics();
		graphics.drawImage(source, 0, 0, null);
		graphics.dispose();
		return image;
	}

}
