<%@page import="cn.ifactory.hypm.utils.ConstantData"%>
<%@page import="cn.ifactory.hypm.entity.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="web" value="${pageContext.request.contextPath}" />
<c:set value="<%=((User)session.getAttribute(ConstantData.SESSION_USER))  %>" var="currUser"></c:set>
<!DOCTYPE>
<html>
<head>
<script type="text/javascript" src="${web}/static/js/jquery-1.11.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="${web}/static/jquery-easyui-1.3.6/themes/default/easyui.css" />
<link type="text/css" rel="stylesheet" href="${web}/static/jquery-easyui-1.3.6/themes/icon.css" />
<script type="text/javascript" src="${web}/static/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>

<link href="${web}/static/umeditor1_2_2/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${web}/static/umeditor1_2_2/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${web}/static/umeditor1_2_2/umeditor.min.js"></script>
<script type="text/javascript" src="${web}/static/umeditor1_2_2/lang/zh-cn/zh-cn.js"></script>

<link rel="stylesheet" href="${web}/static/fancyBox2/source/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
<script type="text/javascript" src="${web}/static/fancyBox2/source/jquery.fancybox.pack.js?v=2.1.5"></script>

<%-- <link rel="stylesheet" type="text/css" href="${web}/static/fancyBox2/source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
<script type="text/javascript" src="${web}/static/fancyBox2/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script> --%>


<link href="${web}/static/css/index.css" type="text/css" rel="stylesheet">

