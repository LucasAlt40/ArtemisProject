<%--
  Created by IntelliJ IDEA.
  User: caiolopes
  Date: 26/11/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="showOptions" value="${param.ownerPostId == user.id}" />

<div id="post" class="card mb-3">
    <div class="card-header bg-secondary-subtle">
        <div class="d-flex justify-content-between">
            <span>
                <c:out value="${param.username}"/>
            </span>
            <c:if test="${showOptions == true}">
                <div class="dropdown">
                    <a class="btn p-0" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-three-dots-vertical" viewBox="0 0 16 16">
                            <path d="M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0"/>
                        </svg>
                    </a>

                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ControllerServlet?action=deletePost&idPost=${param.postId}">Excluir</a></li>
                    </ul>
                </div>
            </c:if>
        </div>
    </div>
    <div class="card-body d-flex flex-column">
        <span class="text-start">
            <c:out value="${param.content}"/>
        </span>
        <c:if test="${not empty param.images}">
            <c:forEach var="image" items="${fn:split(param.images, ',')}">
                <img class="w-100" src="http://10.242.194.182/${fn:replace(fn:replace(image, '[', ''), ']', '')}"  alt="Image"/>
            </c:forEach>
        </c:if>
    </div>
    <c:if test="${param.showFooter}">
        <div class="card-footer">
            <div class="d-flex align-items-end gap-3">
                <jsp:include page="./like-button.jsp">
                    <jsp:param name="isLiked" value="${param.isLiked}"/>
                    <jsp:param name="likesQuantity" value="${param.likesQuantity}"/>
                    <jsp:param name="postId" value="${param.postId}"/>
                </jsp:include>

                <jsp:include page="comment-button.jsp">
                    <jsp:param name="postId" value="${param.postId}"/>
                    <jsp:param name="commentsQuantity" value="${param.commentsQuantity}"/>
                </jsp:include>
            </div>
        </div>
    </c:if>
</div>