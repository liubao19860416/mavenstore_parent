<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://www.liubao.com/" prefix="L"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<link href="<c:url value='/css/maple.css'/>" type="text/css" rel=stylesheet></link>
</head>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript">

//添加防止页面iframe嵌套的js代码实现

if(window.self!=window.top){
window.top.location.href="${pageContext.request.contextPath}/jsps/main.jsp";
}


</script>

<script type="text/javascript">
//在body启动后加载即可
	function startTime() {
		var today = new Date();
		//alert(today.valueOf());//返回原始的毫秒值

		var day = today.getDate();
		var month = today.getMonth() + 1;
		//var year = today.getYear()+1900;
		var year = today.getFullYear();

		var h = today.getHours();
		var m = today.getMinutes();
		var s = today.getSeconds();

		h = checkTime(h);
		m = checkTime(m);
		s = checkTime(s);

		var myArray = new Array(6);
		myArray[0] = "星期日";
		myArray[1] = "星期一";
		myArray[2] = "星期二";
		myArray[3] = "星期三";
		myArray[4] = "星期四";
		myArray[5] = "星期五";
		myArray[6] = "星期六";
		//判断星期几
		weekday = today.getDay();

		if (weekday == 0 | weekday == 6) {
			var week = myArray[weekday];
		} else {
			var week = myArray[weekday];
		}
		//var time000=document.getElementById('timenow').innerHTML;
		document.getElementById('timenow').innerHTML= "<br/><br/>"+year + "年 " + month + "月 "
				+ day + "日 [" + h + ":" + m + ":" + s + "] " + week;
		//两个方法都是可以的;下面的那个容易死机;
		t = setTimeout('startTime()', 1000);
		//setInterval('startTime()', 1000);
	}

	function checkTime(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	}
</script>

<script type="text/javascript">

$(function(){
	//显示系统时间
	startTime();
	//$("#timenow").html("<br/><br/>当前时间:"+ new Date());
	//showtime1();
	
});

function showtime1(){
	$("#timenow").html("<br/><br/>当前时间:"+ new Date());
	t = setTimeout('showtime1()', 1000);
}

</script>



<BODY topMargin=0 rightMargin=0 marginwidth="0" marginheight="0">
	<TABLE height=91 cellSpacing=0 cellPadding=0 width=984
		background=<c:url value='/picture/topbk.jpg'/> border=0>
		<TBODY>
			<TR>
				<TD width=433 height="50" background=<c:url value='/picture/header.jpg'/>></TD>
				<!-- 
				 显示系统当前时间位置:
				<td width="150" id="timenow" style="color:olive;">
					<s:date name="2014/04/05" format="dd/MM/yyyy" id="timenow" nice="true"/>
					<span id="timenow" style="width:150;"></span>
				</td>
					 -->
				<TD  width="150">
					<FONT style="FONT-SIZE: x-small;color:#804060;" color="#fffaed">
					<span id="timenow" style="width:150;"> </span>
							<!-- 
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 <BR>当前用户：${userinfo.name}
							 -->
							 <!-- 这是用标签实现的用户面显示 -->
							<L:userLoginCheck/>
				
					</FONT>
				</TD>
			</TR>
			
		</TBODY>
	</TABLE>
	<TABLE cellSpacing=0 cellPadding=0 width=984 height="120%" border=0 background=<c:url value='/picture/bk3.jpg'/>>
		<TBODY>
			<TR>
				<TD vAlign=top width="12%">
					<TABLE cellSpacing=0 cellPadding=0 width=104 border=0>
						<TBODY>
							<TR>
								<TD valign=middle align="center"><BR>
								<FONT color=#804060>--系统菜单--</FONT>
								</TD>
							</TR>
							<TR>
								<TD id=left1001 style="CURSOR:hand" valign="middle"
									align="center" background=<c:url value="/picture/img01.jpg"/>
									height=28><FONT color=#804020>收货管理</FONT></TD>
							</TR>
							<TR>
								<TD valign=middle align="center">
									<DIV id=shouhuo>
									<s:a id="left1002" target="content" action="storeAction_listAll.action">[仓库管理]</s:a>
										<BR/> 
									<%
										//<A target="content" id="left1002" href="<c:url value='/jsps/store/remain.jsp'/>">[库存管理]</A>
									%>
									<!-- 
									<s:a id="left1002" target="content" action="goodsAction_listAllGoods.action">[库存管理]</s:a>
									 -->
									<s:a id="left1002" target="content" action="goodsAction_listSearchGoodsByPage.action">[库存管理]</s:a>
									
										<BR/> 
										<A id="left1003" target="content" href="<c:url value='/jsps/save/save.jsp'/>">[入库]</A>
										<BR/>
										<a target="content" href="<c:url value='/jsps/out/out.jsp'/>">[出库]</a>
										<br/>
										<!-- 
										<s:a action="historyAction_listAllHsitories.action" target="content">[历史记录]</s:a>
										 -->
										<s:a action="historyAction_listAllHsitoriesByPage.action" target="content">[历史记录]</s:a>
										<br/>
									</DIV>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
				</TD>
				<TD vAlign=top align=left width="88%">
					<iframe name="content" src="<c:url value='/picture/tyyw.bmp'/>" frameborder="0" width="100%" height="100%"></iframe>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</BODY>
</HTML>

