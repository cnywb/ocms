package com.ternnetwork.baseframework.service.impl.org;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.org.DepartmentDao;
import com.ternnetwork.baseframework.model.org.Department;
import com.ternnetwork.baseframework.model.ui.DepartmentZtree;
import com.ternnetwork.baseframework.service.org.DepartmentService;

@Repository("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

	
	@Autowired
	private DepartmentDao departmentDao;
	
	
	public void idoAdd(String nameZh,String nameEn,long parentId,Integer seqNum){
		Department t=new Department(nameZh, nameEn, parentId, seqNum);
		departmentDao.persist(t);
	}
	
	public Department idoAdd(Department t){
		Department pt=departmentDao.findById(Department.class,t.getParentId());
		if(pt==null){
			t.setMessage("父节点不存在!");
			t.setStatus(0);
		}
		departmentDao.persist(t);
		t.setMessage("保存成功!");
		t.setStatus(1);
		return t;
	}
	
	public Department idoUpdate(Department t){
		Department dbt=departmentDao.findById(Department.class,t.getId());
		dbt.setNameZh(t.getNameZh());
		dbt.setNameEn(t.getNameEn());
		dbt.setSeqNum(t.getSeqNum());
		departmentDao.saveOrUpdate(dbt);
		t.setMessage("保存成功!");
		t.setStatus(1);
		return t;
	}
	
	public String idoDeleteById(long id){
		Department t=departmentDao.findById(Department.class, id);
		if(t==null){
			return "1";
		}else if(t.getUsers().size()>0){
			return "2";
		}
		if(t.getParentId()==0L){
			return "4";
		}
		List<Department> resultList=departmentDao.findAll("from Department t where t.parentId=?1",id);
		if(resultList.size()>0){
			return "0";
		}
		departmentDao.delete(t);
		return  "3";
	}
	
	
	
	public String getDepartmentTreeJSON(long parentId)
	{
		List<Department> result=departmentDao.findAll("from Department sm where sm.parentId=?1 order by sm.seqNum",parentId);
		StringBuffer retVal=new StringBuffer("[");
		for(int i=0;i<result.size();i++)
		{
			Department department=(Department)result.get(i);
			boolean leaf=false;
			if(i!=0){
				retVal.append(",");	
			}
			retVal.append("{\"seq\":"+department.getSeqNum()+",cls:\"folder\",id:"+department.getId()+",text:\""+department.getNameZh()+"\",leaf:"+leaf+",nameEn:\""+department.getNameEn()+"\",parentId:\""+department.getParentId()+"\",children:");
			retVal.append(getDepartmentTreeJSON(department.getId()));
			retVal.append("}");
		}
		retVal.append("]");
		return retVal.toString();
	}
	
	
	public String getZTreeJSON(){
		List<Department> result=departmentDao.findAll("from Department sm order by sm.seqNum");
		List<DepartmentZtree> treeList=new ArrayList<DepartmentZtree>();
		for(int i=0;i<result.size();i++){
			Department t=(Department)result.get(i);
			DepartmentZtree tree=new DepartmentZtree();
			tree.setId(t.getId());
			tree.setpId(t.getParentId());
			tree.setName(t.getNameZh());
			tree.setNameEn(t.getNameEn());
			tree.setSeqNum(t.getSeqNum());
			treeList.add(tree);
		}
		return treeList.toString();
	}
	
	public List<Department> findAll(String jpql,Object ...param){
		return departmentDao.findAll(jpql, param);
	}
}
