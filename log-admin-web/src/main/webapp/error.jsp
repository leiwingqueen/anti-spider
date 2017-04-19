<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>出错啦 ~\(≧▽≦)/~</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css" />
<style>
* {margin: 0; padding: 0; font-size: 13px;}
.wrap-head {margin: 0 auto; margin-top: 200px; width: 550px;}
.header {border: #f88 2px solid; padding: 20px;}
.header .text {font-size: 23px; font-weight: bold; margin-left: 50px;}
.header .cont {height: 50px; margin-top: 25px;}
.header .cont span {color: #f00;}
.header .foot {text-align: center;}

.dashline {height: 30px; border-top: #ccc 1px dashed;}
</style>
</head>
<%
 	String error = (String)request.getAttribute("error");
	System.out.println(error);
%>
<body class="login-body">
<div class="wrap-head">
	<div class="header">
		<div class="logo">
        	<span class="pic"><img src="/img/logo_v4.jpg" /></span>
            <span class="text">出错啦 ~\(≧▽≦)/~</span>
        </div>
        <div class="cont">
      		易贷管理系统提示: <span><%=error%></span>
        </div>
        <div class="dashline"></div>
        <div class="foot">
        	<input type="button" class="btn" value="返回" onclick="history.back();" />
        	<!-- input type="button" class="button" value="重新登录" onclick="window.location.href='/udbSdkLogin.jsp';" /-->
        </div>
	</div>
</div>
<div class="login-wrap">
	<div class="box-login">
    </div>
</div>
<div class="footer">
</div>
</body>
</html>