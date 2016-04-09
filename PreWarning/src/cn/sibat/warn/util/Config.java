package cn.sibat.warn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;



public class Config {

	private static Logger log = Logger.getLogger(Config.class);
	private Properties prop = null; //the properties we are using
	private Properties prop_internel = null;//if prop not exit use it
	
	private static Config util = null;

	public static Config getInstance() {
		if (util == null) {
			util = new Config();
			util.init();
		}
		return util;
	}
	
	public static void main(String args[]){
		Config a = Config.getInstance();

		System.out.println(a.getStringValue("aaa", "22"));

		System.out.println(a.getStringValue("mail.server.location", "enenen"));

	}

	public void init() {
		
		log.debug("config begin!");
		
		// for get the ext_config_path,and get the internal config file
		this.loadInternalConfig();
		String ext_config_path = this.getStringValue("ext.config.path", null);

		// if there is no ext_config_path, then use internal config file
		if (ext_config_path == null) {
			return;
		}

		// get the ext_config_file
		File extConfig = new File(ext_config_path);

		// if the file does not exit or cannot read,use internel config file
		if (!extConfig.canRead()) {
			log.debug("ext file not found, using internel file!");
			return;
		}

		try {
			FileInputStream fis = new FileInputStream(extConfig); //use the ext properties
			prop = new Properties();
			prop.load(fis);
			log.debug(" we are using ext file!");
			fis.close();

		} catch (Exception e) {
			prop = prop_internel;
			log.debug("error reading ext config file, use internal config file");
		}
	}

	private void loadInternalConfig() {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("config.properties");
		prop_internel = new Properties();
		try {
			prop_internel.load(inputStream);
			prop = prop_internel; //copy it
			inputStream.close();
		} catch (IOException e) {
			log.error(e);
		}
	}

	private Properties getProp() {
		if (this.prop == null) {
			this.init();
		}
		return this.prop;
	}
	
	public String getStringValue(String key, String def) {
		String value = this.getProp().getProperty(key);
		if (value == null || value.trim().equals("")) {
			value = prop_internel.getProperty(key); //use the internal
		}
		
		//the internal file does not content the key return the default value
		if (value == null || value.trim().equals("")) {
			return def;
		}
		
		return value.trim();
	}

//	public int getIntValue(String key, int def) {
//		String str = this.getStringValue(key, null);
//		if (str == null || str.equals("")) {
//			return def;
//		}
//		return StringUtil.stringToInt(str, def);
//	}
//
//	public long getLongValue(String key, long def) {
//		String str = this.getStringValue(key, null);
//		if (str == null || str.equals("")) {
//			return def;
//		}
//		return StringUtil.stringToLong(str, def);
//	}
}
