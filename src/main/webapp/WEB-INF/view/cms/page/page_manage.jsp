<%@ page language="java" import="java.util.*,com.ternnetwork.cms.enums.CmsPageStatus" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    <c:import url="/WEB-INF/view/include/color_admin_header.jsp"/>
    <link rel="stylesheet" href="<c:url value='/plugin/ztree/css/zTreeStyle/zTreeStyle.css' />" type="text/css">
    <link rel="stylesheet" href="<c:url value='/plugin/bootstrap-table/css/bootstrap-table.css' />">
  
  <script type="text/javascript">
  var userPath='<%=basePath%>';
  </script>
 </head>
   <body >
 
<div class="container-fluid">
	<div class="row-fluid">
	
		
	 <div class="col-md-12">
	 <br>
     <div class="form-inline" role="form">
       <div class="form-group">
           <label>所属站点</label>
            <select class="form-control" name="siteId" id="siteId">
                 <c:forEach var="item" items="${siteList}" varStatus="status">
                  <option code="${item.code}" value="${item.id}">${item.name}</option>
                 </c:forEach>
            </select>
        </div>
        
         <div class="form-group">
          <input type="text" data-toggle="modal" id="channelName" data-target="#treeModal"  class="form-control" placeholder="栏目">
        </div>
        <div class="form-group">
          <input type="text" id="queryKeyword" class="form-control" placeholder="页面名称">
        </div>
         <div class="form-group">
          <input type="text"  class="form-control" id="pageCode" placeholder="页面代码">
        </div>
         <div class="form-group">
         <span class="input-group-btn">
        <button class="btn btn-primary btn-sm" type="button" onclick="refreshTable();">查询</button>
      </span>
        </div>
    </div>
    <br>
      <div class="form-inline" role="form">
        <div class="form-group">
            <div class="input-group">
                 <button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#addModal">新建</button>
            </div>
            <div class="input-group">
                            <div class="btn-group m-r-5 m-b-5">
                               <%--   by: hw 20170919 由于福特安全要求，特注销以下上传功能 
								
								 	<a href="javascript:;" data-toggle="dropdown" class="btn btn-sm btn-primary dropdown-toggle" >模版管理<span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li><a href="javascript:void(0);" onclick="showFileChannelTreeModal('template');" >栏目模版管理</a></li>
										<li><a href="javascript:void(0);" onclick="includeTemplateManage();">代码片段模版管理</a></li>
										<li><a href="javascript:void(0);" onclick="indexTemplateManage();">首页模版管理</a></li>
									</ul>
								--%>
							</div>
			</div>
			 <div class="input-group">
                          <div class="btn-group m-r-5 m-b-5">
                             <%--  by: hw 20170919 由于福特安全要求，特注销以下上传功能 
								<a href="javascript:;" data-toggle="dropdown" class="btn btn-sm btn-primary dropdown-toggle" >资源管理<span class="caret"></span></a>
								<ul class="dropdown-menu">
									<li><a href="javascript:void(0);" onclick="showFileChannelTreeModal('resources');" >栏目资源管理</a></li>
									<li><a href="javascript:void(0);" onclick="includeResourcesManage();">代码片段资源管理</a></li>
									<li><a href="javascript:void(0);" onclick="indexResourcesManage();">首页资源管理</a></li>
								</ul>--%>
							</div>
			</div>							
          
        </div>
     </div>
     <br>
    <table id="data_table" data-url="<c:url value='/cms/page/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
    <thead> 
    </thead> 
    </table> 
	</div> 
	</div>
</div>



<div class="modal fade" role="dialog" id="addModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >新建</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="name" class="form-control" placeholder="名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">代码</span>
             <input type="text" name="code" class="form-control" placeholder="栏目内惟一" aria-describedby="sizing-addon2" >
             </div>
          </div> 
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">所属栏目</span>
             <input type="text"   name="channelName" class="form-control" data-toggle="modal" data-target="#addChannelTreeModal"  placeholder="所属栏目" aria-describedby="sizing-addon2" >
             <input type="hidden" name="channel.id"  />
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">关联模版</span>
             <select  class="form-control" name="template" >
             </select>
             </div>
          </div>      
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">状态</span>
             <select  class="form-control" name="status">
                  <%
                for(CmsPageStatus t: CmsPageStatus.values()){
	  		     out.println(" <option  value=\""+t.name()+"\">"+t.getName()+"</option>");
  	           }
               %>
             </select>
             </div>
          </div>    
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="save();">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade" role="dialog" id="editModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >编辑</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="name" class="form-control" placeholder="名称" aria-describedby="sizing-addon2" >
              <input type="hidden" name="id" />
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">代码</span>
             <input type="text" name="code" class="form-control" placeholder="栏目内惟一" aria-describedby="sizing-addon2" >
             </div>
          </div> 
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">所属栏目</span>
             <input type="text"   name="channelName" class="form-control" data-toggle="modal" data-target="#editChannelTreeModal"  placeholder="所属栏目" aria-describedby="sizing-addon2" >
             <input type="hidden" name="channel.id"  />
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">关联模版</span>
             <select  class="form-control" name="template">
             </select>
             </div>
          </div>      
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">状态</span>
             <select  class="form-control" name="status">
                  <%
                for(CmsPageStatus t: CmsPageStatus.values()){
	  		     out.println(" <option  value=\""+t.name()+"\">"+t.getName()+"</option>");
  	           }
               %>
             </select>
             </div>
          </div>    
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="update();">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
 
   <div class="modal fade " role="dialog" id="alertModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >警告</h4>
      </div>
      <div class="modal-body">
      <p>
	  </p>
      </div>
      <div class="modal-footer">
       <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
   
   
 <div class="modal fade " role="dialog" id="treeModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >选择栏目</h4>
      </div>
      <div class="modal-body">
      <ul id="channelTree" class="ztree" ></ul>
      </div>
      <div class="modal-footer">
       <button type="button" class="btn btn-primary" onclick="clearChannel();">清空</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


 <div class="modal fade " role="dialog" id="addChannelTreeModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >选择栏目</h4>
      </div>
      <div class="modal-body">
      <ul id="addChannelTree" class="ztree" ></ul>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div class="modal fade " role="dialog" id="editChannelTreeModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >选择栏目</h4>
      </div>
      <div class="modal-body">
      <ul id="editChannelTree" class="ztree" ></ul>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade " role="dialog" id="fileChannelTreeModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >选择所属栏目</h4>
      </div>
      <div class="modal-body">
      <ul id="fileChannelTree" class="ztree" ></ul>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

    <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
    <script src="<c:url value='/plugin/ztree/js/jquery.ztree.core-3.5.js' />"></script>
    <script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
    <script src="<c:url value='/resources/js/cms/page/page_manage.js' />"></script>
 
   
   </body>
</html>
