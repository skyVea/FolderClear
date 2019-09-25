package com.folderclear.view.basic;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.folderclear.constant.GlobalColor;

public abstract class BasicPanel extends JPanel {
	public BasicPanel() {
		setBackground(GlobalColor.MAINGRAY);
		setLayout(getLayout());
	}

	public abstract LayoutManager getLayout();
}
