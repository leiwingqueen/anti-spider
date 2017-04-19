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
                            空白页 <small>包含基本结构的空白模板</small>
                        </h3>
                        <ul class="breadcrumb">
                            <li>
                                <i class="icon-home"></i>
                                <a href="/">Home</a> 
                                <i class="icon-angle-right"></i>
                            </li>
                            <li>
                                <a href="javascript:;">Layouts</a>
                                <i class="icon-angle-right"></i>
                            </li>
                            <li><a href="javascript:;">空白页</a></li>
                        </ul>
                        <!-- END 页面标题和面包屑导航 -->
                    </div>
                </div>
                <!-- END 右容器头部-->
                <!-- BEGIN 右容器 main-->
                <div class="row-fluid">
                    <!-- 一切从这里开始，可以复制多个，表示一行又一行`(*∩_∩*)′
                        一个页面被分割为12个栅格，div.span1 ~ div.span12, span*n是浮动的，所以外面放row-fluid来清除浮动
                        <div class="row-fluid">
                            <div class="span3"></div>
                            <div class="span9"></div>
                        </div>
                     -->
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

