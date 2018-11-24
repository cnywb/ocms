package com.ternnetwork.baseframework.dao.impl.org;
import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.dao.org.DepartmentDao;
import com.ternnetwork.baseframework.model.org.Department;




@Repository("departmentDao")
public class DepartmentDaoImpl extends IBaseDaoImpl<Department> implements DepartmentDao {


}
