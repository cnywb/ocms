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
<title>系统首页</title>

</head>
<body>

	
	<div class="container-fluid">
	<div class="row-fluid">
	
	  <br>
      <div class="panel panel-inverse">
      <div class="panel-heading">
        <h3 class="panel-title">
          自定义菜单管理
        </h3>
      </div>
      <div class="panel-body">
			    <!-- begin col-12 -->
			    <div class="row">
			    <div class="col-md-12">
			        <!-- begin panel -->
                    <div class="panel panel-default panel-with-tabs" data-sortable-id="ui-unlimited-tabs-1">
                        <div class="panel-heading p-0">
                           
                            <!-- begin nav-tabs -->
                            <div class="tab-overflow">
                                <ul class="nav nav-tabs nav-tabs-default">
                                    <li class="active"><a href="#nav-tab-1" data-toggle="tab">菜单设计</a></li>
                                    <li ><a href="#nav-tab-2" data-toggle="tab">请求数据预览</a></li>
                               
                                 </ul>
                            </div>
                        </div>
                        <div class="tab-content" style="padding: 1px">
                          <div class="tab-pane fade active in" id="nav-tab-1">
                          
                         <div class="row">
                         <div id="btn_0_sub_btn_4" class="col-md-4"></div><div id="btn_1_sub_btn_4" class="col-md-4"></div><div id="btn_2_sub_btn_4" class="col-md-4"></div>
                         </div><br>
                         <div class="row">
                         <div id="btn_0_sub_btn_3" class="col-md-4"></div><div id="btn_1_sub_btn_3" class="col-md-4"></div><div id="btn_2_sub_btn_3" class="col-md-4"></div>
                         </div><br>
                         <div class="row">
                         <div id="btn_0_sub_btn_2" class="col-md-4"></div><div id="btn_1_sub_btn_2" class="col-md-4"></div><div id="btn_2_sub_btn_2" class="col-md-4"></div>
                         </div><br>
                         <div class="row">
                         <div id="btn_0_sub_btn_1" class="col-md-4"></div><div id="btn_1_sub_btn_1" class="col-md-4"></div><div id="btn_2_sub_btn_1" class="col-md-4"></div>
                         </div><br>
                         <div class="row">
                         <div id="btn_0_sub_btn_0" class="col-md-4"></div><div id="btn_1_sub_btn_0" class="col-md-4"></div><div id="btn_2_sub_btn_0" class="col-md-4"></div>
                         </div><br>
                         <div class="row">
                         <div id="btn_0" class="col-md-4"></div><div id="btn_1" class="col-md-4"></div><div id="btn_2" class="col-md-4"></div>
                         </div><br>
                            <div class="row">
                         <div id="cbtn_0" class="col-md-4"></div><div id="cbtn_1" class="col-md-4"></div><div id="cbtn_2" class="col-md-4"></div>
                         </div>
                          
                   
                          </div>
                          <div class="tab-pane fade  in" id="nav-tab-2">
                          
                           <textarea rows="20" style="width: 100%" id="menuCode"></textarea>
                          </div>
                    </div>
			       
			      
			    </div> <!-- end tab panel -->
			    
			</div><!-- end col-12 -->
			
		</div><!-- end row -->
		</div><!-- end body -->
		  <div class="panel-footer">
          <button type="button" class="btn btn-primary" id="btn_save" >保存</button>
          <button type="button" class="btn btn-danger" id="btn_delete" >删除</button>
          </div>
	</div>
	</div>
	<!-- end page container -->
	
	
