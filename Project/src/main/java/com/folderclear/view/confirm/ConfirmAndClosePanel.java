package com.folderclear.view.confirm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import com.folderclear.constant.GlobalBorder;
import com.folderclear.constant.GlobalColor;
import com.folderclear.constant.GlobalFont;
import com.folderclear.constant.GlobalSize;
import com.folderclear.view.basic.BasicButton;
import com.folderclear.view.basic.BasicPanel;

public abstract class ConfirmAndClosePanel extends BasicPanel {
	public JButton okBtn = null;
	public JButton closeBtn = null;

	public LayoutManager getLayout() {
		return new FlowLayout(FlowLayout.CENTER, GlobalSize.MARGIN, GlobalSize.MARGIN * 2);
	}

	public ConfirmAndClosePanel() {
		createBtn();
	}

	public void createBtn() {
		okBtn = new BasicButton("确认删除");
		okBtn.setPreferredSize(new Dimension(GlobalSize.BTNWINDTH * 15 / 10, GlobalSize.BTNHEIGHT));
		abstractMouseActionListener(okBtn);
		okBtnActionListener(okBtn);

		closeBtn = new BasicButton("关闭");
		abstractMouseActionListener(closeBtn);
		closeBtnActionListener(closeBtn);
		add(okBtn);
		add(closeBtn);
	}

	private void abstractMouseActionListener(JButton jButton) {
		jButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	public void okBtnActionListener(JButton ok) {
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				okBtnAction(e);
			}
		});
	}

	public void closeBtnActionListener(JButton close) {
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				closeBtnAction(e);
			}
		});
	}

	// 根据Frame调整大小
	public void fitSize(Dimension marginsize) {
	}

	public abstract void okBtnAction(ActionEvent e);

	public abstract void closeBtnAction(ActionEvent e);

}
