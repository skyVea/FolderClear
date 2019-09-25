package com.folderclear.view.basic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.folderclear.util.StringUtils;

public abstract class BasicFileSaveChooser extends JFileChooser {
	public BasicFileSaveChooser(String currentDirectoryPath, String filetype, int mode) {
		super(currentDirectoryPath);
		createUI(filetype, mode);
	}

	private void createUI(String filetype, int mode) {
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
		setFileSelectionMode(mode);
		int state = showSaveDialog(null);
		if (state == JFileChooser.APPROVE_OPTION) {
			File file = getSelectedFile();
			String filepath = getCurrentDirectory() + File.separator + getName(file);
			if (!StringUtils.isEmpty(filetype)) {
				String fileSuffix = filetype.startsWith(".") ? filetype : "." + filetype;
				if (filepath.indexOf(fileSuffix) == -1) {
					filepath += fileSuffix;
				}
			}
			handleSave(filepath);
		}
	}

	public abstract void handleSave(String filepath);

}
