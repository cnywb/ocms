package com.ternnetwork.wechat.service.user;





public interface GroupService {
	
	public String queryDyGroup();
	
	public String createDyGroup(String json);
	
	public String deleteDyGroup(String json);
	
	public String updateDyGroup(String json);
	
	public String queryFwGroup();
	
	public String createFwGroup(String json);
	
	public String deleteFwGroup(String json);
	
	public String updateFwGroup(String json);
	

}
