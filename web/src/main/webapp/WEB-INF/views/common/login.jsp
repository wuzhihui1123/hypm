<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="web" value="${pageContext.request.contextPath}" />
<!DOCTYPE>
<html>
<head>
<title></title>
<style>
*{padding:0px; margin:0px; list-style:none;}
body{width:100%;margin:0px;padding:0px;background:url(${web}/static/img/login_bg03.jpg);width:100%;height:100%}
.top{width:960px;height:336px;margin:0 auto; background:url(${web}/static/img/login_bg01.jpg) no-repeat; position:relative}
.bottom{width:960px;height:305px;margin:0 auto; background:url(${web}/static/img/login_bg02.jpg) no-repeat; position:relative}
#username,#p_t,#password,#validatecode{ position:absolute;top:206px;left:187px;width:193px;height:34px;border:0px;font-size:14px;line-height:30px;color:#666}
#p_t,#password{left:402px; z-index:2}
#validatecode{left:618px; z-index:2;width:113px}
#vcodesrc{ position:absolute;top:165px;left:616px; font-family:Arial}
#password{z-index:1;color:#333}
#login_bt{position:absolute;top:207px;left:625px;width:102px;height:33px; background:transparent; z-index:3;border:0px; cursor:pointer}
.forget{color:#94adc3;position:absolute;top:247px;left:700px;font-size:12px; text-decoration:none}
.forget1{color:#ff6600;position:absolute;top:247px;left:745px;font-size:12px; text-decoration:none}
.item_list{position:absolute;top:70px;left:505px;width:300px;padding-top:20px;}
ul,li{padding:0;margin:0; font-size:12px;list-style:none; border:0;font-weight:normal;}
.item_list ul{width:100%}
.item_list li{width:100%;line-height:24px;color:#fff;}
.errorMesg {
	position: absolute;
	bottom: 5px;
	left: 40%;
	color: red;
}
</style>

<script type="text/javascript">
	function fn_logincheck() {
		return true;
	}
	
/* 	$(function() {
		$("#loginForm input[name='username'],#loginForm input[name='password']").change(function(e) {
			$(".errorMesg").empty();
		});
	}); */
</script>

<body>
<div class="top">
<form id="loginForm" method="POST" action="${web}/login.jhtml">
	<input type="text" autofocus="true" id="username" name="username" value="" />
    <input type="password" id="password" name="password" />
    <input type="submit" value="" id="login_bt" name="login_bt" onclick="return logincheck()"/>
    <a href="${web}/register.jhtml" class="forget">注册</a>
</form>
	<span class="errorMesg">${error}</span>
</div>
<div class="bottom">
	<div class="item_list">
   		<ul>
        </ul>
    </div>
</div> 
</body>
	
</html>