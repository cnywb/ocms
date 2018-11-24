package com.ternnetwork.baseframework.util;

import java.io.File;
import org.apache.commons.io.FileUtils;


public class ExtendedFileUtils extends FileUtils{
	
	
	public  static void makeDirs(String dirs){
		File saveDirFile = new File(dirs);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
	}

}
