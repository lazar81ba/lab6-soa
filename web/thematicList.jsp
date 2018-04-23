<%--
  Created by IntelliJ IDEA.
  User: lazar81ba
  Date: 22.04.18
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Thematic lists:
<jsp:useBean id="listBroker" class="list.ListBrokerBean" scope="application"/>
<c:if test="${not empty param.publishToAll && not empty param.currentList}">
    ${listBroker.sendToAllSubscribers("List updated :".concat(param.currentList))}
</c:if>

<c:if test="${not empty param.publisher && not empty param.currentList}">
    ${listBroker.sendToOneSubscriber(param.publisher, "List updated :".concat(param.currentList))}
</c:if>
<ul class="list">
    <c:forEach items="${listBroker.thematicLists}" var="item">
        <li>
                ${item.key}
                    <form action="listPage.jsp" method="post">
                        <input type="hidden" value="${item.key}" name="currentList">
                        <input type="submit" value="Edit list">
                    </form>
                    <form action="thematicList.jsp" method="post">
                        <input type="hidden" value="${item.key}" name="currentList">
                        <input type="hidden" value="${true}" name="publishToAll">
                        <input type="submit" value="Publish to All">
                    </form>
                    <form action="publishers.jsp" method="post">
                        <input type="hidden" value="${item.key}" name="currentList">
                        <input type="submit" value="Select publisher">
                    </form>
        </li>
    </c:forEach>
</ul>
<br/><br/>
Add new List:
<form action="listPage.jsp" method="post">
    Name <input type="text" name="listNameToAdd"><br/>
    <input type="submit" value="Submit">
</form>
</body>
</html>
