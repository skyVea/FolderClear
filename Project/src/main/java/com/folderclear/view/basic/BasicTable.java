package com.folderclear.view.basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.TableModel;

import com.folderclear.constant.GlobalBorder;
import com.folderclear.constant.GlobalColor;
import com.folderclear.constant.GlobalFont;
import com.folderclear.constant.GlobalSize;

public class BasicTable extends JTable {
	private int default_height = GlobalSize.MAINWINDTH / 2 - GlobalSize.MARGIN * 4;
	private int default_width = GlobalSize.MAINWINDTH - GlobalSize.MARGIN * 4 - GlobalSize.BTNWINDTH;
	private Border default_raiseborder = GlobalBorder.RAISEDBORDER;
	private Border default_loweredborder = GlobalBorder.LOWEREDBORDER;
	private Border default_lineborder = GlobalBorder.LINEBORDER;
	private Font default_font = GlobalFont.CONTENTFONT;
	private Color default_backgroundcolor = GlobalColor.MAINGRAY;
	private Color default_rowselectedcolor = GlobalColor.SELECTEDBLUE;

	public BasicTable(TableModel dm) {
		super(dm);
		setUI();
	}

	private void setUI() {
		setBackground(Color.WHITE);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setSelectionBackground(default_rowselectedcolor);
		setSelectionForeground(Color.WHITE);
		getTableHeader().setBackground(default_backgroundcolor);
		getTableHeader().setBorder(default_raiseborder);

	}

}
