package com.ternnetwork.baseframework.service.impl.config;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ternnetwork.baseframework.dao.config.ServerNodeDao;
import com.ternnetwork.baseframework.dao.config.WebAppInstanceDao;
import com.ternnetwork.baseframework.model.config.ServerNode;
import com.ternnetwork.baseframework.model.config.WebAppInstance;
import com.ternnetwork.baseframework.model.ui.ClusterZtree;
import com.ternnetwork.baseframework.service.config.ClusterService;
import com.ternnetwork.baseframework.util.FtpUtils;
import com.ternnetwork.baseframework.util.PropUtils;


@Service("clusterService")
public class ClusterServiceImpl implements ClusterService {
	
	@Resource
	private ServerNodeDao serverNodeDao;
	
	@Resource
	private WebAppInstanceDao webAppInstanceDao;
	
	
	public long idoAddServerNode(ServerNode t){
	   long totaCount=serverNodeDao.getTotalCount("select count(id) from ServerNode where ip=?1", t.getIp());
	   if(totaCount>0){
		   return 0;
	   }
	   serverNodeDao.persist(t);
	   return 1;
	}
	
	public long idoUpdateServerNode(ServerNode t){
	long totaCount=serverNodeDao.getTotalCount("select count(id) from ServerNode where ip=?1 and id!=?2", t.getIp(),t.getId());
	   if(totaCount>0){
		   return 0L;
	   }
	   serverNodeDao.saveOrUpdate(t);
	   return 1L;
	}
	
	public long idoDeleteServerNode(long id){
		ServerNode t=serverNodeDao.findById(ServerNode.class, id);
		if(t==null){
			return 0L;
		}
		List<WebAppInstance> list=webAppInstanceDao.findAll("from WebAppInstance where serverNode.id=?1", id);
		for(WebAppInstance i:list){
		    i=webAppInstanceDao.findById(WebAppInstance.class,i.getId());
			webAppInstanceDao.delete(i);
		}
		serverNodeDao.delete(t);
		return 1L;
    }
	
	
	public long idoAddWebAppInstance(WebAppInstance t){
		 long totaCount=webAppInstanceDao.getTotalCount("select count(id) from WebAppInstance where name=?1", t.getName());
		   if(totaCount>0){
			   return 0L;
		   }
		   webAppInstanceDao.persist(t);
		   return 1L;
	}

	public long idoUpdateWebAppInstance(WebAppInstance t){
		 long totaCount=webAppInstanceDao.getTotalCount("select count(id) from WebAppInstance where name=?1 and id!=?2", t.getName(),t.getId());
		   if(totaCount>0){
			   return 0L;
		   }
		   webAppInstanceDao.saveOrUpdate(t);
		   return 1L;
	}
	
	public long idoDeleteWebAppInstance(long id){
		   WebAppInstance t=webAppInstanceDao.findById(WebAppInstance.class, id);
		   if(t==null){
			   return 0L;
		   }
		   webAppInstanceDao.delete(t);
		   return 1L;
	}
	
	public List<ClusterZtree>getZtreeList(){
		List<ClusterZtree> retVal=new ArrayList<ClusterZtree>();
		List<ServerNode> nodeList=serverNodeDao.findAll("from ServerNode", null);
		for(ServerNode n:nodeList){
			ClusterZtree tree=new ClusterZtree();
			 tree.setId(String.valueOf(n.getId()));
			  tree.setName(n.getName());
			  tree.setIp(n.getIp());
			  tree.setpId("0");
			  tree.setIsParent(true);
			  tree.setRealId(n.getId());
			  retVal.add(tree);
			  List<WebAppInstance> instanceList=webAppInstanceDao.findAll("from WebAppInstance where serverNode.id=?1", n.getId());
			 for(WebAppInstance instance:instanceList){
				  ClusterZtree instanceTree=new ClusterZtree();
				  instanceTree.setId("i"+String.valueOf(instance.getId()));
				  instanceTree.setName(instance.getName());
				  instanceTree.setpId(String.valueOf(n.getId()));
				  instanceTree.setParentId(n.getId());
				  instanceTree.setEnable(instance.getEnable());
				  instanceTree.setFtpPort(instance.getFtpPort());
				  instanceTree.setFtpPassword(instance.getFtpPassword());
				  instanceTree.setIsParent(false);
				  instanceTree.setRealId(Long.valueOf(instance.getId()));
				  retVal.add(instanceTree);
			  }
	    }
		return retVal;
	}
	
	public 	List<WebAppInstance> findAllWebAppInstance(){
		return webAppInstanceDao.findAll("from WebAppInstance",null);
	}
	
	public 	WebAppInstance findWebAppInstanceByName(String name){
		List<WebAppInstance> list=webAppInstanceDao.findAll("from WebAppInstance where name=?1",name);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public 	List<WebAppInstance> findAllWebAppInstanceExceptName(String name){
		return webAppInstanceDao.findAll("from WebAppInstance where name!=?1",name);
	}
	
	public void saveToCluster(String localRealFilePath,String relativeFilePath,String fileName) throws SocketException, IOException{
		String name=PropUtils.getPropertyValue("webapp.properties","instanceName");
		List<WebAppInstance> list=findAllWebAppInstanceExceptName(name);
		for(WebAppInstance t:list){
			FtpUtils ftpUtil=new FtpUtils();
		    ftpUtil.connectServer(t.getServerNode().getIp(),t.getFtpPort(),t.getName(), t.getFtpPassword(), null);
		    ftpUtil.createDirectory(relativeFilePath);
		    ftpUtil.changeDirectory(relativeFilePath);
		    ftpUtil.uploadFile(localRealFilePath+"/"+fileName,fileName);
		    ftpUtil.closeServer();
		}
	}
	
	public void deleteFromCluster(String relativeFilePath,String fileName) throws SocketException, IOException{
		String name=PropUtils.getPropertyValue("webapp.properties","instanceName");
		List<WebAppInstance> list=findAllWebAppInstanceExceptName(name);
		for(WebAppInstance t:list){
			FtpUtils ftpUtil=new FtpUtils();
		    ftpUtil.connectServer(t.getServerNode().getIp(),t.getFtpPort(),t.getName(), t.getFtpPassword(), null);
		    ftpUtil.changeDirectory(relativeFilePath);
		    ftpUtil.deleteFile(fileName);
		    ftpUtil.closeServer();
		}
	}
	
	public void createDirectoryOnCluster(String relativeFilePath) throws SocketException, IOException{
		String name=PropUtils.getPropertyValue("webapp.properties","instanceName");
		List<WebAppInstance> list=findAllWebAppInstanceExceptName(name);
		for(WebAppInstance t:list){
			FtpUtils ftpUtil=new FtpUtils();
		    ftpUtil.connectServer(t.getServerNode().getIp(),t.getFtpPort(),t.getName(), t.getFtpPassword(), null);
		    ftpUtil.createDirectory(relativeFilePath);
		    ftpUtil.closeServer();
		}
	}
	
	public void deleteDirectoryOnCluster(String relativeFilePath) throws SocketException, IOException{
		String name=PropUtils.getPropertyValue("webapp.properties","instanceName");
		List<WebAppInstance> list=findAllWebAppInstanceExceptName(name);
		for(WebAppInstance t:list){
			FtpUtils ftpUtil=new FtpUtils();
		    ftpUtil.connectServer(t.getServerNode().getIp(),t.getFtpPort(),t.getName(), t.getFtpPassword(), null);
		    ftpUtil.removeDirectory(relativeFilePath,true);
		    ftpUtil.closeServer();
		}
	}

}
