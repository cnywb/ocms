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
        <h3 class="panel-title">钣喷活动添加数据例示</h3>
      </div>
      <div class="panel-body" id="add_content">
        <div class="form" role="form">
      
        <div class="form-group">
          <input type="text" name="campaignCode" class="form-control" placeholder="活动代码" value="000008">
        </div>
        <div class="form-group">
          <input type="text" name="channelCode" class="form-control" placeholder="渠道代码" value="1">
        </div>
        <div class="form-group">
          <input type="text" name="buyerMobilePhoneNo" class="form-control" placeholder="车主手机号">
        </div>
       
        <div class="form-group">
          <input type="text" name="buyerDealer" class="form-control" placeholder="经销商">
        </div>
        <div class="form-group">
          <input type="text" name="buyerDealerCode" class="form-control" placeholder="经销商代码">
        </div>
     
       <div class="form-group">
          <input type="text" name="potentialBuyBudget" class="form-control" placeholder="卡券金额">
        </div>                             
         <div class="form-group">
          <input type="text" name="wechatId" class="form-control" placeholder="微信Id">
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
<script src="<c:url value='/resources/js/toolkit/infocollection/data_add_for_bp.js' />"></script>
   
   </body>
</html>
