package com.folderclear.view.basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.Border;

import com.folderclear.constant.GlobalBorder;
import com.folderclear.constant.GlobalColor;
import com.folderclear.constant.GlobalFont;
import com.folderclear.constant.GlobalSize;

public class BasicButton extends JButton {
	private int default_height = GlobalSize.BTNHEIGHT;
	private int default_width = GlobalSize.BTNWINDTH;
	private Border default_raiseborder = GlobalBorder.RAISEDBORDER;
	private Border default_lineborder = GlobalBorder.LINEBORDER;
	private Font default_font = GlobalFont.CONTENTFONT;
	private Color default_backgroundcolor = GlobalColor.MAINGRAY;

	public BasicButton(String text) {
		this(text, null);
	}

	public BasicButton(Icon icon) {
		this(null, icon);
	}

	public BasicButton(String text, Icon icon) {
		super(text, icon);
		setUI();
	}

	private void setUI() {
		setPreferredSize(new Dimension(default_width, default_height));
		setBorder(default_raiseborder);
		setFont(default_font);
		setBackground(default_backgroundcolor);
	}

	@Override
	public synchronized void addMouseListener(MouseListener l) {
		ButtonMouseListenerProxy proxy = new ButtonMouseListenerProxy(l);
		super.addMouseListener(proxy);
	}

	public class ButtonMouseListenerProxy implements MouseListener {
		MouseListener proxytarget = null;

		public ButtonMouseListenerProxy(MouseListener proxytarget) {
			this.proxytarget = proxytarget;
		}

		public void mouseClicked(MouseEvent e) {
			proxytarget.mouseClicked(e);
		}

		public void mousePressed(MouseEvent e) {
			setBorder(default_lineborder);
			proxytarget.mousePressed(e);
		}

		public void mouseReleased(MouseEvent e) {
			setBorder(default_raiseborder);
			proxytarget.mouseReleased(e);
		}

		public void mouseEntered(MouseEvent e) {
			proxytarget.mouseEntered(e);
		}

		public void mouseExited(MouseEvent e) {

		}
	}

}
