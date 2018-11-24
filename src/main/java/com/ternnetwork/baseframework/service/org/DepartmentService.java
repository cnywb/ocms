package com.ternnetwork.baseframework.service.org;

import java.util.List;

import com.ternnetwork.baseframework.model.org.Department;







public interface DepartmentService {
	
	public void idoAdd(String nameZh,String nameEn,long parentId,Integer seqNum);
	public Department idoAdd(Department t);
	public Department idoUpdate(Department t);
	public String getDepartmentTreeJSON(long parentId);
	public String getZTreeJSON();
	public String idoDeleteById(long id);
	public List<Department> findAll(String jpql,Object ...param);

}
