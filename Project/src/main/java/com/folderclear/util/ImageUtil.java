package com.folderclear.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtil {
	public static BufferedImage createImageIcon(InputStream is) throws IOException {
		return ImageIO.read(is);
	}

}
