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
    <link rel="stylesheet" href="<c:url value='/plugin/kindeditor/plugins/code/prettify.css' />" />
    <link rel="stylesheet" href="<c:url value='/plugin/kindeditor/themes/default/default.css' />" />
   
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
            <div class="input-group">
                 <button type="button" class="btn btn-success" data-toggle="modal" data-target="#addModal">新建</button>
            </div>
        </div>
      </div>
    <br>
      <table id="data_table" data-url="<c:url value='/cms/carousel/content/query.htm' />" data-height="400" data-side-pagination="server" data-pagination="true"> 
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
             <span class="input-group-addon" id="sizing-addon1">图片路径</span>
             <input type="text" name="imageSrc" id="imageSrcAdd" class="form-control" placeholder="图片路径" aria-describedby="sizing-addon2" >
              <span class="input-group-addon" ><button type="button" class="btn btn-primary btn-xs" id="btn_file_manage" >浏览</button> <button type="button" class="btn btn-primary btn-xs" id="btn_upload" >上传</button></span>
            
             </div>
          </div>
          <br>
         <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon2">链接</span>
             <input type="text" name="url" class="form-control" placeholder="链接" aria-describedby="sizing-addon2" >
             </div>
          </div>
                <br>
         <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon3">描述</span>
            <textarea class="form-control" name="description" rows="3"  aria-describedby="sizing-addon3"></textarea>
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
      <input type="hidden" name="id" >
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">图片路径</span>
             <input type="text" name="imageSrc" id="imageSrcEdit" class="form-control" placeholder="图片路径" aria-describedby="sizing-addon2" >
                     <span class="input-group-addon" ><button type="button" class="btn btn-primary btn-xs" id="edit_btn_file_manage" >浏览</button> <button type="button" class="btn btn-primary btn-xs" id="edit_btn_upload" >上传</button></span>
        
             </div>
          </div>
          <br>
         <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon2">链接</span>
             <input type="text" name="url" class="form-control" placeholder="链接" aria-describedby="sizing-addon2" >
             </div>
          </div>
                <br>
         <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon3">描述</span>
            <textarea class="form-control" name="description" rows="3"  aria-describedby="sizing-addon3"></textarea>
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
   <div id="btnc04">
<input type="button" value="上传文件" id="btn_upload_file" >
</div>
<div id="btnc05">
<input type="button" value="上传文件" id="edit_btn_upload_file" >
</div>
    <input type="hidden" value="${carouselId}" id="carouselId" >
    <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
    <script src="<c:url value='/plugin/bootstrap-table/js/bootstrap-table.js' />"></script>
    <script charset="utf-8" src="<c:url value='/plugin/kindeditor/kindeditor-all.js' />"></script>
    <script charset="utf-8" src="<c:url value='/plugin/kindeditor/lang/zh_CN.js' />"></script>
    <script src="<c:url value='/resources/js/commons-util.js' />"></script>
    <script src="<c:url value='/resources/js/cms/carousel/carousel_content_manage.js' />"></script>

 </body>
</html>
