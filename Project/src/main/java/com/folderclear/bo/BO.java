package com.folderclear.bo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folderclear.constant.GlobalPath;
import com.folderclear.exception.FCException;
import com.folderclear.util.PathUtil;
import com.folderclear.util.StringUtils;
import com.folderclear.vo.ConfigVO;

public class BO {
	public static String currentPlanpath = null;
	public static String defaultPlanpath = null;
	public static boolean isModify = false;
	// public static List<String> initData = Collections.synchronizedList(new
	// ArrayList());
	// public static List<String> currentData = Collections.synchronizedList(new
	// ArrayList());

	public ObjectMapper objectMapper = new ObjectMapper();

	// 获取默认配置
	public ConfigVO readConfig() throws FileNotFoundException, IOException {
		File file = new File(GlobalPath.CONFIGFILE);
		if (!file.exists()) {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.close();
		}
		ConfigVO configVO = new ConfigVO();
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		if (properties.containsKey("defaultPlan")) {
			configVO.setDefaultPlan((String) properties.get("defaultPlan"));
		}
		return configVO;
	}

	// 更新配置
	public void writeConfig(ConfigVO config) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		File file = new File(GlobalPath.CONFIGFILE);
		properties.load(new FileInputStream(file));
		Field[] fields = config.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			try {
				properties.put(field.getName(), field.get(config));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		properties.store(new FileOutputStream(GlobalPath.CONFIGFILE), null);
	}

	// 载入数据
	public Object[][] readData(String planpath) throws FileNotFoundException, IOException {
		Object[][] data = null;
		if (StringUtils.isEmpty(planpath)) {
			return data;
		}
		Properties properties = new Properties();
		File file1 = new File(planpath);
		properties.load(new FileInputStream(file1));
		if (properties.containsKey("paths")) {
			String dataString = (String) properties.get("paths");
			data = objectMapper.readValue(dataString, Object[][].class);
		}

		return data;
	}

	// 保存新方案
	public void createPlan(String planpath, Object[]... values) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		List<Object[]> valuelist = Arrays.asList(values);
		for (int i = 0; i < valuelist.size(); i++) {
			String value = String.valueOf(valuelist.get(i)[0]);
			if (value == null || "".equals(value)) {
				throw new RuntimeException(String.valueOf("不能添加空"));
			}
		}
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		data.addAll((List) valuelist);
		properties.setProperty("paths", objectMapper.writeValueAsString(data));
		properties.store(new FileOutputStream(planpath), null);
	}

	// 增加数据
	public void addData(String planpath, Object[]... values) throws FileNotFoundException, IOException {
		// 持久到本地
		// String[] columnNames = { "目录列表", "状态", "备注1", "备注2" };
		// Object[][] data = { { "x:sofa_home", "正常", "备注1", "备注2" }, { "x:sofa_home",
		// "正常", "备注1", "备注2" },
		// { "x:sofa_home", "正常", "备注1", "备注2" }, { "x:sofa_home", "正常", "备注1", "备注2" },
		// { "x:sofa_home", "正常", "备注1", "备注2" }, { "x:sofa_home", "正常", "备注1", "备注2" }
		// };
		Properties properties = new Properties();
		File file1 = new File(planpath);
		properties.load(new FileInputStream(file1));
		String dataString = (String) properties.get("paths");
		ArrayList<ArrayList<String>> data = null;
		if (dataString != null && !"".equals(dataString)) {
			data = objectMapper.readValue(dataString, ArrayList.class);
		} else {
			data = new ArrayList<ArrayList<String>>();
		}
		List<Object[]> valuelist = Arrays.asList(values);
		for (int i = 0; i < valuelist.size(); i++) {
			String value = String.valueOf(valuelist.get(i)[0]);
			if (value == null || "".equals(value)) {
				throw new RuntimeException(String.valueOf("不能添加空"));
			}
			for (int j = 0; j < data.size(); j++) {
				if (data.get(j).get(0).equals(String.valueOf(valuelist.get(i)[0]))) {
					throw new RuntimeException("\"" + String.valueOf(valuelist.get(i)[0]) + "\"" + "已存在！");
				}
			}
		}
		data.addAll((List) valuelist);
		properties.setProperty("paths", objectMapper.writeValueAsString(data));
		properties.store(new FileOutputStream(planpath), null);

	}

	// 移除数据
	public void removeData(String planpath, ArrayList<String> column0Rows) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		File file1 = new File(planpath);
		properties.load(new FileInputStream(file1));
		String dataString = (String) properties.get("paths");
		ArrayList data = objectMapper.readValue(dataString, ArrayList.class);
		data.remove(column0Rows);
		properties.setProperty("paths", objectMapper.writeValueAsString(data));
		properties.store(new FileOutputStream(planpath), null);
	}

	// 删除文件夹
	public void delFolder(String folderPath) {
		if (StringUtils.isEmpty(folderPath)) {
			throw new FCException("\"" + folderPath + "\"" + "目录路径为空!");
		}
		String filePath = folderPath;
		filePath = filePath.toString();
		java.io.File myFilePath = new java.io.File(filePath);
		if (!myFilePath.isDirectory()) {
			throw new FCException("\"" + folderPath + "\"" + "不存在!");
		}
		delAllFile(folderPath); // 删除完里面所有内容
		myFilePath.delete(); // 删除空文件夹
	}

	// 删除指定文件夹下的所有文件
	public boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

}
