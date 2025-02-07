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

<t:app name="${user.name}" username="${user.username}" userId="${user.id}">
    <jsp:body>
        <div>
            <div class="sticky-top bg-dark mb-3 py-4">
                <div class="d-flex align-items-center gap-2">
                    <span class="d-flex justify-content-center align-items-center bg-body-secondary rounded-circle" style="width: 64px; height: 64px">
                        <c:out value="${userPost.username.charAt(0)}"/>
                    </span>
                    <div>
                        <h4 class="text-start"><c:out value="${userPost.username}"/></h4>
                        <div>
                            <c:choose>
                                <c:when test="${userPost.friendsQuantity > 0}">
                                    <span><c:out value="${userPost.friendsQuantity}"/></span>
                                </c:when>
                                <c:otherwise>
                                    <span>0</span>
                                </c:otherwise>
                            </c:choose>
                            <span>amigo(s)</span>
                        </div>
                    </div>
                </div>
            </div>
            <div id="feed" class="mb-3">
                <c:choose>
                    <c:when test="${not empty posts}">
                        <c:forEach var="post" items="${posts}">
                            <jsp:include page="./components/post.jsp">
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
                        <jsp:include page="./components/post.jsp">
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