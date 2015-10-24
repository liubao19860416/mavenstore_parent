<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>货物统计</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/maple.css'/>"></link>
	
<!-- 引入外部css_样式库 -->
<link rel="stylesheet" 
	href="${pageContext.request.contextPath }/css/smoothness/jquery-ui-1.9.2.custom.css">
	
<!-- 引入外部j_s库 -->
<script type="text/javascript" 
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" 
	src="${pageContext.request.contextPath }/js/jquery-ui-1.9.2.custom.js"></script>	
	
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
<script type="text/javascript">
$(function(){
	
	//查询显示下拉链表选择框AJAX实现
	$.get("${pageContext.request.contextPath}/storeAction_ajaxList.action",function(data){
		$(data).each(function(){
			var $option=$("<option value='"+this.id+"'>"+this.name+"</option>");
			$("#selectId").append($option);
		});
	});	
		
	
	/**
		这里只需要进行自动联想就可以了;
	*/
	/**	
	// 货物名称输入自动联想提示,需要使用一个数组;这是方式二实现
	$("input[name='name']").focus(function(){
	$.get("${pageContext.request.contextPath}/goodsAction_listAllGoodsNames_ajax2.action",function(data){
		//这个数据是给下面使用的;	
		$("input[name='name']").autocomplete({
			//source: ["电冰箱","洗衣机","电热水器","电微波炉","电话"]
			alert(data);
			source:data
		});
	});
	});
	*/
	
	
		
		// 货物名称输入自动联想提示,需要使用一个数组;这是方式三实现(实时查询对象信息)
		// 方案二： 根据每次输入补全
				
		$("input[name='name']").autocomplete({
			//minLength: 0,
			source: function(request,response) {
				// 每次修改输入框，当前函数调用， 通过 request.term获取当前输入内容
				$.post("${pageContext.request.contextPath}/goodsAction_listAllGoodsNames_ajax3.action", {"name":request.term}, function(data){
					// data 和当前输入内容相关 列表数组
					response(data);// 显示
				});
			},
			//选中之后执行的函数方法;
			select : function(event,ui){
				// ui.item 可以是source绑定数组 某个元素 ，默认获取显示内容 ui.item.value
				$("input[name='nm']").val(ui.item.nm);
				$("#selectId").val(ui.item.store.id); // 通过val函数使select选中
			}
		});


		
	// 简记码输入自动联想提示,需要使用一个数组;可以使用focus试试;
	$("input[name='nm']").focus(function(){
	$.get("${pageContext.request.contextPath}/goodsAction_listAllGoods_ajax.action",function(data){
		//这个数据是给下面使用的;	
		var arr=new Array();
		var i=0;
		$(data).each(function(){
			arr[i++]=this.nm;
		});
		$("input[name='nm']").autocomplete({
			//source: ["电冰箱","洗衣机","电热水器","电微波炉","电话"]
			source:arr
		});
	});
	});
		
	
	
	//客户输入的选择的指定页码
	$("#selectedPageNum").click(function(event){
		//alert($("input[name='selectedPageNum']").val());
		//试一下
		var pageNum=new Number();
		pageNum=$("input[name='selectedPageNum']").val();
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
		window.location.href="${pageContext.request.contextPath}/goodsAction_listSearchGoodsByPage.action?pageNum="+pageNum;
	});
	
	
});

</script>

