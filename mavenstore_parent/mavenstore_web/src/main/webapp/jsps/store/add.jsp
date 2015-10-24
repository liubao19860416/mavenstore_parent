<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加新仓库</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/maple.css'/>"></link>
	
<style type="text/css">
	.tx td{
		padding:3px;
	}
</style>

<!-- 引入外部j_s库 -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript">
	
	$(function(){
		//取消修改操作的确认和取消操作的处理实现;
		$("#cancelbutton").click(function(event){
			var isConfirm=window.confirm("您确定要放弃修改吗");
			if(!isConfirm){
				event.preventDefault();
			}else{
				//window.history.go(-1);
				window.location.href="${pageContext.request.contextPath}/storeAction_listAll.action";
			}
		});
		
		
	//添加光标聚焦
	$("input[name='name']").focus();
	//$("form input[name='name']").focus();
		
	});
	</script>
	
	
</head>
<body>
	<!-- 中间内容（开始） -->
	<table border="0" class="tx" width="100%">
		<tr>
			<td>当前位置&gt;&gt;首页&gt;&gt;创建新仓库</td>
		</tr>
	</table>
	<br>
	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr valign="top">
			<td rowspan="2">
			<s:form  action="storeAction_addStore" method="post" id="myform" name="select" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tx" align="center">
						<colgroup>
							<col width="20%" align="right">
							<col width="*%" align="left">
						</colgroup>
						
						<tr>
							<td bgcolor="a0c0c0" style="padding-left:10px;" colspan="2" align="left">
								<b>创建新仓库：</b>
							</td>
						</tr>
						
						<tr>
						<!-- 更新仓库校验错误信息回显位置 -->
							<td colspan="3">
								<s:fielderror id="errorid" cssStyle="color:red;background:yellow;"></s:fielderror>
							</td>
						</tr>
						
						<tr>
							<td>
								仓库名称：
							</td>
							<td>
							<s:textfield cssClass="tx" name="name" id="store.id"/>
							<!-- 
								<input class="tx" type="text" id="myname"  name="name">
							 -->
							</td>
						</tr>
						<tr>
							<td>
								仓库地址：
							</td>
							<td>
								<input class="tx" type="text" name="addr">
							</td>
						</tr>
						<tr>
							<td>
								库管员：
							</td>
							<td>
								<input class="tx" type="text" name="manager">
							</td>
						</tr>
						<tr>
							<td colspan="3" align="center" style="padding-top:10px;">
								<input class="tx" style="width:120px;margin-right:30px;cursor: pointer;" type="button" onclick="document.forms[0].submit();" value="确定">
								<input class="tx" style="width:120px;margin-right:30px;cursor: pointer;" type="reset" value="清除">
								<input class="tx" style="width:120px;margin-right:30px;cursor: pointer;" type="reset"  id="cancelbutton" value="取消">
							</td>
						</tr>
					</table>
				</s:form>
			</td>
			<td valign="top" width="20%">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td background="<c:url value='/picture/loginpage.gif'/>" align="center"><br>
						<font color="red">操作步骤</font>
						</td>
					</tr>
					<tr>
						<td background="<c:url value='/picture/bg1.jpg'/>" style="padding-left:10px;">
							1.输入仓库的名称,地址
							<br/>
							2.保存后直接去显示仓库列表
						</td>
					</tr>
					<tr>
						<td><img src="<c:url value='/picture/bottom.jpg'/>"></td>
					</tr>
				</table>
		</tr>
	</table>
</body>
</html>