<div class="modal fade" role="dialog" id="win_add" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" ></h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">类型：</span>
              <select class="form-control" name="type" >
              <option value="click">点击推事件</option>
              <option value="view">跳转URL</option>
              <option value="scancode_push">扫码推事件</option>
              <option value="scancode_waitmsg">扫码推事件且弹出“消息接收中”提示框</option>
              <option value="pic_sysphoto">弹出系统拍照发图</option>
              <option value="pic_photo_or_album">弹出拍照或者相册发图</option>
              <option value="pic_weixin">弹出微信相册发图器</option>
              <option value="location_select">弹出地理位置选择器</option>
             </select>
             </div>
          </div>
            <br>
            <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称：</span>
             <input type="text"  class="form-control" name="name" />
             </div>
            </div>
          
            <br>
              <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">链接：</span>
             <input type="text"  class="form-control" name="url" size="30"/>
             </div>
            </div>
              <br>
             <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">键：</span>
             <input type="text"  class="form-control" name="key" />
             </div>
            </div>
          
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="addMenu();">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
	
	
	<div class="modal fade" role="dialog" id="win_update" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" ></h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">类型：</span>
              <select class="form-control" name="type" >
              <option value="click">点击推事件</option>
              <option value="view">跳转URL</option>
              <option value="scancode_push">扫码推事件</option>
              <option value="scancode_waitmsg">扫码推事件且弹出“消息接收中”提示框</option>
              <option value="pic_sysphoto">弹出系统拍照发图</option>
              <option value="pic_photo_or_album">弹出拍照或者相册发图</option>
              <option value="pic_weixin">弹出微信相册发图器</option>
              <option value="location_select">弹出地理位置选择器</option>
             </select>
             </div>
          </div>
            <br>
            <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称：</span>
             <input type="text"  class="form-control" name="name" />
             </div>
            </div>
          
            <br>
              <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">链接：</span>
             <input type="text"  class="form-control" name="url" size="30"/>
             </div>
            </div>
              <br>
             <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">键：</span>
             <input type="text"  class="form-control" name="key" />
             </div>
            </div>
          
        </div>
      </div>
      <div class="modal-footer">
       
        <button type="button" class="btn btn-primary" onclick="updateMenu();">确定</button>
         <button type="button" class="btn btn-danger" onclick="deleteMenuButton();" data-dismiss="modal">删除</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
	
<div class="modal fade" role="dialog" id="win_add_item" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" ></h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">类型：</span>
              <select class="form-control" name="type" >
              <option value="click">点击推事件</option>
              <option value="view">跳转URL</option>
              <option value="scancode_push">扫码推事件</option>
              <option value="scancode_waitmsg">扫码推事件且弹出“消息接收中”提示框</option>
              <option value="pic_sysphoto">弹出系统拍照发图</option>
              <option value="pic_photo_or_album">弹出拍照或者相册发图</option>
              <option value="pic_weixin">弹出微信相册发图器</option>
              <option value="location_select">弹出地理位置选择器</option>
             </select>
             </div>
          </div>
            <br>
            <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称：</span>
             <input type="text"  class="form-control" name="name" />
             </div>
            </div>
          
            <br>
              <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">链接：</span>
             <input type="text"  class="form-control" name="url" size="30"/>
             </div>
            </div>
              <br>
             <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">键：</span>
             <input type="text"  class="form-control" name="key" />
             </div>
            </div>
          
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="addMenuItem();">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->	
	
<div class="modal fade" role="dialog" id="win_update_item" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" ></h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">类型：</span>
              <select class="form-control" name="type" >
              <option value="click">点击推事件</option>
              <option value="view">跳转URL</option>
              <option value="scancode_push">扫码推事件</option>
              <option value="scancode_waitmsg">扫码推事件且弹出“消息接收中”提示框</option>
              <option value="pic_sysphoto">弹出系统拍照发图</option>
              <option value="pic_photo_or_album">弹出拍照或者相册发图</option>
              <option value="pic_weixin">弹出微信相册发图器</option>
              <option value="location_select">弹出地理位置选择器</option>
             </select>
             </div>
          </div>
            <br>
            <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称：</span>
             <input type="text"  class="form-control" name="name" />
             </div>
            </div>
          
            <br>
              <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">链接：</span>
             <input type="text"  class="form-control" name="url" size="30"/>
             </div>
            </div>
              <br>
             <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">键：</span>
             <input type="text"  class="form-control" name="key" />
             </div>
            </div>
          
        </div>
      </div>
      <div class="modal-footer">
        
        <button type="button" class="btn btn-primary" onclick="updateMenuItem();">确定</button>
        <button type="button" class="btn btn-danger" onclick="deleteMenuSubButton();" data-dismiss="modal">删除</button>
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
 	<script src="<c:url value='/resources/js/wechat/dy/menu/menu_manage.js' />"></script>
	
</body>
</html>