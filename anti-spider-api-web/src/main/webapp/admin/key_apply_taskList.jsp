<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
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
                            待我审批列表 <small></small>
                        </h3>
                        <ul class="breadcrumb">
                            <li>
                                <i class="icon-home"></i>
                                <a href="/">Home</a> 
                                <i class="icon-angle-right"></i>
                            </li>
                            <li>
                                <span>员工密钥管理</span>
                                <i class="icon-angle-right"></i>
                            </li>
                            <li><span>待我审批列表</span></li>
                        </ul>
                        <!-- END 页面标题和面包屑导航 -->
                    </div>
                </div>
                <!-- END 右容器头部-->
                <!-- BEGIN 右容器 main-->
                <div class="row-fluid">
                	<div class="portlet box default">
                	    <div class="portlet-title">
                	        <div class="caption"><i class="icon-reorder"></i>搜索</div>
                	        <div class="tools"><a href="javascript:;" class="collapse"></a></div>
                	    </div>
                	    <div class="portlet-body">
                	    	<form action="#" class="form form-horizontal">
                	    		<div class="row-fluid">
                	    			<div class="span6">
                	    				<div class="control-group">
		                	    		    <label class="control-label">开始时间：</label>
		                	    		    <div class="controls">
		                	    	    		<input type="text" />
		                	    		    </div>
		                	    		</div>
                	    			</div>
                	    			<div class="span6">
                	    				<div class="control-group">
		                	    		    <label class="control-label">结束时间：</label>
		                	    		    <div class="controls">
		                	    	    		<input type="text" />
		                	    		    </div>
		                	    		</div>
                	    			</div>
                	    		</div>
                	    		<div class="row-fluid">
                	    			<div class="span6">
                	    				<div class="control-group">
		                	    		    <label class="control-label">提单人：</label>
		                	    		    <div class="controls">
		                	    	    		<input type="text" />
		                	    		    </div>
                	    				</div>
                	    			</div>
                	    			<div class="span6">
                	    				<div class="control-group">
		                	    		    <label class="control-label">申请人：</label>
		                	    		    <div class="controls">
		                	    	    		<input type="text" />
		                	    		    </div>
		                	    		</div>
                	    			</div>
                	    		</div>
                	    		<div class="form-actions">
                	    			<button type="submit" class="btn blue"><i class="icon-ok"></i> 搜索</button>
                	    		</div>
                	    	</form>
                	    </div>
                	</div>
                </div>
                <div class="row-fluid">
                	<div class="portlet box default">
                	    <div class="portlet-title">
                	        <div class="caption"><i class="icon-reorder"></i> 列表</div>
                	        <div class="tools"><a href="javascript:;" class="collapse"></a></div>
                	    </div>
                	    <div class="portlet-body">
                	    	<table class="table table-bordered table-striped table-hover">
                	    	    <thead>
                	    	        <tr>
                	    	            <th>流程ID：</th>
                	    	            <th>流程类型：</th>
                	    	            <th>提单人：</th>
                	    	            <th>申请人：</th>
                	    	            <th>申请时间：</th>
                	    	            <th>所在步骤：</th>
                	    	            <th>操作：</th>
                	    	        </tr>
                	    	    </thead>
                	    	    <tbody>
                	    	        <tr>
                	    	            <td>11733</td>
                	    	            <td>11</td>
                	    	            <td>康春华</td>
                	    	            <td>康春华</td>
                	    	            <td>2013-10-11 20:30:00</td>
                	    	            <td>主管审批</td>
                	    	            <td>
                	    	            	<a href="#" class="btn mini green"><i class="icon-book"></i> 查看流程</a>
                	    	            	<a href="#" class="btn mini blue"><i class="icon-pencil"></i> 签收流程</a>
                	    	            	<a href="#" class="btn mini red"><i class="icon-arrow-left"></i> 返回</a>
                	    	            </td>
                	    	        </tr>
                	    	        <tr>
                	    	            <td>11733</td>
                	    	            <td>11</td>
                	    	            <td>康春华</td>
                	    	            <td>康春华</td>
                	    	            <td>2013-10-11 20:30:00</td>
                	    	            <td>主管审批</td>
                	    	            <td>
                	    	            	<a href="#" class="btn mini green"><i class="icon-book"></i> 查看流程</a>
                	    	            	<a href="#" class="btn mini blue"><i class="icon-pencil"></i> 签收流程</a>
                	    	            	<a href="#" class="btn mini red"><i class="icon-arrow-left"></i> 返回</a>
                	    	            </td>
                	    	        </tr>
                	    	        <tr>
                	    	            <td>11733</td>
                	    	            <td>11</td>
                	    	            <td>康春华</td>
                	    	            <td>康春华</td>
                	    	            <td>2013-10-11 20:30:00</td>
                	    	            <td>主管审批</td>
                	    	            <td>
                	    	            	<a href="#" class="btn mini green"><i class="icon-book"></i> 查看流程</a>
                	    	            	<a href="#" class="btn mini blue"><i class="icon-pencil"></i> 签收流程</a>
                	    	            	<a href="#" class="btn mini red"><i class="icon-arrow-left"></i> 返回</a>
                	    	            </td>
                	    	        </tr>
                	    	    </tbody>
                	    	</table>
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
	<!-- BEGIN 页面基本js -->
    <script>
        $(document).ready(function() {    
            App.init();
        });
    </script>
    <!-- END 页面基本js -->
	</body>
</html>

