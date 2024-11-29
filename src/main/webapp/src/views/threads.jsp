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

<t:app name="${user.name}" username="${user.username}">
    <jsp:body>
        <div>
            <h4 class="text-start">Threads</h4>
            <div id="threads" class="mb-3">
                <c:if test="${not empty post}">
                    <jsp:include page="../components/post.jsp">
                        <jsp:param name="username" value="${post.user.username}"/>
                        <jsp:param name="content" value="${post.content}"/>
                        <jsp:param name="postId" value="${post.id}"/>
                        <jsp:param name="showFooter" value="${false}"/>
                    </jsp:include>

                    <div class="mb-3">
                        <h5>Comentar</h5>
                        <form action="${pageContext.request.contextPath}/post?action=add&threadId=${post.id}" method="POST">
                            <div class="mb-2">
                                <textarea class="form-control" id="content" name="content" rows="3"></textarea>
                            </div>
                            <div class="d-flex justify-content-end">
                                <button class="btn btn-primary">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-send" viewBox="0 0 16 16">
                                        <path d="M15.854.146a.5.5 0 0 1 .11.54l-5.819 14.547a.75.75 0 0 1-1.329.124l-3.178-4.995L.643 7.184a.75.75 0 0 1 .124-1.33L15.314.037a.5.5 0 0 1 .54.11ZM6.636 10.07l2.761 4.338L14.13 2.576zm6.787-8.201L1.591 6.602l4.339 2.76z"/>
                                    </svg>
                                    <span>Enviar</span>
                                </button>
                            </div>
                        </form>
                    </div>

                    <c:choose>
                        <c:when test="${not empty post.threads}">
                            <c:forEach var="thread" items="${post.threads}">
                                <jsp:include page="../components/post.jsp">
                                    <jsp:param name="username" value="${thread.user.username}"/>
                                    <jsp:param name="content" value="${thread.content}"/>
                                    <jsp:param name="postId" value="${thread.id}"/>
                                    <jsp:param name="showFooter" value="${true}"/>
                                </jsp:include>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <jsp:include page="../components/post.jsp">
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