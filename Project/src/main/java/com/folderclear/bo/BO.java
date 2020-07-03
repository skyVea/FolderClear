package com.folderclear.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folderclear.constant.GlobalPath;
import com.folderclear.exception.FCException;
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
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(GlobalPath.CONFIGFILE);
		ConfigVO configVO = new ConfigVO();
		Properties properties = new Properties();
		properties.load(is);
		if (properties.containsKey("defaultPlan")) {
			configVO.setDefaultPlan((String) properties.get("defaultPlan"));
		}
		return configVO;
	}

	// 更新配置
	public void writeConfig(ConfigVO config) throws FileNotFoundException, IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(GlobalPath.CONFIGFILE);
		Properties properties = new Properties();
		properties.load(is);
		Field[] fields = config.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			try {
				properties.putIfAbsent(field.getName(), field.get(config));
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

	// 删除指定File
	public boolean delFile(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		for (int i = 0; files != null && i < files.length; i++) {
			boolean isSuccess = delFile(files[i].getPath());
			if (isSuccess == false) {
				return isSuccess;
			}
		}
		return file.delete();
	}

}
