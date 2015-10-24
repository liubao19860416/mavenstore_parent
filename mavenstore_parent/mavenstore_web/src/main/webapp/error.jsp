<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>

<title>错误页面.jsp</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<title>欢迎您光临！十分抱歉，服务器出错了 ！</title>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<meta http-equiv="refresh"
	content="100;URL=http://localhost:8080/estore">
<STYLE type=text/css>
INPUT {
	FONT-SIZE: 12px
}

TD {
	FONT-SIZE: 12px
}

.p2 {
	FONT-SIZE: 12px
}

.p6 {
	FONT-SIZE: 12px;
	COLOR: #1b6ad8
}

A {
	COLOR: #1b6ad8;
	TEXT-DECORATION: none
}

A:hover {
	COLOR: red
}
</STYLE>

<META content="Microsoft FrontPage 5.0" name=GENERATOR>
</HEAD>
<BODY oncontextmenu="return false" onselectstart="return false">
	<P align=center></P>
	<P align=center></P>

	Action中的错误信息显示位置:
	<s:fielderror/><br />
	<s:actionerror/><br />
	<s:actionmessage/><br />
	页面异常的具体信息:<s:property value="exception.message"/>

	<TABLE cellSpacing=0 cellPadding=0 width=540 align=center border=0>
		<TBODY>
			<TR>
				<TD vAlign=top height=270>
					<DIV align=center>
						<BR>
						<IMG height=211 src="images/error.gif" width=329><BR>
						<BR>
						<TABLE cellSpacing=0 cellPadding=0 width="80%" border=0>
							<TBODY>
								<TR>
									<TD><FONT class=p2>&nbsp;&nbsp;&nbsp; <FONT
											color=#ff0000><IMG height=13 src="images/emessage.gif"
												width=12>&nbsp;无法访问本页的原因是：</FONT>
									</FONT>
									</TD>
								</TR>
								<TR>
									<TD height=8></TD>
								<TR>
									<TD>
										<P>
											<FONT color=#000000><BR> 服务器出现异常 ，异常信息...</FONT>!
										</P>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</DIV>
				</TD>
			</TR>
			<TR>
				<TD height=5></TD>
			<TR>
				<TD align=middle>
					<CENTER>
						<TABLE cellSpacing=0 cellPadding=0 width=480 border=0>
							<TBODY>
								<TR>
									<TD width=6><IMG height=26 src="images/left.gif" width=7>
									</TD>
									<TD background=images/bg.gif>
										<DIV align=center>
											<FONT class=p6>
											<s:a href="jsps/main.jsp" >关闭本页</s:a>
											<s:a href="javascript:history.go(-1);" >返回出错页</s:a>
											<s:a href="login.jsp" >返回首页</s:a>
											<!-- 
											<A href="${pageContext.request.contextPath}/jsps/main.jsp">关闭本页</A>
											<A href="javascript:history.go(-1);">返回出错页</A> | 
											<A href="${pageContext.request.contextPath}/login.jsp">返回首页</A>
											 -->
											</FONT>
										</DIV>
									</TD>
									<TD width=7><IMG src="images/right.gif">
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</CENTER>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
	<P align=center></P>
	<P align=center></P>
</BODY>
</HTML>
