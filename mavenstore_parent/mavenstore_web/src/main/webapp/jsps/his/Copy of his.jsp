<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>历史记录</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/maple.css'/>"></link>

<!-- 引入外部j_s库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>

<style type="text/css">
.tx td {
	padding: 3px;
}

.store {
	width: 100%;
	border: 1px solid gray;
	border-collapse: collapse;
}

.store td {
	border: 1px solid gray;
	padding: 3px;
}

.store a {
	text-decoration: underline;
	color: blue;
}
.odd{
	background-color: C3F3C3;
	}
	
	.even{
	background-color: F3C3F3;
	}
	
</style>
</head>

<script type="text/javascript">
	$(function() {

		//查询显示下拉链表选择框AJAX实现
		$.get("${pageContext.request.contextPath}/storeAction_ajaxList.action",
				function(data) {
					$(data).each(
							function() {
								var $option = $("<option value='"+this.id+"'>"
										+ this.name + "</option>");
								$("#selectId").append($option);
							});
				});
		
		//显示所有历史记录的按钮
		$("#showAllHsitories").click(function(){
			window.location.href="${pageContext.request.contextPath}/historyAction_listAllHsitories.action";
		});

	//聚焦
	$("#focusposition").focus();
	
	
	//客户输入的选择的指定页码
	$("#selectedPageNum1").click(function(event){
		//alert($("input[name='selectedPageNum2']").val());
		//试一下
		var pageUrl=$("input[name='selectedPageNum1']").val();
		//alert($("input[name='selectedPageNum1']").val());
		var pageNum=new Number();
		 pageNum=$("input[name='selectedPageNum2']").val();
		//这一行不可少!!!
		 pageNum=new Number(pageNum);
		
		//添加js校验
		
		//校验数据类型格式
		var regex=/^[0-9]+$/;
		if(!regex.test(pageNum)){
			alert("请输入数字格式的页码信息!");
			return false;
			//event.preventDefault();
		}
		//判断页码范围
		var totalPageCount= $("#totalPageCount").html();
		//alert($("#totalPageCount").text());
		if(pageNum>totalPageCount||pageNum<=0){
			alert("请输入正确的页码,页码范围是[1-"+totalPageCount+"]");
			return false;
			//event.preventDefault();
		}
		//下面的语句可以拦截任何的信息,等价于
		//语句;中断后面的代码不执行;//return false;//event.preventDefault();
		//alert("拦截失败!");
		window.location.href="${pageContext.request.contextPath}/historyAction_listAllHsitoriesByPage.action?pageNum="+pageNum+"&"+pageUrl;
	});
	
	
	
	
	});
	

	
</script>



