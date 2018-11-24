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
        <form id="queryForm" action="<c:url value='toolkit/infocollection/data/exportExcel.htm' />"  method="post" >
        <div class="form-group">
           <label>所属活动</label>
           <select  class="form-control" name="campaignId">
           <option value="">不限</option>
            <c:forEach var="item" items="${campaignList}" varStatus="status">
                  <option value="${item.id}">${item.name}</option>
            </c:forEach>
           </select>
        </div> 
        <div class="form-group">
           <label>所属渠道</label>
           <select  class="form-control" name="channelId">
           <option value="">不限</option>
             <c:forEach var="item" items="${channelList}" varStatus="status">
                  <option value="${item.id}">${item.name}</option>
             </c:forEach>
           </select>
        </div>           
      
        <div class="form-group">
          <input type="text" name="subject" class="form-control" placeholder="活动主题">
        </div>
         <div class="form-group">
          <input type="text" name="potentialBuyerMobilePhoneNo" class="form-control" placeholder="潜客手机号">
        </div>
      
           <div class="form-group">
           <input type="text" name="startTime" class="form_datetime form-control" placeholder="创建时间起">
           </div>
            <div class="form-group">
             <input type="text" name="endTime" class="form_datetime form-control" placeholder="创建时间止">
            </div>
           
            
            </form>
           </div>
            <div class="form-inline" role="form">
            <div class="form-group" >
           
            <button class="btn btn-primary" type="button" onclick="refreshTable();">查询</button>
            <button class="btn btn-primary" type="button" onclick="clearForm('query_con');">清空查询条件</button>
            <button class="btn btn-primary" type="submit" onclick="exportExcel();">导出</button>
            </div>
            </div>
      </div>
      </div>
   </div>
 </div>


	<div class="row-fluid">
	<div class="span12">
    <br>
    <table id="data_table"  data-url="<c:url value='/toolkit/infocollection/data/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
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
        <h4 class="modal-title" >详情</h4>
      </div>
      <div class="modal-body">
        <div class="form" role="form">
      
        <div class="form-group">
          <label>所属活动</label>
          <input type="text" name="campaignName" class="form-control" placeholder="所属活动">
        </div>
        <div class="form-group">
        <label>所属渠道</label>
          <input type="text" name="channelName" class="form-control" placeholder="所属渠道">
        </div>
        <div class="form-group">
        <label>已购车型</label>
        <input type="text" name="purchasedProductModel" class="form-control" placeholder="已购车型，多辆车请用“;”隔开">
        </div>
        <div class="form-group">
         <label>VIN</label>
          <input type="text" name="purchasedProductSerialNumber" class="form-control" placeholder="已购车VIN">
        </div>
        <div class="form-group">
          <label>车主姓名</label>
          <input type="text" name="buyerName" class="form-control" placeholder="车主姓名">
        </div>
         <div class="form-group">
           <label>车主性别</label>
           <input type="text" name="buyerGenderName" class="form-control" >
        </div>
        <div class="form-group">
            <label>车主手机号</label>
          <input type="text" name="buyerMobilePhoneNo" class="form-control" placeholder="车主手机号">
        </div>
       <div class="form-group">
        <label>车主所在省</label>
          <input type="text" name="buyerProvince" class="form-control" placeholder="车主所在省">
        </div>
        <div class="form-group">
         <label>车主所在市</label>
          <input type="text" name="buyerCity" class="form-control" placeholder="车主所在市">
        </div>
        <div class="form-group">
         <label>车主所在经销商</label>
          <input type="text" name="buyerDealer" class="form-control" placeholder="车主所在经销商">
        </div>
        <div class="form-group">
         <label>购车时间</label>
          <input type="text" name="buyTime" class="form-control" placeholder="购车时间">
        </div>        
        <div class="form-group">
         <label>潜客姓名</label>
          <input type="text" name="potentialBuyerName" class="form-control" placeholder="潜客姓名">
        </div>        
          <div class="form-group">
           <label>潜客性别</label>
            <input type="text" name="potentialBuyerGenderName" class="form-control" placeholder="潜客性别">
          </div>     
         <div class="form-group">
          <label>潜客手机</label>
          <input type="text" name="potentialBuyerMobilePhoneNo" class="form-control" placeholder="潜客手机">
        </div>       
         <div class="form-group">
          <label>潜客所在省</label>
          <input type="text" name="potentialBuyerProvince" class="form-control" placeholder="潜客所在省">
        </div> 
        <div class="form-group">
         <label>潜客所在市</label>
          <input type="text" name="potentialBuyerCity" class="form-control" placeholder="潜客所在市">
        </div>  
       <div class="form-group">
        <label>潜客所在经销商</label>
          <input type="text" name="potentialBuyerDealer" class="form-control" placeholder="潜客所在经销商">
        </div>          
         <div class="form-group">
          <label>潜在购车车型</label>
          <input type="text" name="potentialBuyProductModel" class="form-control" placeholder="潜客意向车型">
        </div>  
        <div class="form-group">
         <label>潜在购买时间</label>
          <input type="text" name="potentialBuyTime" class="form-control" placeholder="潜客意向购买时间">
        </div>  
       <div class="form-group">
        <label>潜在购车预算</label>
          <input type="text" name="potentialBuyBudget" class="form-control" placeholder="潜客意向购买预算">
        </div>
        <div class="form-group">
        <label>上传文件的URL</label>
          <input type="text" name="fileUrl" class="form-control" placeholder="上传文件的URL">
        </div>
         <div class="form-group">
        <label>微信ID</label>
          <input type="text" name="wechatId" class="form-control" placeholder="微信ID">
        </div>                                
      </div>
      <div class="modal-footer">
       
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
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
<script src="<c:url value='/resources/js/commons-util.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js' />"></script>
<script src="<c:url value='/resources/js/toolkit/infocollection/data_manage.js' />"></script>
   
   </body>
</html>
