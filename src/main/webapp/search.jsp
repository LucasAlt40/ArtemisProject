<%--
  Created by IntelliJ IDEA.
  User: caiolopes
  Date: 06/02/25
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:app name="${user.name}" username="${user.username}" userId="${user.id}">
    <jsp:body>
        <div>
            <h4 class="text-start">Explorar</h4>
            <form class="d-flex mb-3" role="search" action="${pageContext.request.contextPath}/ControllerServlet">
                <input class="form-control me-2" type="search" name="searchTerm" placeholder="Pesquisar usuários" aria-label="Search">
                <button class="btn btn-outline-primary" type="submit" name="action" value="searchUser">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-search">
                        <circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/>
                    </svg>
                </button>
            </form>

            <div id="results" class="mb-3">
                <c:choose>
                    <c:when test="${not empty results}">
                        <c:forEach var="user" items="${results}">
                            <jsp:include page="components/user-card.jsp">
                                <jsp:param name="username" value="${user.username}"/>
                                <jsp:param name="userId" value="${user.id}"/>
                            </jsp:include>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="components/post.jsp">
                            <jsp:param name="username" value="Artemis"/>
                            <jsp:param name="content" value="Nenhum usuário encontrado"/>
                            <jsp:param name="showFooter" value="${false}"/>
                        </jsp:include>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </jsp:body>
</t:app>

