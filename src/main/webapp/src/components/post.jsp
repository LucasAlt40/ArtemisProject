<%--
  Created by IntelliJ IDEA.
  User: caiolopes
  Date: 26/11/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div id="post" class="card mb-3">
    <div class="card-header">
        <div class="d-flex justify-content-between">
            <div>
                <span>
                    <c:out value="${param.username}"/>
                </span>
            </div>
        </div>
    </div>
    <div class="card-body">
        <span class="text-start">
            <c:out value="${param.content}"/>
        </span>
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