<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="web" value="${pageContext.request.contextPath}" />
<!DOCTYPE>
<html>
<head>
<script type="text/javascript" src="${web}/static/js/jquery-1.11.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="${web}/static/jquery-easyui-1.3.6/themes/default/easyui.css" />
<link type="text/css" rel="stylesheet" href="${web}/static/jquery-easyui-1.3.6/themes/icon.css" />
<script type="text/javascript" src="${web}/static/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>

<title></title>

<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {
		equalTo : {
			validator : function(value, param) {
				return $(param[0]).val() == value;
			},
			message : '两次输入密码不一样'
		}
	});
	function fn_submit() {
		$.messager.progress();	// display the progress bar
		var isValid = $('#registerForm').form('validate');
		if (!isValid){
			$.messager.progress('close');	// hide progress bar while the form is invalid
		}else {
			$('#registerForm').submit();
		}
	}
</script>
</head>
<body>
	<div style="color: red">&nbsp;${registerMesg}</div>
	<div class="easyui-panel" title="用户注册" style="width:400px;padding:10px 60px 20px 60px;">
		<form id="registerForm" action="${web}/register.jhtml" method="post">
			<table cellpadding="5">
				<tr>
					<td>登录名：</td>
					<td><input name="username" class="easyui-validatebox textbox" value="${user.username }" data-options="missingMessage:'请输入登录名',required:true"></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input name="password" id="password" type="password" value="" class="easyui-validatebox textbox" data-options="missingMessage:'请输入密码.',required:true"></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input name="confirmPassword" type="password" value="" class="easyui-validatebox textbox" data-options="invalidMessage:'两次输入密码不一样'"  validType="equalTo['#password']" ></td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td><input name="nickname" class="easyui-validatebox textbox" value="${user.nickname }" data-options="missingMessage:'请输入你的姓名,用于在系统中展现',required:true"></td>
				</tr>
				<tr>
					<td>邮箱：</td>
					<td><input name="email" class="easyui-validatebox textbox" value="${user.email }" data-options="invalidMessage:'邮箱格式不正确',validType:'email'"></td>
				</tr>
				<tr>
					<td>电话：</td>
					<td><input name="telphone" class="easyui-validatebox textbox" value="${user.telphone }"></td>
				</tr>
				<tr>
					<td colspan="2" align="right" style="padding-right: 15px;"><a style="margin-right: 15px;font-size: 13px;color: #EE4E1B;" href="${web}/login.jhtml">登陆</a><a href="javascrpt:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="fn_submit();">提交</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>