<%--
  Created by IntelliJ IDEA.
  User: caiolopes
  Date: 06/02/25
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div id="post" class="card p-2 mb-3">
    <div class="d-flex justify-content-between align-items-center">
        <div class="d-flex align-items-center gap-2">
        <span class="d-flex justify-content-center align-items-center bg-body-secondary rounded-circle" style="width: 64px; height: 64px">
            <c:out value="${param.username.charAt(0)}"/>
        </span>
            <span>
            <c:out value="${param.username}"/>
        </span>
        </div>
        <button class="btn d-flex align-items-center gap-2 send-request" data-user-received-id="${param.userId}">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-user-round-plus">
                <path d="M2 21a8 8 0 0 1 13.292-6"/>
                <circle cx="10" cy="8" r="5"/>
                <path d="M19 16v6"/>
                <path d="M22 19h-6"/>
            </svg>
        </button>
    </div>
</div>