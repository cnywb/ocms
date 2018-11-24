package com.ternnetwork.cms.service.impl.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;





import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.config.ClusterService;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.baseframework.util.ExtendedFileUtils;
import com.ternnetwork.baseframework.util.ExtendedStringUtils;
import com.ternnetwork.cms.dao.page.PageComponentDao;
import com.ternnetwork.cms.model.page.PageComponent;
import com.ternnetwork.cms.service.page.PageComponentService;

@Service("pageComponentService")
public class PageComponentServiceImpl implements PageComponentService {
	
	@Resource
	private PageComponentDao pageComponentDao;
	
	@Resource
	private UserService userService;
	
	
	@Resource
	private ClusterService clusterService;
	
	public PageComponent findById(Long id,HttpServletRequest request) throws Exception{
		PageComponent t=pageComponentDao.findById(PageComponent.class, id);
		String realPath=request.getSession().getServletContext().getRealPath("/")+"/resources/component/"+t.getId()+"/";
		String jsfileName=realPath+t.getCode()+".js";
        File jsFile=new File(jsfileName);
        InputStream jsFileIs = new FileInputStream(jsFile);
		String javaScript=ExtendedStringUtils.inputStream2String(jsFileIs);
		t.setJavaScript(javaScript);
		String cssfileName=realPath+t.getCode()+".css";
        File cssFile=new File(cssfileName);
        InputStream cssFileIs = new FileInputStream(cssFile);
		String css=ExtendedStringUtils.inputStream2String(cssFileIs);
		t.setCss(css);
		return t;
	}
	
	public long idoAdd(PageComponent t) throws Exception{
		Long totalCount=pageComponentDao.getTotalCount("select count(t.id) from PageComponent t where t.code=?1",t.getCode());
		if(totalCount.longValue()>0L){
			return 0L;
		}
		t.setUser(userService.getCurrentUser());
		handleSourceCode(t);
		pageComponentDao.persist(t);
		createSourceCodeFile(t);
		return t.getId();
	}

	private void deleteSourceCodeFile(PageComponent t) throws IOException {
		String relativePath="/resources/component/"+t.getId()+"/";
		String jsfilePath=t.getRealPath()+relativePath;
		String jsfileName=t.getCode()+".js";
        File file=new File(jsfilePath,jsfileName);
		ExtendedFileUtils.deleteQuietly(file);
		String cssfilePath=t.getRealPath()+relativePath;
		String cssfileName=t.getCode()+".css";
        File cssFile=new File(cssfilePath,cssfileName);
		ExtendedFileUtils.deleteQuietly(cssFile);
		clusterService.deleteFromCluster(relativePath, jsfileName);//从集群中删除
		clusterService.deleteFromCluster(relativePath, cssfileName);
	}
	
	private void createSourceCodeFile(PageComponent t) throws IOException {
		String relativePath="/resources/component/"+t.getId()+"/";
		String jsfilePath=t.getRealPath()+relativePath;
		String jsfileName=t.getCode()+".js";
		ExtendedFileUtils.makeDirs(jsfilePath);
        File file=new File(jsfilePath,jsfileName);
   		ExtendedFileUtils.writeStringToFile(file, t.getJavaScript(),"UTF-8",false);
		String cssfilePath=t.getRealPath()+relativePath;
		String cssfileName=t.getCode()+".css";
        File cssFile=new File(cssfilePath,cssfileName);
		ExtendedFileUtils.writeStringToFile(cssFile, t.getCss(),"UTF-8",false);
		clusterService.saveToCluster(jsfilePath, relativePath, jsfileName);//保存到集群中
		clusterService.saveToCluster(cssfilePath, relativePath, cssfileName);
	}
    //为组件的容器加上name属性
	private void handleSourceCode(PageComponent t) {
		Document doc= Jsoup.parse(t.getSourceCode());
		List<Element> list=doc.select(".component_container");
		for(Element e:list){
			e.attr("name", t.getCode());
		}
		List<Element> list2=doc.select(".preview");
		for(Element e:list2){
			e.text(t.getName());
		}
		t.setSourceCode(doc.getElementsByTag("body").html());
	}
	
	public long idoUpdate(PageComponent t) throws Exception{
		Long totalCount=pageComponentDao.getTotalCount("select count(t.id) from PageComponent t where t.code=?1 and t.id!=?2",t.getCode(),t.getId());
		if(totalCount.longValue()>0L){
			return 0L;
		}
		PageComponent dbPageComponent=pageComponentDao.findById(PageComponent.class, t.getId());
		
		if(!dbPageComponent.getCode().equals(t.getCode())){
			deleteSourceCodeFile(t);
		}
		handleSourceCode(t);
		dbPageComponent.setCode(t.getCode());
		dbPageComponent.setMemo(t.getMemo());
		dbPageComponent.setSourceCode(t.getSourceCode());
		dbPageComponent.setName(t.getName());
		pageComponentDao.saveOrUpdate(dbPageComponent);
		createSourceCodeFile(t);
		return t.getId();
	}

	public long idoDeleteById(long id,HttpServletRequest request) throws IOException{
		PageComponent t=pageComponentDao.findById(PageComponent.class, id);
		if(t==null){
			return 0L;
		}
		t.setRealPath(request.getSession().getServletContext().getRealPath("/"));
		deleteSourceCodeFile(t);
		pageComponentDao.delete(t);
		return 1L;
	}
	
	public Page<PageComponent> query(Page page,String name){
		StringBuffer jpql=new StringBuffer("from  PageComponent t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(ExtendedStringUtils.isNotEmpty(name)){
			params.add(name+"%");
			jpql.append(" and t.name like?"+params.size());
		}
		return pageComponentDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page,params.toArray());
	}
	
	public String queryToJsonStr(Page page,String name){
		Page<PageComponent> result=query(page, name);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public List<PageComponent> findAll(){
		return pageComponentDao.findAll("from PageComponent", null);
	}

	public PageComponent findByCode(String  code){
	 List<PageComponent>	list=pageComponentDao.findAll("from PageComponent t where t.code=?1", code);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
