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
   <link rel="stylesheet" href="<c:url value='/plugin/ztree/css/zTreeStyle/zTreeStyle.css' />" type="text/css">
    <link rel="stylesheet" href="<c:url value='/plugin/kindeditor/themes/default/default.css' />" />
	<link rel="stylesheet" href="<c:url value='/plugin/kindeditor/plugins/code/prettify.css' />" />
   <script type="text/javascript">
  var userPath='<%=basePath%>';
  </script>
  <style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
 </head>
   <body >
 
<div class="container-fluid">
	<div class="row-fluid">
		<div class="col-md-12">
			<br>
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
              <div class="panel panel-inverse">
             <div class="panel-heading" role="tab" id="headingOne">
              <h4 class="panel-title">
              <div class="col-md-12 input-group">
            <span class="input-group-addon" id="sizing-addon1">所属站点</span>
               <select class="form-control" name="siteId" id="siteId">
                 <c:forEach var="item" items="${siteList}" varStatus="status">
                  <option value="${item.id}">${item.name}</option>
                 </c:forEach>
              </select>
               </div>
             </h4>
              </div>
           <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
             <div class="panel-body">
             <ul id="treeDemo" class="ztree" ></ul>
           </div>
          </div>
           </div>
            <div class="panel-footer">
        <button type="button" class="btn btn-primary" id="btn_add_root_category" >创建根分类</button>
       </div>
            </div>
		</div>
		<div class="col-md-5">
		</div>
		<div class="col-md-5">
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
          <input type="hidden" name="parentId" id="parentId" >
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="nameZh" class="form-control" placeholder="名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">排序</span>
             <input type="text"  name="seqNum" class="form-control" placeholder="排序" aria-describedby="sizing-addon2"  data-toggle="popover" data-placement="top" data-content="请填写正整数" >
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">代码</span>
             <input type="text" name="code" class="form-control" placeholder="代码" aria-describedby="sizing-addon2" data-toggle="popover" data-placement="top" data-content="请填写大写英文字母">
             </div>
          </div> 
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">链接</span>
             <input type="text" name="pageUrl" class="form-control" placeholder="无需链接请留空" aria-describedby="sizing-addon2" >
             </div>
          </div> 
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">是否可见</span>
            <select class="form-control" name="visible">
              <option value="true">是</option>
              <option value="false">否</option>
            </select>
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">描述</span>
                  <textarea name="tempContentDescriptionString" id="addTempContentDescriptionString" cols="10" rows="8" style="width:100%;height:100px;visibility:hidden;"></textarea>
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
        <input type="hidden" name="id"  >
               <input type="hidden" name="parentId" id="parentId" >
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="nameZh" class="form-control" placeholder="名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">排序</span>
             <input type="text"  name="seqNum" class="form-control" placeholder="排序" aria-describedby="sizing-addon2"  data-toggle="popover" data-placement="top" data-content="请填写正整数" >
             </div>
          </div>
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">代码</span>
             <input type="text" name="code" class="form-control" placeholder="代码" aria-describedby="sizing-addon2" data-toggle="popover" data-placement="top" data-content="请填写大写英文字母">
             </div>
          </div> 
           <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">链接</span>
             <input type="text" name="pageUrl" class="form-control" placeholder="无需链接请留空" aria-describedby="sizing-addon2" >
             </div>
          </div> 
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">是否可见</span>
            <select class="form-control" name="visible">
              <option value="true">是</option>
              <option value="false">否</option>
            </select>
             </div>
          </div>
             <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">描述</span>
                  <textarea name="tempContentDescriptionString" id="editTempContentDescriptionString" cols="10" rows="8" style="width:100%;height:100px;visibility:hidden;"></textarea>
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
   <script src="<c:url value='/plugin/ztree/js/jquery.ztree.core-3.5.js' />"></script>
   <script src="<c:url value='/plugin/ztree/js/jquery.ztree.excheck-3.5.js' />"></script>
   <script src="<c:url value='/plugin/ztree/js/jquery.ztree.exedit-3.5.js' />"></script>
   <script charset="utf-8" src="<c:url value='/plugin/kindeditor/kindeditor.js' />"></script>
   <script charset="utf-8" src="<c:url value='/plugin/kindeditor/lang/zh_CN.js' />"></script>
   <script src="<c:url value='/resources/js/cms/content/content_category_manage.js' />"></script>
   
   </body>
</html>
