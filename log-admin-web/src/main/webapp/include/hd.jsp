<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 8]> <html class="ie8"> <![endif]-->
<!--[if IE 9]> <html class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html>
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title id="PAGETITLE">系统日志管理后台</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<link rel="shortcut icon" href="http://static.gzdai.com/sysop_fed_lib/assets/img/ico.png" />
	<!-- BEGIN 全局样式 所有页面都必须引入 -->
	<link href="http://static.gzdai.com/sysop_fed_lib/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
	<link href="http://static.gzdai.com/sysop_fed_lib/assets/plugins/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" />
	<link href="http://cdn.bootcss.com/font-awesome/3.2.0/css/font-awesome.min.css"	rel="stylesheet" />
	<link href="http://static.gzdai.com/sysop_fed_lib/assets/css/style-metro.css" rel="stylesheet" />
	<link href="http://static.gzdai.com/sysop_fed_lib/assets/css/style.css"	rel="stylesheet" />
	<link href="http://static.gzdai.com/sysop_fed_lib/assets/css/style-responsive.css" rel="stylesheet" />
	<link href="http://static.gzdai.com/sysop_fed_lib/assets/css/themes/default.css" rel="stylesheet" id="style_color" />
	<link href="http://static.gzdai.com/sysop_fed_lib/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" />
	<link href="http://static.gzdai.com/sysop_fed_lib/assets/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet" />
	<link rel="stylesheet" href="http://static.gzdai.com/sysop_fed_lib/assets/plugins/data-tables/DT_bootstrap.css" />
	<!-- END 全局样式 所有页面都必须引入 -->
	<!-- 具体页面样式 -->
	<link href="/css/page.css" rel="stylesheet" />
</head>
<body class="page-header-fixed page-sidebar-fixed">
	<!-- BEGIN 页面头部 -->
	<div class="header navbar navbar-inverse navbar-fixed-top">
		<!-- BEGIN 顶部导航条 -->
		<div class="navbar-inner">
			<div class="container-fluid">
				<!-- BEGIN LOGO -->
				<a class="brand" href="/"> <img width="30" height="30" src="http://static.gzdai.com/sysop_fed_lib/assets/img/ico.png" alt="logo" /> <span class="font-small">系统日志管理后台</span> </a>
				<!-- END LOGO -->
				<!-- BEGIN 响应式时 菜单按钮 -->
				<a href="javascript:;" class="btn-navbar collapsed"
					data-toggle="collapse" data-target=".nav-collapse"> <img
					src="http://static.gzdai.com/sysop_fed_lib/assets/img/menu-toggler.png"
					alt="" /> </a>
				<!-- END 响应式时 菜单按钮 -->
				<!-- BEGIN 导航条右侧菜单 -->
				<ul class="nav pull-right">
					<!-- BEGIN 用户信息 -->
					<li class="dropdown user"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" data-hover="dropdown"
						data-close-others="true"> <!-- <img width="28" height="28" alt="" src="http://static.gzdai.com/sysop_fed_lib/assets/img/avatar.png" /> -->
							欢迎您：<span class="username" id="userInfo"></span> <i
							class="icon-angle-down"></i> </a>
						<ul class="dropdown-menu">
							<li><a href="javascript:;"><i class="icon-user"></i> 资料</a>
							</li>
							<li class="ip-address"><span><i
									class="icon-map-marker"></i> <b id="serverIps"></b> </span></li>
							<li><a href="javascript:;" id="trigger_fullscreen"><i
									class="icon-move"></i> 全屏</a></li>
							<li><a href="#" id="J_logout"><i class="icon-key"></i>
									退出</a></li>
						</ul>
					</li>
					<!-- END 用户信息 -->
					<!-- BEGIN 快速通道 -->
					<li class="dropdown quick-launcher"><a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true"> <span>快速通道</span>
							<i class="icon-angle-down"></i> </a>
						<ul class="dropdown-menu" id="J_appList">
						</ul>
					</li>
					<!-- END 快速通道 -->
				</ul>
				<!-- END 导航条右侧菜单 -->
			</div>
		</div>
		<!-- END 顶部导航条 -->
	</div>
	<!-- END 页面头部 -->
	<!-- BEGIN 页面主容器 -->
	<div class="page-container row-fluid">
		<!-- BEGIN 左边栏 -->
		<div class="page-sidebar nav-collapse collapse">
			<!-- BEGIN 左边栏 菜单 -->
			<!-- BEGIN 左边栏 显示隐藏按钮 -->
			<div class="sidebar-toggler hidden-phone"></div>
			<!-- BEGIN 左边栏 显示隐藏按钮 -->
			<ul class="page-sidebar-menu" id="menu-container"></ul>
			<!-- END 左边栏 MENU -->
		</div>
		<!-- END 左边栏 -->
