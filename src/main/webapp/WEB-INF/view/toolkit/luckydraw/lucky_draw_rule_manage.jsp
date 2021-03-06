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
 


	<div class="row-fluid">
		<div class="span12">

   <br>
    <div class="form-inline" role="form">
        <div class="form-group">
            <div class="input-group">
                 <button type="button" class="btn btn-success" data-toggle="modal" data-target="#addModal">新建</button>
            </div>
        </div>
     </div>
     <br>
    <table id="data_table"  data-url="<c:url value='/toolkit/luckydraw/rule/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
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
             <span class="input-group-addon" id="sizing-addon1">选择奖品</span>
             <select class="form-control" name="award.id">
              
             </select>
             </div>
            </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">出奖数量</span>
             <input type="text" name="awardQty" class="form-control" placeholder="出奖数量" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">出奖时间</span>
             <input type="text" name="awardDate"class="form_datetime form-control" placeholder="请选择出奖时间" aria-describedby="sizing-addon2" >
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
             <input type="hidden" name="id" >
             <span class="input-group-addon" id="sizing-addon1">选择奖品</span>
             <select class="form-control" name="award.id" >
              
             </select>
             </div>
            </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">出奖数量</span>
             <input type="text" name="awardQty" class="form-control" placeholder="出奖数量" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">出奖时间</span>
             <input type="text" name="awardDate"class="form_datetime form-control" placeholder="请选择出奖时间" aria-describedby="sizing-addon2" >
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
   
<input  type="hidden" id="luckyDrawId" value="${luckyDrawId}"/> 
<c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
<script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js' />"></script>
<script src="<c:url value='/resources/js/toolkit/luckydraw/lucky_draw_rule_manage.js' />"></script>
   
   </body>
</html>
