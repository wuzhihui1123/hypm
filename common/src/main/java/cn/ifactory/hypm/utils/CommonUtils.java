package cn.ifactory.hypm.utils;

import org.apache.commons.lang3.StringUtils;

public class CommonUtils {
	/**
	 * 获取图片的Mime类型
	 * @param imageName 图片全名(包含扩展名）
	 * @return
	 */
	public static String getImageMimeType(String imageName) {
		String ret = "";
		imageName = StringUtils.isBlank(imageName) ? "jpg" : imageName.trim().toLowerCase();
		if("jpeg".endsWith(imageName) || "jpg".endsWith(imageName)) {
			ret = "image/jpeg";
		}else if("png".endsWith(imageName)) {
			ret = "image/png";
		}else if("gif".endsWith(imageName)) {
			ret = "image/gif";
		}else if("bmp".endsWith(imageName)) {
			ret = "image/bmp";
		}else {
			ret = "image/jpeg";
		}
		return ret;
	}
}
