<%--
  Created by IntelliJ IDEA.
  User: caiolopes
  Date: 26/11/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:app name="${user.name}" username="${user.username}" userId="${user.id}">
    <jsp:body>
        <div>
            <h4 class="text-start">Postagem</h4>
            <div id="threads" class="mb-3">
                <c:if test="${not empty post}">
                    <jsp:include page="components/post.jsp">
                        <jsp:param name="username" value="${post.user.username}"/>
                        <jsp:param name="content" value="${post.content}"/>
                        <jsp:param name="isLiked" value="${post.isLiked}"/>
                        <jsp:param name="likesQuantity" value="${post.likesQuantity}"/>
                        <jsp:param name="commentsQuantity" value="${post.commentsQuantity}"/>
                        <jsp:param name="postId" value="${post.id}"/>
                        <jsp:param name="showFooter" value="${true}"/>
                    </jsp:include>

                    <div class="mb-3">
                        <h5>Comentários</h5>
                        <jsp:include page="components/post-form.jsp">
                            <jsp:param name="threadId" value="${post.id}"/>
                        </jsp:include>
                    </div>

                    <c:choose>
                        <c:when test="${not empty post.threads}">
                            <c:forEach var="thread" items="${post.threads}">
                                <jsp:include page="components/post.jsp">
                                    <jsp:param name="username" value="${thread.user.username}"/>
                                    <jsp:param name="content" value="${thread.content}"/>
                                    <jsp:param name="isLiked" value="${thread.isLiked}"/>
                                    <jsp:param name="likesQuantity" value="${thread.likesQuantity}"/>
                                    <jsp:param name="commentsQuantity" value="${thread.commentsQuantity}"/>
                                    <jsp:param name="postId" value="${thread.id}"/>
                                    <jsp:param name="showFooter" value="${true}"/>
                                </jsp:include>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <jsp:include page="components/post.jsp">
                                <jsp:param name="username" value="Artemis"/>
                                <jsp:param name="content" value="Nenhuma comentário até o momento, seja o primeiro a comentar!"/>
                                <jsp:param name="showFooter" value="${false}"/>
                            </jsp:include>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
        </div>
    </jsp:body>
</t:app>