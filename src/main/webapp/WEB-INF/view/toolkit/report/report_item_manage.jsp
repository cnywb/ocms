<%@ page language="java" import="java.util.*,com.ternnetwork.toolkit.enums.ReportFormat,com.ternnetwork.toolkit.enums.ReportSendFrequency" pageEncoding="UTF-8"%>
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
   <script type="text/javascript">
  var userPath='<%=basePath%>';
  </script>
 </head>
   <body >
 
<div class="container-fluid">


	
	<div class="row-fluid">
	<br>
	 <div class="col-md-12">
	
	
    <div class="form-inline" role="form">
        <div class="form-group">
            <div class="input-group">
                 <button type="button" class="btn btn-success" data-toggle="modal" data-target="#addModal">新建</button>
            </div>
        </div>
        <div class="form-group" id="query_con">
          <input type="text" name="name" class="form-control" placeholder="输入名称">
          <input type="hidden" name="reportId"  value="${reportId}" id="reportId" >
        </div>
         <div class="form-group">
        
      <span class="input-group-btn">
        <button class="btn btn-primary" type="button" onclick="refreshTable();">查询</button>
      </span>
        </div>
    </div>
     <br>
      <table id="data_table" data-url="<c:url value='/toolkit/report/item/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
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
             <input type="text" name="name" class="form-control" placeholder="名称，同一报告内不能重复" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">表头</span>
             <input type="text" name="header" class="form-control" placeholder="请用英文逗号分隔,表头至少要两列" aria-describedby="sizing-addon2" >
             </div>
          </div>
         
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">SQL</span>
             <textarea  class="form-control" rows="10" name="sql" placeholder="当发送频率为每天时用\${startTime}占位符可以获取系统前一天的00:00:00，用\${endTime}可以获取前一天23:59:59；当发送频率为每周时用\${startTime}占位符可以获取上周一的00:00:00，用\${endTime}可以获取上周日的23:59:59；当发送频率为每月时用\${startTime}占位符可以获取上月第一天的00:00:00，用\${endTime}可以获取上月最后一天的23:59:59"></textarea>
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
             <input type="hidden" name="id" >
             <input type="text" name="name" class="form-control" placeholder="名称，同一报告内不能重复" aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">表头</span>
             <input type="text" name="header" class="form-control" placeholder="请用英文逗号分隔,表头至少要两列" aria-describedby="sizing-addon2" >
             </div>
          </div>
          
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">SQL</span>
             <textarea  class="form-control" rows="10" name="sql" placeholder="当发送频率为每天时用\${startTime}占位符可以获取系统前一天的00:00:00，用\${endTime}可以获取前一天23:59:59；当发送频率为每周时用\${startTime}占位符可以获取上周一的00:00:00，用\${endTime}可以获取上周日的23:59:59；当发送频率为每月时用\${startTime}占位符可以获取上月第一天的00:00:00，用\${endTime}可以获取上月最后一天的23:59:59"></textarea>
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
   
    <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
    <script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
    <script src="<c:url value='/resources/js/toolkit/report/report_item_manage.js' />"></script>
    <script src="<c:url value='/plugin/blockui/jquery.blockUI.js' />"></script>
 
   
   </body>
</html>
