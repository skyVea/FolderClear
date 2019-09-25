package com.folderclear.view.basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.folderclear.constant.GlobalBorder;
import com.folderclear.constant.GlobalColor;
import com.folderclear.constant.GlobalFont;
import com.folderclear.constant.GlobalSize;

public class BasicTextField extends JTextField {
	private int default_height = GlobalSize.BTNHEIGHT;
	private int default_width = GlobalSize.MAINWINDTH - GlobalSize.MARGIN * 4 - GlobalSize.BTNWINDTH;
	private Border default_raiseborder = GlobalBorder.RAISEDBORDER;
	private Border default_lowerborder = GlobalBorder.LOWEREDBORDER;
	private Font default_font = GlobalFont.CONTENTFONT;
	private Color default_backgroundcolor = GlobalColor.MAINGRAY;

	public BasicTextField() {
//		super(columns);
		setUI();
	}

	private void setUI() {
		setBorder(default_lowerborder);
		setPreferredSize(new Dimension(default_width, default_height));
		setEditable(true);
	}

}
