<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="web" value="${pageContext.request.contextPath}" />
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

<link href="${web}/static/umeditor1_2_2/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${web}/static/umeditor1_2_2/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${web}/static/umeditor1_2_2/umeditor.min.js"></script>
<script type="text/javascript" src="${web}/static/umeditor1_2_2/lang/zh-cn/zh-cn.js"></script>

<link href="${web}/static/css/index.css" type="text/css" rel="stylesheet">
<title></title>

	<SCRIPT type="text/javascript">
	IDMark_A = "_a";
	
		var setting = {
			view: {
				dblClickExpand: dblClickExpand,
				showIcon: showIconForTree,
				addDiyDom: addDiyDom
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onRightClick: OnRightClick,
				onClick: zTreeOnClick
			}
		};

		var zNodes =[
			{ id:1, pId:0, name:"禾源云谷（北京）农业科技有限公司", open:true,iconOpen:"${web}/static/ztree3/css/zTreeStyle/img/diy/1_open.png", iconClose:"${web}/static/ztree3/css/zTreeStyle/img/diy/1_close.png"},
			{ id:11, pId:1, name:"思陆云谷之小苹果", open:true, checked:true},
			{ id:12, pId:1, name:"思陆云谷之特选迷你南瓜", open:true},
			{ id:13, pId:1, name:"思陆云谷电子商务平台", open:true},
			{ id:111, pId:11, name:"云谷电商平台一期"},
			{ id:112, pId:11, name:"用户体验备选方案汇总一期"},
			{ id:121, pId:12, name:"云谷官网"},
			{ id:122, pId:12, name:"公众平台_微信"},
			{ id:123, pId:12, name:"迷你南瓜线下销售 1/4周"}
		];
		
		function addDiyDom(treeId, treeNode) {
			if (treeNode.parentNode) return;
			var aObj = $("#" + treeNode.tId + IDMark_A);
			var editStr = "<span class='demoIcon' id='diyBtn_" +treeNode.id+ "' title='"+treeNode.name+"' onfocus='this.blur();'><span class='button icon09'></span></span>";
			aObj.append(editStr);
			var btn = $("#diyBtn_"+treeNode.id);
			if (btn) btn.bind("click", function(e){OnRightClick(e, treeId, treeNode);});
		}
		
		function showIconForTree(treeId, treeNode) {
			return treeNode.getParentNode() == null;
		}
	function zTreeOnClick(event, treeId, treeNode) {
		    $(".pro_name").text(treeNode.name);
		}
	/* function fn_showPro(name) {
			$(".pro_name").text(name);
			
		} */
		function dblClickExpand(treeId, treeNode) {
			return treeNode.level > 0;
		}

 		$(document).ready(function(){
			fn_ztree_init();
		}); 
		
		function fn_ztree_init(){
			$.ajax({
				url:"${web}/node/getAll.jhtml",
				dataType:"json",
				success:function(data,status) {
					$.fn.zTree.init($("#treeDemo"), setting, data);
					zTree = $.fn.zTree.getZTreeObj("treeDemo");
					rMenu = $("#rMenu"); 
				},
				error:function() {
					alert("项目数据加载失败");
				}
			});
		}
		
		
		
		function OnRightClick(event, treeId, treeNode) {
			if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
				showRMenu("root", event.clientX, event.clientY);
			} else if (treeNode && !treeNode.noR) {
				zTree.selectNode(treeNode);
				showRMenu("node", event.clientX, event.clientY);
			}
		}

		function showRMenu(type, x, y) {
			$("#rMenu ul").show();
			if (type=="root") {
				$("#m_del").hide();
				$("#m_check").hide();
				$("#m_unCheck").hide();
			} else {
				$("#m_del").show();
				$("#m_check").show();
				$("#m_unCheck").show();
			}
			rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

			$("body").bind("mousedown", onBodyMouseDown);
		}
		function hideRMenu() {
			if (rMenu) rMenu.css({"visibility": "hidden"});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
		function onBodyMouseDown(event){
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				rMenu.css({"visibility" : "hidden"});
			}
		}
		var addCount = 1;
		
		function fn_addTreeNode() {
			hideRMenu();
			if (zTree.getSelectedNodes()[0]) {
				fn_node_add(zTree.getSelectedNodes()[0]);
			}
		}
		function fn_updateTreeNode() {
			hideRMenu();
			if (zTree.getSelectedNodes()[0]) {
				fn_node_update(zTree.getSelectedNodes()[0]);
			}
		}
		function fn_removeTreeNode() {
			hideRMenu();
			var nodes = zTree.getSelectedNodes();
			if (nodes && nodes.length>0) {
				if (nodes[0].children && nodes[0].children.length > 0) {
					alert("要删除的节点是父节点，请先删除子节点");

				} else {
					fn_node_delete(nodes[0]);
				}
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
			$("#nodeForm input[name='id']").val("");
			$("#nodeForm input[name='parentId']").val("");
			var opTitle = "";
			if(opType == "add"){
				opTitle = "新增";
				$("#nodeForm input[name='parentId']").val(node.id);
			}else if(opType == "update") {
				opTitle = "修改";
				$("#nodeForm input[name='id']").val(node.id);
				$("#nodeForm input[name='name']").val(node.name);
				$("#nodeForm input[name='descp']").val(node.descp);
			}else {
				return;
			}
			
			$(".nodeInputDiv").dialog({
				width : 500,
				height : 400,
				resizable : true,
				title : opTitle,
				buttons : [ {
					text : '确定',
					handler : function() {
						$(".loadingDiv").show();
						$("#nodeForm").ajaxSubmit({
							dataType:"text",
							resetForm:true,
							success:function(data,status) {
								fn_ztree_init();
								$(".nodeInputDiv").dialog("close");
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
	</script>

</head>
<body  style="width: 1050px;">
<div class="loadingDiv"></div>
 <div class="easyui-layout" style="width:100%;height:100%;">
        <div data-options="region:'west',split:true,border:false" style="width:315px;">
        	<ul id="treeDemo" class="ztree"></ul>
			<div id="rMenu">
				<ul>
					<li id="m_add" onclick="fn_addTreeNode();">增加节点</li>
					<li id="m_del" onclick="fn_updateTreeNode();">修改节点</li>
					<li id="m_del" onclick="fn_removeTreeNode();">删除节点</li>
				</ul>
			</div>
        </div>
        <div data-options="region:'center',border:false"  style="width: 733px;">
            <div class="easyui-layout" data-options="fit:true" style="width: 733px;">
                <div data-options="region:'north',border:false" style="height:180px">
                	  <h1><span>禾源云谷官网建设</span></h1>
					  <div class="inner_top">
					    <table width="100%" border="0" cellspacing="0" cellpadding="0">
					      <tr>
					        <th scope="row">项目简介：</th>
					        <td>禾源云谷成立于2013年，是以农业为基础，以新兴科技为先导的创新型农业科技公司。
					          在城市化的今天，农业虽为人类的生存之本，却一直以一种低附加值的状态，看不见地存在；    
					          在食品安全戕害民生的当下，本该轻松享用的一菜一饭，也让人疑窦丛生。</td>
					      </tr>
					      <tr>
					        <th scope="row">参与人员：</th>
					        <td>王二、某某、宋佳欣、王语嫣、刘思齐、霍霍、梁晓云 ......</td>
					      </tr>
					    </table>
					  </div>
                </div>
                <div data-options="region:'south',border:false" style="height: 230px;margin-top: 15px;">
                	<script type="text/plain" id="myEditor" style="height: 140px;width: 100%;">
					</script>
                </div>
                <div data-options="region:'center',border:false" style="width: 733px;overflow-x:hidden;border: 1px solid #ECECEC">
                	  <div class="inner_in">
					    <div class="inner_in_top"><span>2014-06-06</span><span>AM10:00</span> </div>
					    <div class="inner_in_center">
					      <table width="100%" border="0" cellspacing="0" cellpadding="0">
					        <tr>
					          <th><img src="${web}/static/img/user_icon.png"><br>
					            <span>名字哈</span></th>
					          <td>1、网站分为五部分:首页、关于我们、我们的产品、联系我们、诚聘英才<br>
					            2、具体内容介绍文本方面详见附件。<br>
					            3、网站风格需要宽屏大气的效果。<br>
					            4、参考网站 hy.xyz0.cn www.winnerway.com<br>
					            附件：<a href="#">文件名.doc</a></td>
					        </tr>
					      </table>
					    </div>
					    <div class="inner_in_top"><span>2014-06-06</span><span>AM10:00</span> </div>
					    <div class="inner_in_center">
					      <table width="100%" border="0" cellspacing="0" cellpadding="0">
					        <tr>
					          <th><img src="${web}/static/img/user_icon.png"><br>
					            <span>名字哈</span></th>
					          <td>1、网站分为五部分:首页、关于我们、我们的产品、联系我们、诚聘英才<br>
					            2、具体内容介绍文本方面详见附件。<br>
					            3、网站风格需要宽屏大气的效果。<br>
					            4、参考网站 hy.xyz0.cn www.winnerway.com<br>
					            附件：<a href="#">文件名.doc</a></td>
					        </tr>
					      </table>
					    </div>
					  </div>
                </div>
            </div>
        </div>
    </div>
    
    <div style="display: none;">
    	<div class="nodeInputDiv">
    		<form id="nodeForm" action="${web}/node/input.jhtml" method="post">
    			<input type="hidden" name="id">
    			<input type="hidden" name="parentId">
	    		<table>
	    			<tr>
	    				<td>名称</td>
	    				<td><input type="text" name="name" /></td>
	    			</tr>
	    			<tr>
	    				<td>描述</td>
	    				<td><input type="text" name="descp" /></td>
	    			</tr>
	    			<tr>
	    				<td>计划开始时间</td>
	    				<td> <input class="easyui-datebox" name="startTimeStr" data-options="formatter:fn_myformatter,parser:fn_myparser"></input></td>
	    			</tr>
	    			<tr>
	    				<td>计划结束时间</td>
	    				<td><input class="easyui-datebox" name="endTimeStr" data-options="formatter:fn_myformatter,parser:fn_myparser"></input></td>
	    			</tr>
	    			<tr>
	    				<td>参与用户</td>
	    				<td>
	    					<input type="hidden" name="userIds" ></input>
	    					<input type="text" id="userSelect" name="userNames" readonly></input><a id="menuBtn" href="#" onclick="showUserMenu(); return false;">选择</a>
	    				</td>
	    			</tr>
	    		</table>
    		</form>
    	</div>
    	
    	
    </div>
   	<div id="menuContent" class="menuContent" style="display:none; position: absolute;z-index: 10000;background-color: #ddd;border: 1px solid #888;">
		<ul id="userTree" class="ztree" style="margin-top:0; width:180px; height: 300px;overflow-y:auto;" ></ul>
	</div>
</body>

<script type="text/javascript">
    //实例化编辑器
   var um = UM.getEditor('myEditor');
    
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
					chkboxType: {"Y":"", "N":""}
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
	   var userNodes =[
	    			{id:1, pId:0, name:"北京"},
	    			{id:2, pId:0, name:"天津"},
	    			{id:3, pId:0, name:"上海"},
	    			{id:6, pId:0, name:"重庆"},
	    			{id:4, pId:0, name:"河北省", open:true, nocheck:true},
	    			{id:41, pId:4, name:"石家庄"},
	    			{id:42, pId:4, name:"保定"},
	    			{id:43, pId:4, name:"邯郸"},
	    			{id:44, pId:4, name:"承德"},
	    			{id:5, pId:0, name:"广东省", open:true, nocheck:true},
	    			{id:51, pId:5, name:"广州"},
	    			{id:52, pId:5, name:"深圳"},
	    			{id:53, pId:5, name:"东莞"},
	    			{id:54, pId:5, name:"佛山"},
	    			{id:6, pId:0, name:"福建省", open:true, nocheck:true},
	    			{id:61, pId:6, name:"福州"},
	    			{id:62, pId:6, name:"厦门"},
	    			{id:63, pId:6, name:"泉州"},
	    			{id:64, pId:6, name:"三明"}
	    		 ];
	   $.fn.zTree.init($("#userTree"), uSetting, userNodes);
   }
	function beforeClick(treeId, treeNode) {
		var userTree = $.fn.zTree.getZTreeObj("userTree");
		userTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
   
	function onCheck(e, treeId, treeNode) {
		var userTree = $.fn.zTree.getZTreeObj("userTree"),
		nodes = userTree.getCheckedNodes(true),
		v = "";
		var ids = "";
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			ids += nodes[i].id + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (ids.length > 0 ) ids = ids.substring(0, ids.length-1);
		var cityObj = $("#userSelect");
		cityObj.attr("value", v);
		var idsObj = $("#nodeForm input[name='userIds']");
		idsObj.val(ids);
	}

	function showUserMenu() {
		var cityObj = $("#userSelect");
		var cityOffset = $("#userSelect").offset();
		$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideUserMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "userSelect" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideUserMenu();
		}
	}
</script>
</html>