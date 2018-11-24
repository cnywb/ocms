package com.ternnetwork.baseframework.model.http;

import java.util.ArrayList;
import java.util.List;

import com.ternnetwork.baseframework.model.ui.KindEditorFile;

public class KindEditorFileResponse {

	private String moveup_dir_path;
	private String current_dir_path;
	private String current_url;
	private Integer total_count;
	private List<KindEditorFile> file_list=new ArrayList<KindEditorFile>();
	public String getMoveup_dir_path() {
		return moveup_dir_path;
	}
	public void setMoveup_dir_path(String moveup_dir_path) {
		this.moveup_dir_path = moveup_dir_path;
	}
	public String getCurrent_dir_path() {
		return current_dir_path;
	}
	public void setCurrent_dir_path(String current_dir_path) {
		this.current_dir_path = current_dir_path;
	}
	public String getCurrent_url() {
		return current_url;
	}
	public void setCurrent_url(String current_url) {
		this.current_url = current_url;
	}
	public Integer getTotal_count() {
		return total_count;
	}
	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}
	public List<KindEditorFile> getFile_list() {
		return file_list;
	}
	public void setFile_list(List<KindEditorFile> file_list) {
		this.file_list = file_list;
	}
	
	
	
}
