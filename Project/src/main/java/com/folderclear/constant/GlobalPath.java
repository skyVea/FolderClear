package com.folderclear.constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.folderclear.bo.BO;
import com.folderclear.util.PathUtil;

public class GlobalPath {

	// VAR
	// public final static String ROOT = PathUtil.getPath("");
	public final static String PLANS = "plans";
	public final static String CONFIG = "config";
	public final static String ICON = "icons";
	/**
	 * 1. 存放Plan的路径
	 */
	public final static String CONFIGFILE = CONFIG + "/config.properties";
	public static String DEFAULT_PLAN_FILE = null;
	static {
		try {
			DEFAULT_PLAN_FILE = new BO().readConfig().getDefaultPlan();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
