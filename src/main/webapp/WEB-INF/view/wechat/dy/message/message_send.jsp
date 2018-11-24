<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<base href="<%=basePath%>">
<head>
<c:import url="/WEB-INF/view/include/color_admin_header.jsp"/>
 <link rel="stylesheet" href="<c:url value='/plugin/ztree/css/zTreeStyle/zTreeStyle.css' />" type="text/css">
 <link rel="stylesheet" href="<c:url value='/plugin/kindeditor/themes/default/default.css' />" />
 <link rel="stylesheet" href="<c:url value='/plugin/kindeditor/plugins/code/prettify.css' />" />
 <link href="<c:url value='/assets/plugins/jquery-tag-it/css/jquery.tagit.css' />" rel="stylesheet" />
  
<title>发送消息</title>
</head>
<body>

    
	<div class="container-fluid">
	<div class="row-fluid">
	
	  <br>
      <div class="panel panel-inverse">
      <div class="panel-heading">
        <h3 class="panel-title">
          发送消息
        </h3>
      </div>
      <div class="panel-body">
             
         <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">接收用户</span>
              <ul id="jquery-tagIt-primary" class="primary">
                       
              </ul>
              <span class="input-group-addon" id="chooseDepartmentBtn">选择部门</span>
             </div>
          </div>
          <br>
             <div class="row"> 
			    <!-- begin col-12 -->
			    <div class="col-md-12">
			        <!-- begin panel -->
                    <div class="panel panel-default panel-with-tabs" data-sortable-id="ui-unlimited-tabs-1">
                        <div class="panel-heading p-0">
                           
                            <!-- begin nav-tabs -->
                            <div class="tab-overflow">
                                <ul class="nav nav-tabs nav-tabs-default">
                                    <li class="active"><a href="#nav-tab-1" data-toggle="tab">文字</a></li>
                                    <li ><a href="#nav-tab-2" data-toggle="tab">图文</a></li>
                                    <li ><a href="#nav-tab-3" data-toggle="tab">图片</a></li>
                                    <li ><a href="#nav-tab-5" data-toggle="tab">视频</a></li>
                                    <li ><a href="#nav-tab-6" data-toggle="tab">语音</a></li>
                                 </ul>
                            </div>
                        </div>
                        <div class="tab-content" style="padding: 1px">
                           <div class="tab-pane fade active in" id="nav-tab-1">
                            <textarea rows="10" style="width: 100%" id="msg_content"></textarea>
                          
                           </div>
                            <div class="tab-pane fade  in" id="nav-tab-2">
                             <br>
                            <div class="row">
                                    <div class="col-md-12 input-group"> 
                                     <span class="input-group-addon" >缩略图</span>
                                     <input type="button" class="btn btn-primary" value="上传图片" id="delegate_btn_upload_image_2" >
                                     <input type="hidden" name="title" id="media_image_id_2"  >
                                    </div>
                              </div>
                           <br>
                           
                              <div class="row">
                                    <div class="col-md-12 input-group"> 
                                     <span class="input-group-addon" >作者</span>
                                     <input type="text" class="form-control" name="author" id="author"  >
                                    </div>
                              </div>
                              <br>
                              <div class="row">
                                    <div class="col-md-12 input-group"> 
                                     <span class="input-group-addon" >标题</span>
                                     <input type="text" class="form-control" name="title" id="title"  >
                                    </div>
                              </div>
                               <br>
                              <div class="row">
                                    <div class="col-md-12 input-group"> 
                                     <span class="input-group-addon" >描述</span>
                                     <input type="text" class="form-control" name="title" id="digest"  >
                                    </div>
                              </div>
                              <br>
                                <div class="row">
                                    <div class="col-md-12 input-group"> 
                                    <span class="input-group-addon" >链接</span>
                                     <input type="text" class="form-control" name="content_source_url" id="content_source_url"  >
                                    </div>
                               </div>
                               <br>
                                <div class="row">
                                    <div class="col-md-12 input-group"> 
                                    <span class="input-group-addon" >是否显示封面</span>
                                     <select class="form-control" id="show_cover_pic">
                                     <option value="1">是</option>
                                      <option value="0">否</option>
                                     </select>
                                    </div>
                               </div>
                                <br>
                               <div class="row">
                                    <div class="col-md-12 input-group"> 
                                    <span class="input-group-addon" >内容</span>
                                     <textarea class="form-control" rows="5" style="width: 100%" id="content"></textarea>
                                    </div>
                               </div>
                            </div>
                            <div class="tab-pane fade  in" id="nav-tab-3">
                            <br>
                              <div class="row">
                                    <div class="col-md-12 input-group"> 
                                     <input type="button" class="btn btn-primary" value="上传图片" id="delegate_btn_upload_image" >
                                     <input type="hidden" name="title" id="media_image_id"  >
                                    </div>
                              </div>
                           
                            </div>
                          
                             <div class="tab-pane fade  in" id="nav-tab-5">
                             <br>
                            <div class="row">
                                    <div class="col-md-12 input-group"> 
                                     <input type="button" class="btn btn-primary" value="上传视频" id="delegate_btn_upload_video" >
                                     <input type="hidden" name="title" id="media_video_id"  >
                                    </div>
                              </div>
                            </div>
                             <div class="tab-pane fade  in" id="nav-tab-6">
                             <br>
                               <div class="row">
                                    <div class="col-md-12 input-group"> 
                                     <input type="button" class="btn btn-primary" value="上传语音" id="delegate_btn_upload_voice" >
                                     <input type="hidden" name="title" id="media_voice_id"  >
                                    </div>
                              </div>
                            </div>
                       </div> <!-- end tab-content -->
			       
			      
			    </div> <!-- end panel -->
			   
			</div> <!-- end col-12 -->
			
		</div><!-- end row -->
		</div><!-- end body -->
		  <div class="panel-footer">
          <button type="button" class="btn btn-primary" id="btn_save"  onclick="sendMessage();">发送</button>
        
          </div><!-- end panel footer -->
	   </div><!-- end panel body -->
	</div><!-- end page container -->
	
	
	

	
	
	


	
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
   
 <div class="modal fade " role="dialog" id="treeModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >请选择用户组</h4>
      </div>
      <div class="modal-body">
      <ul id="treeAdd" class="ztree" ></ul>
      </div>
      <div class="modal-footer">
       <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->  
   

<div id="btnc01">
<input type="button" value="上传图片" id="btn_upload_image" >
</div>
<div id="btnc02">
<input type="button" value="上传语音" id="btn_upload_voice" >
</div>
<div id="btnc03">
<input type="button" value="上传视频" id="btn_upload_video" >
</div>
<div id="btnc04">
<input type="button" value="上传图片" id="btn_upload_image_2" >
</div>
   
    <c:import url="/WEB-INF/view/include/color_admin_footer.jsp"/>
    <script src="<c:url value='/assets/plugins/jquery-tag-it/js/tag-it.js' />"></script>
    <script src="<c:url value='/plugin/ztree/js/jquery.ztree.core-3.5.js' />"></script>
    <script charset="utf-8" src="<c:url value='/plugin/kindeditor/kindeditor.js' />"></script>
	<script charset="utf-8" src="<c:url value='/plugin/kindeditor/lang/zh_CN.js' />"></script>
    <script src="<c:url value='/resources/js/commons-util.js' />"></script>
    <script src="<c:url value='/resources/js/wechat/dy/message/message_send.js' />"></script>
	
</body>
</html>