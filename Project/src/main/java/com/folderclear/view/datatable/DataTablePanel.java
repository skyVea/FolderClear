package com.folderclear.view.datatable;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.folderclear.constant.GlobalPath;
import com.folderclear.constant.GlobalSize;
import com.folderclear.view.basic.BasicButton;
import com.folderclear.view.basic.BasicPanel;
import com.folderclear.view.basic.BasicScrollPane;
import com.folderclear.view.basic.BasicTable;

public abstract class DataTablePanel extends BasicPanel {
	private JScrollPane scrollPane = null;
	private BasicTable basicTable = null;
	private BasicButton removeBtn = null;

	private Dimension scrollPaneDefaultDimension = null;

	public LayoutManager getLayout() {
		return new FlowLayout(FlowLayout.LEFT, GlobalSize.MARGIN, GlobalSize.MARGIN);
	}

	public BasicTable getBasicTable() {
		return basicTable;
	}

	public DataTablePanel(Object[][] rowData, final Object[] columnNames) {
		createTable(rowData, columnNames);
		createRemoveBtn();
	}

	private void createTable(Object[][] rowData, final Object[] columnNames) {
		basicTable = new BasicTable(new DefaultTableModel(rowData, columnNames));
		scrollPane = new BasicScrollPane(basicTable);
		scrollPaneDefaultDimension = scrollPane.getPreferredSize();
		add(scrollPane);
	}

	private void createRemoveBtn() {
		removeBtn = new BasicButton(new ImageIcon(GlobalPath.ICON + File.separator + "trash.gif"));
		removeBtnListener(removeBtn);
		add(removeBtn);
	}

	private void removeBtnListener(JButton removeBtn) {
		// 为按钮绑定监听器
		removeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeBtnAction(e);
			}
		});
	}

	public abstract void removeBtnAction(ActionEvent e);

	// 清空表重新添加数据
	public void initTableUI(Object[]... objects) {
		DefaultTableModel tableModel = (DefaultTableModel) basicTable.getModel();
		tableModel.setRowCount(0);
		if (objects != null && objects.length > 0) {
			for (int i = 0; i < objects.length; i++) {
				tableModel.addRow(objects[i]);
			}
		}
	}

	// 添加数据
	public void addData(Object[]... objects) {
		DefaultTableModel tableModel = (DefaultTableModel) basicTable.getModel();
		for (int i = 0; i < objects.length; i++) {
			tableModel.addRow(objects[i]);
		}
	}

	// 删除数据
	public void removeData(int... rowindexs) {
		DefaultTableModel tableModel = (DefaultTableModel) basicTable.getModel();
		for (int i = 0; i < rowindexs.length; i++) {
			tableModel.removeRow(rowindexs[i]);
		}
	}

	// 根据Frame调整表大小
	public void fitSize(Dimension marginsize) {
		Dimension preferredSize = new Dimension(scrollPaneDefaultDimension.width + marginsize.width,
				marginsize.height / 2 + scrollPaneDefaultDimension.height);
		scrollPane.setPreferredSize(preferredSize);
		basicTable.setPreferredScrollableViewportSize(preferredSize);
	}

	public static void main(String[] args) {

	}

}
