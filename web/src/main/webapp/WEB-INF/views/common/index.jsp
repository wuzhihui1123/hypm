<%@page import="cn.ifactory.hypm.entity.User"%>
<%@page import="cn.ifactory.hypm.utils.ConstantData"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="web" value="${pageContext.request.contextPath}" />
<c:set value="<%=((User)session.getAttribute(ConstantData.SESSION_USER))  %>" var="currUser"></c:set>
<!DOCTYPE>
<html>
<head>
<script type="text/javascript" src="${web}/static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${web}/static/js/jquery.form.min.js"></script>
<link type="text/css" rel="stylesheet" href="${web}/static/jquery-easyui-1.3.6/themes/default/easyui.css" />
<link type="text/css" rel="stylesheet" href="${web}/static/jquery-easyui-1.3.6/themes/icon.css" />
<script type="text/javascript" src="${web}/static/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>

<link type="text/css" rel="stylesheet" href="${web}/static/ztree3/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="${web}/static/ztree3/js/jquery.ztree.all-3.5.min.js"></script>

<%-- <link rel="stylesheet" href="${web}/static/fancyBox2/source/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
<script type="text/javascript" src="${web}/static/fancyBox2/source/jquery.fancybox.pack.js?v=2.1.5"></script> --%>

<link href="${web}/static/css/index.css" type="text/css" rel="stylesheet">
<style type="text/css">
	.freeDay1,.freeDay2 {
		font-weight: bold;
		padding-left: 5px;
	}
	.freeDay1 {
		color: red;
	}
	.freeDay2 {
		color: orange;
	}
	.userMesg {
		position: fixed;top: 10px;right: 5px;width: 80px;border: 1px solid #95B8E7;padding: 8px 4px;font-size: 13px;line-height: 22px;
	}
	.zTreeBut {
		padding:5px;border-width: 0px;border-top-width: 1px;bottom: 0px;position: absolute;height: 40px!important;overflow: hidden;
	}
	.zTreeBut .l-btn-text {
		font-size: 14px;
	}
	.userMesg .easyui-linkbutton,.userMesg .l-btn-left,.userMesg .l-btn-text {
		width: 100%;
		text-align: left;
	}
	.panel-fit, .panel-fit body {
		overflow: auto;
	}
	.nodeTreeEdit {
		background-color: #D6D2D2;
	}
