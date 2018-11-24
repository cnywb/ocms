package com.ternnetwork.wechat.service.menu;



public interface MenuService {
	
	public  String queryDyMenu();
	public  String createDyMenu(String json);
	public  String deleteDyMenu();
	public  String queryQyMenu();
	public  String createQyMenu(String json);
	public  String deleteQyMenu();
	public  String queryFwMenu();
	public  String createFwMenu(String json);
	public  String deleteFwMenu();
	
}
