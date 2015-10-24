<%@page import="cn.itcast.storemanage.domain.Userinfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.liubao.com/" prefix="L"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1 align="center">自定义freemarker实现标签的测试页面</h1>
<!-- 
<L:pageTagFreemarker pageQueryUrl="${pageContext.request.contextPath}/test.action" paramUrl="1=1&testname=liubao" totalCount="100" numberPerPage="10" pageNum="5"></L:pageTagFreemarker>
 -->


<L:userLoginCheck/>


</body>
</html>