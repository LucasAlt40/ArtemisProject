<%--
  Created by IntelliJ IDEA.
  User: caiolopes
  Date: 29/11/24
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="isComment" value="${not empty param.threadId}" />

<form action="${pageContext.request.contextPath}/ControllerServlet" method="POST">
    <div class="mb-2 position-relative">
        <input type="hidden" name="threadId" value="${param.threadId}">
        <textarea class="form-control" id="content" name="content" rows="5" placeholder="Escreva alguma coisa..."></textarea>
        <button class="btn btn-primary btn-sm position-absolute" style="bottom: 10px; right: 10px" name="action" value="addPost">
            <span>Enviar</span>
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-send" viewBox="0 0 16 16">
                <path d="M15.854.146a.5.5 0 0 1 .11.54l-5.819 14.547a.75.75 0 0 1-1.329.124l-3.178-4.995L.643 7.184a.75.75 0 0 1 .124-1.33L15.314.037a.5.5 0 0 1 .54.11ZM6.636 10.07l2.761 4.338L14.13 2.576zm6.787-8.201L1.591 6.602l4.339 2.76z"/>
            </svg>
        </button>
    </div>
</form>