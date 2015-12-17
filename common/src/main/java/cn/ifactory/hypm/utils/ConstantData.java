package cn.ifactory.hypm.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 常量数据
 * @author yaha
 *
 */
public class ConstantData {
	private static final Logger logger = LoggerFactory.getLogger(ConstantData.class);
	/**
	 * Session中登陆用户存储的属性名
	 */
	public static final String SESSION_USER = "SYS_SESSION_USER";
	/**
	 * 存储于request.Attribute里面的日志（log)对象名
	 */
	public static final String LOG_OBJ_NAME = "HYPM_LOG_OBJ";
	
	
	/**
	 * 附件所在的目录名
	 */
	public static final String ATTACHMENT_DIR_PATH = getAttachDirPath();
	
	private static String getAttachDirPath() {
		String ret = null;
		String defaultPath = "";
		{
			String osName = System.getProperty("os.name");
			if(osName.startsWith("Windows")) {
				defaultPath = "c:/attachment/";
			}else {
				defaultPath = "/usr/www/www_data/hypm.xyz0.cn/";
			}
		}
		
//		try{
//			Properties prop = new Properties();
//			InputStream is = ConstantData.class.getClassLoader().getResourceAsStream("config.properties");
//			prop.load(is);
//			ret = (String)prop.get("attach_dir_path");
//			if(StringUtils.isBlank(ret)) {
//				throw new RuntimeException("附件路径找不到，使用默认路径");
//			}
//		}catch(Exception e) {
//			ret = defaultPath;
//		}
		ret = defaultPath;
		if(!ret.endsWith("/") && !ret.endsWith("\\")) {
			ret = ret + "/";
		}
		if(logger.isInfoEnabled()) {
			logger.info("附件存放目录[{}]",ret);
		}
		File attachDir = new File(ret);
		if(!attachDir.exists()) {
			attachDir.mkdirs();
		}
		return ret;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(ATTACHMENT_DIR_PATH);
		System.out.println(ATTACHMENT_DIR_PATH);
		System.out.println(ATTACHMENT_DIR_PATH);
		System.out.println(ATTACHMENT_DIR_PATH);
		System.out.println(ATTACHMENT_DIR_PATH);
		System.out.println(ATTACHMENT_DIR_PATH);
		System.out.println(ATTACHMENT_DIR_PATH);
		System.out.println(ATTACHMENT_DIR_PATH);
	}
	
}
