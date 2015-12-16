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
</style>

<script type="text/javascript">
	function fn_desc(value,row,index) {
		return "<label title='" + value + "'>" + value + "</label>";
	}
</script>
</head>
<body>
    <table id="hypmTable" style="width:100%;height:100%;">
        <thead>
            <tr>
                <th field="id" hidden="true"></th>
                <th field="target" width="60" sortable="true" >对象名</th>
                <th field="operateTypeStr" width="40" >操作类型</th>
                <th field="createDate" width="80" >操作时间</th>
                <th field="operator" width="60" >操作人</th>
                <th field="descp" width="200" formatter="fn_desc">描述</th>
            </tr>
        </thead>
    </table>    
</body>
<script type="text/javascript">

	$(function() {
		$('#hypmTable').datagrid({
			//view: cardview,
			url : "${web}/admin/log/getByPage.jhtml",
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