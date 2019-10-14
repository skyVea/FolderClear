package com.folderclear.view.basic;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.folderclear.util.StringUtils;

public abstract class BasicFileOpenChooser extends JFileChooser {
	public static String FILETYPE_PROPERTIES = "properties";

	public BasicFileOpenChooser(String currentDirectoryPath, String filetype, int mode) {
		super(currentDirectoryPath);
		createUI(filetype, mode);
	}

	private void createUI(final String filetype, int mode) {
		addChoosableFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public boolean accept(File file) {
				String name = file.getName();
				String fileSuffix = null;
				if (!StringUtils.isEmpty(filetype)) {
					fileSuffix = filetype.startsWith(".") ? filetype : "." + filetype;
					return file.isDirectory() || name.toLowerCase().endsWith(fileSuffix);
				} else {
					return true;
				}
			}
		});
		setFileSelectionMode(mode);// 设定只能选择到文件夹
		int state = showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
		if (state == 1) {
			return;// 撤销则返回
		} else {
			File f = getSelectedFile();// f为选择到的目录
			handleOpen(f.getAbsolutePath());
		}
	}

	public abstract void handleOpen(String filepath);
}
