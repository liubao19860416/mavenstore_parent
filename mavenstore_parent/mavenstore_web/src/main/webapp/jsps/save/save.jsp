<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>收货登记</title>

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
</style>

<script type="text/javascript">
$(function(){
	
	//查询仓库显示下拉链表选择框AJAX实现
		$.get("${pageContext.request.contextPath}/storeAction_ajaxList.action",function(data){
			$(data).each(function(){
				var $option=$("<option value='"+this.id+"'>"+this.name+"</option>");
				$("#selectId").append($option);
			});
	});
	
	
	//查询单位显示AJAX实现自动提醒(边输入,边提示信息补全(实时查询补全))
		$("input[name='unit']").autocomplete({
			source:function(request,response){
				//alert(request.term);
				$.post("${pageContext.request.contextPath}/goodsAction_findUnitLike.action",{"unit":request.term},function(data){
					response(data);//这样就返回显示了
				});
			}
		});
	
	
	//添加简记码离焦事件
	$("input[name='nm']").blur(function(){
		$.post("${pageContext.request.contextPath}/goodsAction_findGoodsByNm.action",{"nm":$(this).val()},function(data){
			if(data!=null){
				//查询到商品,可以回显
				$("input[name='name']").val(data.name);
				$("input[name='unit']").val(data.unit);
				//这是隐藏域中的商品id信息
				$("#goodsId").val(data.id);
				//回显其对应的仓库信息
				$("#selectId").val(data.store.id);// 通过val函数使select选中
				
			}else{
				//没有该商品,清空显示数据即可;
				//$("input[name='name']").val("");
				$("input[name='unit']").val("");
				$("#goodsId").val("");
				$("#selectId").val("0");// 通过val函数t选中默认值
			}
			
		});
	});
	
	
	//添加取消操作,返回显示页面按钮
		$("#returnbutton").click(function(event){
			var isConfirm =window.confirm("您确认要取消操作吗?");
			if(!isConfirm){
				event.preventDefault();
			}else{
				window.location.href="${pageContext.request.contextPath}/goodsAction_listAllGoods.action";
			}
	});
	
	
	//自定义的全局变量,用于给下面的自动补全回显提供使用
		var tempdata;
	// 货物名称输入自动联想提示(data就是一个数组就行了,这里使用点击click事件就行了,以newi访问一次就可以了)
	$("input[name='name']").focus(function(){
	$.get("${pageContext.request.contextPath}/goodsAction_listAllGoods_ajax.action",function(data){
		//这个数据是给下面使用的;	
		tempdata=data;
		var arr=new Array();
		var i=0;
		$(data).each(function(){
			arr[i++]=this.name;
		});
			//alert(arr);
		$("input[name='name']").autocomplete({
			//source: ["电冰箱","洗衣机","电热水器","电微波炉","电话"]
			source:arr
		});
	});
	});
	
	
	//添加商品信息自动补全,这时候不用再发送请求了;
	$("input[name='name']").blur(function(){
		//这里需要查询取出该对象的其他值,保存到这个数组中(这是一个多维数组);
		var data=tempdata;
		var storeid;
		//获取当前选择的名称;
		var namevalue=$("input[name='name']").val();
		//alert(namevalue);
		//遍历数组,获取需要的对象
		$(tempdata).each(function(){
			if(this.name==namevalue){
				data=this;
				storeid=this.store.id;
				//使用这个语句可以结束each循环;
				return false;
				alert("没有返回");
			}
		});
		
		//查询到商品,可以回显(但需要判断是否选择了对应的数据)
		if(data!=null){
			if($("input[name='name']").val()!=null&&(!($("input[name='name']").val().isEmpty))){
				//alert(storeid);
				if(data.nm!=null){
					$("input[name='nm']").val(data.nm);
				}
				$("input[name='unit']").val(data.unit);
				//这是隐藏域中的商品id信息
				$("#goodsId").val(data.id);
				//回显其对应的仓库信息
				$("#selectId").val(storeid);// 通过val函数使select选中
			}
		}else{
			//没有该商品,清空显示数据即可;
			//$("input[name='nm']").val("");//添加新商品,简记码不需要清空
			$("input[name='unit']").val("");
			$("#goodsId").val("");
			$("#selectId").val("0");// 通过val函数t选中默认值
		}
	});
	
	
	//添加光标聚焦
	$("form input[name='nm']").focus();
		
	
	//添加js校验
	$("#submitbutton").click(function(event){
		
		var amount=new Number();
		amount=$("input[name='amount']").val();

		//这里可以将其进行一个数字格式的转换,防止double等类型的数据比较出错;
		 amount=new Number(amount);
		 
		//校验数量格式是否正确
		var regex=/^[0-9]+$/;
		if(!regex.test(amount)){
			alert("请输入非空的数字格式的数量数据!");
			return false;
			//event.preventDefault();//在这里使用这个是不行的,只能使用greturn false;
		}
		
		//判断页码范围
		if(amount>1001.0||amount<=0.0){
			alert("请输入正确的数量,范围是[1-1001]");
			return false;
			//event.preventDefault();
		}
		document.forms[0].submit();
	});
	
	
});
</script>
</head>

