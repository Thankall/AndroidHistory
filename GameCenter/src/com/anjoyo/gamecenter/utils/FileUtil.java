package com.anjoyo.gamecenter.utils;

import java.io.File;

import android.os.Environment;

/**
 * 图片缓存文件帮助类�?
 * 
 * @author lixuebin
 * 
 */
public class FileUtil {

	/**
	 * 给定图片uri，获取图片的缓存文件路径
	 * 
	 * @param imageUri图片地址
	 * @return图片文件存放路径
	 */
	public File getCacheFile(String imageUri) {

		File cacheFile = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File sdCardDir = Environment.getExternalStorageDirectory(); // 获取sd卡目录?
			File dir = new File(sdCardDir, Constants.IMAGEDIR_NAME); // 创建文件夹为sdcard/court
			if (!dir.exists()) {
				dir.mkdirs();
			}
			int index = imageUri.lastIndexOf("/");
			String fileName = imageUri.substring(index + 1);
			cacheFile = new File(dir, fileName); // 创建图片文件路径
		}
		return cacheFile;

	}

	/**
	 * 清除图片缓存
	 * 
	 * @param cacheFile存放图片文件的文件夹
	 */
	public void clearCacheFile(File cacheFile) {

		File[] files = cacheFile.listFiles();
		if (files == null) {
			return;
		} else {
			for (File file : files) {
				file.delete();
			}
		}
	}

}

