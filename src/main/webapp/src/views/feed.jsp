<%--
  Created by IntelliJ IDEA.
  User: caiolopes
  Date: 24/11/24
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:app>
    <jsp:body>
        <div>
            <h4 class="text-start">Feeds</h4>
            <div id="feed">
                <jsp:include page="../components/post.jsp" />
            </div>
        </div>
    </jsp:body>
</t:app>