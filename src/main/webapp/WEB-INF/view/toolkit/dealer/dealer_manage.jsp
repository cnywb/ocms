<%@ page language="java" import="java.util.*,com.ternnetwork.toolkit.enums.CampaignDataSendFrequency" pageEncoding="UTF-8"%>
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
   <link rel="stylesheet" href="<c:url value='/plugin/kindeditor/themes/default/default.css' />" />
   <link rel="stylesheet" href="<c:url value='/plugin/kindeditor/plugins/code/prettify.css' />" />
   <link rel="stylesheet" href="<c:url value='/plugin/bootstrap-table/css/bootstrap-table.css' />">
   <link rel="stylesheet" href="<c:url value='/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css' />">
   <link rel="stylesheet" href="https://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
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
          <input type="text" name="dealerCode" class="form-control" placeholder="经销商代码">
        </div>
        <div class="form-group">
          <input type="text" name="dealerName" class="form-control" placeholder="经销商名称">
        </div>
        <div class="form-group">
           <label>是否需要重新定位</label>
           <select  class="form-control" name="addressUpdated"><option value="">不限</option><option value="true">是</option><option value="false">否</option></select>
        </div>
          <div class="form-group">
           <button class="btn btn-primary" type="button" onclick="refreshTable();">查询</button>
          </div>
          <div class="form-group">
                         <button type="button" class="btn btn-primary" id="btn_upload" >导入</button>
                         <button type="button" class="btn btn-primary" onclick="window.location.href='toolkit/dealer/manageTreeView.htm';" >切换为树视图</button>
           </div>
           </div>
      </div>
      </div>
   </div>
 </div>


	<div class="row-fluid">
		<div class="span12">

  
     <br>
    <table id="data_table"  data-url="<c:url value='/toolkit/dealer/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
    <thead> 
   
    </thead> 
   </table> 
     
     </div>
	</div>
	
	
</div>




<div class="modal fade" role="dialog" id="editModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >编辑</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row" >
             <div class="col-md-12" id="bdmap" style="height: 300px">
                
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">城市/区域</span>
             <input type="hidden" name="id" />
             <input type="text" name="cityOrTerritory" id="edit_cityOrTerritory" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">福特品牌店地址</span>
             <input type="text" name="fordBrandShopAddress" id="edit_fordBrandShopAddress" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
           <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">经度</span>
             <input type="text" name="longitude" id="edit_longitude" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
           <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">纬度</span>
             <input type="text" name="latitude" id="edit_latitude" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
          
         
       </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="searchAddress();">定位</button>
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
<div id="btnc04">
<input type="button" value="上传文件" id="btn_upload_file" >
</div>
  
  <div id="sql"></div>
<c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
<script charset="utf-8" src="<c:url value='/plugin/kindeditor/kindeditor.js' />"></script>
<script charset="utf-8" src="<c:url value='/plugin/kindeditor/lang/zh_CN.js' />"></script>

<script src="<c:url value='/resources/js/commons-util.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
<script src="<c:url value='/plugin/blockui/jquery.blockUI.js' />"></script>
<script src="<c:url value='/resources/js/toolkit/dealer/dealer_manage.js' />"></script>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=B797ad09b828b8226be5744cb706f7a3&s=1"></script>
<script type="text/javascript" src="https://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
   
   </body>
</html>
