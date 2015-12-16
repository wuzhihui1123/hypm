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
	html,body {
		width: 100%;
		height: 100%;
	}
	.c-label{
            display:inline-block;
            width:100px;
            font-weight:bold;
        }
    .equipmentImg {
    	width:120px;
    	height: 140px;
    }
    .userOp {
    	margin: 0px 5px;
    }
</style>
<script type="text/javascript">
	function fn_userType(value,row,index) {
		if(row.admin) {
			return "管理员";
		}else {
			return "普通用户";
		}
	} 
	function fn_userStatus(value,row,index) {
		if(row.useable) {
			return "正常";
		}else {
			return "禁用";
		}
	} 
	function fn_action(value,row,index) {
		var ret = "";
		if(row.useable) {
			ret = ret + '<a class="userOp" href="javascript:fn_disable(\'' + row.id + '\');">禁用</a>';
		}else {
			ret = ret + '<a class="userOp" href="javascript:fn_useable(\'' + row.id + '\');">启用</a>';
		}
		if(row.admin) {
			ret = ret + '<a class="userOp" href="javascript:fn_admin_cacel(\'' + row.id + '\');">取消管理员</a>';
		}else {
			ret = ret + '<a class="userOp" href="javascript:fn_admin(\'' + row.id + '\');">设为管理员</a>';
		}
		return ret;
	}
</script>
</head>
<body>
    <table id="hypmTable" style="width:100%;height:100%;">
        <thead>
            <tr>
                <th field="id" hidden="true"></th>
                <th field="username" width="80" sortable="true" >用户名</th>
                <th field="nickname" width="80" sortable="true" >姓名</th>
                <th field="email" width="100" >邮箱</th>
                <th field="userType" width="60" formatter="fn_userType">用户类型</th>
                <th field="userStatus" width="60" formatter="fn_userStatus">状态</th>
                <th field="createDate" width="120" sortable="true">注册日期</th>
                <th field="action" width="120" align="center" formatter="fn_action">操作</th>
            </tr>
        </thead>
    </table>    
</body>
<script type="text/javascript">

	//禁用
	function fn_disable(id) {
		$.ajax({
			url:'${web}/admin/user/disable.jhtml',
			type:"get",
			data:{
				id:id
			},
			success:function(data,status) {
				$('#hypmTable').datagrid('reload');
			},
			error:function() {
				alert("操作失败");
			}
		});
	}
	//启用
	function fn_useable(id) {
		$.ajax({
			url:'${web}/admin/user/useable.jhtml',
			type:"get",
			data:{
				id:id
			},
			success:function(data,status) {
				$('#hypmTable').datagrid('reload');
			},
			error:function() {
				alert("操作失败");
			}
		});
	}
	//设为管理员
	function fn_admin(id) {
		$.ajax({
			url:'${web}/admin/user/admin.jhtml',
			type:"get",
			data:{
				id:id
			},
			success:function(data,status) {
				$('#hypmTable').datagrid('reload');
			},
			error:function() {
				alert("操作失败");
			}
		});
	}
	//取消管理员
	function fn_admin_cacel(id) {
		$.ajax({
			url:'${web}/admin/user/adminCacel.jhtml',
			type:"get",
			data:{
				id:id
			},
			success:function(data,status) {
				$('#hypmTable').datagrid('reload');
			},
			error:function() {
				alert("操作失败");
			}
		});
	}

	$(function() {
		$('#hypmTable').datagrid({
			//view: cardview,
			url : "${web}/admin/user/getAll.jhtml",
			idField : "id",
			rownumbers : true,
			singleSelect : true,
			fitColumns : true,
			pagination : true,
			striped : true,
			fit : true,
			selectOnCheck : false,
			checkOnSelect : false,
			remoteSort : false,
			pageList : [ 50, 100, 200 ],
			pageSize : 50
		});
	});
</script>
</html>