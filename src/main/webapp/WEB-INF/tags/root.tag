<%@tag description="Root page layout" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<!DOCTYPE html>
<html lang="pt-br" data-bs-theme="dark">
    <header>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/global.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css" />
    </header>
    <body>
        <main id="body">
            <jsp:doBody/>
        </main>
        <script defer src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script defer src="${pageContext.request.contextPath}/js/theme.js"></script>
        <script defer src="${pageContext.request.contextPath}/js/formValidation.js"></script>
        <script defer src="${pageContext.request.contextPath}/js/likePost.js"></script>
    </body>
</html>