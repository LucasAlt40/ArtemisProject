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
            <h4 class="text-start">Profile</h4>
            <div class="mb-3">
                <span>
                    <c:out value="${user.username}"/>
                </span>
            </div>
            <div id="feed" class="mb-3">
                <c:choose>
                    <c:when test="${not empty posts}">
                        <c:forEach var="post" items="${posts}">
                            <jsp:include page="components/post.jsp">
                                <jsp:param name="username" value="${post.user.username}"/>
                                <jsp:param name="content" value="${post.content}"/>
                                <jsp:param name="isLiked" value="${post.isLiked}"/>
                                <jsp:param name="likesQuantity" value="${post.likesQuantity}"/>
                                <jsp:param name="commentsQuantity" value="${post.commentsQuantity}"/>
                                <jsp:param name="ownerPostId" value="${post.user.id}"/>
                                <jsp:param name="postId" value="${post.id}"/>
                                <jsp:param name="showFooter" value="${true}"/>
                            </jsp:include>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="components/post.jsp">
                            <jsp:param name="username" value="Artemis"/>
                            <jsp:param name="content" value="Me parece que você acabou de aterrisar! Adicione amigos para começar a interagir. Faça uma postagem agora mesmo!"/>
                            <jsp:param name="showFooter" value="${false}"/>
                        </jsp:include>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </jsp:body>
</t:app>