<%@tag description="App page layout" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="userId" required="true"%>
<%@attribute name="name" required="true"%>
<%@attribute name="username" required="true"%>

<t:root>
    <jsp:body>
        <div class="container py-5">
            <div class="row justify-content-between">
                <div class="col-3  position-sticky" style="height: fit-content; top: 50px">
                    <div class="d-flex flex-column justify-content-center align-items-center mb-3">
                        <span class="d-flex justify-content-center align-items-center bg-body-secondary rounded-circle" style="width: 64px; height: 64px">${username.charAt(0)}</span>
                        <span>
                            <c:out value="${name}"/>
                        </span>
                        <span>
                            <c:out value="${username}"/>
                        </span>
<%--                        <button id="switchTheme" class="btn">--%>
<%--                            <span class="text-light">--%>
<%--                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-brightness-high" viewBox="0 0 16 16">--%>
<%--                                    <path d="M8 11a3 3 0 1 1 0-6 3 3 0 0 1 0 6m0 1a4 4 0 1 0 0-8 4 4 0 0 0 0 8M8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0m0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13m8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5M3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8m10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0m-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0m9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707M4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708"/>--%>
<%--                                </svg>--%>
<%--                            </span>--%>
<%--                            <span class="text-dark">--%>
<%--                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-moon" viewBox="0 0 16 16">--%>
<%--                                    <path d="M6 .278a.77.77 0 0 1 .08.858 7.2 7.2 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277q.792-.001 1.533-.16a.79.79 0 0 1 .81.316.73.73 0 0 1-.031.893A8.35 8.35 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.75.75 0 0 1 6 .278M4.858 1.311A7.27 7.27 0 0 0 1.025 7.71c0 4.02 3.279 7.276 7.319 7.276a7.32 7.32 0 0 0 5.205-2.162q-.506.063-1.029.063c-4.61 0-8.343-3.714-8.343-8.29 0-1.167.242-2.278.681-3.286"/>--%>
<%--                                </svg>--%>
<%--                            </span>--%>
<%--                        </button>--%>
                    </div>
                    <div class="d-flex flex-column gap-2">
                        <jsp:include page="/components/nav-button.jsp">
                            <jsp:param name="icon" value="feed"/>
                            <jsp:param name="text" value="Postagens"/>
                            <jsp:param name="href" value="/ControllerServlet?action=feed"/>
                        </jsp:include>

                        <jsp:include page="/components/nav-button.jsp">
                            <jsp:param name="icon" value="profile"/>
                            <jsp:param name="text" value="Perfil"/>
                            <jsp:param name="href" value="/ControllerServlet?action=viewPost&username=${username}"/>
                        </jsp:include>

                        <jsp:include page="/components/nav-button.jsp">
                            <jsp:param name="icon" value="search"/>
                            <jsp:param name="text" value="Explorar"/>
                            <jsp:param name="href" value="/ControllerServlet?action=searchUser"/>
                        </jsp:include>

                        <jsp:include page="/components/nav-button.jsp">
                            <jsp:param name="icon" value="signout"/>
                            <jsp:param name="text" value="Desconectar"/>
                            <jsp:param name="href" value="/ControllerServlet?action=signOut"/>
                        </jsp:include>
                    </div>
                </div>
                <div class="col-5">
                    <jsp:doBody/>
                </div>
                <div class="col-3 position-sticky" style="height: fit-content; top: 50px">
                    <div class="card">
                        <h4>Solicitações de amizades</h4>
                        <div id="friend-requests">
                            <div class="spinner-border" role="status">
                                <span class="visually-hidden">Loading...</span>
                            </div>
                        </div>
                    </div>

                    <div class="card">
                        <h4>Amigos</h4>
                        <div id="friends">
                            <div class="spinner-border" role="status">
                                <span class="visually-hidden">Loading...</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:root>
