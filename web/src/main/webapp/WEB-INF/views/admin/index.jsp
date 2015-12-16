<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="web" value="${pageContext.request.contextPath}" />
<!DOCTYPE>
<html>
<head>
<title></title>
<script type="text/javascript" src="${web}/static/js/jquery-1.11.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="${web}/static/jquery-easyui-1.3.6/themes/default/easyui.css" />
<link type="text/css" rel="stylesheet" href="${web}/static/jquery-easyui-1.3.6/themes/icon.css" />
<script type="text/javascript" src="${web}/static/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>

<style type="text/css">
.menuUl {
	padding-left: 10px;
}

.menuUl>li {
	width: 90px;
	height: 24px;
	background-image: url("${web}/static/img/tabbg01.gif");
	padding-left: 40px;
	margin-top: 10px;
	list-style: none;
	line-height: 24px;
	font-size: 1.1em;
	cursor: pointer;
}

#editButton {
	position: absolute;
	top: 0px;
	right: 0px;
	width: 50px;
	height: 20px;
	margin-top: 3px;
	margin-right: 10px;
	padding-top: 3px;
	border-radius: 5px 5px 5px 5px;
	background-color: #75CDFF;
	text-decoration: none;
	cursor: pointer;
	display: none;
}
#editButton:HOVER {
	background-color: #0FA7EB;
}
#editButton > img{
	margin-bottom: -3px;
}
</style>

<script type="text/javascript">
	$(function() {
		//menu event
		$(".menuUl > li").click(function(e) {
			$(".menuUl > li").css("background-image","url('${web}/static/img/tabbg01.gif')");
			$(this).css("background-image","url('${web}/static/img/tabbg02.gif')");
			$("#centerIframe").attr("src",$(this).attr("viewHref"));
			$("#myLayout").layout("panel","center").panel("setTitle",$(this).text());
			if($(this).attr("inputHref")) {
				$("#editButton").attr("href",$(this).attr("inputHref")).show();
			}else {
				$("#editButton").attr("href","javascript:void(0);").hide();
			}
		});
		
		//init a menu
		$(".menuUl > li").eq(0).trigger("click");
	});
	
</script>
</head>
<body id="myLayout" class="easyui-layout" style="width:1000px;margin:0 auto;background-color: #DAE7F6;">
    <div data-options="region:'north',border:false" style="height:50px;background-color: #DAE7F6;">
    	<div style="position: absolute;right: 10px;top: 5px"><a href="${ empty web ? '/' : web }">首页</a></div>
    </div>
    <div data-options="region:'west',border:true,title:'菜单管理',collapsible:false,split:true" style="width:165px;background-color: #DAE7F6;">
    	<ul class="menuUl">	
    		<li viewHref="${web}/admin/user/list.jhtml">用户管理</li>
    		<li viewHref="${web}/admin/log/list.jhtml">日志查看</li>
    	</ul>
    </div>
    <div data-options="region:'center',title:' '">
    	<iframe id="centerIframe" name="centerIframe" style="margin: 0px;padding: 0px;width: 100%;height: 100%;border-width: 0px;"></iframe>
    </div>
    <div data-options="region:'south'" style="height:25px; text-align: center;background-color: #E0ECFF;line-height: 25px;color:#555;overflow: hidden;">
    </div>
</body>
</html>