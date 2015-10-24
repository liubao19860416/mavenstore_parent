<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>用户登陆</TITLE>
<link rel="stylesheet" type="text/css" href='<c:url value="/css/maple.css"/>' />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript">

//添加防止页面iframe嵌套的js代码实现

if(window.self!=window.top){
window.top.location.href="${pageContext.request.contextPath}/login.jsp";
}
 
 //初始化光标聚焦到输入框指定位置
function ini(){
   document.myform.myname.focus();
}
 
$(function(){
	
	 //初始化光标聚焦到输入框指定位置
	 $("#myform input[name='name']").focus();
	 //$("#myname").focus();
	 
 
 
 
 
 
 
 
 
});

 
</script>

</HEAD>
<!-- 
<BODY  bgColor=#ffffff leftMargin=0 background='<c:url value="/picture/bj1.gif"/>' topMargin='0' onload="ini()">
 -->
<BODY  bgColor=#ffffff leftMargin=0 background='<c:url value="/picture/bj1.gif"/>' topMargin='0'>
		
		<s:form action="loginAction_login" id="myform" method="post" cssStyle="margin-top:250px;">
		
			<DIV align=center>
				<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%"
					border=0>
					<TBODY>
						<TR>
							<TD>
								<TABLE cellSpacing=0 cellPadding=0 width=623 align=center
									border=0>
									<TBODY>
										<TR>
											<TD align="center" colSpan=3 width="623" height="260"
												background='<c:url value="/picture/welcome_01.gif"/>'>
												<!-- 验证码返回提示 --> <br> <br> <br> <br> <br>
												<font color="#ff60a0" size="5">
													
												</font>
											</TD>
										</TR>
										<TR>
											<TD rowSpan=4>
												<IMG height=92 src='<c:url value="/picture/welcome_02.gif"/>' width=46></TD>
											<TD align="right" background="" height=13>
												<DIV align=left>
												<!-- 错误显示区域 ,使用的是s:fielderror,输出页面检验的错误回显结果,s:actionerror输出数据库查询后的错误回显结果;组合使用;-->
												<!-- 
												 -->
												<s:actionerror cssStyle="color:red;"/>
												<s:fielderror cssStyle="color:red;" />
													<FONT color=#006633 size=2>用户名：</FONT><FONT color=#006633
														size=2> 
														
													 <s:textfield name="name" id="myname" cssClass="tx" maxlength="15" size="15" value=""></s:textfield>
														</FONT><FONT color=#006633 size=2>
													</FONT>
												</DIV></TD>
											<TD rowSpan=4>
												<IMG height=92 src='<c:url value="/picture/welcome_04.gif"/>' width=402></TD>
										</TR>
										<TR>
											<TD Align=left background="" height=9>
												<DIV align=left>
													<FONT color=#006633 size=2>密码：
													<s:password name="password" cssClass="tx" maxlength="15" size="15" ></s:password>
														</FONT>
												</DIV>
											</TD>
										</TR>

										<TR>
											<TD align="left" height=21>
												<DIV align=center>
													<FONT color=#006633 size=2> <input class=txt1
														style="BACKGROUND-COLOR: #ffffff" type=submit value=确定
														name=ChkCngPwd2> <INPUT class=txt1
														style="BACKGROUND-COLOR: #ffffff" type=reset value=取消
														name=ChkCngPwd> </FONT>
												</DIV>
											</TD>
										</TR>
										<TR>
											<TD align="center" height=2 width="175" background='<c:url value="/picture/welcome_05.gif"/>'></TD>
										</TR>
									</TBODY>
								</TABLE>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
				<FONT color=red></FONT>
			</DIV>
		</s:form>
</BODY>
</HTML>