</style>
<title></title>

	<SCRIPT type="text/javascript">

		//数组包含判断
		Array.prototype.contains = function(element) {
			for (var i = 0; i < this.length; i++) {
				if (this[i] == element) {
					return true;
				}
			}
			return false;
		};

		var setting = {
			view : {
				dblClickExpand : dblClickExpand,
				addDiyDom : addDiyDom,
				fontCss : getFont,
				showIcon : showIconForTree
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				beforeDrop : beforeDrop,
				onRightClick : OnRightClick,
				onClick : zTreeOnClick
			/* ,
							beforeDrag: zTreeBeforeDrag */
			},
			edit : {
				drag : {
					autoExpandTrigger : true,
					prev : true,
					inner : true,
					next : true
				},
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			}
		};

		function zTreeBeforeDrag(treeId, treeNodes) {
		}
		function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
			var ret = false;
			$.ajax({
				url : "${web}/node/move.jhtml",
				async : false, //必须为同步请求
				data : {
					id : treeNodes[0].id,
					targetId : targetNode.id
				},
				success : function(data, status) {
					if (data == true) {
						ret = true;
					} else {
						alert("移动失败");
						ret = false;
					}
				},
				dataType : "json",

			});
			return ret;
		}

		function addDiyDom(treeId, treeNode) {
			if (treeNode.freeDay > 0 && treeNode.freeDay < 7) {
				var aObj = $("#" + treeNode.tId + "_a");
				var $freeDay = $("<span>(剩余 " + treeNode.freeDay + " 天)</span>");
				if (treeNode.freeDay < 4) {
					$freeDay.addClass("freeDay1");
				} else {
					$freeDay.addClass("freeDay2");
				}
				aObj.after($freeDay);
			}
		}
		function getFont(treeId, node) {
			//自身参与项目的样式
			if(node.userIds.contains("${currUser.id}")) {
				return {'color':'blue'};
			}else {
				return {};
			}
		}

		function showIconForTree(treeId, treeNode) {
			//return true;
			return treeNode.getParentNode() == null;
		}
		function zTreeOnClick(event, treeId, treeNode) {
			var url = "${web}/node/detail.jhtml?id=" + treeNode.id;
			if (url != $("#centerIframe").attr("src")) {
				$("#centerIframe").attr("src", url);
			}
			zTree.expandNode(treeNode, true);
		}
		function dblClickExpand(treeId, treeNode) {
			return treeNode.level > 0;
		}

		$(document).ready(function() {
			fn_ztree_init();
		});

		function fn_ztree_init() {
			$.ajax({
						url : "${web}/node/getAll.jhtml",
						dataType : "json",
						success : function(data, status) {
							for ( var i in data) {
								if (!data[i].pId) {//根节点图标
									data[i].iconOpen = "${web}/static/ztree3/css/zTreeStyle/img/diy/1_open.png";
									data[i].iconClose = "${web}/static/ztree3/css/zTreeStyle/img/diy/1_close.png";
								}
							}
							/* 					for(var i in data) {//一级展开
							 if(!data[i].pId) {
							 data[i].iconOpen = "${web}/static/ztree3/css/zTreeStyle/img/diy/1_open.png";
							 data[i].iconClose = "${web}/static/ztree3/css/zTreeStyle/img/diy/1_close.png";
							 data[i].open = true;
							 for(var j in data) {//二级展开
							 if(data[j].pId == data[i].id) {
							 data[j].open = true;
							 }
							 }
							 }
							 } */
							$.fn.zTree.init($("#treeDemo"), setting, data);
							zTree = $.fn.zTree.getZTreeObj("treeDemo");
							zTree.expandAll(true);
							rMenu = $("#rMenu");
						},
						error : function() {
							alert("项目数据加载失败");
						}
					});
		}

		function OnRightClick(e, treeId, treeNode) {
			if (treeNode) {
				zTree.selectNode(treeNode);
				$('#rightMenu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		}

		function fn_addTreeNode() {
			if (zTree.getSelectedNodes()[0]) {
				fn_node_add(zTree.getSelectedNodes()[0]);
			} else {
				alert("请先选择一个节点");
			}
		}
		/* 		function fn_setEditTree() {
		 if($("#editStatus").is(":checked")) {
		 $("#editStatus").prop("checked",false);
		 $("#westRegion").removeClass("nodeTreeEdit");
		 zTree.setEditable(false);
		 }else {
		 $("#editStatus").prop("checked",true);
		 $("#westRegion").addClass("nodeTreeEdit");
		 zTree.setEditable(true);
		 } 
		 } */
		function fn_updateTreeNode() {
			if (zTree.getSelectedNodes()[0]) {
				fn_node_update(zTree.getSelectedNodes()[0]);
			} else {
				alert("请先选择一个节点");
			}
		}
		function fn_removeTreeNode() {
			$('#rightMenu').menu('hide');
			var nodes = zTree.getSelectedNodes();
			if (nodes && nodes.length > 0) {
				if (nodes[0].children && nodes[0].children.length > 0) {
					alert("要删除的节点是父节点，请先删除子节点");

				} else {
					if (confirm("确认要删除？")) {
						fn_node_delete(nodes[0]);
					}
				}
			} else {
				alert("请先选择一个节点");
			}
		}
	</SCRIPT>
	
	<script type="text/javascript">
		function fn_node_add(parentNode) {
			fn_node_input(parentNode,"add");
		}
		function fn_node_update(node) {
			fn_node_input(node,"update");
		}
		
		function fn_node_input(node,opType) {
			if(!node) return;
			//clear from
			$("#nodeForm").get(0).reset();
			$("#nodeForm input[name='id']").val("");
			$("#nodeForm input[name='parentId']").val("");
			$("#nodeForm textarea[name='descp']").text("");
			var userTree = $.fn.zTree.getZTreeObj("userTree");
			//取消所有选择的用户
			var checkNodes = userTree.getCheckedNodes(true);
			for( var i in checkNodes) {
				userTree.checkNode(checkNodes[i], false,false, true);
			}
			var opTitle = "";
			if(opType == "add"){
				opTitle = "新增";
				$("#nodeForm input[name='parentId']").val(node.id);
			}else if(opType == "update") {
				opTitle = "修改";
				$("#nodeForm input[name='id']").val(node.id);
				$("#nodeForm input[name='name']").val(node.name);
				$("#nodeForm textarea[name='descp']").text(node.descp);
				$("#startTime").datebox('setValue', node.startTime);	
				$("#endTime").datebox('setValue', node.endTime);
				for(var i in node.userIds) {
					var uNode = userTree.getNodeByParam("id", node.userIds[i], null);
					if(uNode) {
						userTree.checkNode(uNode, true, false,true);
					}
				}
			}else {
				return;
			}
			
			$(".nodeInputDiv").dialog({
				width : 500,
				height : 400,
				resizable : true,
				modal:true,
				title : opTitle,
				buttons : [ {
					text : '确定',
					handler : function() {
						$(".loadingDiv").show();
						$("#nodeForm").ajaxSubmit({
							dataType:"json",
							resetForm:true,
							success:function(data,status) {
								$(".nodeInputDiv").dialog("close");
								if(opType == "update"){
									//刷新
									//(document.getElementById("centerIframe").contentWindow || document.frames["centerIframe"]).location.reload();
									for(var prop in data) {
										if(node[prop]) {
											node[prop] = data[prop];
										}
									}
									zTree.updateNode(node);
								}else if(opType == "add") {
									zTree.addNodes(node, data,false);
								}
							},
							error:function() {
								alert("出错了");
							},
							complete:function (XMLHttpRequest, textStatus) {
								$(".loadingDiv").hide();
							}
						});
					}
				}, {
					text : '取消',
					handler : function() {
						$(".nodeInputDiv").dialog("close");
					}
				} ]
			});
			
		}
		
		function fn_node_delete(node) {
			if(!node) return;
			$(".loadingDiv").show();
			$.ajax({
				url:"${web}/node/delete.jhtml",
				data:{
					id:node.id
				},
				success:function(data,status) {
					fn_ztree_init();
				},
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					alert("删除失败");
				},
				complete:function (XMLHttpRequest, textStatus) {
					$(".loadingDiv").hide();
				}
				
			});
		}
		
		$(function() {
			$("#checkbox_empty,#checkbox_full").click(function() {
				$(this).hide();
				$("#checkbox_empty,#checkbox_full").not($(this)).show();
				if($(this).attr("id") == "checkbox_empty") {
					$("#westRegion").addClass("nodeTreeEdit");
					zTree.setEditable(true);
				}else {
					$("#westRegion").removeClass("nodeTreeEdit");
					zTree.setEditable(false);
				}
			});
		})
	</script>

</head>
<body  style="min-width: 950px;max-width: 1360px;padding-right: 90px;">
<div class="loadingDiv"></div>
 <div class="easyui-layout" style="width:100%;height:100%;" data-options="fit:true">
        <div id="westRegion" data-options="region:'west',split:true,border:false" style="width:265px;position: relative;">
        	<ul id="treeDemo" class="ztree"></ul>
        	<div  class="easyui-panel zTreeBut">
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'"  style="height: 100%;" onclick="fn_addTreeNode();">新增</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" style="height: 100%;" onclick="fn_updateTreeNode();">修改</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" style="height: 100%;" onclick="fn_removeTreeNode();">删除</a>
				<a id="checkbox_empty" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'checkbox_empty'" style="height: 100%;">编辑</a>
				<a id="checkbox_full"  href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'checkbox_full'" style="height: 100%;display: none;">编辑</a>
				
			</div>
			<div id="rightMenu" class="easyui-menu" style="width:120px;">
		        <div data-options="iconCls:'icon-add'" onclick="fn_addTreeNode();">新增</div>
		        <div data-options="iconCls:'icon-edit'" onclick="fn_updateTreeNode();">修改</div>
		        <div data-options="iconCls:'icon-cancel'" onclick="fn_removeTreeNode();">删除</div>
		    </div>
        </div>
        <div data-options="region:'center',border:false">
        	<iframe name="centerIframe" id="centerIframe" src="${web}/welcome.jhtml" style="width: 100%;height: 100%;overflow: hidden;border-width: 0px;width: 100%;">
        	</iframe>
        </div>
    </div>
    
    <div style="display: none;">
    	<div class="nodeInputDiv" style="text-align: center;">
    		<form id="nodeForm" action="${web}/node/input.jhtml" method="post">
    			<input type="hidden" name="id">
    			<input type="hidden" name="parentId">
	    		<table style="border-spacing: 8px;margin: 15px 5px 15px 20px;">
	    			<tr>
	    				<td align="right">名称：</td>
	    				<td><input type="text" name="name" /></td>
	    			</tr>
	    			<tr>
	    				<td align="right">计划开始时间：</td>
	    				<td> <input id="startTime" class="easyui-datebox" name="startTimeStr" data-options="formatter:fn_myformatter,parser:fn_myparser"></input></td>
	    			</tr>
	    			<tr>
	    				<td align="right">计划结束时间：</td>
	    				<td><input id="endTime" class="easyui-datebox" name="endTimeStr" data-options="formatter:fn_myformatter,parser:fn_myparser"></input></td>
	    			</tr>
	    			<tr>
	    				<td align="right">参与用户：</td>
	    				<td>
	    					<input type="hidden" name="userIds" ></input>
	    					<input type="text" id="userSelect" name="userNames" readonly></input><a id="menuBtn" href="#" onclick="showUserMenu(); return false;">选择</a>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td align="right" style="vertical-align: top;">描述：</td>
	    				<td><textarea name="descp"></textarea></td>
	    			</tr>
	    		</table>
    		</form>
    	</div>
    	
    	
    </div>
   	<div id="menuContent" class="menuContent" style="display:none; position: absolute;z-index: 10000;background-color: #ddd;border: 1px solid #888;">
		<ul id="userTree" class="ztree" style="margin-top:0; width:180px; height: 300px;overflow-y:auto;" ></ul>
	</div>
	
	<span class="userMesg">
		<span style="padding-left:19px;background-repeat: no-repeat;background-image: url('${web}/static/img/user.png');">${currUser.nickname}</span>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="self.location.href='${web}/logout.jhtml'">注销</a><br>
		<c:if test="${currUser.admin }">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="self.location.href='${web}/admin.jhtml'">后台管理</a><br>
		</c:if>
	</span>
	
	<div id="mm" class="easyui-menu" style="width:120px;">
        <div data-options="iconCls:'icon-add'" onclick="fn_addTreeNode();">新增</div>
        <div data-options="iconCls:'icon-edit'" onclick="fn_updateTreeNode();">修改</div>
        <div data-options="iconCls:'icon-cancel'" onclick="fn_removeTreeNode();">删除</div>
    </div>
</body>

<script type="text/javascript">
    
   function fn_myformatter(date){
       var y = date.getFullYear();
       var m = date.getMonth()+1;
       var d = date.getDate();
       return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
   }
   function fn_myparser(s){
       if (!s) return new Date();
       var ss = (s.split('-'));
       var y = parseInt(ss[0],10);
       var m = parseInt(ss[1],10);
       var d = parseInt(ss[2],10);
       if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
           return new Date(y,m-1,d);
       } else {
           return new Date();
       }
   }
   
   $(function() {
	   fn_user_tree_init(); 
   });
   
   function fn_user_tree_init() {
		var uSetting = {
				check: {
					enable: true,
					chkboxType: {"Y":"ps", "N":"ps"}
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeClick: beforeClick,
					onCheck: onCheck
				}
			};
	   $.ajax({
		  url:"${web}/user/getAll.jhtml", 
		  dataType:"json",
		  success:function(data,status) {
			  data.push({'name':'全选','id':'-1','pId':null,'open':true});

				for ( var i in data) {
					if (data[i].pId) {//节点图标
						data[i].icon = "${web}/static/img/tree_user.png";
					}
				}
				$.fn.zTree.init($("#userTree"), uSetting, data);
			}
		});
	}
	function beforeClick(treeId, treeNode) {
		var userTree = $.fn.zTree.getZTreeObj("userTree");
		userTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}

	function onCheck(e, treeId, treeNode) {
		var userTree = $.fn.zTree.getZTreeObj("userTree"), nodes = userTree
				.getCheckedNodes(true), v = "";
		var ids = "";
		for (var i = 0, l = nodes.length; i < l; i++) {
			if (!nodes[i].pId)
				continue;
			v += nodes[i].name + ",";
			ids += nodes[i].id + ",";
		}
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		if (ids.length > 0)
			ids = ids.substring(0, ids.length - 1);
		var cityObj = $("#userSelect");
		cityObj.attr("value", v);
		var idsObj = $("#nodeForm input[name='userIds']");
		idsObj.val(ids);
	}

	function showUserMenu() {
		var cityObj = $("#userSelect");
		var cityOffset = $("#userSelect").offset();
		$("#menuContent").css({
			left : cityOffset.left + "px",
			top : cityOffset.top + cityObj.outerHeight() + "px"
		}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideUserMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "userSelect"
				|| event.target.id == "menuContent" || $(event.target).parents(
				"#menuContent").length > 0)) {
			hideUserMenu();
		}
	}
</script>

<script type="text/javascript">
	$(function() {
		//禁止easyui 日期框自输入
		$(".datebox :text").attr("readonly","readonly");
	});
</script>
</html>