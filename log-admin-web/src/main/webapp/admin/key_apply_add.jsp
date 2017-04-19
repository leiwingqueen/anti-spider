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
                            员工密钥申请 <small></small>
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
                            <li><span>员工密钥申请</span></li>
                        </ul>
                        <!-- END 页面标题和面包屑导航 -->
                    </div>
                </div>
                <!-- END 右容器头部-->
                <!-- BEGIN 右容器 main-->
                <div class="row-fluid">
                	<div class="portlet box blue">
                	    <div class="portlet-title">
                	        <div class="caption"><i class="icon-reorder"></i>员工密钥申请</div>
                	        <div class="tools"><a href="javascript:;" class="collapse"></a></div>
                	    </div>
                	    <div class="portlet-body">
                	    	<form action="#" id="J_formDemo" class="form form-horizontal">
                	    		<div class="control-group">
                                    <label class="control-label">用户名：</label>
                                    <div class="controls">
                                    	<div class="row-fluid">
                                    		<span class="span4 form-text">dw_kangchunhua</span>
                                    		<span class="span4 form-text">康春华</span>
                                    	</div>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">私钥密钥：</label>
                                    <div class="controls">
                                        <input type="text" placeholder="私钥密钥" id="J_key" name="J_key" required class="m-wrap span12" />
                                        <div class="space10"></div>
                                        <div class="alert mini alert-warn">必须为8位或8位以上并且同时不能全为数字或字母</div>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">确认密钥：</label>
                                    <div class="controls">
                                        <input type="text" placeholder="确认密钥" required equalTo="#J_key" class="m-wrap span12" />
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">直属上级：</label>
                                    <div class="controls">
                                        <span class="form-text">dw_wuerping</span>
                                    </div>
                                </div>
                                <div class="form-actions">
                                    <button type="submit" class="btn blue"><i class="icon-ok"></i> 申请</button>
                                </div>
                	    	</form>
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
    <script src="http://172.19.108.114/lib/0/0.1.0/assets/plugins/jquery-validation/dist/jquery.validate.js"></script>
    <script src="http://172.19.108.114/lib/0/0.1.0/assets/plugins/jquery-validation/dist/additional-methods.js"></script>
	<!-- BEGIN 页面基本js -->
    <script>
        $(document).ready(function() {    
            App.init();
            // 表单验证:
            var foo = function(a){
                alert(a);
            }
            App.validate('#J_formDemo',function(){
                foo('提交数据2');
            })
        });
    </script>
    <!-- END 页面基本js -->
	</body>
</html>

