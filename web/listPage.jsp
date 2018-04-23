<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lazar81ba
  Date: 22.04.18
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:useBean id="listBroker" class="list.ListBrokerBean" scope="application"/>
<c:if test="${not empty param.listNameToAdd}">
    ${listBroker.addNewThematicList(param.listNameToAdd)}
    <c:set var="listToDisplay" value="${param.listNameToAdd}"/>
</c:if>
<c:if test="${not empty param.currentList && not empty param.recordToAdd}">
    ${listBroker.addNewRecordToList(param.currentList,param.recordToAdd)}
</c:if>
<c:if test="${not empty param.currentList}">
    <c:set var="listToDisplay" value="${param.currentList}"/>
</c:if>

Lista "${listToDisplay}"
<ul class="list">
<c:forEach items="${listBroker.getList(listToDisplay)}" var="item">
    <li>${item}</li>
</c:forEach>
</ul>
Add new record:
<form action="listPage.jsp" method="post">
    <input type="hidden" value="${listToDisplay}" name="currentList">
    <input type="text"  name="recordToAdd">
    <input type="submit" value="Submit">
</form>

<br/><br/>
<form action="thematicList.jsp" method="post">
    <input type="submit" value="Back">
</form>
</body>
</html>
