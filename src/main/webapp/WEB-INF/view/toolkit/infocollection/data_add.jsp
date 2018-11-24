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
        <h3 class="panel-title">添加数据</h3>
      </div>
      <div class="panel-body" id="add_content">
        <div class="form" role="form">
      
        <div class="form-group">
          <input type="text" name="campaignCode" class="form-control" placeholder="活动代码">
        </div>
        <div class="form-group">
          <input type="text" name="channelCode" class="form-control" placeholder="渠道代码">
        </div>
        <div class="form-group">
        <input type="text" name="purchasedProductModel" class="form-control" placeholder="已购车型，多辆车请用“;”隔开">
        </div>
        <div class="form-group">
          <input type="text" name="purchasedProductSerialNumber" class="form-control" placeholder="已购车VIN">
        </div>
        <div class="form-group">
          <input type="text" name="buyerName" class="form-control" placeholder="车主姓名">
        </div>
         <div class="form-group">
           <label>车主性别</label>
           <select  class="form-control" name="buyerGender"><option value="M">男</option><option value="FM">女</option></select>
        </div>
            <div class="form-group">
          <input type="text" name="buyerMobilePhoneNo" class="form-control" placeholder="车主手机号">
        </div>
       <div class="form-group">
          <input type="text" name="buyerProvince" class="form-control" placeholder="车主所在省">
        </div>
        <div class="form-group">
          <input type="text" name="buyerCity" class="form-control" placeholder="车主所在市">
        </div>
        <div class="form-group">
          <input type="text" name="buyerDealer" class="form-control" placeholder="车主所在经销商">
        </div>
        <div class="form-group">
          <input type="text" name="buyTime" class="form-control" placeholder="购车时间">
        </div>        
        <div class="form-group">
          <input type="text" name="potentialBuyerName" class="form-control" placeholder="潜客姓名">
        </div>        
          <div class="form-group">
           <label>潜客性别</label>
           <select  class="form-control" name="potentialBuyerGender"><option value="M">男</option><option value="FM">女</option></select>
        </div>     
         <div class="form-group">
          <input type="text" name="potentialBuyerMobilePhoneNo" class="form-control" placeholder="潜客手机">
        </div>       
         <div class="form-group">
          <input type="text" name="potentialBuyerProvince" class="form-control" placeholder="潜客所在省">
        </div> 
        <div class="form-group">
          <input type="text" name="potentialBuyerCity" class="form-control" placeholder="潜客所在市">
        </div>  
       <div class="form-group">
          <input type="text" name="potentialBuyerDealer" class="form-control" placeholder="潜客所在经销商">
        </div>          
         <div class="form-group">
          <input type="text" name="potentialBuyProductModel" class="form-control" placeholder="潜在购车车型">
        </div>  
        <div class="form-group">
          <input type="text" name="potentialBuyTime" class="form-control" placeholder="潜在购车时间">
        </div>  
       <div class="form-group">
          <input type="text" name="potentialBuyBudget" class="form-control" placeholder="潜在购车预算">
        </div>                             
         <div class="form-group">
          <input type="text" name="wechatId" class="form-control" placeholder="微信Id">
        </div>       
           <div class="form-group">
          <input type="text" name="fileUrl" class="form-control" placeholder="上传文件路径">
        </div>
        <div class="form-group">
           <button class="btn btn-primary" type="button" onclick="save();">保存</button>
            </div>
           </div>
      </div>
      </div>
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
<script src="<c:url value='/resources/js/commons-util.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js' />"></script>
<script src="<c:url value='/resources/js/toolkit/infocollection/data_add.js' />"></script>
   
   </body>
</html>
