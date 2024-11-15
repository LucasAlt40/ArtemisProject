<%--
  Created by IntelliJ IDEA.
  User: Eduardo
  Date: 15/11/2024
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>POST</title>
</head>
<body>
    <p>${post.getPostDate()}</p>

    <ul>
        <c:forEach var="post" items="${posts}">
           <li style="color: blue">
               <p>${post.getPostDate()}</p>
               <p>${post.getContent()}</p>
               <p>thread</p>
               <ol style="color: red">
                   <c:forEach var="thread" items="${post.getThreads()}">
                       <li>
                           <p>${thread.getPostDate()}</p>
                           <p>${thread.getContent()}</p>
                       </li>
                   </c:forEach>
               </ol>
           </li>
        </c:forEach>
    </ul>

</body>
</html>