<body>
	<table border="0" class="tx" width="100%">
		<tr>
			<td>当前位置&gt;&gt;首页&gt;&gt;历史记录</td>
		</tr>
	</table>
	<br>
	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td rowspan="1"><s:form
					action="historyAction_listAllHsitoriesByPage.action" method="post"
					name="select">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="tx" align="center">
						<colgroup>
							<col width="20%" align="right">
							<col width="*%" align="left">
						</colgroup>
						<tr>
							<td bgcolor="a0c0c0" style="padding-left:10px;" colspan="2"
								align="left"><b>查询条件：</b>
							</td>
						</tr>
						<tr>
							<td>简记码：</td>
							<td><s:textfield cssClass="tx" id="focusposition" name="goods.nm"></s:textfield>
							</td>
						</tr>
						<tr>
							<td>货物名称：</td>
							<td><s:textfield cssClass="tx" name="goods.name"></s:textfield>
							</td>
						</tr>
						<tr>
							<td>选择仓库：</td>
							<td><select class="tx" id="selectId" style="width: 120px;" name="goods.store.id">
									<option value="">请选择仓库</option>
							</select>
							</td>
						</tr>
						<tr>
							<td colspan="1" align="center" style="padding-top:10px;"><input
								class="tx" style="width:120px;margin-right:30px;cursor:pointer;" type="button"
								id="showAllHsitories" value="显示所有">
							</td>
							<td colspan="1" align="right" style="padding-top:10px;"><input
								class="tx" style="width:120px;margin-right:30px;cursor:pointer; " type="button"
								onclick="document.forms[0].submit();" value="条件查询">
							</td>
						</tr>
					</table>
				</s:form>
			</td>
			<td valign="top" width="20%">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td background="<c:url value='/picture/loginpage.gif'/>"
							align="center"><br> <font color="red">操作步骤</font>
						</td>
					</tr>
					<tr>
						<td background="<c:url value='/picture/bg1.jpg'/>"
							style="padding-left:10px;">1.显示某种货物的出入库记录 <br />
							2.根据条件查询某种货的库存情况</td>
					</tr>
					<tr>
						<td><img src="<c:url value='/picture/bottom.jpg'/>"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr valign="top">
			<td rowspan="2">
				<form action="" name="select">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="tx" align="center">
						<colgroup>
							<col width="20%" align="right">
							<col width="*%" align="left">
						</colgroup>
						<tr>
							<td bgcolor="a0c0c0" style="padding-left:10px;" colspan="1"
								align="left"><b>货物库存：</b>
							</td>
						</tr>
						<tr>
							<td>
								<table class="store">
									<tr style="background:#D2E9FF;text-align: center;">
										<td>简记码</td>
										<td>名称</td>
										<td>时间</td>
										<td>类型</td>
										<td>单位</td>
										<td>数量</td>
										<td>库存余量</td>
										<td>仓库</td>
										<td>操作员</td>
									</tr>
									<!-- 显示列表 -->

								<!-- 添加多重判断,没有商品信息的时候,显示一行文字即可 -->
									<s:if test="#page.records!=null">
									<s:iterator value="#page.records"  var="history" status="st">
									<tr class="<s:property value="#st.even?'even':'odd'"/>">
										<td>${history.goods.nm}</td>
										<td>${history.goods.name}</td>
										<td>${history.datetime}</td>
										<td>${history.type}</td>
										<td>${goods.unit}</td>
										<td>${history.amount}</td>
										<td>
											<s:property value="%{#history.remain}"/>
										</td>
										<td>${goods.store.name}</td>
										<td>
											<s:property value="{#history.user}"/>
										</td>
									</tr>
								</s:iterator>
									</s:if>
									<!-- 
										没有商品的时候,显示一行文字
									 -->
									<s:if test="#page.records.isEmpty">
									<tr>
										<td colspan="9">
											<div align="center" style="size:20px; color: red;margin-right: auto;text-shadow: gray;">您当前查询的商品信息不存在!请重新查询!!!</div>									
										</td>
									</tr>
									</s:if>

								</table>
							</td>
						</tr>
					</table>

					<!-- 分页显示位置 -->
					<div align="right">
						<span>
						总记录数：<s:property value="{#page.totalCount}" />条</span>
						<span>每页记录数:${page.numberPerPage}条</span>
						总页数：<span id="totalPageCount">${page.totalPageCount}</span>页 
						<span>当前第：<s:property value="%{#page.pageNum}" />页</span>
						
						<!-- 如果当前页面就是第一页，不要给链接 -->
						<s:if test="#page.pageNum==1">
							<a>首页</a>
							<a>上一页</a>
						</s:if>
						<s:if test="#page.pageNum!=1">
							<a href="${pageContext.request.contextPath}/historyAction_listAllHsitoriesByPage.action?pageNum=1&${page.paramUrl}" >首页</a>
							<a href="${pageContext.request.contextPath}/historyAction_listAllHsitoriesByPage.action?pageNum=${page.pageNum-1}&${page.paramUrl}" >上一页</a>
						</s:if>
						
						<!-- pageNum是从0开始的,而indexNum是从1开始的;st.getIndex()是从0开始的 -->
						<s:if test="#page.totalPageCount<=5">
							<s:iterator begin="1" end="#page.totalPageCount" status="st" var="indexNum">
								<s:if test="#page.pageNum==#indexNum">
								<!--
									//或者这样也可以:test="#page.pageNum==#st.getIndex()+1"
								 -->
									<s:a href="#">[${indexNum}]</s:a>
								</s:if>
								<s:else>
									<a href="${pageContext.request.contextPath}/historyAction_listAllHsitoriesByPage.action?pageNum=${indexNum}&${page.paramUrl}" >[${indexNum}]</a>
								</s:else>
							</s:iterator>
						</s:if>
						<s:else>
					
							<s:if test="#page.totalPageCount>5">
								<!-- 遍历页码的位置,至少显示第一页,显示5个页码,前面2个,后面2个 -->
									<s:iterator begin="%{#page.pageNum-2>0?#page.pageNum-2:1}" end="%{#page.pageNum+2<=#page.totalPageCount?#page.pageNum+2:#page.totalPageCount}" status="st" var="indexNum">
											<s:if test="#page.pageNum-1==#st.getIndex()">
												<s:a href="#">[${indexNum}]</s:a>
											</s:if>
											<s:else>
												<a href="${pageContext.request.contextPath}/historyAction_listAllHsitoriesByPage.action?pageNum=${indexNum}&${page.paramUrl}">[${indexNum}]</a>
											</s:else>
									</s:iterator>
							</s:if>
						
						</s:else>
						<!-- 如果当前页面就是最后一页，不要给链接 -->
						<s:if test="#page.pageNum==#page.totalPageCount">
							<a>下一页</a>
							<a>尾页</a>
						</s:if>
						<s:else>
							<a href="${pageContext.request.contextPath}/historyAction_listAllHsitoriesByPage.action?pageNum=${page.pageNum+1}&${page.paramUrl}" >下一页</a>
							<a href="${pageContext.request.contextPath}/historyAction_listAllHsitoriesByPage.action?pageNum=${page.totalPageCount}&${page.paramUrl}" >尾页</a>
						</s:else>
						<s:textfield size="2" name="selectedPageNum2" />
						<input type="hidden" name="selectedPageNum1" value="${page.paramUrl}">
						<s:textfield type="button"  id="selectedPageNum1" value="GO"/>

					</div>
				</form>
			</td>
		</tr>
	</table>
</body>
</html>

