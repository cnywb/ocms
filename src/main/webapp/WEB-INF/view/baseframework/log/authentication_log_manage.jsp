<%@ page language="java" import="java.util.*,com.ternnetwork.cms.enums.CmsPageStatus,com.ternnetwork.cms.enums.CmsPageType" pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" href="<c:url value='/plugin/bootstrap-table/css/bootstrap-table.css' />">
    <link rel="stylesheet" href="<c:url value='/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css' />">
  
  <script type="text/javascript">
  var userPath='<%=basePath%>';
  </script>
 </head>
   <body >
 
<div class="container-fluid">


	
	<div class="row-fluid">
	<br>
	 <div class="col-md-12">
	
	<div class="panel panel-inverse">
      <div class="panel-heading">
        <h3 class="panel-title">查询条件</h3>
      </div>
      <div class="panel-body" id="query_con">
        <div class="form-inline" role="form">
         
        <div class="form-group">
          <input type="text" name="userName" class="form-control" placeholder="登录账号">
        </div>
         <div class="form-group">
           <label>登录结果</label>
           <select  class="form-control" name="result"><option value="">不限</option><option value="SUCCESS">成功</option><option value="FAILURE">失败</option></select>
                          
        </div>
        <div class="form-group">
          <input type="text" name="createTimeMin" class="form_datetime form-control" placeholder="登录开始日期">
          </div>
            <div class="form-group">
             <input type="text" name="createTimeMax" class="form_datetime form-control" placeholder="登录结束日期">
            </div>
           </div>
      </div>
       <div class="panel-footer panel-info">
      
        <button class="btn btn-primary" type="button" onclick="refreshTable();">查询</button>
      
        </div>
    </div>
    <table id="data_table" data-url="<c:url value='/baseframework/log/authentication/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
    <thead></thead> 
    </table> 
     </div> 
	</div>
	
	
</div>




 
   <div class="modal fade " role="dialog" id="editModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >用户详情</h4>
      </div>
      <div class="modal-body">
      <div class="container-fluid">
        <div class="row">
             <div class="col-md-12 input-group">
               <span class="input-group-addon" id="sizing-addon1">头像</span>
             <img alt="" id="photo" src="" width="100" height="100">
             
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">用户名</span>
             <input type="text" name="name" class="form-control" readonly="readonly" />
             </div>
          </div>
             <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">微信ID</span>
             <input type="text" name="wechatId" class="form-control" readonly="readonly" />
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">昵称</span>
             <input type="text" name="nickname" class="form-control" readonly="readonly" />
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">性别</span>
             <input type="text" name="genderName" class="form-control" readonly="readonly" />
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">省</span>
             <input type="text" name="province" class="form-control" readonly="readonly" />
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">市</span>
             <input type="text" name="city" class="form-control" readonly="readonly" />
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">县</span>
             <input type="text" name="country" class="form-control" readonly="readonly" />
             </div>
          </div>
         
      </div>
      
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
   
     <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
    <script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
    <script src="<c:url value='/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js' />"></script>
    <script src="<c:url value='/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js' />"></script>
    <script src="<c:url value='/resources/js/baseframework/log/authentication_log_manage.js' />"></script>
 
 
   
   </body>
</html>
