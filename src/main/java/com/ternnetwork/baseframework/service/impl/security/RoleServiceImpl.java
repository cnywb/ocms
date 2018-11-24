/**
 * 
 */
package com.ternnetwork.baseframework.service.impl.security;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.dao.security.RoleDao;
import com.ternnetwork.baseframework.model.security.Role;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.security.RoleService;







/**
 *
 * @author wenfeng.xu
 *2011-4-24下午09:33:05
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleDao roleDao;
	
	public long idoAdd(Role t) {
	
	   List<Role>  list=findAll("from Role t where t.nameEn=?1", t.getNameEn());
	   if(list.size()>0){
		   return 0L;
	   }	
		
	   roleDao.persist(t);
		
       return t.getId();
	}

	public long idoUpdate(Role t) {
		List<Role>  list=findAll("from Role t where t.nameEn=?1 and t.id!=?2", t.getNameEn(),t.getId());
		   if(list.size()>0){
			   return 0L;
		}	
		roleDao.saveOrUpdate(t);

		return 1L;
	}

	public void delete(Role t) {
		roleDao.delete(t);
	}

	
	public List<Role> findAll(String jpql, Object... param) {
		return roleDao.findAll(jpql, param);
	}

	public Role findById(long id) {
		return roleDao.findById(Role.class, id);
	}

	public Role getReferenceById(long id) {
		return roleDao.getReferenceById(Role.class, id);
	}

	public Long getTotalCount(String countJpql, Object... param) {
		return roleDao.getTotalCount(countJpql, param);
	}

	public String query(Page page){
		
		String jpql="from Role t";
		Page<Role> result=roleDao.query("select count(t.id) from Role t",jpql, page,null);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	



}
