<%@ page language="java" import="java.util.*,com.ternnetwork.toolkit.enums.CampaignDataSendFrequency,com.ternnetwork.toolkit.enums.CampaignType" pageEncoding="UTF-8"%>
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
   <link rel="stylesheet" href="<c:url value='/plugin/kindeditor/plugins/code/prettify.css' />" />
   <link rel="stylesheet" href="<c:url value='/plugin/kindeditor/themes/default/default.css' />" />
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
           <button class="btn btn-primary btn-sm" type="button" onclick="refreshTable();">查询</button>
            </div>
           </div>
      </div>
      </div>
   </div>
 </div>


	<div class="row-fluid">
		<div class="span12">

   <br>
    <div class="form-inline" role="form">
        <div class="form-group">
            <div class="input-group">
                 <button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#addModal">新建</button>
            </div>
        </div>
     </div>
     <br>
    <table id="data_table"  data-url="<c:url value='/toolkit/infocollection/campaign/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
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
      <div class="modal-body" style="font-size:4px">
        <div class="container-fluid">
           
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">活动类型</span>
            <select class="form-control" name="type">
               <%
                for(CampaignType t: CampaignType.values()){
	  		     out.println(" <option  value=\""+t.name()+"\">"+t.getName()+"</option>");
  	           }
               %>
            </select>
             </div>
          </div>
          <br>
          
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">活动代码</span>
             <input type="text" name="code"  class="form-control" placeholder="请填写非中文非符号字符,系统内惟一" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">活动名称</span>
             <input type="text" name="name" class="form-control" placeholder="活动名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
           
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">数据发送频率</span>
            <select class="form-control" name="dataSendFrequency">
               <%
                for(CampaignDataSendFrequency t: CampaignDataSendFrequency.values()){
	  		     out.println(" <option  value=\""+t.name()+"\">"+t.getName()+"</option>");
  	           }
               %>
            </select>
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">数据接收邮箱</span>
             <input type="text" name="dataReceiveEmail" class="form-control" placeholder="多个邮箱用“;”隔开" aria-describedby="sizing-addon2" >
             </div>
          </div>
         <br>
         <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">活动开始时间</span>
             <input type="text" name="startTime" class="form_datetime form-control" placeholder="" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">活动结束时间</span>
             <input type="text" name="endTime" class="form_datetime form-control" placeholder="" aria-describedby="sizing-addon2" >
             </div>
          </div>
             <br>
             <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">校验活动时间</span>
             <select class="form-control" name="timeCheck">
               <option value="true">是</option>
               <option value="false">否</option>
             </select>
             </div>
            </div>
          <br>
             <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">是否启用</span>
             <select class="form-control" name="enable">
               <option value="true">是</option>
               <option value="false">否</option>
             </select>
             </div>
            </div>
            <br>
            <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">活动KV</span>
             <input type="text" name="kvUrl" id="imageSrcAdd" class="form-control" placeholder="图片路径" aria-describedby="sizing-addon2" >
              <span class="input-group-addon" ><button type="button" class="btn btn-primary btn-xs" id="btn_file_manage" >浏览</button> <button type="button" class="btn btn-primary btn-xs" id="btn_upload" >上传</button></span>
             </div>
          </div>
          <br>
            <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">缩略图</span>
             <input type="text" name="thumbnailUrl" id="thumbnailSrcAdd" class="form-control" placeholder="图片路径" aria-describedby="sizing-addon2" >
              <span class="input-group-addon" ><button type="button" class="btn btn-primary btn-xs" id="btn_thumbnail_file_manage" >浏览</button> <button type="button" class="btn btn-primary btn-xs" id="btn_thumbnail_upload" >上传</button></span>
             </div>
          </div>
           <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" >活动主题</span>
             <textarea class="form-control" name="subject" rows="3"  aria-describedby="sizing-addon3"></textarea>
         </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" >活动车型</span>
               <textarea class="form-control" name="product" rows="3"  aria-describedby="sizing-addon3"></textarea>
         </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" >奖项设置</span>
                <textarea class="form-control" name="award" rows="3"  aria-describedby="sizing-addon3"></textarea>
     
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">注意事项</span>
       <textarea class="form-control" name="memo" rows="3"  aria-describedby="sizing-addon3"></textarea>
       
             </div>
          </div>
                                 
         </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary btn-sm" onclick="save();">保存</button>
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
     <form action="" id="previewForm" target="_blank" method="post">
      <div class="modal-body" style="font-size:4px">
        <div class="container-fluid">
         <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">活动类型</span>
            <select class="form-control" name="type">
               <%
                for(CampaignType t: CampaignType.values()){
	  		     out.println(" <option  value=\""+t.name()+"\">"+t.getName()+"</option>");
  	           }
               %>
            </select>
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">活动代码</span>
             <input type="hidden" name="id" />
             
             <input type="text" name="code" id="edit_code" class="form-control" placeholder="请填写非中文非符号字符,系统内惟一" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">活动名称</span>
             <input type="text" name="name" class="form-control" placeholder="活动名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">数据发送频率</span>
            <select class="form-control" name="dataSendFrequency">
               <%
                for(CampaignDataSendFrequency t: CampaignDataSendFrequency.values()){
	  		     out.println(" <option  value=\""+t.name()+"\">"+t.getName()+"</option>");
  	           }
               %>
            </select>
             </div>
          </div>
                   <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">数据接收邮箱</span>
             <input type="text" name="dataReceiveEmail" class="form-control" placeholder="多个邮箱用“;”隔开" aria-describedby="sizing-addon2" >
             </div>
          </div>
         <br>
         <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">活动开始时间</span>
             <input type="text" name="startTime" class="form_datetime form-control" placeholder="" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">活动结束时间</span>
             <input type="text" name="endTime" class="form_datetime form-control" placeholder="" aria-describedby="sizing-addon2" >
             </div>
          </div>
              <br>
             <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">校验活动时间</span>
             <select class="form-control" name="timeCheck">
               <option value="true">是</option>
               <option value="false">否</option>
             </select>
             </div>
            </div>
          <br>
             <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">是否启用</span>
             <select class="form-control" name="enable">
               <option value="true">是</option>
               <option value="false">否</option>
             </select>
             </div>
            </div>
          
            <br>
            <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">活动KV</span>
               <input type="text" name="kvUrl" id="imageSrcEdit" class="form-control" placeholder="图片路径" aria-describedby="sizing-addon2" >
               <span class="input-group-addon" ><button type="button" class="btn btn-primary btn-xs" id="edit_btn_file_manage" >浏览</button> <button type="button" class="btn btn-primary btn-xs" id="edit_btn_upload" >上传</button></span>
             </div>
           </div>
                      <br>
            <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">缩略图</span>
               <input type="text" name="thumbnailUrl" id="thumbnailSrcEdit" class="form-control" placeholder="图片路径" aria-describedby="sizing-addon2" >
               <span class="input-group-addon" ><button type="button" class="btn btn-primary btn-xs" id="edit_btn_thumbnail_file_manage" >浏览</button> <button type="button" class="btn btn-primary btn-xs" id="edit_btn_thumbnail_upload" >上传</button></span>
             </div>
           </div>
           <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" >活动主题</span>
            
                   <textarea class="form-control" name="subject" rows="3"  aria-describedby="sizing-addon3"></textarea>
          
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" >活动车型</span>
                  <textarea class="form-control" name="product" rows="3"  aria-describedby="sizing-addon3"></textarea>
          
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" >奖项设置</span>
                 <textarea class="form-control" name="award" rows="3"  aria-describedby="sizing-addon3"></textarea>
          
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">注意事项</span>
              <textarea class="form-control" name="memo" rows="3"  aria-describedby="sizing-addon3"></textarea>
              </div>
          </div>
       </div>
      </div>
      </form>
      <div class="modal-footer">
        <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary btn-sm" onclick="preview();">预览</button>
        <button type="button" class="btn btn-primary btn-sm" onclick="update();">保存</button>
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
<div id="btnc05">
<input type="button" value="上传文件" id="edit_btn_upload_file" >
</div>

<div id="btnc06">
<input type="button" value="上传文件" id="btn_thumbnail_upload_file" >
</div>
<div id="btnc07">
<input type="button" value="上传文件" id="edit_btn_thumbnail_upload_file" >
</div>

<c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
<script src="<c:url value='/resources/js/commons-util.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js' />"></script>
<script src="<c:url value='/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js' />"></script>
<script charset="utf-8" src="<c:url value='/plugin/kindeditor/kindeditor-all.js' />"></script>
<script charset="utf-8" src="<c:url value='/plugin/kindeditor/lang/zh_CN.js' />"></script>
<script src="<c:url value='/resources/js/toolkit/infocollection/campaign_manage.js' />"></script>
   
   </body>
</html>
