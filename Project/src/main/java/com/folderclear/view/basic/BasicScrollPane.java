package com.folderclear.view.basic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import com.folderclear.constant.GlobalBorder;
import com.folderclear.constant.GlobalColor;
import com.folderclear.constant.GlobalFont;
import com.folderclear.constant.GlobalSize;

public class BasicScrollPane extends JScrollPane {
	private int default_height = GlobalSize.MAINWINDTH / 2 - GlobalSize.MARGIN * 4;
	private int default_width = GlobalSize.MAINWINDTH - GlobalSize.MARGIN * 4 - GlobalSize.BTNWINDTH;
	private Border default_raiseborder = GlobalBorder.RAISEDBORDER;
	private Border default_lineborder = GlobalBorder.LINEBORDER;
	private Border default_loweredborder = GlobalBorder.LOWEREDBORDER;
	private Font default_font = GlobalFont.CONTENTFONT;
	private Color default_backgroundcolor = GlobalColor.MAINGRAY;

	public BasicScrollPane(Component view) {
		super(view);
		setUI();
	}

	private void setUI() {
		// setLayout(getLayout());
		setBackground(default_backgroundcolor);
		// setLocation(GlobalSize.MARGIN, GlobalSize.MARGIN);
		setBorder(default_loweredborder);
		setPreferredSize(new Dimension(default_width, default_height));
	}

	// public abstract LayoutManager getLayout();
}
