<%--
  Created by IntelliJ IDEA.
  User: caiolopes
  Date: 28/11/24
  Time: 08:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:app name="${user.name}" username="${user.username}">
    <jsp:body>
        <div>
            <h4 class="text-start">Profile de <c:out value="${user.username}"/></h4>
            <div id="feed" class="mb-3">
                <c:choose>
                    <c:when test="${not empty posts}">
                        <c:forEach var="post" items="${posts}">
                            <jsp:include page="components/post.jsp">
                                <jsp:param name="username" value="${post.authorUsername}"/>
                                ... (outros parâmetros)
                            </jsp:include>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">
                            Nenhum post encontrado. Comece a compartilhar suas ideias!
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </jsp:body>
</t:app>