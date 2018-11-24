package com.ternnetwork.cms.model.file;

public class TreeFile {
	
	private String ext;//扩展名
	
	private String fileName;//文件名
	
	private String filePath;//文件路径
	
	private boolean isDirectory;//是否目录
	
	private Boolean showButtons;//是否显示操作按钮

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public Boolean getShowButtons() {
		return showButtons;
	}

	public void setShowButtons(Boolean showButtons) {
		this.showButtons = showButtons;
	}
	
	
	

}
