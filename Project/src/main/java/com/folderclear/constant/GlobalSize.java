package com.folderclear.constant;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;

public class GlobalSize {

	// SIZE
	public final static Point CENTERPOINT = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
	public final static Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
	public final static int SCREENWINDTH = (int) SCREENSIZE.getWidth();
	public final static int SCREENHEIGHT = (int) SCREENSIZE.getHeight();
	public final static int MAINWINDTH = SCREENHEIGHT / 2;
	public final static int MAINHEIGHT = SCREENHEIGHT / 2;
	public final static int MARGIN = 15;
	public final static int BTNWINDTH = 50;
	public final static int BTNHEIGHT = 25;

}
