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
import com.ternnetwork.toolkit.dao.report.ReportDao;
import com.ternnetwork.toolkit.dao.report.ReportItemDao;
import com.ternnetwork.toolkit.enums.ReportSendFrequency;
import com.ternnetwork.toolkit.model.report.Report;
import com.ternnetwork.toolkit.service.report.ReportService;


@Service("reportService")
public class ReportServiceImpl implements ReportService {
	
	@Resource
	private ReportDao  reportDao;
	
	
	
	
	public Report findById(Long id){
		return reportDao.findById(Report.class, id);
	}
	
	public BaseResponse idoAdd(Report t){
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getName())){
			res.setStatus(0);
			res.setMessage("操作失败，名称不能为空！");
			return res;
		}
		if(!t.getSendFrequency().equals(ReportSendFrequency.NEVER)&&StringUtils.isEmpty(t.getReceiveEmail())){
			res.setStatus(0);
			res.setMessage("操作失败，发送频率不能为从不发送时接收邮箱不能为空！");
			return res;
		}
		long totalCount=reportDao.getTotalCount("select count(id) from Report where name=?1", t.getName());
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，名称已经存在，请用其它名称！");
			return res;
		}
		reportDao.persist(t);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	public BaseResponse idoUpdate(Report t){
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getName())){
			res.setStatus(0);
			res.setMessage("操作失败，名称不能为空！");
			return res;
		}
		if(!t.getSendFrequency().equals(ReportSendFrequency.NEVER)&&StringUtils.isEmpty(t.getReceiveEmail())){
			res.setStatus(0);
			res.setMessage("操作失败，发送频率不能为从不发送时接收邮箱不能为空！");
			return res;
		}
		long totalCount=reportDao.getTotalCount("select count(id) from Report where name=?1 and id!=?2", t.getName(),t.getId());
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，名称已经存在，请用其它名称！");
			return res;
		}
		reportDao.saveOrUpdate(t);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	public BaseResponse idoDelete(long id){
		BaseResponse res=new BaseResponse();
		Report t=reportDao.findById(Report.class, id);
      	if(t==null){
      		res.setStatus(0);
      		res.setMessage("操作失败，对象不存在！");
      		return res;
      	}
       	reportDao.delete(t);
    	res.setStatus(1);
  		res.setMessage("操作成功！");
  		return res;
    }
	

	
	   public Page<Report> query(Page page,String name){
		   
		   
			StringBuffer jpql=new StringBuffer("from  Report t where 1=1");
			List<Object> params=new ArrayList<Object>();
			if(ExtendedStringUtils.isNotEmpty(name)){
				params.add(name+"%");
				jpql.append(" and t.name like?"+params.size());
			}
			return reportDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page,params.toArray());
		}
	   public BootstrapGrid queryToBootstrapGrid(Page page,String name){
			Page<Report> result=query(page, name);
			return new BootstrapGrid(result);
	   }
		public List<Report>findAll(ReportSendFrequency sendFrequency){
			return	reportDao.findAll("from Report where sendFrequency=?1", sendFrequency);
		}
}
