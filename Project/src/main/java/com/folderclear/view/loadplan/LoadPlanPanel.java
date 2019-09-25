package com.folderclear.view.loadplan;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.folderclear.constant.GlobalPath;
import com.folderclear.constant.GlobalSize;

public abstract class LoadPlanPanel extends Panel {
	public JButton loadBtn = null;
	public JButton saveAsBtn = null;
	public JButton setAsDefaultBtn = null;

	public LoadPlanPanel() {
		FlowLayout flowLayout = new FlowLayout(GlobalSize.MARGIN, GlobalSize.MARGIN, GlobalSize.MARGIN);
		flowLayout.setAlignment(FlowLayout.CENTER);
		setLayout(flowLayout);
		initBtn();
		add(loadBtn);
		add(saveAsBtn);
		add(setAsDefaultBtn);
	}

	public void initBtn() {
		loadBtn = new JButton("载入方案");
		saveAsBtn = new JButton("另存为");
		setAsDefaultBtn = new JButton("设为默认方案");
		loadBtn.setSize(GlobalSize.BTNWINDTH * 15 / 10, GlobalSize.BTNHEIGHT);
		saveAsBtn.setSize(GlobalSize.BTNWINDTH, GlobalSize.BTNHEIGHT);
		setAsDefaultBtn.setSize(GlobalSize.BTNWINDTH * 15 / 10, GlobalSize.BTNHEIGHT);
		loadBtnActionListener(loadBtn);
		saveAsBtnActionListener(saveAsBtn);
		setAsDefaultBtnActionListener(setAsDefaultBtn);
		// saveAsBtn.setLocation(GlobalConstant.BTNWINDTH, GlobalConstant.BTNHEIGHT);
	}

	public abstract void loadBtnAction(ActionEvent e);

	public abstract void saveAsBtnAction(ActionEvent e);

	public abstract void setAsDefaultBtnAction(ActionEvent e);

	public void loadBtnActionListener(JButton load) {
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadBtnAction(e);
			}
		});
	}

	public void saveAsBtnActionListener(JButton saveas) {
		saveas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAsBtnAction(e);
			}
		});
	}

	public void setAsDefaultBtnActionListener(JButton setasdefault) {
		setasdefault.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setAsDefaultBtnAction(e);
			}
		});
	}

	// 根据Frame调整大小
	public void fitSize(Dimension marginsize) {
		// Dimension preferredSize = new Dimension(scrollPaneDefaultDimension.width +
		// marginsize.width,
		// marginsize.height / 2 + scrollPaneDefaultDimension.height);
		// scrollPane.setPreferredSize(preferredSize);
		// jt.setPreferredScrollableViewportSize(preferredSize);
	}

}
