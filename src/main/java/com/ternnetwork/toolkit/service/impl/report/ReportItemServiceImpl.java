package com.ternnetwork.toolkit.service.impl.report;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.ExtendedStringUtils;
import com.ternnetwork.toolkit.dao.report.ReportItemDao;
import com.ternnetwork.toolkit.model.report.ReportItem;
import com.ternnetwork.toolkit.service.report.ReportItemService;



@Service("reportItemService")
public class ReportItemServiceImpl implements ReportItemService {
	
	@Resource
	private ReportItemDao reportItemDao;
	
	public BaseResponse idoAdd(ReportItem t){
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getName())){
			res.setStatus(0);
			res.setMessage("操作失败，名称不能为空！");
			return res;
		}
		if(StringUtils.isEmpty(t.getHeader())){
			res.setStatus(0);
			res.setMessage("操作失败，表头不能为空！");
			return res;
		}
		if(!t.getHeader().contains(",")){
			res.setStatus(0);
			res.setMessage("操作失败，表头至少要两列！");
			return res;
		}
		if(StringUtils.isEmpty(t.getSql())){
			res.setStatus(0);
			res.setMessage("操作失败，查询语句不能为空！");
			return res;
		}
		if(!t.getSql().contains(",")){
			res.setStatus(0);
			res.setMessage("操作失败，查询语句返回至少要两列！");
			return res;
		}
		long totalCount=reportItemDao.getTotalCount("select count(id) from ReportItem where name=?1 and report.id=?2", t.getName(),t.getReport().getId());
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，名称已经存在，请用其它名称！");
			return res;
		}
		reportItemDao.persist(t);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	public BaseResponse idoUpdate(ReportItem t){
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getName())){
			res.setStatus(0);
			res.setMessage("操作失败，名称不能为空！");
			return res;
		}
		if(!t.getHeader().contains(",")){
			res.setStatus(0);
			res.setMessage("操作失败，表头至少要两列！");
			return res;
		}
		if(StringUtils.isEmpty(t.getSql())){
			res.setStatus(0);
			res.setMessage("操作失败，查询语句不能为空！");
			return res;
		}
		if(!t.getSql().contains(",")){
			res.setStatus(0);
			res.setMessage("操作失败，查询语句返回至少要两列！");
			return res;
		}
		long totalCount=reportItemDao.getTotalCount("select count(id) from ReportItem where name=?1 and report.id=?2 and id!=?3", t.getName(),t.getReport().getId(),t.getId());
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，名称已经存在，请用其它名称！");
			return res;
		}
		reportItemDao.saveOrUpdate(t);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	public BaseResponse idoDelete(long id){
		BaseResponse res=new BaseResponse();
		ReportItem t=reportItemDao.findById(ReportItem.class, id);
      	if(t==null){
      		res.setStatus(0);
      		res.setMessage("操作失败，对象不存在！");
      		return res;
      	}
      	reportItemDao.delete(t);
    	res.setStatus(1);
  		res.setMessage("操作成功！");
  		return res;
    }
	
	 public Page<ReportItem> query(Page page,String name,String reportId){
			StringBuffer jpql=new StringBuffer("from  ReportItem t where 1=1");
			List<Object> params=new ArrayList<Object>();
			if(ExtendedStringUtils.isNotEmpty(name)){
				params.add(name+"%");
				jpql.append(" and t.name like?"+params.size());
			}
			if(ExtendedStringUtils.isNotEmpty(reportId)){
				params.add(Long.parseLong(reportId));
				jpql.append(" and t.report.id=?"+params.size());
			}
			return reportItemDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page,params.toArray());
	}

	public BootstrapGrid queryToBootstrapGrid(Page page,String name,String reportId){
			Page<ReportItem> result=query(page, name,reportId);
			BootstrapGrid grid=new BootstrapGrid(result);
			return grid;
	}

}
