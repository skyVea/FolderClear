package com.folderclear.view.folderselector;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import com.folderclear.constant.GlobalColor;
import com.folderclear.constant.GlobalPath;
import com.folderclear.constant.GlobalSize;
import com.folderclear.util.ImageUtil;
import com.folderclear.view.basic.BasicButton;
import com.folderclear.view.basic.BasicPanel;
import com.folderclear.view.basic.ChooseTextField;

public abstract class FolderSelecterPanel extends BasicPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BasicButton addBtn = null;
	private ChooseTextField chooseField = null;

	public LayoutManager getLayout() {
		return new FlowLayout(GlobalSize.MARGIN, GlobalSize.MARGIN, GlobalSize.MARGIN);
	}

	public FolderSelecterPanel() {
		setBackground(GlobalColor.MAINGRAY);
		createTF();
		createBtn();
		add(chooseField);
		add(addBtn);
	}

	public void createTF() {
		chooseField = new ChooseTextField() {

			@Override
			public void handleClick(ActionEvent actionevent, String filepath) {

			}
		};
	}

	public void createBtn() {
		ImageIcon ii = null;
		try {
			ii = new ImageIcon(ImageUtil.createImageIcon(
					this.getClass().getClassLoader().getResourceAsStream(GlobalPath.ICON + "/AddIcon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		addBtn = new BasicButton(ii);// 添加路径按钮
		addBtnActionListener(addBtn);
	}

	private void addBtnActionListener(BasicButton button) {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(addBtn)) {
					addAction(e, chooseField.getText());
				}
			}
		});
	}

	public abstract void addAction(ActionEvent e, String text);

	// 根据Frame调整表大小
	public void fitSize(Dimension marginsize) {
		Dimension preferredSize = new Dimension(ChooseTextField.DEFAULT_DIMENSION.width + marginsize.width,
				ChooseTextField.DEFAULT_DIMENSION.height);
		chooseField.setPreferredSize(preferredSize);
	}

}
