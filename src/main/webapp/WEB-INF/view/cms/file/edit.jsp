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
<c:import url="/WEB-INF/view/include/codemirror.jsp"/>
 <link rel="stylesheet" href="<c:url value='/plugin/jqueryFileTree/jqueryFileTree.css' />">
<style type="text/css">
 .fileTree {
				border-top: solid 1px #BBB;
				border-left: solid 1px #BBB;
				border-bottom: solid 1px #FFF;
				border-right: solid 1px #FFF;
				background: #FFF;
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
               编辑${file}
              </div>
             </h4>
              </div>
                 <div class="panel-body">
             <c:if test="${mode=='htmlmixed'}">   
             <div class="container-fluid">
             <div class="row">
             <div class="col-md-12 input-group">
                             <div class="btn-group m-r-5 m-b-5">
								<a href="javascript:;" data-toggle="dropdown" class="btn btn-sm btn-primary dropdown-toggle" >插入<span class="caret"></span></a>
								<ul class="dropdown-menu">
									<li><a href="javascript:void(0);" onclick="initPluginFileTree();" >插件</a></li>
									<li><a href="javascript:void(0);" onclick="initResouresFileTree();">资源文件</a></li>
									<li><a href="javascript:void(0);" onclick="initIncludeFileTree();">代码片段</a></li>
								</ul>
							</div>
              </div>
             </div>
             </div>
             </c:if>
                  
                   <div class="form-horizontal" role="form">
                  
                    <div class="form-group">
                         <div class="col-md-12">
                         <textarea id="code" name="code" rows="60" >${code}</textarea>
                         </div>
                    </div>
                   <div class="form-group">
                  
    <label class="col-md-2 control-label">选择编辑器主题：</label>
          
    <div class="col-md-4">
    <select class="form-control" onchange="selectTheme()" id="selectTheme" >
    <option selected="selected">blackboard</option>
    <option >default</option>
    <option>3024-day</option>
    <option>3024-night</option>
    <option>abcdef</option>
    <option>ambiance</option>
    <option>base16-dark</option>
    <option>base16-light</option>
    <option>bespin</option>
     <option>cobalt</option>
    <option>colorforth</option>
    <option>dracula</option>
    <option>duotone-dark</option>
    <option>duotone-light</option>
    <option>eclipse</option>
    <option>elegant</option>
    <option>erlang-dark</option>
    <option>hopscotch</option>
    <option>icecoder</option>
    <option>isotope</option>
    <option>lesser-dark</option>
    <option>liquibyte</option>
    <option>material</option>
    <option>mbo</option>
    <option>mdn-like</option>
    <option>midnight</option>
    <option>monokai</option>
    <option>neat</option>
    <option>neo</option>
    <option>night</option>
    <option>panda-syntax</option>
    <option>paraiso-dark</option>
    <option>paraiso-light</option>
    <option>pastel-on-dark</option>
    <option>railscasts</option>
    <option>rubyblue</option>
    <option>seti</option>
    <option>solarized dark</option>
    <option>solarized light</option>
    <option>the-matrix</option>
    <option>tomorrow-night-bright</option>
    <option>tomorrow-night-eighties</option>
    <option>ttcn</option>
    <option>twilight</option>
    <option>vibrant-ink</option>
    <option>xq-dark</option>
    <option>xq-light</option>
    <option>yeti</option>
    <option>zenburn</option>
</select>
        </div>
         <label class="col-md-6 control-label">按F11可进行全屏切换,按Alt+/可显示代码提示</label>
         
        </div>
             
                 </div>
                 <div class="panel-footer">
                  <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" onclick="save();">保存</button>
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


 <div class="modal fade " role="dialog" id="fileModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >请选择文件</h4>
      </div>
      <div class="modal-body">
      <div id="fileTree" class="fileTree"></div>
      </div>
      
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


   <input type="hidden" id="file" value="${file}">
   <input type="hidden" id="mode" value="${mode}">
   
   <input type="hidden" id="siteCode" value="${siteCode}">
   <input type="hidden" id="module" value="${module}">
   <input type="hidden" id="moduleCode" value="${moduleCode}">
   
   <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
   <script src="<c:url value='/plugin/jqueryFileTree/jqueryFileTree.js' />"></script>
   <script src="<c:url value='/plugin/blockui/jquery.blockUI.js' />"></script>
   <script src="<c:url value='/resources/js/cms/file/edit.js' />"></script>
  
 
  
   
   </body>
</html>
