<%@ page language="java" import="java.util.*,com.ternnetwork.baseframework.enums.RoleType" pageEncoding="UTF-8"%>
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
          <input type="text" id="queryKeyword" class="form-control" placeholder="标题">
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
                 <button type="button" class="btn btn-success btn-sm"  id="btnAdd">新建</button>
            </div>
            
            <div class="input-group">
             <%-- by: hw 20170919 由于福特安全要求，特注销以下上传功能 
              <button type="button" class="btn btn-primary btn-sm" onclick="templateManage();" >模版文件管理</button> --%>
            </div>
             <div class="input-group">
               <%--  by: hw 20170919 由于福特安全要求，特注销以下上传功能 
               <button type="button" class="btn btn-primary btn-sm" onclick="resourcesManage();" >资源文件管理</button> --%>
            </div>
        </div>
     </div>
     <br>
     
    <table id="data_table" data-url="<c:url value='/cms/content/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
   
   </table> 
   
     </div> 
	</div>
	
	
</div>

 <div class="modal fade " role="dialog" id="treeModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >选择栏目</h4>
      </div>
      <div class="modal-body">
      <ul id="treeDemo" class="ztree" ></ul>
      </div>
      <div class="modal-footer">
       <button type="button" class="btn btn-primary" onclick="clearChannel();">清空</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


    <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
    <script src="<c:url value='/plugin/ztree/js/jquery.ztree.core-3.5.js' />"></script>
    <script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
    <script src="<c:url value='/resources/js/cms/content/content_manage.js' />"></script>
 
 
   
   </body>
</html>
