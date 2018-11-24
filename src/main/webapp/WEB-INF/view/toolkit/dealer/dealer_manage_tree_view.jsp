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
  <link rel="stylesheet" href="https://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
   <link href="<c:url value='/plugin/select2/dist/css/select2.min.css'/>" rel="stylesheet" />
  <script type="text/javascript">
  var userPath='<%=basePath%>';
  </script>
  <style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
 </head>
   <body >
 
<div class="container-fluid">
<br>
	<div class="row-fluid">
		<div class="col-md-12">
			
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
              <div class="panel panel-inverse">
              <div class="panel-heading" role="tab" id="headingOne">
              <h4 class="panel-title">
              <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                       经销商管理
              </a>
             </h4>
            </div>
           <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
             <div class="panel-body">
               <div class="container-fluid">
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">搜索</span>
             <input type="text" id="keyword"  class="form-control" placeholder="输入关键词" aria-describedby="sizing-addon2" >
                     <span class="input-group-addon" ><button type="button" class="btn btn-primary btn-xs" onclick="searchNode();" >搜索</button></span>
             </div>
          </div>
          </div>
             <ul id="treeDemo" class="ztree" ></ul>
           </div>
      <div class="panel-footer">
      <button type="button" class="btn btn-primary" data-toggle="modal" onclick="showAddBigAreaModal();">新建大区</button>
      <button type="button" class="btn btn-primary" onclick="window.location.href='toolkit/dealer/manage.htm';" >切换为列表视图</button>
         
       </div>
          </div>
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
        <h4 class="modal-title" >添加</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row" >
             <div class="col-md-12" id="addModal_map" style="height: 300px">
             </div>
          </div>
          <br>
           
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="title" id="addModal_title" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">经度</span>
             <input type="text" name="longitude" id="addModal_longitude" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div> 
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">纬度</span>
             <input type="text" name="latitude" id="addModal_latitude" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div> 
         </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="addModalSearchAddress();">定位</button>
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
          <div class="row" >
             <div class="col-md-12" id="editModal_map" style="height: 300px">
             </div>
          </div>
          <br>
           <input type="hidden" name="id"  >
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">名称</span>
             <input type="text" name="title" id="editModal_title" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">经度</span>
             <input type="text" name="longitude" id="editModal_longitude" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div> 
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">纬度</span>
             <input type="text" name="latitude" id="editModal_latitude" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
           <br>
           <div class="row" id="provinceSelectRow" >
           <div class="col-md-12 input-group">
           <span class="input-group-addon" >所在省</span>
           <select id="provinceSelect" name="province.id" class="form-control selectpicker" data-size="20" data-live-search="true" data-style="btn-white">
                    <option value="" selected="selected">请选译</option>
                   <c:forEach var="item" items="${provinceList}" varStatus="status">
                  <option value="${item.id}">${item.title}</option>
                 </c:forEach>
           </select>
           </div>
          </div>
            <br>
           <div class="row" id="bigAreaSelectRow" >
           <div class="col-md-12 input-group">
           <span class="input-group-addon" >所在大区</span>
           <select id="bigAreaSelect" name="bigArea.id" class="form-control selectpicker" data-size="20" data-live-search="true" data-style="btn-white">
                    <option value="" selected="selected">请选译</option>
                   <c:forEach var="item" items="${bigAreaList}" varStatus="status">
                  <option value="${item.id}">${item.title}</option>
                 </c:forEach>
           </select>
           </div>
          </div>
         </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="editModalSearchAddress();">定位</button>
        <button type="button" class="btn btn-primary" onclick="update();">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="modal fade" role="dialog" id="addDealerModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >添加</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row" >
             <div class="col-md-12" id="addDealerModal_map" style="height: 300px">
             </div>
          </div>
          <br>
         
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">经销商销售代码</span>
             <input type="text" name="dealerCode" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
         
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">经销商服务代码</span>
             <input type="text" name="dealerServiceCode" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
           <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">经销商名称</span>
             <input type="text" name="dealerName" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">地址</span>
             <input type="text" name="fordBrandShopAddress" id="addDealerModal_fordBrandShopAddress" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">邮编</span>
             <input type="text" name="postcode" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
               <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">区号</span>
             <input type="text" name="areaCode" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
                <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">销售热线</span>
             <input type="text" name="salesHotline" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
                 <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">服务电话</span>
             <input type="text" name="serviceTel" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
                <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">传真</span>
             <input type="text" name="fax" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
              <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">邮箱</span>
             <input type="text" name="emailAddress" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">经度</span>
             <input type="text" name="longitude" id="addDealerModal_longitude" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div> 
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">纬度</span>
             <input type="text" name="latitude" id="addDealerModal_latitude" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div> 
         </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="addDealerModalSearchAddress();">定位</button>
        <button type="button" class="btn btn-primary" onclick="saveDealer();">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="modal fade" role="dialog" id="editDealerModal" aria-labelledby="gridSystemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >编辑</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
          <div class="row" >
             <div class="col-md-12" id="editDealerModal_map" style="height: 300px">
             </div>
          </div>
          <br>
           <input type="hidden" name="id"  >
         
         <br>
           <div class="row" >
           <div class="col-md-12 input-group">
           <span class="input-group-addon" >所在市</span>
           <select name="city.id" class="form-control selectpicker" data-size="20" data-live-search="true" data-style="btn-white">
                    <option value="" selected="selected">请选译</option>
                   <c:forEach var="item" items="${cityList}" varStatus="status">
                  <option value="${item.id}">${item.title}</option>
                 </c:forEach>
           </select>
           </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">经销商销售代码</span>
             <input type="text" name="dealerCode" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">经销商服务代码</span>
             <input type="text" name="dealerServiceCode" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
           <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">经销商名称</span>
             <input type="text" name="dealerName" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">地址</span>
             <input type="text" name="fordBrandShopAddress" id="editDealerModal_fordBrandShopAddress" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
            <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">邮编</span>
             <input type="text" name="postcode" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
               <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">区号</span>
             <input type="text" name="areaCode" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
           <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">销售热线</span>
             <input type="text" name="salesHotline" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
           <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">服务电话</span>
             <input type="text" name="serviceTel" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
                <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">传真</span>
             <input type="text" name="fax" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
              <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">邮箱</span>
             <input type="text" name="emailAddress" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div>
          <br>
           <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">经度</span>
             <input type="text" name="longitude" id="editDealerModal_longitude" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div> 
          <br>
          <div class="row">
             <div class="col-md-12 input-group">
             <span class="input-group-addon" id="sizing-addon1">纬度</span>
             <input type="text" name="latitude" id="editDealerModal_latitude" class="form-control"  aria-describedby="sizing-addon2" >
             </div>
          </div> 
         </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="editDealerModalSearchAddress();">定位</button>
        <button type="button" class="btn btn-primary" onclick="updateDealer();">保存</button>
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
   <script src="<c:url value='/resources/js/toolkit/dealer/dealer_manage_tree_view.js' />"></script>
   <script src="<c:url value='/plugin/blockui/jquery.blockUI.js' />"></script>
   <script src="<c:url value='/plugin/select2/dist/js/select2.min.js' />"></script>
  <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=B797ad09b828b8226be5744cb706f7a3&s=1"></script>
  <script type="text/javascript" src="https://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
 
   </body>
</html>
