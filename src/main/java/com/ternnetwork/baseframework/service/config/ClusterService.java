package com.ternnetwork.baseframework.service.config;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import com.ternnetwork.baseframework.model.config.ServerNode;
import com.ternnetwork.baseframework.model.config.WebAppInstance;
import com.ternnetwork.baseframework.model.ui.ClusterZtree;

public interface ClusterService {
	    public long idoAddServerNode(ServerNode t);
		
		public long idoUpdateServerNode(ServerNode t);
		
		public long idoDeleteServerNode(long id);
		
		public long idoAddWebAppInstance(WebAppInstance t);

		public long idoUpdateWebAppInstance(WebAppInstance t);
		
		public long idoDeleteWebAppInstance(long id);
		
		public List<ClusterZtree>getZtreeList();
		
		public 	List<WebAppInstance> findAllWebAppInstance();
		
		public WebAppInstance findWebAppInstanceByName(String name);
		
		public void saveToCluster(String localRealFilePath,String relativeFilePath,String fileName) throws SocketException, IOException;
		
		public void deleteFromCluster(String relativeFilePath,String fileName) throws SocketException, IOException;
		
		public void createDirectoryOnCluster(String relativeFilePath) throws SocketException, IOException;
		
		public void deleteDirectoryOnCluster(String relativeFilePath) throws SocketException, IOException;

}
