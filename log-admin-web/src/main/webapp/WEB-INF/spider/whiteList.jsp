<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="/include/hd.jsp"></jsp:include>
<!-- BEGIN PAGE -->
<div class="page-content">
	<!-- BEGIN 右边容器-->
	<div class="container-fluid">
		<!-- BEGIN 右容器头部-->
		<div class="row-fluid">
			<div class="span12">
				<!-- BEGIN 页面标题和面包屑导航 -->
				<h3 class="page-title">
					IP白名单管理<small></small>
				</h3>
				<ul class="breadcrumb">
					<li><i class="icon-home"></i> <a href="/">Home</a>
					</li>
				</ul>
				<!-- END 页面标题和面包屑导航 -->
			</div>
		</div>
		<!-- END 右容器头部-->
		<!-- BEGIN 右容器 main-->
		<div class="row-fluid">
			<div class="portlet box default">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i> 查询
					</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
				<form id="fm" class="form form-horizontal">
					<!-- BEGIN 查询条件 -->
				 	<div class="control-group">
				 		<label class="control-label">创建时间：</label> 
				 		<div class="controls">
				 		<input id="startTime" name="startTime"
							class="input-medium J_calendar" type="text" />~<input id="endTime"
							name="endTime" class="input-medium J_calendar" type="text" />
						</div>
				 	</div>
				 	<div class="control-group">
				 		<label class="control-label">IP：</label>
						<div class="controls">
							<input id="ip" name="ip" style="width: 100px">
						</div>
				 	</div>
				 	<!-- END 查询条件 -->
				 	<div class="form-actions">
                            <a id="J_saveForm" class="btn green btn-primary"style="left: 500px" onclick="searchData()">查询</a>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="btn red btn-primary"  onclick="addNew()">添加</a>
                    </div>
				</form>
			</div>
			</div>
			<div class="portlet box default">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-reorder"></i> 列表查询
					</div>
				</div>
				<div class="portlet-body">
					<table class="table table-bordered table-striped table-hover">
						<thead>
							<tr>
								<th>序号</th>
								<th>IP</th>
								<th>创建时间</th>
								<th>创建者</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody ms-controller="vm_accessLog">
							<tr ms-repeat-item="list" >	
								<td>{{$index + 1}}</td>
								<td>{{item.ip}}</td>
								<td>{{item.createTime | date('yyyy-MM-dd &nbsp; HH:mm:ss')}}</td>
								<td>{{item.createAdmin}}</td>
								<td>
									<a class="btn red btn-primary"  ms-click="deleteDialog(item.ip)">删除</a>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- 放置分页 -->
					<div id="common_page"></div>
				</div>
			</div>
		</div>
		<!-- END 右容器 main-->
	</div>
	<!-- END 右边容器-->
</div>
<!-- END PAGE -->
</div>
<!-- END 页面主容器 -->

<jsp:include page="/include/ft.jsp"></jsp:include>
<script src="/js/common.js"></script>
<script src="/js/base.js"></script>
<!-- BEGIN 页面基本js -->
<script src="/js/spider/whiteList.js"></script>
<!-- END 页面基本js -->
</body>
</html>