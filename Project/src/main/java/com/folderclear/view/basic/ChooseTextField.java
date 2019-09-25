package com.folderclear.view.basic;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.folderclear.constant.GlobalBorder;
import com.folderclear.constant.GlobalColor;
import com.folderclear.constant.GlobalPath;
import com.folderclear.constant.GlobalSize;

public abstract class ChooseTextField<T> extends BasicTextField {
	public static Dimension DEFAULT_DIMENSION = new Dimension(
			GlobalSize.MAINWINDTH - GlobalSize.MARGIN * 4 - GlobalSize.BTNWINDTH, GlobalSize.BTNHEIGHT);
	private BasicButton choose = null;// 选择文件夹按钮

	public ChooseTextField() {
		super();
		setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));// 如果没有这句话，按钮无法显示，原因是默认覆盖
		setEditable(false);
		createBtn();
	}

	private void createBtn() {
		choose = new BasicButton(new ImageIcon(GlobalPath.ICON + File.separator + "fldr_obj.gif"));
		choose.setPreferredSize(new Dimension(GlobalSize.BTNHEIGHT - 5, GlobalSize.BTNHEIGHT - 5));
		chooseBtnActionListener(choose);
	}

	private void chooseBtnActionListener(BasicButton button) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				new BasicFileOpenChooser(null, null, BasicFileOpenChooser.DIRECTORIES_ONLY) {
					@Override
					public void handleOpen(String filepath) {
						setText(filepath);
						handleClick(actionevent, filepath);
					}
				};
			}
		});
	}

	@Override
	public void addNotify() {
		super.addNotify();
		add(choose);// Container中方法将指定组件追加到此容器的尾部
	}

	public abstract void handleClick(ActionEvent actionevent, String filepath);

}