<style type="text/css">
	/* .speechInputDiv {display: none;} */
	.speechOpDiv {position: fixed;z-index: 100;bottom: 1px;background-color: #eee;width: 100%;padding-top: 3px;text-align: right;min-height: 40px;margin-right: 15px;}
	.buttonToggle1 {float: right;margin-top: 6px;margin-right: 35px}
	.buttonToggle2 {display: none;float: right;margin-top: 6px;margin-right: 35px;}
	.fileAddIcon {max-height: 20px;padding-right: 8px;position: relative;bottom: -5px;cursor: pointer;}
	.fileSpan {float:left;background-color:#aaa;border:1px solid #aaa;margin: 1px 3px;}
	.fileRemove {cursor: pointer;margin-left: 5px;background-color: #888;padding: 0px 2px;}
	.attachment {
		padding-left: 20px;color: blue;
		text-decoration: none;
	}
	.attachment:HOVER {
		color:#ec6100;
	}
	.speechOpSpan {
		float: right;
		margin-right: 35px;
		position: relative;
	}
	.speechOpSpan img{
		padding-right: 1px;
		position: relative;
		bottom: -4px;
	}
	.speechOpSpan a{
		color: #069;
		margin-right: 6px;
	}
</style>
<title></title>
<script type="text/javascript">
	$(function() {
		$('.nodeToolTip').tooltip({
			content:$("<div><div>").append($("<pre></pre>").text($('.nodeToolTip').attr("title"))),
		    position: 'bottom',
		    onPosition: function(left,top){
		    	if(left < 0) {
		    		$(".tooltip").css("left","5px");
		    	}
		    }
		});
	});
</script>
</head>
<body  style="width: 100%;">
	<div class="loadingDiv"></div>
	<div id="easyuiLayout" class="easyui-layout" data-options="fit:true" style="width: inherit;">
		<div data-options="region:'north',border:false" style="height: 130px;overflow: hidden;">
			<h1 class="nodeTitle">
				<span>${node.name }</span>
			</h1>
			<div class="inner_top">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<c:if test="${not empty node.startTime and not empty node.endTime}">
						<tr>
							<th>计划时间：</th>
							<td>
									<fmt:formatDate value="${node.startTime}" pattern="yyyy-MM-dd"/> -------> <fmt:formatDate value="${node.endTime}" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
					</c:if>
					<tr>
						<th scope="row">参与人员：</th>
						<td>
							<c:forEach items="${node.users }" var="user">
								&nbsp;${user.nickname }&nbsp;,
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th scope="row">项目简介：</th>
						<td >
							<span class="nodeToolTip" title="${node.descp}" >
								${node.descpSimple}
							</span>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div data-options="region:'south',border:false" style="height: 35px; margin-top: 10px;position: relative;overflow: hidden;">
			<form id="speechForm" action="${web}/speech/input.jhtml" method="post">
				<div class="fileDiv" style="display: none;">
				</div>
				<input type="hidden" name="nodeId" value="${node.id}">
				<script type="text/plain" id="myEditor" name="content" style="height: 120px; width: 100%;"></script>
			</form>
		</div>
		<div data-options="region:'center',border:false" style="width: 733px; overflow-x: hidden; border: 1px solid #ECECEC;overflow-y:auto;">
			<div class="inner_in">
				<c:if test="${empty speechList }">
					还没有人发言...
				</c:if>
				<c:forEach items="${speechList }" var="speech">
					<div id="${speech.id}">
						<div class="inner_in_top" >
							<span>
								<fmt:formatDate value="${speech.createDate }" pattern="yyyy-MM-dd"/></span><span><fmt:formatDate value="${speech.createDate }" pattern="HH:mm:ss"/>
								<c:if test="${currUser.id == speech.user.id or currUser.admin}">
									<span class="speechOpSpan">
										<a class="commentInput" speechId="${speech.id}" href="javascript:void(0);">发表评论</a>
										<a class="commentView" speechId="${speech.id}" href="javascript:void(0);">查看评论(<label class="commentCounts" style="color:blue">${fn:length(speech.comments)}</label>条)</a>
										<a href="javascript:fn_speech_delete('${speech.id }');"><img src="${web }/static/img/delete.gif">删除</a>
									</span>
								</c:if>
							</span>
						</div>
						<div class="inner_in_center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th><img src="${web}/static/img/user_icon.png"><br>
										<span>${speech.user.nickname }</span></th>
									<td class="speechContent">${speech.content }<br>
									<c:if test="${not empty speech.attachs }">
										附件:<br>
										<c:forEach  items="${speech.attachs}" var="attach">
											<a class="attachment" href="${web}/attachment/download.jhtml?id=${attach.id}">${attach.name}</a>
										</c:forEach>
									</c:if>
									</td>
								</tr>
							</table>
						</div>
					</div>
					
				</c:forEach>
			</div>
		</div>
	</div>
	
	<div class="speechOpDiv">
		<span class="buttonToggle1">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="fn_speech_input_show();">发言</a>
		</span>
		<span class="buttonToggle2">
			<a href="javascript:fn_add_file();" title="添加文件" class="easyui-tooltip" data-options="position:'top'"><img class="fileAddIcon" src="${web}/static/img/add.png" alt="添加文件"></a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="fn_speech_submit();">提交发言</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="fn_speech_input_hide();">取消</a>
		</span>
	</div>
</body>

<script type="text/javascript">
	//实例化编辑器
	var um = UM.getEditor('myEditor');
	um.setHide();
	
	function fn_speech_input_show() {
		um.setShow();
		$(".buttonToggle1").hide();
		$(".buttonToggle2").show();
	}
	function fn_speech_input_hide() {
        um.setHide();
		$(".buttonToggle1").show();
		$(".buttonToggle2").hide();
	}
	
	function fn_speech_submit() {
		var content = um.getContent();
		if(!content){
			alert("请输入发言内容...");
			return false;
		}
		
		var $form = $("#speechForm");
		if($form.find(".fileDiv").find("input[type='file']").length > 0) {
			$form.attr("enctype","multipart/form-data");
		}else {
			$form.attr("enctype","application/x-www-form-urlencoded");
		}
		$(".loadingDiv").show();
		$form.submit();
	}
	
	function fn_speech_delete(id) {
		if(id) {
			if(confirm("确定删除？")) {
				$.ajax({
					url:"${web}/speech/delete.jhtml?id=" + id,
					dataType:"json",
					success:function(data,status) {
						if(data == true) {
							$("#" + id).remove();
						}else {
							alert("删除失败");
						}
					}
				});
			}
		}
		
	}
	
	
	$(function(){
/* 		 $('.commentInput').click(function() {
			  $(this).tooltip("show");
		 });
		 $('.commentView').click(function() {
		       $(this).tooltip("show");
		 }); */
		 //评论发表
         $('.commentInput').tooltip({
            content: function() {
            	var $content = $("<div></div>");
            	var $but = $("<a style='margin:3px 5px 0 0;float:right'></a>").attr("href","javascript:fn_comment_submit('" + $(this).attr("speechId") + "')");
            	$but.linkbutton({
            	    iconCls: 'icon-ok',
            	    text:"提交"
            	});
            	return $content.append($("<textarea class='commentCotent' style='width: 220px;height: 50px;resize:none;'></textarea>").attr("speechId",$(this).attr("speechId")))
            		.append("<br>").append($but)[0].outerHTML;
            	
            
            },
            showEvent: 'click',
            showDelay:0,
            hideDelay:500,
            onShow: function(){
                var t = $(this);
                t.tooltip('tip').unbind().bind('mouseenter', function(){
                    t.tooltip('show');
                }).bind('mouseleave', function(){
                    t.tooltip('hide');
                }); 
            }
        });
		 //评论查看
         $('.commentView').tooltip({
            content: function() {
            	var speechId = $(this).attr("speechId");
            	var $content = $("<div style='width:250px;max-height:150px;overflow-y:auto;text-align:center;'></div>");
            	$.ajax({
            		url:'${web}/speechComment/view.jhtml?speechId=' + speechId,
            		dataType:"json",
            		async:false,
            		success:function(data,status) {
            			if(data && data.length > 0) {
            				$content.css("text-align","left");
	            			for(var i in data) {
	            				$content.append($("<div style='background-color:#CECACA;'></div>").html((data.length - i) + "楼 " + "<label style='color:blue;'>" + data[i].speecherName + "</label> " + data[i].createDate ));
	            				$content.append($("<div></div>").text(data[i].content));
	            			}
            			}else {
            				$content.html("暂无评论");
            			}
            		},
            		error:function() {
            			$content.html("加载数据出错");
            		}
            	});
            	return $content;
            },
            showEvent: 'click',
            showDelay:0,
            hideDelay:400,
            onShow: function(){
                var t = $(this);
                t.tooltip('tip').unbind().bind('mouseenter', function(){
                    t.tooltip('show');
                }).bind('mouseleave', function(){
                    t.tooltip('hide');
                }); 
            }
        }); 
    });
	//发言评论提交
	function fn_comment_submit(speechId) {
		if(speechId) {
			var $content = $("textarea[speechId='" + speechId + "']");
			var $commentInput = $(".commentInput[speechId='" + speechId + "']");
			var $commentView = $(".commentView[speechId='" + speechId + "']");
			if($content.val()) {
				$.ajax({
					url:"${web}/speechComment/add.jhtml",
					type:"post",
					data:{
						speechId:speechId,
						content:$content.val()
					},
					dataType:"json",
					success:function(data,status) {
						if(data == true) {
							$commentInput.tooltip("hide");
							$commentView.tooltip("update"); 
							var $commentCounts = $(".commentView[speechId='" + speechId + "']").find(".commentCounts");
							$commentCounts.text(parseInt($commentCounts.text()) + 1);
							$content.val("");
						}else {
							alert("评论失败");
						}
					}
				});
			}
		}
	}
	
	function fn_add_file() {
		var $fileWrap = $("#speechForm .fileDiv");
		var fileId = new Date().getTime();
		var $file = $("<input type='file' name='attachments' />");
		$file.attr("id",fileId);
		$fileWrap.prepend($file);
		$file.change(function() {
			var path = $file.val();
			name = path.substr(path.lastIndexOf('\\')+1);
			if(name) {
				var $fileSpan = $("<span class='fileSpan'>");
				$fileSpan.text(name);
				var $removeIcon = $("<span class='fileRemove'>X</span>");
				$removeIcon.click(function(e) {
					$("#" + fileId).remove();
					$(this).parent("span").remove();
				});
				$fileSpan.append($removeIcon);
				$(".speechOpDiv").prepend($fileSpan);
			}
		});
		$file.trigger("click");
		
	}
	
	$(function() {
		$(".speechContent img[src*='.jhtml']").each(function(i) {
			$(this).wrap($("<a class='fancybox'></a>").attr("href",$(this).attr("src")));
		}); 
		$(".fancybox").fancybox({
			type:"image"
		});  
	});
</script>
</html>