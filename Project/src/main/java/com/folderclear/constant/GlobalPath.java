package com.folderclear.constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.folderclear.bo.BO;
import com.folderclear.util.PathUtil;

public class GlobalPath {

	// VAR
	public final static String ROOT = PathUtil.getPath("/");
	public final static String PLANS = ROOT + File.separator + "plans";
	public final static String CONFIG = ROOT + File.separator + "config";
	public final static String ICON = ROOT + File.separator + "icons";
	public final static String CONFIGFILE = CONFIG + File.separator + "config.properties";
	public static String DEFAULTPLANFILE = null;
	static {
		try {
			DEFAULTPLANFILE = new BO().readConfig().getDefaultPlan();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
