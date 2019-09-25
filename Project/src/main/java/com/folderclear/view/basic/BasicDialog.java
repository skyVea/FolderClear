package com.folderclear.view.basic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.naming.InitialContext;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.folderclear.constant.GlobalColor;
import com.folderclear.constant.GlobalFont;
import com.folderclear.constant.GlobalPath;
import com.folderclear.constant.GlobalSize;
import com.folderclear.util.StringUtils;

public abstract class BasicDialog extends JDialog {
	public BasicButton yesBtn = null;
	public BasicButton noBtn = null;
	public BasicButton cancleBtn = null;
	private int dialogWidth = 290;
	private int dialogHeight = 138;
	// private int configPanelWidth = dialogWidth;
	// private int configPanelHeight = 43;
	// private int contentPanelHeight = 75;
	private int btnWidh = 75;
	private int btnHeight = 21;
	private int btnMargin = 8;
	private int labelMargin = 25;

	public BasicDialog(Icon icon, String title, String content, BasicButton... basicButtons) {
		setModal(true);
		createUI(icon, title, content, basicButtons);
	}

	public void createUI(Icon icon, String title, String content, BasicButton... basicButtons) {
		BorderLayout borderLayout = new BorderLayout(0, 0);
		setBackground(GlobalColor.MAINGRAY);
		setLayout(borderLayout);
		setTitle(title);
		setSize(dialogWidth, dialogHeight);
		setLocation(GlobalSize.CENTERPOINT.x - dialogWidth / 2, GlobalSize.CENTERPOINT.y - dialogHeight / 2);
		BasicPanel contentPanel = new BasicPanel() {
			@Override
			public LayoutManager getLayout() {
				return new FlowLayout(FlowLayout.LEFT, labelMargin, labelMargin);
			}

			@Override
			public Color getBackground() {
				return Color.WHITE;

			}
		};
		if (icon != null) {
			JLabel iconL = new JLabel(icon);
			contentPanel.add(iconL);
		}
		if (!StringUtils.isEmpty(content)) {
			JLabel contentL = new JLabel(content);
			contentL.setFont(GlobalFont.CONTENTFONT);
			contentPanel.add(contentL);
		}

		BasicPanel buttonPanel = new BasicPanel() {
			@Override
			public LayoutManager getLayout() {
				return new FlowLayout(FlowLayout.RIGHT, btnMargin, btnMargin);
			}
		};

		for (int i = 0; i < basicButtons.length; i++) {
			basicButtons[i].setPreferredSize(new Dimension(btnWidh, btnHeight));
			addActionListener(basicButtons[i]);
			buttonPanel.add(basicButtons[i]);
		}
		add("Center", contentPanel);
		add("South", buttonPanel);
		setVisible(true);
	}

	private void addActionListener(JButton button) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonAction(e);
			}
		});
	}

	public abstract void buttonAction(ActionEvent e);

}
