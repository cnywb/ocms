<%@ page language="java" import="java.util.*,com.ternnetwork.cms.enums.ContentStatus" pageEncoding="UTF-8"%>
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
 </head>
   <body >
 
<div class="container-fluid" id="content_container">
	
            <input type="hidden" value="${siteId}" id="siteId" >
	        <br>
	        <div class="row-fluid">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">栏目</span>
             <input type="text" readonly id="channel" class="form-control" placeholder="栏目" aria-describedby="sizing-addon2" >
             <input type="hidden" name="channel.id" id="channelId" />
             </div>
            </div>
            <div class="row-fluid" id="channelMenuContent">
             <div class="col-md-offset-2 col-md-10 input-group">
              <ul id="channelTree" class="ztree" style="margin-top:0; width:160px;"></ul>
             </div>
           </div>
            <br>
             <div class="row-fluid">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">模板</span>
             <select class="form-control" name="template" >
             <option value="">请选择模板</option>
              <c:forEach var="item" items="${fileNameList}" varStatus="status">
                  <option value="${item}">${item}</option>
              </c:forEach>
             </select>
             </div>
            </div>
            <br>
           <div class="row-fluid">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">标题</span>
             <input type="text" name="title" class="form-control" placeholder="标题" aria-describedby="sizing-addon2" >
             </div>
          </div>
           <br>
           <div class="row-fluid">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">来源</span>
             <input type="text" name="contentFrom" class="form-control" placeholder="来源" aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
            <div class="row-fluid">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">摘要</span>
             <input type="text" name="subTitle" class="form-control" placeholder="摘要" aria-describedby="sizing-addon2" >
             </div>
            </div>
            <br>
          <div class="row-fluid">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">作者</span>
             <input type="text" name="author" class="form-control" placeholder="作者" aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
            <div class="row-fluid">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">类别</span>
             <input type="text" readonly id="contentCategory" class="form-control" placeholder="类别" aria-describedby="sizing-addon2" >
             <input type="hidden" name="contentCategory.id" id="contentCategoryId" />
             </div>
            </div>
             
           <div class="row-fluid" id="menuContent">
             <div class="col-md-offset-2 col-md-10 input-group">
              <ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
             </div>
           </div>
             <br>
            <div class="row-fluid">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">是否可评论</span>
             <select class="form-control" name="commentAble">
              <option value="false">否</option>
               <option value="true">是</option>
             </select>
             </div>
            </div>
            <br>
             <div class="row-fluid">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">状态</span>
             <select class="form-control" name="status" >
                <%
                for(ContentStatus t: ContentStatus.values()){
	  		     out.println(" <option  value=\""+t.name()+"\">"+t.getName()+"</option>");
  	           }
               %>
             </select>
             </div>
            </div>
            <br>
           <div class="row-fluid">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">内容</span>
            <textarea name="content" id="contenteditor" cols="100" rows="8" style="width:100%;height:300px;visibility:hidden;"></textarea>
             </div>
           </div>
           <br>
           <div class="row-fluid">
             <div class="col-md-5 input-group">
             <span class="input-group-addon" id="sizing-addon1">操作</span>
              <button type="button" class="btn btn-success" id="btn_save">保存</button>
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
   <script src="<c:url value='/plugin/ztree/js/jquery.ztree.core-3.5.js' />"></script>
  	<script charset="utf-8" src="<c:url value='/plugin/kindeditor/kindeditor.js' />"></script>
	<script charset="utf-8" src="<c:url value='/plugin/kindeditor/lang/zh_CN.js' />"></script>
    <script src="<c:url value='/resources/js/commons-util.js' />"></script>
    <script src="<c:url value='/plugin/blockui/jquery.blockUI.js' />"></script>
    <script src="<c:url value='/resources/js/cms/content/content_add.js' />"></script>
    
   </body>
</html>
