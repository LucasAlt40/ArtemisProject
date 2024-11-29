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
            <div class="d-flex gap-3">
                <jsp:include page="./like-button.jsp">
                    <jsp:param name="liked" value="${param.liked}"/>
                </jsp:include>
                <a class="btn p-0" href="${pageContext.request.contextPath}/post?action=viewById&id=${param.postId}">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chat-left-text" viewBox="0 0 16 16">
                        <path d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H4.414A2 2 0 0 0 3 11.586l-2 2V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12.793a.5.5 0 0 0 .854.353l2.853-2.853A1 1 0 0 1 4.414 12H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
                        <path d="M3 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5M3 6a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 6m0 2.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5"/>
                    </svg>
                </a>
            </div>
        </div>
    </c:if>
</div>