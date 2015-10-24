<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>仓库管理</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/maple.css'/>"></link>
	
	<!-- 引入外部j_s库 -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	
	<script type="text/javascript">
	$(function(){
		$(".deleteConfirm").click(function(event){
			var isConfirm =window.confirm("您确认要删除吗?");
			if(!isConfirm){
				event.preventDefault();
			}
		});
		
		
		//删除A标签按钮,当仓库不为空的时候,不能被删除;
		$("deleteATag");
		
		
	});
	
	</script>
	
<style type="text/css">
	.tx td{
		padding:3px;
	}
	.store{
		width:100%;
		border:1px solid gray;
		border-collapse:collapse;
	}
	.store td{
		border:1px solid gray;
		padding:3px;
	}
	.store a{
		text-decoration:underline;
		color:blue;
	}
	.odd{
	background-color: C3F3C3;
	}
	
	.even{
	background-color: F3C3F3;
	}
	
</style>
</head>
<body>
	<table border="0" class="tx" width="100%">
		<tr>
			<td>当前位置&gt;&gt;首页&gt;&gt;仓库管理</td>
		</tr>
	</table>
	<br>
	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr valign="top">
			<td rowspan="2">
			
				<form action="" method="post" name="select">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tx" align="center">
						<colgroup>
							<col width="20%" align="right">
							<col width="*%" align="left">
						</colgroup>
						<tr>
							<td bgcolor="a0c0c0" style="padding-left:10px;" colspan="1" align="left">
								<b>已经有仓库(当仓库不为空的时候是不能被删除的)：</b>
							</td>
						</tr>
						<tr>
							<td>
								<table class="store">
									<tr style="background:#D2E9FF;text-align: center;">
										<td>名称</td>
										<td>地址</td>
										<td>管理员</td>
										<td>操作</td>
									</tr>
									
									<s:iterator value="#stores" status="st" var="store" >
									<tr class="<s:property value="#st.even?'even':'odd'"/>">
										<td>
										<s:property value="{#store.name}"></s:property>
										</td>
										<td>
										<s:property  value="addr"></s:property>
										</td>
										<td>
										${manager}
										</td>
										<td>
										<s:a action="storeAction_editStore">
										<!-- 直接获取的id -->
											修改<s:param name="id" value="id"></s:param>
										</s:a>
										
										<s:if test="#store.goodses.size()>0">
											<a href="javascript:void(0);">删除</a><font color="red">[仓库不为空,不可以被删除]</font>
										<!-- href="javascript:void(-1);">都可以
										 -->
										</s:if>
										<s:else>
											<s:a  action="storeAction_deleteStore" id="deleteATag" cssClass="deleteConfirm">
											<!-- 从map中取的id -->
												删除<s:param name="id"  value="%{#store.id}"></s:param>
											</s:a>
										</s:else>
										
										</td>		
									</tr>
									</s:iterator>
									
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="right" style="padding-top:10px;">
								<input class="tx" style="width:120px;margin-right:30px;cursor: pointer;" 
								type="button" 
								onclick="window.location.href='<c:url value='/jsps/store/add.jsp'/>'" 
								value="创建新仓库">
							</td>
						</tr>
					</table>
				</form>
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
							1.显示所有货物的库存情况
							<br/>
							2.根据条件查询某种货的库存情况
							<br/>
							3.出入库完成后显示某种货物的库存情况
						</td>
					</tr>
					<tr>
						<td><img src="<c:url value='/picture/bottom.jpg'/>"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>