</head>
<body>
	<table border="0" class="tx" width="100%">
		<tr>
			<td>当前位置&gt;&gt;首页&gt;&gt;货物库存</td>
		</tr>
	</table>
	<br>
	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td rowspan="1">
			
			<!-- 
				<form action="<c:url value='/jsps/store/remain.jsp'/>" method="post" name="select">
			 -->
			 
			 <!-- 查询表单提交位置 -->
			 
			<s:form action="goodsAction_listSearchGoodsByPage.action" name="select" method="post">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tx" align="center">
						<colgroup>
							<col width="20%" align="right">
							<col width="*%" align="left">
						</colgroup>
						<tr>
							<td bgcolor="a0c0c0" style="padding-left:10px;" colspan="2" align="left">
								<b>查询条件：</b>
							</td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td>
								简记码：
							</td>
							<td>
								<s:textfield cssClass="tx" name="nm" />
							</td>
						</tr>
						<tr>
							<td>
								货物名称：
							</td>
							<td>
								<s:textfield cssClass="tx" name="name" />
							</td>
						</tr>
						<tr>
							<td>
								选择仓库：
							</td>
							
							<td>
								<select class="tx" style="width:120px;" id="selectId" name="store.id">
									<option value="">请选择仓库</option>
								</select>
							</td>
							
						</tr>
						<tr>
							<td colspan="2" align="right" style="padding-top:10px;">
								<input class="tx" style="width:120px;margin-right:30px;" type="button" onclick="document.forms[0].submit();" value="查询">
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
		<tr valign="top">
			<td rowspan="2">
			
			<!-- 显示商品信息的表单,但是这个表单只是显示数据使用,不用提交数据 -->
			
				<s:form action="#" method="post" name="select">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tx" align="center">
						<colgroup>
							<col width="20%" align="right">
							<col width="*%" align="left">
						</colgroup>
						<tr>
							<td bgcolor="a0c0c0" style="padding-left:10px;" colspan="1" align="left">
								<b>货物库存：</b>
							</td>
						</tr>
						<tr>
							<td>
								<table class="store">
									<tr style="background:#D2E9FF;text-align: center;">
										<td>简记码</td>
										<td>名称</td>
										<td>计量单位</td>
										<td>库存数量</td>
										<td>所在仓库</td>
										<td>操作</td>		
									</tr>
									
									<!-- 显示数据的位置,需要遍历对象,使用struts和spring的都使用一下 -->
									<!-- 需要注意传递当前的操作对象的id,考虑有几种方式可以实现 -->
									<s:if test="#goodses!=null">
									<s:iterator status="st" var="goods" value="#goodses">
									<tr  class="<s:property value="#st.even?'even':'odd'"/>">	
										<td>${goods.nm}</td>
										<td>${goods.name}</td>
										<td>${goods.unit}</td>
										<td>${goods.amount}</td>
										<!-- 两种方式都可以
										<s:property value="%{store.name}"/>
										 -->
										<td>${store.name}</td>	
										<td><!-- 
											<s:a href="jsps/save/save.jsp" method="post"/>入库
										 -->
											<s:a action="goodsAction_showSave.action">入库
														<s:param name="nm" value="%{nm}"></s:param>
														<s:param name="name" value="%{name}"></s:param>
														<s:param name="unit" value="unit"></s:param>
														<s:param name="store.id" value="{store.id}"></s:param>
													</s:a>
													<s:a action="goodsAction_showOut.action" encode="utf-8">出库
														<s:param name="nm" value="%{nm}"></s:param>
														<s:param name="name" value="%{name}"></s:param>
														<s:param name="unit" value="unit"></s:param>
														<s:param name="store.id" value="{store.id}"></s:param>
													</s:a>
													<s:a href="jsps/his/his.jsp" method="post">历史记录
														<s:param name="nm" value="%{nm}"></s:param>
														<s:param name="name" value="%{name}"></s:param>
														<s:param name="unit" value="unit"></s:param>
														<s:param name="store.id" value="{store.id}"></s:param>
													</s:a>
										</td>	
										</s:iterator>	
										</s:if>
										<s:else>
										
										<!-- 添加多重判断,没有商品信息的时候,显示一行文字即可 -->
										<s:if test="#page.records!=null">
										<s:iterator status="st" var="goods" value="#page.records">
											<tr  class="<s:property value="#st.even?'even':'odd'"/>">	
												<td>${goods.nm}</td>
												<td>${goods.name}</td>
												<td>${goods.unit}</td>
												<td>${goods.amount}</td>
												<td>${goods.store.name}</td>	
												<td>
													<s:a action="goodsAction_showSave.action" method="post" encode="utf-8">入库
														<s:param name="nm" value="%{nm}"></s:param>
														<s:param name="name" value="%{name}"></s:param>
														<s:param name="unit" value="unit"></s:param>
														<s:param name="store.id" value="{store.id}"></s:param>
													</s:a>
													<s:a action="goodsAction_showOut.action" encode="utf-8">出库
														<s:param name="nm" value="%{nm}"></s:param>
														<s:param name="name" value="%{name}"></s:param>
														<s:param name="unit" value="unit"></s:param>
														<s:param name="store.id" value="{store.id}"></s:param>
													</s:a>
													<s:a href="jsps/his/his.jsp" method="post">历史记录
														<s:param name="nm" value="%{nm}"></s:param>
														<s:param name="name" value="%{name}"></s:param>
														<s:param name="unit" value="unit"></s:param>
														<s:param name="store.id" value="{store.id}"></s:param>
													</s:a>
												</td>		
											</tr>
									</s:iterator>
									</s:if>
									<!-- 
										没有商品的时候,显示一行文字
									 -->
									<s:if test="#page.records.isEmpty">
									<tr>
										<td colspan="6">
											<div align="center" style="size:20px; color: red;margin-right: auto;text-shadow: gray;">您当前查看到商品信息不存在!请重新查询!!!</div>									
										</td>
									</tr>
									</s:if>
									</s:else>
									
								</table>
							</td>
						</tr>
						<!-- 分页显示的按钮位置 -->
					</table>
					<div align="right">
						<span>总记录数：<s:property value="{#page.totalCount}"/>条</span>
						<span>每页记录数:${page.numberPerPage}条</span>
						<!-- 
						<span>总页数：${page.totalPageCount}页</span>
						 -->
						总页数：<span id="totalPageCount" >${page.totalPageCount}</span>页
						
						<span >当前第：<s:property value="%{#page.pageNum}"/>页</span>
						<!-- 如果当前页面就是第一页，不要给链接 -->
						<s:if test="#page.pageNum==1">
							<a>首页</a>
							<a>上一页</a>
						</s:if>
						<s:else>
							<s:a action="goodsAction_listSearchGoodsByPage.action" >首页
								<s:param name="pageNum" value="%{1}"/> 
							</s:a>
							<s:a action="goodsAction_listSearchGoodsByPage.action">上一页
								<s:param name="pageNum" value="#page.pageNum-1"/> 
							</s:a>
						</s:else>
						
						
						<s:if test="#goodses==null">
						<!-- keke可以通过ajax计算begin和end value="#page.records" var="goods"  -->
						<s:iterator begin="%{#page.totalPageCount-5>0?#page.totalPageCount-5:1}" end="%{#page.totalPageCount}" status="st" var="indexNum" step="1">
							<s:a action="goodsAction_listSearchGoodsByPage.action">[${indexNum}]
							<s:param name="pageNum" value="%{indexNum}"/> 
							</s:a>
						</s:iterator>
						</s:if>
						
						<!-- 如果当前页面就是最后一页，不要给链接 -->
						<s:if test="#page.pageNum==#page.totalPageCount">
							<a>下一页</a>
							<a>尾页</a>
						</s:if>
						<s:else>
							<s:a action="goodsAction_listSearchGoodsByPage.action" >下一页
								<s:param name="pageNum" value="#page.pageNum+1"/> 
							</s:a>
							<s:a action="goodsAction_listSearchGoodsByPage.action">尾页
								<s:param name="pageNum" value="#page.totalPageCount"/> 
							</s:a>
						</s:else>
						<s:textfield size="2" name="selectedPageNum"/>
						<s:textfield type="button"  id="selectedPageNum" value="GO" />
						
					</div>
				</s:form>
			</td>
		</tr>
	</table>
</body>
</html>

