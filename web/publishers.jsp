<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lazar81ba
  Date: 22.04.18
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:useBean id="listBroker" class="list.ListBrokerBean" scope="application"/>

<ul class="list">
<c:forEach items="${listBroker.subscriberList}" var="item">
    <form action="thematicList.jsp" method="post">
        <input type="hidden" value="${param.currentList}" name="currentList">
        <input type="submit" value="${item}" name="publisher">
    </form>
</c:forEach>
</ul>
</body>
</html>
