package com.folderclear.view.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.plaf.basic.BasicMenuItemUI;
import javax.swing.plaf.basic.BasicMenuUI;

import com.folderclear.constant.GlobalBorder;
import com.folderclear.constant.GlobalColor;
import com.folderclear.constant.GlobalFont;
import com.folderclear.constant.GlobalSize;

public abstract class MenuPanel extends Panel {
	public JMenuBar jBar = null;
	public JMenu menu = null;
	private Border menuDefaultBorder = null;
	public JMenuItem loadItem = null;
	public JMenuItem saveAsItem = null;
	public JMenuItem saveItem = null;
	public JMenuItem closeItem = null;
	static Dimension DEFAULTDIMENSION = new Dimension(GlobalSize.MAINWINDTH, GlobalSize.BTNHEIGHT);

	public MenuPanel() {
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
		setLayout(flowLayout);
		jBar = createBar(jBar);
		menu = createMenu(menu);
		loadItem = createLoadItem(loadItem);
		saveItem = createSaveItem(saveItem);
		saveAsItem = createSaveAsItem(saveAsItem);
		menu.add(loadItem);
		menu.add(saveItem);
		menu.add(saveAsItem);
		jBar.add(menu);
		add(jBar);
	}

	public JMenuItem createLoadItem(JMenuItem loadItem) {
		loadItem = new JMenuItem("载入方案(O)", KeyEvent.VK_O);
		loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		loadItem.setPreferredSize(new Dimension(100, GlobalSize.BTNHEIGHT));
		loadItem.setFont(GlobalFont.CONTENTFONT);
		loadItem.setBackground(GlobalColor.MAINGRAY);
		CustomMenuItemUI itemUI = new CustomMenuItemUI();
		loadItem.setUI(itemUI);
		loadItem.setBorder(null);
		itemActionListener(loadItem);
		return loadItem;
	}

	public JMenuItem createSaveItem(JMenuItem saveItem) {
		saveItem = new JMenuItem("保存(S)...", KeyEvent.VK_S);
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.setPreferredSize(new Dimension(100, GlobalSize.BTNHEIGHT));
		saveItem.setFont(GlobalFont.CONTENTFONT);
		saveItem.setBackground(GlobalColor.MAINGRAY);
		CustomMenuItemUI itemUI = new CustomMenuItemUI();
		saveItem.setUI(itemUI);
		saveItem.setBorder(null);
		itemActionListener(saveItem);
		return saveItem;
	}

	public JMenuItem createSaveAsItem(JMenuItem saveAsItem) {
		saveAsItem = new JMenuItem("另存为(A)...", KeyEvent.VK_A);
		saveAsItem.setPreferredSize(new Dimension(200, GlobalSize.BTNHEIGHT));
		saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK | InputEvent.CTRL_MASK));
		saveAsItem.setFont(GlobalFont.CONTENTFONT);
		saveAsItem.setBackground(GlobalColor.MAINGRAY);
		CustomMenuItemUI itemUI = new CustomMenuItemUI();
		saveAsItem.setUI(itemUI);
		saveAsItem.setBorder(null);
		itemActionListener(saveAsItem);
		return saveAsItem;
	}

	public JMenu createMenu(JMenu menu) {
		menu = new JMenu("文件(F)");
		CustomMenuUI menuUI = new CustomMenuUI();
		menu.setUI(menuUI);
		menu.setMnemonic(KeyEvent.VK_F);
		menu.setFont(GlobalFont.CONTENTFONT);
		menu.setBackground(GlobalColor.BORDERGRAY);
		menuDefaultBorder = menu.getBorder();
		menu.getPopupMenu().setBorder(GlobalBorder.RAISEDBORDER);
		// menu.getPopupMenu().setPreferredSize(new
		// Dimension(200,GlobalSize.BTNHEIGHT*3));
		menuMouseListener(menu);
		menuActionListener(menu);
		return menu;
	}

	public JMenuBar createBar(JMenuBar jBar) {
		jBar = new JMenuBar();
		jBar.setPreferredSize(new Dimension(GlobalSize.MAINWINDTH, GlobalSize.BTNHEIGHT));
		jBar.setBorder(null);
		jBar.setBackground(GlobalColor.MAINGRAY);
		return jBar;
	}

	public void itemActionListener(final JMenuItem item) {
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (item.equals(loadItem)) {
					loadAction();
				} else if (item.equals(saveItem)) {
					saveAction();
				} else if (item.equals(saveAsItem)) {
					saveAsAction();
				}
			}
		});

	}

	public abstract void loadAction();

	public abstract void saveAction();

	public abstract void saveAsAction();

	public void menuActionListener(final JMenu menu) {
		menu.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				menu.setBorder(GlobalBorder.MENULOWEREDBORDER);
			}

			@Override
			public void menuDeselected(MenuEvent e) {
				menu.setBorder(menuDefaultBorder);
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});
	}

	public void menuMouseListener(final JMenu menu) {
		menu.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {// 移出
				if (!menu.isPopupMenuVisible()) {
					menu.setBorder(menuDefaultBorder);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {// 移入
				if (!menu.isPopupMenuVisible()) {
					menu.setBorder(GlobalBorder.MENURAISEDBORDER);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	// 根据Frame调整大小
	public void fitSize(Dimension marginsize) {
		Dimension preferredSize = new Dimension(DEFAULTDIMENSION.width + marginsize.width, DEFAULTDIMENSION.height);
		jBar.setPreferredSize(preferredSize);
	}

	public class CustomMenuItemUI extends BasicMenuItemUI {

		public CustomMenuItemUI() {
			super.selectionBackground = GlobalColor.SELECTEDBLUE;
			super.selectionForeground = Color.WHITE;
			super.acceleratorSelectionForeground = Color.WHITE;
			super.acceleratorForeground = Color.black;
		}
	}

	public class CustomMenuUI extends BasicMenuUI {
		public CustomMenuUI() {
			super.selectionBackground = GlobalColor.MAINGRAY;
		}
	}

}
