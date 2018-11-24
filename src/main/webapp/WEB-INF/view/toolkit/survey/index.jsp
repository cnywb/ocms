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
   <link rel="stylesheet" href="<c:url value='/plugin/bootstrap-table/css/bootstrap-table.css' />">
   <link rel="stylesheet" href="<c:url value='/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css' />">
  
  <script type="text/javascript">
  var userPath='<%=basePath%>';
  </script>
 
 </head>
   <body >
 
<div class="container-fluid">
    <br>
    <div class="row-fluid">
     <div class="col-md-12">
    <div class="panel panel-inverse">
      <div class="panel-heading">
        <h3 class="panel-title">查询条件</h3>
      </div>
      <div class="panel-body" id="query_con">
        <div class="form-inline" role="form">
        <div class="form-group">
          <input type="text" name="code" class="form-control" placeholder="活动代码">
        </div>
         <div class="form-group">
          <input type="text" name="name" class="form-control" placeholder="活动名称">
        </div>
         <div class="form-group">
           <label>是否启用</label>
           <select  class="form-control" name="enable"><option value="">不限</option><option value="true">是</option><option value="false">否</option></select>
        </div>
        <div class="form-group">
          <input type="text" name="startTime" class="form_datetime form-control" placeholder="活动开始时间">
          </div>
            <div class="form-group">
             <input type="text" name="endTime" class="form_datetime form-control" placeholder="活动结束时间">
            </div>
            <div class="form-group">
           <button class="btn btn-primary" type="button" onclick="refreshTable();">查询</button>
            </div>
           </div>
      </div>
      </div>
   </div>
 </div>


  <div class="row-fluid">
  <div class="span12">
   
    <br>
    <table id="data_table"  data-url="<c:url value='/toolkit/survey/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
    <thead> 
   
    </thead> 
   </table> 
     
     </div>
	</div>
	
	
</div>





   
   
 
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
   
  
<c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
<script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js' />"></script>
<script src="<c:url value='/resources/js/toolkit/survey/index.js' />"></script>
   
   </body>
</html>