<body>
	<!-- 中间内容（开始） -->
	<table border="0" class="tx" width="100%">
		<tr>
			<td>当前位置&gt;&gt;首页&gt;&gt;入库</td>
		</tr>
	</table>
	<br>
	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr valign="top">
			<td rowspan="2">
			
			<s:form action="goodsAction_saveGoods.action" method="post" name="select">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tx" align="center">
						<colgroup>
							<col width="20%" align="right">
							<col width="*%" align="left">
						</colgroup>
						<tr>
							<td bgcolor="a0c0c0" style="padding-left:10px;" colspan="2" align="left">
								<b>货物入库登记：</b>
							</td>
						</tr>
						<tr>
							<td>
								<!-- 隐藏域 -->
								<s:hidden id="goodsId" name="id"></s:hidden>
							</td>
						</tr>
						<!-- 校验错误信息回显位置 -->
						<tr>
							<td colspan="3" style="color:red;" align="left">
								<s:fielderror/>
								<s:actionerror/>
							</td>
						</tr>
						<tr>
							<td>
								简记码：
							</td>
							<td>
							
							<s:textfield cssClass="tx" name="nm" ></s:textfield>
							</td>
						</tr>
						<tr>
							<td>
								货物名称：
							</td>
							<td>
							<s:textfield cssClass="tx" id="goodsname" name="name"></s:textfield>
							</td>
						</tr>
						<tr>
							<td>
								计量单位：
							</td>
							<td>
							<s:textfield cssClass="tx" name="unit"></s:textfield>
							</td>
						</tr>
						<tr>
							<td>
								入库数量：
							</td>
							<td>
							<s:textfield cssClass="tx" name="amount"></s:textfield>
							</td>
						</tr>
						<tr>
							<td>
								选择仓库：
							</td>
							
							<td>
							<!-- 这里的store.id非常的重要的!!!直接获取选择的仓库id -->
								<select class="tx" style="width:120px;" id="selectId" name="store.id">
									 <option value="">请选择仓库</option>
									<!-- 
									 -->
								</select>
								(此信息从数据库中加载)
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center" style="padding-top:10px;">
								<input class="tx" style="width:120px;margin-right:30px;" type="button"  id="submitbutton" value="入库">
								<input class="tx" style="width:120px;margin-right:30px;" type="reset" value="清除" />
								<input class="tx" style="width:120px;margin-right:30px;" id="returnbutton" type="button" value="取消" />
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
							1.输入简记码从数据库<br/>查询是否已经存在此
							<br/>货物
							<br/>
							2.没有则直接输入货物名称
							<br>
							3.从数据库选择仓库
							<br>
							4.向仓库中新添加或是追加货物
							<br/>
							5.记录入库历史记录
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

