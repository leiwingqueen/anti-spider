package com.elend.log.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elend.p2p.util.ResourceUtil;
/**
 * 系统设置
 *
 */
public class CommConfig {
	
	private final static Logger logger = LoggerFactory.getLogger(CommConfig.class);
	
	/** 默认的配置文件 */
	private static String CONFIG_FILE = "classpath:config/common-config.xml";

	/** 配置文件内容 */
	private static Properties config = new Properties();

	/** 是否已初始化 */
	private static boolean configInited = false;
	
	private static String importFile = null;

	/**
	 * 设置配置文件名称
	 * 
	 * @param file
	 */
	public static void setConfigFile(String file) {
		if (file != null) {
			file = file.trim();
			if (file.length() > 0) {
				CONFIG_FILE = file;
			}
		}
	}

	/**
	 * 初始化
	 */
	public static synchronized void init() {
		if (!configInited) {
			
			config.clear();
			importFile = null;
			
			InputStream is = null;
			try {
				is = ResourceUtil.getFileAsStream(CONFIG_FILE);
			} catch (Exception e) {
				logger.error("Cannot find " + CONFIG_FILE, e);
			}

			if (is == null) {
				throw new RuntimeException("Cannot load configuration file: "
						+ CONFIG_FILE);
			}

			try {
				loadConfigFile(is, true);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
					}
				}
			}
			
			// 加载 import 的文件
			if (importFile != null && importFile.length() > 0) {
				// import 的文件可以以多个英文逗号 ( , ) 隔开
				String[] files = importFile.split(",");
				for (int i=0; i < files.length; i++) {
					is = null;
					try {
						is = ResourceUtil.getFileAsStream(files[i]);
					} catch (Exception e) {
						logger.warn("Cannot load file " + files[i] + ", error: " + e.getMessage());
					}
					
					if (is != null) {
						loadConfigFile(is, false);
						
						try {
							is.close();
						} catch (IOException e) {
						}
					}
				} // end for
			} // end if
			
			configInited = true;
		}
	}
	
	/**
	 * 读取配置文件
	 * @param file
	 */
	@SuppressWarnings("unchecked")
	private static void loadConfigFile(InputStream file, boolean toImport) {

		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(file);
			List list = document.selectNodes("//config" );
			if (list != null && list.size() == 1) {
				Element el = (Element) list.get(0);
				list = el.elements();
			}
			else {
				return;
			}

			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Element element = (Element)iter.next();
				String key = element.getName().trim();
				String value = element.attributeValue("value");
				if (value == null) {
					value = element.getText();
				}
				if (value != null) {
					value = value.trim();
				}
				if (toImport && "import".equals(key)) {
					// <import /> 标签
					importFile = value;
				}
				else {
					config.setProperty(key, value);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 获得配置
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static String get(String key) {
		if (!configInited) {
			init();
		}
		String value = config.getProperty(key);
		if (value != null)
			value = value.trim();
		return value;
	}

	/**
	 * 获得 int 值
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key, int defaultValue) {
		if (!configInited) {
			init();
		}

		String value = config.getProperty(key);
		if (value == null) {
			return -1;
		}
		int iValue = 0;
		try {
			iValue = Integer.parseInt(value.trim());
		} catch (Exception e) {
			iValue = defaultValue;
		}
		return iValue;
	}

	/**
	 * 获得bool 值
	 * 
	 * @param key
	 * @return
	 */
	public static boolean getBool(String key) {
		if (!configInited) {
			init();
		}

		String value = config.getProperty(key);
		if (value == null) {
			return false;
		}
		value = value.trim().toLowerCase();
		if ("true".equals(value) || "yes".equals(value) || "1".equals(value)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获得 double 值
	 * @param key - 键
	 * @param defaultValue - 默认值
	 * @return
	 */
	public static double getDouble(String key, double defaultValue) {
		if (!configInited) {
			init();
		}

		String value = config.getProperty(key);
		if (value == null) {
			return defaultValue;
		}
		double dValue = 0;
		try {
			dValue = Double.parseDouble(value.trim());
		} catch (Exception e) {
			dValue = defaultValue;
		}
		return dValue;
	}

	/**
	 * 获得所有配置
	 * 
	 * @return
	 */
	public static Properties getAllConfigs() {
		if (!configInited) {
			init();
		}

		Properties props = new Properties();
		props.putAll(config);
		return props;
	}

	public static void main(String[] args) {
		try {
			CommConfig.init();
			System.out.println(CommConfig.get("domain"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
