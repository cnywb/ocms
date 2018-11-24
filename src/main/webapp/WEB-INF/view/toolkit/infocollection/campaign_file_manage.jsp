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
    <link rel="stylesheet" href="<c:url value='/plugin/jqueryFileTree/jqueryFileTree.css' />">
    <link rel="stylesheet" href="<c:url value='/plugin/kindeditor/themes/default/default.css' />" />
	<link rel="stylesheet" href="<c:url value='/plugin/kindeditor/plugins/code/prettify.css' />" />
	
   <script type="text/javascript">
  var userPath='<%=basePath%>';
 
  </script>
  
  <style type="text/css">
  
  .demo {
				
				
				border-top: solid 1px #BBB;
				border-left: solid 1px #BBB;
				border-bottom: solid 1px #FFF;
				border-right: solid 1px #FFF;
				background: #FFF;
				overflow: scroll;
				padding: 5px;
			}
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
               
              
               </div>
             </h4>
              </div>
                 
             
                 <div class="panel-body">
                   <div class="alert alert-info" role="alert">
                    <strong >根目录：${rootDir}</strong>
                    <strong id="currentDirAlert">当前目录：${rootDir}</strong>
                   </div>
                  <div id="fileTree" class="demo"></div>
                
              
                 </div>
                 <div class="panel-footer">
                  <c:if test="${type=='resources'}">
                  <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addFolderModal" >创建文件夹</button>
                  </c:if>
                  <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" onclick="showAddFileModal();" >创建文件</button>
                  <button type="button" class="btn btn-primary btn-sm" id="btn_upload" >上传文件</button>
                  <button type="button" class="btn btn-primary btn-sm" onclick="zipAll();" >压缩所有文件</button>
                  <button type="button" class="btn btn-primary btn-sm" onclick="window.location.reload();" >回到主目录</button>
                </div>
                </div>
              </div>
            </div>
  
     </div> 
	</div>
	
	
</div>



<div class="modal fade" role="dialog" id="addFolderModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >创建文件夹</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">当前路径</span>
             <input type="text"  readonly="readonly" id="currentDir" name="currentDir" class="form-control" placeholder="" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">文件夹名称</span>
             <input type="text" name="forderName" class="form-control" placeholder="文件夹名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
       </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="addFolder();">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="modal fade" role="dialog" id="addFileModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >创建文件</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">当前路径</span>
             <input type="text"  readonly="readonly"  name="currentDir" class="form-control" placeholder="" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">文件名称</span>
             <input type="text" name="fileName" class="form-control" placeholder="文件名称" aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">文件类型</span>
            <select class="form-control" name="fileType">
               <c:if test="${type=='template'}">
                <option value="html">html</option>
                </c:if>
                <c:if test="${type=='resources'}">
                <option value="js">js</option>
                <option value="css">css</option>
                </c:if>
            </select>
             </div>
          </div>
          
       </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="addFile();">保存</button>
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


<div class="modal fade " role="dialog" id="deleteModal" aria-labelledby="gridSystemModalLabel">
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
      <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
       <button type="button" class="btn btn-primary" onclick="doDelete();">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="modal fade" role="dialog" id="zipModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >压缩文件</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">被压缩文件或目录</span>
             <input type="text"  readonly="readonly" id="zipSrcFile" name="srcFile" class="form-control" placeholder="" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">压缩后文件路径:</span>
             <input type="text" id="zipDestFile"  name="destFile" class="form-control" value="/" placeholder="压缩文件路径" aria-describedby="sizing-addon2" >
               <input type="hidden" name="campaignCode" value="${campaignCode}" />
               <input type="hidden" name="type" value="${type}" />
             <span class="input-group-addon" id="sizing-addon1">.zip</span>
             </div>
          </div>
       </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="doZip();">压宿</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade" role="dialog" id="unZipModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >压缩文件</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">被解压文件:</span>
             <input type="text"  readonly="readonly" id="unZipSrcFile" name="srcFile" class="form-control" placeholder="" aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">解压到目录:</span>
             <input type="text"  name="destFile" class="form-control" value="/" placeholder="解压到目录" aria-describedby="sizing-addon2" >
            
             </div>
          </div>
       </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="doUnZip();">解压</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="modal fade" role="dialog" id="showUrlModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >文件链接</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          
          <div class="row">
             <div class="col-md-12 input-group">
                  <input type="text" name="url" class="form-control" placeholder="" aria-describedby="sizing-addon2" >
             </div>
          </div>
       </div>
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
 
 
   <input type="hidden" id="rootDir" value="${rootDir}" >
   <input type="hidden" id="moduleCode" value="${campaignCode}" >
   
     <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
    <script src="<c:url value='/plugin/jqueryFileTree/jquery.easing.js' />"></script>
    <script src="<c:url value='/plugin/jqueryFileTree/jqueryFileTree.js' />"></script>
 	<script charset="utf-8" src="<c:url value='/plugin/kindeditor/kindeditor.js' />"></script>
	<script charset="utf-8" src="<c:url value='/plugin/kindeditor/lang/zh_CN.js' />"></script>
	<script src="<c:url value='/plugin/blockui/jquery.blockUI.js' />"></script>
	<script src="<c:url value='/resources/js/toolkit/infocollection/campaign_file_manage.js' />"></script>
  
   </body>
</html>
